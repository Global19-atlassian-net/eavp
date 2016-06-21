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
package org.eclipse.eavp.geometry.view.javafx.decorator.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.eavp.geometry.view.javafx.decorators.FXWireframeDecorator;
import org.eclipse.eavp.geometry.view.javafx.render.FXMeshCache;
import org.eclipse.eavp.geometry.view.javafx.render.FXRenderObject;
import org.junit.Test;

import geometry.GeometryFactory;
import geometry.Shape;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;

/**
 * A class to test the functionality of the FXWireframeDecorator.
 * 
 * @author Robert Smith
 *
 */
public class FXWireframeDecoratorTester {

	/**
	 * Check that the decorator will set the object's material correctly.
	 */
	@Test
	public void checkMesh() {

		// Create a render object
		Shape shape = GeometryFactory.eINSTANCE.createShape();
		FXRenderObject object = new FXRenderObject(
				GeometryFactory.eINSTANCE.createShape(), new FXMeshCache());

		// Create an opacity decorator for it
		FXWireframeDecorator decorator = new FXWireframeDecorator();
		decorator.setSource(object);

		// Set the shape as a wireframe
		decorator.setWireframe(true);
		;

		// The child's draw mode should have been changed
		assertTrue(((MeshView) decorator.getMesh().getChildren().get(0))
				.getDrawMode() == DrawMode.LINE);

		// Make the shape solid again and check that it was reset
		decorator.setWireframe(false);
		assertTrue(((MeshView) decorator.getMesh().getChildren().get(0))
				.getDrawMode() == DrawMode.FILL);
	}
}