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
package org.eclipse.eavp.viz.service.geometry.reactor.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.eavp.viz.modeling.properties.MeshProperty;
import org.eclipse.eavp.viz.service.geometry.reactor.HeatExchanger;
import org.junit.Test;

/**
 * A class to test the functionality of the HeatExchangerMesh.
 * 
 * @author Robert Smith
 *
 */
public class HeatExchangerTester {

	/**
	 * Check that the part is cloned correctly.
	 */
	@Test
	public void checkClone() {

		// Create an exchanger
		HeatExchanger exchanger = new HeatExchanger();
		exchanger.setProperty(MeshProperty.INNER_RADIUS, "Property");

		// Clone it and check that they are identical
		HeatExchanger clone = (HeatExchanger) exchanger.clone();
		assertTrue(exchanger.equals(clone));
	}
}
