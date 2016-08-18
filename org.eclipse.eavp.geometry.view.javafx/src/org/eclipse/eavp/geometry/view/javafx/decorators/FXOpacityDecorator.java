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

import org.eclipse.eavp.geometry.view.model.impl.OpacityDecoratorImpl;
import org.eclipse.emf.common.notify.Notification;

import javafx.scene.Group;

/**
 * A decorator for FXRenderObjects that allows the shape to be made transparent.
 * 
 * The decorator will read properties from its source IRenderElement in order to
 * configure itself. The properties used for this decorator are:
 * 
 * "opacity"- A double that sets how transparent the element is, with 0 being
 * fully transparent and 100 being fully opaque. Negative values will signal
 * that the decorator should make no change to the element.
 * 
 * @author Robert Smith
 *
 */
public class FXOpacityDecorator extends OpacityDecoratorImpl<Group> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.impl.RenderObjectDecoratorImpl#getMesh()
	 */
	@Override
	public Group getMesh() {

		// Get the group from the source
		Group group = source.getMesh();

		// FIXME When JavaFX offers full functionality for opaque shapes, the
		// opacity should simply be passed in to the group rather than snapping
		// it to either fully opaque or fully translucent.
		// Set the group's opacity.
		if (!(opacity < 100)) {
			group.setOpacity(1);
		}

		// Opacities below 0 will be a special value signaling that the
		// decorator will make no change.
		else if (opacity >= 0) {
			group.setOpacity(0);
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

		// If the opacity was changed, update to the new value
		if ("opacity".equals(notification.getOldValue())) {
			setOpacity((double) notification.getNewValue());
		}

		// Pass the update to own listeners
		super.handleUpdate(notification);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.impl.OpacityDecoratorImpl#clone()
	 */
	@Override
	public Object clone() {

		// Create a new color decorator
		FXOpacityDecorator clone = new FXOpacityDecorator();

		// Copy this object's data into the clone
		clone.copy(this);
		return clone;
	}
}
