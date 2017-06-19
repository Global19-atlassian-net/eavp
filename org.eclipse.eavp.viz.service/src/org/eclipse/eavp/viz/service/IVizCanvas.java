/*******************************************************************************
 * Copyright (c) 2015 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jay Jay Billings - Initial API and implementation and/or initial documentation
 *   Jordan Deyton - changed signature of draw to return Composite
 *   Alex McCaskey - added redraw method
 *   Kasper Gammeltoft - viz series refactor (extracted IVizCanvas from IPlot)
 *   
 *******************************************************************************/

package org.eclipse.eavp.viz.service;

import java.net.URI;
import java.util.Map;

import org.eclipse.eavp.viz.service.drawhandler.IDrawHandler;
import org.eclipse.january.geometry.Geometry;

/**
 * This interface defines the principle type for canvases created and handled by
 * the platform. Its primary purpose is to provide a specific interface for
 * manipulating canvases and analysis data that is separated in purpose and
 * scope from the IVizService (perhaps completely separate, in fact).
 * <p>
 * When implemented in tandem with an IVizService, the class that realizes this
 * interface should encapsulate all details related to the internal workings of
 * the IVizService and refrain from exposing them to the client. For example,
 * service ids, credentials, window ids and other information should be stored
 * as private variables and not exposed in the properties map unless absolutely
 * necessary.
 * </p>
 * <p>
 * A proper implementation of IVizCanvas is one that can be used in standalone
 * tools, workbench-based tools, and embedded in composites in larger, existing
 * tools. Implementations should not be restricted to one usage scenario.
 * </p>
 * 
 * @author Jay Jay Billings
 * @author Alex McCaskey
 * @author Kasper Gammeltoft
 * @author Robert Smith
 *
 */
public interface IVizCanvas {

	/**
	 * This operation draws the canvas contents onto the draw handler's parent.
	 * The exact details of how the plot is drawn and what is drawn inside the
	 * parent composite are left completely up to the implementation.
	 * 
	 * After drawing, the resulting graphical object containing the canvas can
	 * be retrieved by invoking getResult() on the canvas's draw handler.
	 * 
	 * @throws Exception
	 */
	public void draw() throws Exception;

	/**
	 * This operation returns the data source that is plotted/drawn by this
	 * IPlot.
	 * 
	 * @return The data source
	 */
	public URI getDataSource();

	/**
	 * This operation returns the number of axes of the plot.
	 * 
	 * @return The number of axes or zero if the plot has not been drawn
	 */
	public int getNumberOfAxes();

	/**
	 * This operation returns properties of this IPlot that can be safely
	 * modified and/or tuned by the client. These properties should contains
	 * things such as, for example, the axis labels, the title, the subtitle,
	 * etc.
	 * 
	 * @return A map of the properties.
	 */
	public Map<String, String> getProperties();

	/**
	 * Get the list of render elements used to display the given geometry in
	 * this canvas.
	 * 
	 * @param geometry
	 *            The geometry whose corresponding IRenderElements are sought.
	 * @return An array list of each of the top level IRenderElements in the
	 *         hierarchy, corresponding to the geometry's nodes list from
	 *         getNodes()
	 */
	public IRenderElementHolder getRenderElementHolder(Geometry geometry);

	/**
	 * Getter method for the canvas's draw handler.
	 * 
	 * @return The draw handler being used to perform drawing operations.
	 */
	public IDrawHandler getDrawHandler();

	/**
	 * Get the result of the draw() operation, which is a UI element specific to
	 * the implementation of the windowing system for the DrawHandler.
	 * 
	 * @param resultType
	 *            The expected type of the drawn result.
	 * @return The result of the draw() operation, or null if it is not
	 *         available or of the wrong type.
	 */
	public <T> T getResult(Class<T> resultType);

	/**
	 * This operation retrieves the hostname for this IPlot's data source.
	 * 
	 * @return the hostname
	 */
	public String getSourceHost();

	/**
	 * This operation signifies whether or not the IPlot's host is local or
	 * remote.
	 * 
	 * @return True if the source is on a remote machine, false otherwise
	 */
	public boolean isSourceRemote();

	/**
	 * This operation directs the IPlot to redraw its contents. This can be
	 * invoked when, for example, the data represented by this IPlot changes,
	 * and a change in the IPlot's UI must change accordingly.
	 * 
	 * The exact details of how this IPlot is redrawn after a valid draw is left
	 * completely to the implementation.
	 * 
	 */
	public void redraw();

	/**
	 * Set the IDrawHandler which will perform the drawing for this canvas.
	 * 
	 * @param handler
	 *            The new handler to handle drawing operations for the canvas.
	 */
	public void setDrawHandler(IDrawHandler handler);

	/**
	 * This operation updates the properties of the plot based on client-side
	 * modifications. The IPlot should redraw itself as needed if the properties
	 * changed.
	 * 
	 * @param props
	 *            The updated properties
	 * @throws Exception
	 *             This exception indicates that the IPlot could not update its
	 *             properties or redraw itself.
	 */
	public void setProperties(Map<String, String> props) throws Exception;

}
