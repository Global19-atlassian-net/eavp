/*******************************************************************************
 * Copyright (c) 2015 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Tony McCrary (tmccrary@l33tlabs.com)
 *******************************************************************************/
package org.eclipse.eavp.viz.service.javafx.canvas;

import org.eclipse.eavp.viz.service.modeling.AbstractController;
import org.eclipse.jface.viewers.StructuredSelection;

/**
 * 
 * @author Tony McCrary (tmccrary@l33tlabs.com)
 *
 */
public class FXSelection extends StructuredSelection {

	/** */
	private final AbstractController shape;

	/**
	 * 
	 * @param modelShape
	 */
	public FXSelection(AbstractController modelShape) {
		super(modelShape);

		this.shape = modelShape;
	}

	public AbstractController getShape() {
		return shape;
	}

}
