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

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import model.impl.ColorDecoratorImpl;

/**
 * A Decorator for an FXRenderObject that sets the displayed material for the
 * obejct and all of its children.
 * 
 * @author Robert Smith
 *
 */
public class FXColorDecorator extends ColorDecoratorImpl<Group> {

	/**
	 * Set all the MeshViews in the group to the given material, as well as the
	 * children of any child groups.
	 * 
	 * @param group
	 *            The group whose children will have their material set.
	 * @param material
	 *            The material to be set to the group's children.
	 */
	private void setMaterial(Group group, Material material) {

		// Handle each of the group's children
		for (Node node : group.getChildren()) {

			// If the node is a mesh view, set its material
			if (node.getClass() == MeshView.class) {
				((MeshView) node).setMaterial(material);
			}

			// Otherwise, recursively handle the child group
			else if (node.getClass() == Group.class) {
				setMaterial((Group) node, material);
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

		// Get the mesh
		Group group = source.getMesh();

		// Create a material of the specified color and set it.
		PhongMaterial material = new PhongMaterial(Color.rgb(red, green, blue));
		material.setSpecularColor(Color.WHITE);

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

		// Get the property
		Object property = notification.getOldValue();

		// Check if the property is any of the RGB colors. If so,
		// set the decorator's color correctly
		if ("red".equals(property)) {
			setRed((int) notification.getNewValue());
		}
		if ("green".equals(property)) {
			setGreen((int) notification.getNewValue());
		}
		if ("blue".equals(property)) {
			setBlue((int) notification.getNewValue());
		}

		// Pass the update along to own listeners
		super.handleUpdate(notification);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.impl.ColorDecoratorImpl#clone()
	 */
	@Override
	public Object clone() {

		// Create a new color decorator
		FXColorDecorator clone = new FXColorDecorator();

		// Copy this object's data into the clone
		clone.copy(this);
		return clone;
	}
}
