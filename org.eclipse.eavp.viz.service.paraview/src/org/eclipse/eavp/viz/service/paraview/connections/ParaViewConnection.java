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
package org.eclipse.eavp.viz.service.paraview.connections;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.eavp.viz.service.connections.IVizConnection;
import org.eclipse.eavp.viz.service.connections.VizConnection;
import org.eclipse.eavp.viz.service.paraview.web.HttpParaViewWebClient;
import org.eclipse.eavp.viz.service.paraview.web.IParaViewWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * Provides an {@link IVizConnection} for connecting to
 * {@link IParaViewWebClient}s. This connection specifically uses the
 * {@link HttpParaViewWebClient} implementation for the web client.
 * 
 * @author Jordan Deyton
 *
 */
public class ParaViewConnection extends VizConnection<IParaViewWebClient> {

	/**
	 * Logger for handling event messages and other information.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ParaViewConnection.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.eavp.viz.service.connections.VizConnection#connectToWidget()
	 */
	@Override
	protected IParaViewWebClient connectToWidget() {

		// Set the default return value.
		IParaViewWebClient client = null;

		// The OS specific string that describes the path to ParaView the base
		// ParaView directory given the ParaView installation path.
		String osPath = "";

		// Check the operating system and set the path accordingly.
		// TODO Add support for windows here
		String OS = System.getProperty("os.name", "generic")
				.toLowerCase(Locale.ENGLISH);
		if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {

			// For Mac, go inside the application's contents
			osPath = "/paraview.app/Contents";
		}

		// Get the properties for the paraview python command line
		String path = getPath();
		String host = getHost();
		String port = Integer.toString(getPort());
		String serverPath = getProperty("serverPath");
		String visualizerPort = getProperty("visualizerPort");

		// The system delimiter for directories
		String delimiter = System.getProperty("file.separator");

		// If the path ends with the delimiter, remove it
		if (path.endsWith(delimiter)) {
			path = path.substring(0, path.length() - 1);
		}

		// If the server path ends with the delimiter, remove it
		if (serverPath.endsWith(delimiter)) {
			serverPath = serverPath.substring(0, path.length() - 1);
		}

		try {

			// Check the host name to see if this is a local launch
			InetAddress hostAddr = InetAddress.getByName(host);
			if (hostAddr.isAnyLocalAddress() || hostAddr.isLoopbackAddress() || NetworkInterface.getByInetAddress(hostAddr) != null) {

				// Get the system name
				String os = System.getProperty("os.name", "generic")
						.toLowerCase(Locale.ENGLISH);

				// Get the specified directory
				File dir = new File(path);

				// If the directory exists, search inside it
				if (dir.exists()) {

					// Check inside the directory, looking for ParaView. The
					// ParaView
					// directory will have different structures on different
					// operation systems, requiring that different places be
					// checked in each case. If the file cannot be found, give
					// an error message
					if ((os.indexOf("mac") >= 0)
							|| (os.indexOf("darwin") >= 0)) {
						if (!new File(
								dir + "/paraview.app/Contents/bin/pvpython")
										.exists()) {
							addErrorMessage(
									"Could not find ParaView in directory \""
											+ dir + "\".");
						}
					} else if (os.indexOf("win") >= 0) {
						// TODO Specify where pvpython is inside a Windows
						// install
					} else if (os.indexOf("nux") >= 0) {
						if (!new File(dir + "/bin/pvpython").exists()) {
							addErrorMessage(
									"Could not find VisIt in directory \"" + dir
											+ "\".");
						}
					}
				} else {
					// If the directory doesn't exist, give an error message
					addErrorMessage(
							"Could not find directory \"" + dir + "\".");
				}

				// Get the directory containing the server Python script
				File serverDir = new File(serverPath);

				// Check that the server directory exists
				if (serverDir.exists()) {

					// Check if the server script can be found and give an error
					// message if not
					if (!new File(serverDir + "/http_pvw_server.py").exists()) {
						addErrorMessage(
								"Could not find http_pvw_server.py in \""
										+ serverPath + "\".");
					}
				} else {

					// If the server directory does no exist, give an error
					// message
					addErrorMessage(
							"Could not find directory \"" + serverDir + "\".");
				}

				// A test socket for checking the port
				Socket s = null;
				try {

					// Try to open a port to the given number
					s = new Socket("localhost", Integer.parseInt(port));

					// If something responded, then the port is already in use.
					addErrorMessage("Port number " + port + " is in use.");
				} catch (NumberFormatException | IOException e) {
					// We expect an IOException here if the port is not
					// currently being used, so there is nothing to do
				} finally {

					// Close the socket, if one was made
					if (s != null) {
						try {
							s.close();
						} catch (IOException e) {
							logger.error(
									"Error while closing the test socket.");
						}
					}
				}

				try {

					// Try to open a port to the visualizer's port number
					s = new Socket("localhost",
							Integer.parseInt(visualizerPort));

					// If something responded, then the port is already in use.
					addErrorMessage(
							"Port number " + visualizerPort + " is in use.");
				} catch (NumberFormatException | IOException e) {
					// We expect an IOException here if the port is not
					// currently being used, so there is nothing to do
				} finally {

					// Close the socket, if one was made
					if (s != null) {
						try {
							s.close();
						} catch (IOException e) {
							logger.error(
									"Error while closing the test socket.");
						}
					}
				}
				
				ProcessBuilder serverBuilder = new ProcessBuilder(
						path + osPath + "/bin/pvpython",
						getProperty("serverPath") + "/http_pvw_server.py", "--host",
						host, "--port", port);

				// Redirect the process's error stream to its output stream so we
				// only have to deal with one
				final Process process = serverBuilder.redirectErrorStream(true)
						.start();

				// Create a thread to consume the process's output ourselves, or
				// else the process will freeze once its IO buffer is full.
				new Thread(new Runnable() {

					@Override
					public void run() {

						// While the process is alive, keep reading and discarding
						// its output
						while (process.isAlive()) {
							try {
								process.getInputStream().read();
							} catch (IOException e) {
								logger.error(
										"Error while handling ParaView process's "
												+ "output stream.");
							}
						}
					}
				}).start();

			}
			
			//Otherwise, if the host is a valid remote machine, try to launch paraview on it
			else {

				 //If a username is not specified, assume that it is the same as the one used on the current machine
			     String username = System.getProperty("user.name");
			     
//			        proxyInfo[0] = username;
//			        proxyInfo[1] = host;
//
//			        if (host.contains("@")) {
//			            proxyInfo = host.split("@");
//			        }
			        
					String mGateway = host;
					String mGatewayUser = "";

					if (mGateway.indexOf("@") > 0) {
						mGatewayUser = username;
						mGateway = host.substring(host.indexOf("@"));
					}

					//TODO Let the user choose this port
					int mGatewayPort = 22;
			        
					UserInfo ui = new UserInfo(){

						@Override
						public String getPassphrase() {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public String getPassword() {
							// TODO Auto-generated method stub
							return null;
						}

						@Override
						public boolean promptPassword(String message) {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public boolean promptPassphrase(String message) {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public boolean promptYesNo(String message) {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public void showMessage(String message) {
							// TODO Auto-generated method stub
							
						}
						
					};
					
			        try {
						Session gateway = new JSch().getSession(mGatewayUser.length() == 0 ? username
						        : mGatewayUser, mGateway);
						
						new JSch()
						
						gateway.setUserInfo(ui);
						gateway.connect();

						// forward ssh to mGatewayPort..
						gateway.setPortForwardingL(mGatewayPort, host, 22);
						
						Session session;
						

							// connect to localhost
							session = new JSch().getSession(mGatewayUser, "localhost", mGatewayPort);
							session.setUserInfo(ui);
							session.connect();
							session.setPortForwardingL(Integer.parseInt(port), "localhost", Integer.parseInt(port));

						
						ChannelExec channel = (ChannelExec) session.openChannel("exec");

						String commandString = path + "/bin/pvpython " +
						getProperty("serverPath") + " /http_pvw_server.py " + "--host " +
						host + " --port " + port;
						
						channel.setCommand(commandString);
						channel.setInputStream(System.in, true);

						BufferedReader input = new BufferedReader(
								new InputStreamReader(channel.getExtInputStream()));

						channel.connect();
						

					} catch (JSchException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		} catch (IOException e) {

			// If a problem occurred while trying to resolve the host name, warn
			// the user
			addErrorMessage(
					"Could not find server with host name \"" + host + "\"");
		}

		// Try to create and connect to a ParaView web client.
		boolean connected = false;
		try {

			// Create an HTTP implementation of the ParaView web client..
			client = new HttpParaViewWebClient();
			// Set up the HTTP URL
			String url = "http://" + host + ":" + port + "/rpc-http/";

			// Try to connect.
			connected = client.connect(url).get(1, TimeUnit.MINUTES);

		} catch (InterruptedException e) {
			logger.error(
					"Error occurred while attempting to connect to ParaView client.");
		} catch (ExecutionException e) {
			logger.error(
					"Error occurred while attempting to connect to ParaView client.");
		} catch (TimeoutException e) {

			// The connection timed out
			connected = false;
		}

		// If the connection was not successful, we should return null.
		if (!connected) {
			client = null;
		}

		return client;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.eavp.viz.service.connections.VizConnection#
	 * disconnectFromWidget(java.lang.Object)
	 */
	@Override
	protected boolean disconnectFromWidget(IParaViewWebClient widget) {
		boolean closed = false;
		// Attempt to disconnect, returning the success of the operation.
		if (widget != null) {
			try {
				closed = widget.disconnect().get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return closed;
	}

}
