/*******************************************************************************
 * Copyright (c) 2015 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jordan Deyton - Initial API and implementation and/or initial documentation
 *   
 *******************************************************************************/
package org.eclipse.eavp.viz.service.connections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides a basic implementation of {@link IVizConnectionManager},
 * which is responsible for synchronizing {@link IVizConnection}s with Eclipse
 * preferences.
 * 
 * @author Jordan Deyton
 *
 * @param <T>
 *            The type of the underlying connection widget.
 */
public abstract class VizConnectionManager<T>
		implements IVizConnectionManager<T> {

	/**
	 * Logger for handling event messages and other information.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(VizConnectionManager.class);

	/**
	 * A map of the viz connections keyed on their names, which should all be
	 * unique.
	 */
	protected final Map<String, VizConnection<T>> connectionsByName;

	/**
	 * A map of the viz connection names keyed on the hosts. Multiple
	 * connections can be configured per host.
	 */
	protected final Map<String, Set<String>> connectionsByHost;

	/**
	 * A map of connection names to connections, for those connections which
	 * have been previously opened in this session then removed.
	 */
	private final Map<String, VizConnection<T>> oldConnections;
	/**
	 * The ID of the preference node under which connection information will be
	 * stored.
	 */
	protected String connectionsNodeId;

	/**
	 * The default constructor.
	 */
	public VizConnectionManager() {
		// Create the maps.
		connectionsByName = new HashMap<String, VizConnection<T>>();
		connectionsByHost = new HashMap<String, Set<String>>();
		oldConnections = new HashMap<String, VizConnection<T>>();
	}

	/**
	 * Adds a new connection based on the specified name and preference value.
	 * The connection will attempt to connect.
	 * 
	 * @param name
	 *            The name of the connection (a preference name in the store).
	 * @param preferences
	 *            The preference value for the connection. This value should
	 *            come straight from the {@link #preferenceStore}.
	 * 
	 * @return The Future state of the connection being added.
	 */
	@Override
	public Future<ConnectionState> addConnection(String name,
			String preferences) {
		logger.debug("VizConnectionManager message: " + "Adding connection \""
				+ name + "\" using the preference string \"" + preferences
				+ "\".");

		// Update the connection if it already exists
		if (connectionsByName.containsKey(name)) {
			return updateConnection(name, preferences);
		}

		// The connection to be added
		VizConnection<T> connection = null;

		if (!oldConnections.keySet().contains(name)) {

			// If a connection by the given name does not exist, create one
			connection = createConnection(name, preferences);
		} else {

			// If this is a recognized connection being re-added to the table
			// after being removed, retrieve the old reference from the map.
			connection = oldConnections.get(name);
		}

		// Split the string using the delimiter. The -1 is necessary to include
		// empty values from the split.
		String[] split = preferences.split(getConnectionPreferenceDelimiter(),
				-1);

		// A future reference to the connection's state after the attempted
		// operation is completed
		Future<ConnectionState> state = null;

		try {

			// Ensure the connection's basic preferences are set.
			connection.setName(name);
			connection.setHost(split[0]);
			connection.setPort(Integer.parseInt(split[1]));
			connection.setPath(split[2]);

			// Add the connection to the map of connections by name.
			connectionsByName.put(name, connection);

			// Add the connection to the map of connections by host.
			String host = connection.getHost();
			Set<String> connections = connectionsByHost.get(host);
			// If necessary, create a new set for the connection's host.
			if (connections == null) {
				connections = new HashSet<String>();
				connectionsByHost.put(host, connections);
			}
			connections.add(name);

			// Try to connect.
			state = connection.connect();

		} catch (IndexOutOfBoundsException | NullPointerException
				| NumberFormatException e) {
			// Cannot add the connection.
		}

		return state;
	}

	/**
	 * Creates a viz connection instance based on the name and the preference
	 * value from the preference store.
	 * <p>
	 * The name and preferences are provided as a convenience in case additional
	 * preferences besides the name, host, port, and path are required. After
	 * this method is called, the host, port, and path will be pulled from the
	 * preference string using the delimiter provided by
	 * {@link #getConnectionPreferenceDelimiter()} , if possible. Additional
	 * preferences should be located in the preference string <i>after</i> these
	 * three required preferences.
	 * </p>
	 * 
	 * @param name
	 *            The name of the connection.
	 * @param preferences
	 *            The preference string from the store.
	 * @return A new viz connection instance using the provided name and
	 *         preferences, or {@code null} if the properties could not be
	 *         sufficiently read from the string to create a connection.
	 */
	protected abstract VizConnection<T> createConnection(String name,
			String preferences);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.eavp.viz.service.connections.IVizConnectionManager#
	 * getConnection(java.lang.String)
	 */
	@Override
	public IVizConnection<T> getConnection(String name) {
		return connectionsByName.get(name);
	}

	/**
	 * Gets the delimiter used to separate a connection's individual
	 * preferences.
	 * 
	 * @return The string delimiter for connection preferences.
	 */
	protected String getConnectionPreferenceDelimiter() {
		return DEFAULT_CONNECTION_PREFERENCE_DELIMITER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.eavp.viz.service.connections.IVizConnectionManager#
	 * getConnections()
	 */
	@Override
	public Set<String> getConnections() {
		return new TreeSet<String>(connectionsByName.keySet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.eavp.viz.service.connections.IVizConnectionManager#
	 * getConnectionsForHost(java.lang.String)
	 */
	@Override
	public Set<String> getConnectionsForHost(String host)
			throws NullPointerException {
		// Throw an exception if the specified host name is null.
		if (host == null) {
			throw new NullPointerException("VizConnectionManager error: "
					+ "Cannot find connections for null host name.");
		}
		// Get the associated connection names. If the host is not recognized,
		// return an empty set.
		Set<String> connections = connectionsByHost.get(host);
		return connections != null ? new TreeSet<String>(connections)
				: new TreeSet<String>();
	}

	/**
	 * Removes a connection based on the specified name. The connection will be
	 * disconnected.
	 * 
	 * @param name
	 *            The name of the connection to remove.
	 */
	@Override
	public void removeConnection(String name) {
		logger.debug("VizConnectionManager message: " + "Removing connection \""
				+ name + "\".");

		// Get the specified connection
		VizConnection<T> connection = connectionsByName.remove(name);

		// Store the connection in the map of old connections
		oldConnections.put(name, connection);

		// Disconnect
		connection.disconnect();

		// Remove the connection from the map of connections by host.
		String host = connection.getHost();
		Set<String> connections = connectionsByHost.get(host);
		connections.remove(name);
		// If there are no more connections for the host, remove the host.
		if (connections.isEmpty()) {
			connectionsByHost.remove(host);
		}

		return;
	}

	/**
	 * Updates the properties for a connection based on the specified name and
	 * preference value. If necessary, the connection may be reset.
	 * 
	 * @param name
	 *            The name of the connection (a preference name in the store).
	 * @param preferences
	 *            The <i>new</i> preference value for the connection. This value
	 *            should come straight from the {@link #preferenceStore}.
	 * @return A future reference to the connection state after the update
	 */
	protected Future<ConnectionState> updateConnection(String name,
			String preferences) {
		logger.debug("VizConnectionManager message: " + "Updating connection \""
				+ name + "\" using the preference string \"" + preferences
				+ "\".");

		final VizConnection<T> connection = connectionsByName.get(name);

		// Get the current host for the connection.
		String oldHost = connection.getHost();

		// Update the connection's preferences.
		boolean requiresReset = updateConnectionPreferences(connection,
				preferences);

		// If the host changed, we need to update the connections-by-host map.
		String newHost = connection.getHost();
		if (!oldHost.equals(newHost)) {
			// Dissociate the connection from the old host, deleting the map
			// entry for the old host if it has no more associated connections.
			Set<String> hosts = connectionsByHost.get(oldHost);
			hosts.remove(name);
			if (hosts.isEmpty()) {
				connectionsByHost.remove(oldHost);
			}
			// Associate the connection with the new host, creating the map
			// entry for the new host if it had no associated connections.
			hosts = connectionsByHost.get(newHost);
			if (hosts == null) {
				hosts = new HashSet<String>();
				connectionsByHost.put(newHost, hosts);
			}
			hosts.add(name);
		}

		// If the update requires a reset, reset the connection.
		if (requiresReset) {

			final Future<ConnectionState> disconnectRequest = connection
					.disconnect();
			final ExecutorService executor = Executors
					.newSingleThreadExecutor();
			try {
				return executor.submit(new Callable<Future<ConnectionState>>() {

					@Override
					public Future<ConnectionState> call() throws Exception {
						try {
							disconnectRequest.get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
						// Try to re-connect.
						Future<ConnectionState> state = connection.connect();

						// Stop the executor service.
						executor.shutdown();

						return state;
					}
				}).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// If there was no update, return the current connection state

		// Get a final reference to the current connection state
		final ConnectionState oldState = connection.getState();

		return new Future<ConnectionState>() {
			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				return false;
			}

			@Override
			public ConnectionState get()
					throws InterruptedException, ExecutionException {
				return oldState;
			}

			@Override
			public ConnectionState get(long timeout, TimeUnit unit)
					throws InterruptedException, ExecutionException,
					TimeoutException {
				return oldState;
			}

			@Override
			public boolean isCancelled() {
				return false;
			}

			@Override
			public boolean isDone() {
				return true;
			}
		};
	}

	/**
	 * Gets each connection property from the string of preferences, if
	 * possible, and updates the connection based on them.
	 * <p>
	 * <b>Note:</b> If overridden, it is recommended to call the super method so
	 * that the host, port, and path will be updated. This method will return
	 * true if any of those three properties change.
	 * </p>
	 * 
	 * @param connection
	 *            The connection whose preferences are being updated.
	 * @param preferences
	 *            The serialized string of preferences.
	 * 
	 * @return True if one of the properties changed and a reset of the
	 *         connection is required, false if a reset is <i>not</i> required.
	 */
	protected boolean updateConnectionPreferences(VizConnection<T> connection,
			String preferences) {
		boolean requiresReset = false;

		// Split the string using the delimiter. The -1 is necessary to include
		// empty values from the split.
		String[] split = preferences.split(getConnectionPreferenceDelimiter(),
				-1);

		try {
			// Get the host, port, and path, if possible.
			String host = split[0];
			int port = Integer.parseInt(split[1]);
			String path = split[2];

			// If any of these change, the connection will need to be reset.
			requiresReset |= connection.setHost(host);
			requiresReset |= connection.setPort(port);
			requiresReset |= connection.setPath(path);
		} catch (IndexOutOfBoundsException | NullPointerException
				| NumberFormatException e) {
			// Cannot update the connection.
			requiresReset = false;
		}

		return requiresReset;
	}
}
