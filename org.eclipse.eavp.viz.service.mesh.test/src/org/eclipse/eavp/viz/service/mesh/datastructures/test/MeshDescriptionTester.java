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
package org.eclipse.eavp.viz.service.mesh.datastructures.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.eavp.viz.modeling.DetailedEdgeController;
import org.eclipse.eavp.viz.modeling.DetailedEdgeMesh;
import org.eclipse.eavp.viz.modeling.FaceMesh;
import org.eclipse.eavp.viz.modeling.VertexController;
import org.eclipse.eavp.viz.modeling.VertexMesh;
import org.eclipse.eavp.viz.modeling.base.BasicController;
import org.eclipse.eavp.viz.modeling.base.BasicMesh;
import org.eclipse.eavp.viz.modeling.base.BasicView;
import org.eclipse.eavp.viz.modeling.base.EntityMapEntry;
import org.eclipse.eavp.viz.modeling.base.IController;
import org.eclipse.eavp.viz.modeling.base.IMesh;
import org.eclipse.eavp.viz.modeling.base.PropertyMapEntry;
import org.eclipse.eavp.viz.modeling.base.Transformation;
import org.eclipse.eavp.viz.modeling.factory.BasicControllerProviderFactory;
import org.eclipse.eavp.viz.modeling.factory.IControllerProvider;
import org.eclipse.eavp.viz.modeling.properties.MeshCategory;
import org.eclipse.eavp.viz.modeling.properties.MeshProperty;
import org.eclipse.eavp.viz.service.mesh.datastructures.BoundaryCondition;
import org.eclipse.eavp.viz.service.mesh.datastructures.BoundaryConditionType;
import org.eclipse.eavp.viz.service.mesh.datastructures.MeshDescription;
import org.eclipse.eavp.viz.service.mesh.datastructures.NekPolygonController;
import org.eclipse.eavp.viz.service.mesh.datastructures.NekPolygonMesh;
import org.junit.Test;

/**
 * A class to test the functionality of the MeshDescription.
 * 
 * @author Robert Smith
 *
 */
public class MeshDescriptionTester {

