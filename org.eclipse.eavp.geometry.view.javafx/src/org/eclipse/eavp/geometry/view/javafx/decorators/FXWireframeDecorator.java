/*******************************************************************************
 * Copyright (c) 2016 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Robert Smith
 *******************************************************************************/
package org.eclipse.eavp.geometry.view.javafx.decorators;

import org.eclipse.emf.common.notify.Notification;

import geometry.GeometryPackage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import model.impl.WireframeDecoratorImpl;

/**
 * A decorator for FXRenderObjects that will allow them to be drawn in wireframe
 * mode.
 * 
 * @author Robert Smith
 *
 */
public class FXWireframeDecorator extends WireframeDecoratorImpl<Group> {

	/**
	 * Set all the MeshViews in the group to the given draw mode, as well as the
	 * children of any child groups.
	 * 
	 * @param group
	 *            The group whose children will have their draw modes set.
	 * @param mode
	 *            The mode to be set to the group's children.
	 */
	private void setMode(Group group, DrawMode mode) {

		// Handle each of the group's children
		for (Node node : group.getChildren()) {

			// If the node is a mesh view, set its material
			if (node.getClass() == MeshView.class) {
				((MeshView) node).setDrawMode(mode);
			}

			// Otherwise, recursively handle the child group
			else if (node.getClass() == Group.class) {
				((MeshView) node).setDrawMode(mode);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.impl.RenderObjectDecoratorImpl#getMesh()
	 */
	@Override
	public Group getMesh() {

		// Get the source's group
		Group group = source.getMesh();

		// Set the group's children to the correct drawing mode.
		if (!wireframe) {
			setMode(group, DrawMode.FILL);
		} else {
			setMode(group, DrawMode.LINE);
		}

		return group;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * model.impl.RenderObjectDecoratorImpl#handleUpdate(org.eclipse.emf.common.
	 * notify.Notification)
	 */
	@Override
	protected void handleUpdate(Notification notification) {

		// If a property was set, check if it was relevant to this
		// decorator
		if (notification.getFeatureID(
				GeometryPackage.class) == GeometryPackage.SHAPE___SET_PROPERTY__STRING_DOUBLE) {

			// If the wireframe was changed, update to the new value
			if (notification.getOldStringValue().equals("wireframe")) {

				// Set the local wireframe variable according to the new
				// value
				if ((boolean) notification.getNewValue()) {
					wireframe = true;
				} else {
					wireframe = false;
				}
			}
		}
	}
}
