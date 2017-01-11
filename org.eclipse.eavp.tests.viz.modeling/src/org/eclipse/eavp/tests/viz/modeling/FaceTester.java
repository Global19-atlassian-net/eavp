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
package org.eclipse.eavp.tests.viz.modeling;

import static org.junit.Assert.assertTrue;

import org.eclipse.eavp.viz.modeling.Face;
import org.eclipse.eavp.viz.modeling.properties.MeshProperty;
import org.junit.Test;

/**
 * A class for testing the functionality of FaceMesh.
 * 
 * @author Robert Smith
 *
 */
public class FaceTester {

	/**
	 * Check that FaceMeshes are cloned correctly.
	 */
	@Test
	public void checkClone() {

		// Clone a mesh and check that the result is identical
		Face mesh = new Face();
		mesh.setProperty(MeshProperty.DESCRIPTION, "Property");
		Face clone = (Face) mesh.clone();
		assertTrue(mesh.equals(clone));
	}
}