	/**
	 * Test that a hierarchy of object can be placed into and correctly
	 * retrieved from a MeshDescription.
	 */
	@Test
	public void checkPackaging() {

		// The root object for the hierarchy
		BasicController root = new BasicController(new BasicMesh(),
				new BasicView());

		// Create the vertices. In space they are laid out thusly:
		// 1 2 5
		// 4 3 6
		VertexController vertex1 = new VertexController(new VertexMesh(),
				new BasicView());
		vertex1.setTranslation(0, 0, 0);
		vertex1.setX(0);
		vertex1.setY(0);
		vertex1.setZ(0);
		vertex1.setProperty(MeshProperty.ID, "1");
		VertexController vertex2 = new VertexController(new VertexMesh(),
				new BasicView());
		vertex2.setTranslation(1, 0, 0);
		vertex2.setX(1);
		vertex2.setY(0);
		vertex2.setZ(0);
		vertex2.setProperty(MeshProperty.ID, "2");
		VertexController vertex3 = new VertexController(new VertexMesh(),
				new BasicView());
		vertex3.setTranslation(1, 1, 0);
		vertex3.setX(1);
		vertex3.setY(1);
		vertex3.setZ(0);
		vertex3.setProperty(MeshProperty.ID, "3");
		VertexController vertex4 = new VertexController(new VertexMesh(),
				new BasicView());
		vertex4.setTranslation(0, 1, 0);
		vertex4.setX(0);
		vertex4.setY(1);
		vertex4.setZ(0);
		vertex4.setProperty(MeshProperty.ID, "4");
		VertexController vertex5 = new VertexController(new VertexMesh(),
				new BasicView());
		vertex5.setTranslation(2, 0, 0);
		vertex5.setX(2);
		vertex5.setY(0);
		vertex5.setZ(0);
		vertex5.setProperty(MeshProperty.ID, "5");
		VertexController vertex6 = new VertexController(new VertexMesh(),
				new BasicView());
		vertex6.setTranslation(2, 1, 0);
		vertex6.setX(2);
		vertex6.setY(1);
		vertex6.setZ(0);
		vertex6.setProperty(MeshProperty.ID, "6");

		// Create the edges. In space they are laid out thusly:
		// -9- -19-
		// |_ |__ |
		// 29 17_ 41
		// |_ |__ |
		// -25- -45-
		//
		// The edge's are given the IDs they should be assigned by the
		// MeshDescription compression process, so that this can be checked.
		DetailedEdgeController edge1 = new DetailedEdgeController(
				new DetailedEdgeMesh(vertex1, vertex2), new BasicView());
		edge1.setProperty(MeshProperty.ID, "9");
		DetailedEdgeController edge2 = new DetailedEdgeController(
				new DetailedEdgeMesh(vertex2, vertex3), new BasicView());
		edge2.setProperty(MeshProperty.ID, "17");
		DetailedEdgeController edge3 = new DetailedEdgeController(
				new DetailedEdgeMesh(vertex3, vertex4), new BasicView());
		edge3.setProperty(MeshProperty.ID, "25");
		DetailedEdgeController edge4 = new DetailedEdgeController(
				new DetailedEdgeMesh(vertex4, vertex1), new BasicView());
		edge4.setProperty(MeshProperty.ID, "29");
		DetailedEdgeController edge5 = new DetailedEdgeController(
				new DetailedEdgeMesh(vertex2, vertex5), new BasicView());
		edge5.setProperty(MeshProperty.ID, "19");
		DetailedEdgeController edge6 = new DetailedEdgeController(
				new DetailedEdgeMesh(vertex5, vertex6), new BasicView());
		edge6.setProperty(MeshProperty.ID, "41");
		DetailedEdgeController edge7 = new DetailedEdgeController(
				new DetailedEdgeMesh(vertex6, vertex3), new BasicView());
		edge7.setProperty(MeshProperty.ID, "45");

		// Create the faces. Face 1 on the left, 2 on the right.
		NekPolygonController face1 = new NekPolygonController(
				new NekPolygonMesh(), new BasicView());
		face1.setProperty(MeshProperty.ID, "1");
		face1.addEntityToCategory(edge1, MeshCategory.EDGES);
		face1.addEntityToCategory(edge2, MeshCategory.EDGES);
		face1.addEntityToCategory(edge3, MeshCategory.EDGES);
		face1.addEntityToCategory(edge4, MeshCategory.EDGES);
		NekPolygonController face2 = new NekPolygonController(
				new NekPolygonMesh(), new BasicView());
		face2.setProperty(MeshProperty.ID, "2");
		face2.addEntityToCategory(edge2, MeshCategory.EDGES);
		face2.addEntityToCategory(edge5, MeshCategory.EDGES);
		face2.addEntityToCategory(edge6, MeshCategory.EDGES);
		face2.addEntityToCategory(edge7, MeshCategory.EDGES);

		// Add a boundary condition to one of the faces
		BoundaryCondition boundary = new BoundaryCondition();
		boundary.setType(BoundaryConditionType.AxisymmetricBoundary);
		ArrayList<Float> values = new ArrayList<Float>();
		values.add(1.0f);
		values.add(2.0f);
		values.add(3.0f);
		values.add(4.0f);
		values.add(5.0f);
		boundary.setValues(values);
		face1.setFluidBoundaryCondition(9, boundary);

		// Add a second boundary condition
		BoundaryCondition boundary2 = new BoundaryCondition();
		boundary2.setType(BoundaryConditionType.DirichletTemperatureScalar);
		ArrayList<Float> values2 = new ArrayList<Float>();
		values2.add(2.0f);
		values2.add(2.0f);
		values2.add(3.0f);
		values2.add(4.0f);
		values2.add(5.0f);
		boundary2.setValues(values2);
		face1.setThermalBoundaryCondition(9, boundary2);

		// Set polygon properties for the faces
		face1.setPolygonProperties("Test", 8);
		face2.setPolygonProperties("TEST", 11);

		// Add everything to the root node
		root.addEntity(face1);
		root.addEntity(face2);

		// Create a mesh description of the hierarchy
		MeshDescription desc = new MeshDescription(root);

		// Retrieve the data structures from the description
		IController unpacked = desc.unpack(new FakeControllerProviderFactory());

		// Check that the input and output are identical.
		// assertTrue(root.equals(unpacked));

		// Create an output stream for a test file
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream("test.xml");
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
			fail("Could not open test.xml file for writing.");
		}

