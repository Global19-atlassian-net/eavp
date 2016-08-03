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
package org.eclipse.eavp.viz.service.javafx.geometry.plant.test;

import org.eclipse.eavp.viz.service.geometry.reactor.HeatExchanger;
import org.eclipse.eavp.viz.service.geometry.reactor.JunctionController;
import org.eclipse.eavp.viz.service.geometry.reactor.Junction;
import org.eclipse.eavp.viz.service.geometry.reactor.Pipe;
import org.eclipse.eavp.viz.service.geometry.reactor.ReactorController;
import org.eclipse.eavp.viz.service.geometry.reactor.Reactor;
import org.eclipse.eavp.viz.service.javafx.geometry.plant.FXHeatExchangerController;
import org.eclipse.eavp.viz.service.javafx.geometry.plant.FXPipeController;
import org.eclipse.eavp.viz.service.javafx.geometry.plant.FXPlantViewControllerProviderFactory;
import org.junit.Test;

/**
 * A class to test the functionality of the FXPlantViewFactory
 * 
 * @author Robert Smith
 *
 */
public class FXPlantViewControllerFactoryTester {

	/**
	 * Check that the factory produces the correct types of output.
	 */
	@Test
	public void checkCreation() {

		// The factory to test
		FXPlantViewControllerProviderFactory factory = new FXPlantViewControllerProviderFactory();

		// A HeatExchangerMesh should be given a FXHeatExchangerController
		HeatExchanger heatExchangerMesh = new HeatExchanger();
		FXHeatExchangerController exchanger = (FXHeatExchangerController) factory
				.createProvider(heatExchangerMesh)
				.createController(heatExchangerMesh);

		// A JunctionMesh should be given a JunctionController
		Junction junctionMesh = new Junction();
		JunctionController junction = (JunctionController) factory
				.createProvider(junctionMesh).createController(junctionMesh);

		// A PipeMesh should be given a FXPipeController
		Pipe pipeMesh = new Pipe();
		FXPipeController pipeController = (FXPipeController) factory
				.createProvider(pipeMesh).createController(pipeMesh);

		// A ReactorMesh should be given a ReactorController
		Reactor reactorMesh = new Reactor();
		ReactorController reactorController = (ReactorController) factory
				.createProvider(reactorMesh).createController(reactorMesh);
	}
}
