/*******************************************************************************
 * Copyright (c) 2015 UT-Battelle, LLC.
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

import org.eclipse.eavp.viz.modeling.PointController;
import org.eclipse.eavp.viz.modeling.Point;
import org.eclipse.eavp.viz.modeling.base.BasicView;
import org.eclipse.eavp.viz.modeling.properties.MeshProperty;
import org.junit.Test;

/**
 * A class to test the functionality of PointController.
 * 
 * @author Robert Smith
 *
 */
public class PointControllerTester {

	/**
	 * Check that PointControllers can be properly cloned.
	 */
	@Test
	public void checkClone() {

		// Create a cloned point and check that it is identical to the original
		PointController point = new PointController(new Point(),
				new BasicView());
		point.setProperty(MeshProperty.DESCRIPTION, "Property");
		PointController clone = (PointController) point.clone();
		assertTrue(point.equals(clone));
	}
}