		// Initialize the data structures for the JAXB context
		JAXBContext jaxbContext = null;
		Class[] classArray = {};
		ArrayList<Class> classList = new ArrayList<Class>();

		// Add all neccesary classes to the list
		classList.add(BasicMesh.class);
		classList.add(MeshCategory.class);
		classList.add(MeshProperty.class);
		classList.add(BasicController.class);
		classList.add(BasicView.class);
		classList.add(EntityMapEntry.class);
		classList.add(PropertyMapEntry.class);
		classList.add(Transformation.class);
		classList.add(MeshDescription.class);

		// Create a context
		try {
			jaxbContext = JAXBContext
					.newInstance(classList.toArray(classArray));
		} catch (JAXBException e) {
			e.printStackTrace();
			fail("Failed to make new JAXBContext.");
		}

		// Marshal the objects to xml
		Marshaller marshaller;
		try {
			marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);

			// Write to file
			marshaller.marshal(desc, outputStream);
			outputStream.close();
		} catch (JAXBException | IOException e1) {
			e1.printStackTrace();
			fail("Failed to marshal to .xml file.");
		}

		// Get an input stream for the file we just wrote
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("test.xml");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("Failed to get input stream from test.xml.");
		}

		// The mesh read from the persisted xml file
		MeshDescription restoredMesh = null;

		// Unmarshal the objects from xml
		try {
			Unmarshaller unmarshaller = null;
			unmarshaller = jaxbContext.createUnmarshaller();
			Object restored = unmarshaller.unmarshal(inputStream);

			// Caste the object as a mesh
			restoredMesh = (MeshDescription) restored;
		} catch (JAXBException e) {
			e.printStackTrace();
			fail("Error while unmarshaling from .xml file");
		}

		// Check that the the restored object is equal to the original
		IController output = restoredMesh
				.unpack(new FakeControllerProviderFactory());

		// Check that the result of the unmarshal operation is equal to the
		// original input
		assertTrue(root.equals(output));
	}

	/**
	 * An IControllerProviderFactory that will create providers with simple base
	 * classes for testing purpostes.
	 * 
	 * @author Robert Smith
	 *
	 */
	private class FakeControllerProviderFactory
			extends BasicControllerProviderFactory {

		/**
		 * The default constructor
		 */
		public FakeControllerProviderFactory() {
			super();

			// Set the EdgeMesh provider
			typeMap.put(DetailedEdgeMesh.class,
					new IControllerProvider<DetailedEdgeController>() {
						@Override
						public DetailedEdgeController createController(
								IMesh model) {

							// If the model is an edge component, create an edge
							// with a basic view
							BasicView view = new BasicView();
							return new DetailedEdgeController(
									(DetailedEdgeMesh) model, view);
						}
					});

			// Set the NekPolygonMesh provider
			typeMap.put(NekPolygonMesh.class,
					new IControllerProvider<NekPolygonController>() {
						@Override
						public NekPolygonController createController(
								IMesh model) {

							// Create a NekPolygonController with a basic view
							BasicView view = new BasicView();
							return new NekPolygonController((FaceMesh) model,
									view);
						}
					});

			// Set the VertexMesh provider
			typeMap.put(VertexMesh.class,
					new IControllerProvider<VertexController>() {
						@Override
						public VertexController createController(IMesh model) {

							// Create a vertex controller
							BasicView view = new BasicView();
							return new VertexController((VertexMesh) model,
									view);
						}
					});
		}
	}

}
