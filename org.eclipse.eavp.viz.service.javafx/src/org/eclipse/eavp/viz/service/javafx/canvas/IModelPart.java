/*******************************************************************************
 * Copyright (c) 2015 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Tony McCrary (tmccrary@l33tlabs.com), Robert Smith
 *******************************************************************************/
package org.eclipse.eavp.viz.service.javafx.canvas;

import java.util.List;

import org.eclipse.eavp.viz.service.modeling.AbstractController;

/**
 * <p>
 * Interface for accepting and working with ICE modeling parts.
 * </p>
 * 
 * @author Tony McCrary (tmccrary@l33tlabs.com), Robert Smith
 *
 */
public interface IModelPart {

	/**
	 * <p>
	 * Adds a Geometry instance to this entity.
	 * </p>
	 * 
	 * @param geom
	 *            an ICE Geometry instance
	 */
	public void addGeometry(AbstractController geom);

	/**
	 * <p>
	 * Adds an IShape to this entity.
	 * </p>
	 * 
	 * @param shape
	 *            an ICE IShape instance
	 */
	public void addShape(AbstractController shape);

	/**
	 * Removes a part from the list of top level parts in the model.
	 * 
	 * @param geom
	 *            The top level node to be removed.
	 */
	public void removeGeometry(AbstractController geom);

	/**
	 * <p>
	 * Removes the supplied IShape from this entity.
	 * </p>
	 * 
	 * @param shape
	 *            the ICE IShape to remove
	 */
	public void removeShape(AbstractController shape);

	/**
	 * <p>
	 * Returns true if the entity contains the supplied IShape, false otherwise.
	 * </p>
	 * 
	 * @param the
	 *            ICE IShape to test for
	 * 
	 * @return true if the entity contains the supplied IShape, false otherwise.
	 */
	public boolean hasShape(AbstractController shape);

	/**
	 * <p>
	 * Returns the IShape at the specified index or null if it cannot be found.
	 * </p>
	 * 
	 * @param index
	 *            the index to retrieve the IShape at
	 * 
	 * @return an IShape instance or null if one cannot be found
	 */
	public AbstractController getShape(int index);

	/**
	 * <p>
	 * Returns a list of the shapes associated with this entity.
	 * </p>
	 * 
	 * <p>
	 * Optionally, a copy can be made of the list.
	 * </p>
	 * 
	 * @param copy
	 *            if true, the returned list will be a copy
	 * 
	 * @return a List of IShapes associated with this shape.
	 */
	public List<AbstractController> getShapes(boolean copy);

	/**
	 * <p>
	 * Removes all shapes associated with this entity.
	 * </p>
	 */
	public void clearShapes();

	/**
	 * <p>
	 * Sets the entity to be immutable, which means it's values cannot be
	 * changed (no new geometry or shapes).
	 * </p>
	 * 
	 * @param immutable
	 *            if true, the entity will be made immutable otherwise the
	 *            entity will be mutable
	 */
	void setImmutable(boolean immutable);

	/**
	 * <p>
	 * Returns true if the entity is immutable, false otherwise.
	 * </p>
	 */
	boolean isImmutable();

	/**
	 * <p>
	 * Sets the entity to be visible or not visible in the scene.
	 * </p>
	 * 
	 * @param visible
	 *            if true, the entity will be made visible otherwise the entity
	 *            will not be visible
	 */
	void setVisible(boolean visible);

	/**
	 * <p>
	 * Returns true if the entity is visible, false otherwise.
	 * </p>
	 */
	boolean isVisible();

}
