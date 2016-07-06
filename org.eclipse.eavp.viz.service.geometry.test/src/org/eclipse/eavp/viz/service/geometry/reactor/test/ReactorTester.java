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

import org.eclipse.eavp.viz.modeling.base.BasicView;
import org.eclipse.eavp.viz.modeling.properties.MeshCategory;
import org.eclipse.eavp.viz.service.geometry.reactor.PipeController;
import org.eclipse.eavp.viz.service.geometry.reactor.PipeRefactor;
import org.eclipse.eavp.viz.service.geometry.reactor.ReactorController;
import org.eclipse.eavp.viz.service.geometry.reactor.Reactor;
import org.eclipse.eavp.viz.service.geometry.reactor.ReactorMeshCategory;
import org.junit.Test;

/**
 * A class to test the functionality of the ReactorMesh.
 * 
 * @author Robert Smith
 *
 */
public class ReactorTester {

	/**
	 * Test that the Reactor sets itself as the parent to any core channel which
	 * is added to it.
	 */
	@Test
	public void checkPipes() {

		// Create a reactor and pipe
		ReactorController reactor = new ReactorController(new Reactor(),
				new BasicView());
		PipeController pipe = new PipeController(new PipeRefactor(),
				new BasicView());

		// Add the pipe as a core channel
		reactor.addEntityToCategory(pipe, ReactorMeshCategory.CORE_CHANNELS);

		// Check that the pipe has the reactor as a parent
		assertTrue(pipe.getEntitiesFromCategory(MeshCategory.PARENT)
				.get(0) == reactor);
	}
}
