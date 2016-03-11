/*******************************************************************************
 * Copyright (c) 2011, 2014 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Initial API and implementation and/or initial documentation - Jay Jay 
 *   Billings
 *******************************************************************************/
package org.eclipse.eavp.viz.service.datastructures.VizObject.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.Normalizer.Form;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.eavp.viz.service.datastructures.VizObject.IElementSource;
import org.eclipse.eavp.viz.service.datastructures.VizObject.VizJAXBHandler;
import org.eclipse.eavp.viz.service.datastructures.VizObject.VizListComponent;
import org.eclipse.eavp.viz.service.datastructures.VizObject.VizObject;
import org.eclipse.eavp.viz.service.datastructures.resource.IResource;
import org.eclipse.eavp.viz.service.datastructures.resource.VisualizationResource;
import org.eclipse.eavp.viz.service.datastructures.resource.test.TestComponentListener;
import org.junit.Test;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;

/**
 * The ListComponentTester is responsible for testing the ListComponent class.
 * It only tests the name, id, and description properties as well as persistence
 * (insofar as it can for the latter). It also checks equality, hashCode
 * computation, copying, and cloning.
 * 
 * Finally, this class tests those operations from Component that are
 * implemented by ListComponentTester.
 * 
 * @author Jay Jay Billings
 */
public class ListComponentTester implements IElementSource<Integer>,
		WritableTableFormat<Integer>, ListEventListener<Integer> {

	/**
	 * A flag to mark whether or not visitation worked.
	 */
	private boolean visited = false;

	/**
	 * The value set in the table format
	 */
	private Integer value = 7;

	/**
	 * True if the test was notified via the GlazedLists ListEventListener
	 * interface instead of the ICE interface.
	 */
	private volatile boolean notified = false;

	/**
	 * This operation checks the ListComponent to insure that the id, name and
	 * description getters and setters function properly.
	 */
	@Test
	public void checkProperties() {

		// Local declarations
		int id = 20110901;
		String name = "September 1st 2011";
		String description = "The 1st day of the ninth month in the year of "
				+ "our Lord 2011";

		// Create the ListComponent
		VizListComponent<?> component = new VizListComponent<Object>();

		// Set up the id, name and description
		component.setId(id);
		component.setName(name);
		component.setDescription(description);

		// Check the id, name and description
		assertEquals(component.getId(), id);
		assertEquals(component.getName(), name);
		assertEquals(component.getDescription(), description);
	}

	/**
	 * This operation checks the ListComponent class to ensure that its copy()
	 * and clone() operations work as specified.
	 */
	@Test
	public void checkCopying() {

		// Local declarations
		int id = 20110901;
		String name = "September 1st 2011";
		String description = "The 1st day of the ninth month in the year of "
				+ "our Lord 2011";
		VizListComponent<Integer> component = new VizListComponent<Integer>();

		// Set up the id, name and description
		component.setId(id);
		component.setName(name);
		component.setDescription(description);
		// Add some integers to the list
		component.add(5);
		component.add(383400);

		// Create a new instance of ListComponent and copy contents
		VizListComponent<Integer> component2 = new VizListComponent<Integer>();
		component2.copy(component);

		// Check the id, name and description with copy
		assertEquals(component.getId(), component2.getId());
		assertEquals(component.getName(), component2.getName());
		assertEquals(component.getDescription(), component2.getDescription());
		// Make sure it holds the integers
		assertTrue(component2.contains(5));
		assertTrue(component2.contains(383400));

		// Create a clone of the component and check it
		VizListComponent<?> componentClone = (VizListComponent<?>) component.clone();
		assertEquals(component, componentClone);

		// Test to show an invalid use of copy - null args

		// Local declarations
		id = 20110901;
		name = "September 1st 2011";
		description = "The 1st day of the ninth month in the year of "
				+ "our Lord 2011";
		component = new VizListComponent<Integer>();

		// Set up the id, name and description
		component.setId(id);
		component.setName(name);
		component.setDescription(description);

		// Attempt the null copy
		component.copy(null);

		// Check the id, name and description - nothing has changed
		assertEquals(component.getId(), id);
		assertEquals(component.getName(), name);
		assertEquals(component.getDescription(), description);

	}

	/**
	 * This operation checks the ability of the ListComponent to persist itself
	 * to XML and to load itself from an XML input stream.
	 * 
	 * @throws IOException
	 * @throws JAXBException
	 * @throws NullPointerException
	 */
	@Test
	public void checkXMLPersistence() throws NullPointerException,
			JAXBException, IOException {

		// Local declarations
		VizListComponent<?> component2 = null;
		int id = 20110901;
		String name = "September 1st 2011";
		String description = "The 1st day of the ninth month in the year of "
				+ "our Lord 2011";
		VizJAXBHandler xmlHandler = new VizJAXBHandler();
		ArrayList<Class> classList = new ArrayList<Class>();
		classList.add(VizListComponent.class);
		classList.add(Form.class);
		classList.add(Integer.class);
		classList.add(VizObject.class);
		classList.add(VisualizationResource.class);

		// Demonstrate a basic "write" to file. Should not fail
		// Initialize the object and set values.
		VizListComponent<Integer> component = new VizListComponent<Integer>();
		component.setId(id);
		component.setName(name);
		component.setDescription(description);
		// Add some list contents
		component.add(5);
		component.add(1337);

		// Persist the Integer list to an output stream
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		xmlHandler.write(component, classList, outputStream);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				outputStream.toByteArray());
		// Load it up and check it
		component2 = (VizListComponent<?>) xmlHandler.read(classList, inputStream);
		assertEquals(component.getId(), component2.getId());
		assertEquals(component.getName(), component2.getName());
		assertEquals(component.getDescription(), component2.getDescription());
		assertEquals(component.get(0), component2.get(0));
		assertEquals(component.get(1), component2.get(1));
		assertTrue(component.equals(component2));

		// Create a list to hold ICEObjects and put one in it.
		VizListComponent<VizObject> objList = new VizListComponent<VizObject>();
		VizObject obj = new VizObject();
		obj.setId(777777);
		objList.add(obj);
		// Write it to the stream
		outputStream = new ByteArrayOutputStream();
		xmlHandler.write(objList, classList, outputStream);
		inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		// Load it back up and check it
		VizListComponent<?> loadedList = (VizListComponent<?>) xmlHandler.read(
				classList, inputStream);
		assertEquals(objList.getId(), loadedList.getId());
		assertEquals(1, objList.size());
		assertEquals(obj, loadedList.get(0));
		assertTrue(objList.equals(loadedList));

		// Create a list to hold ICEResources and put one in it.
		VizListComponent<IResource> resList = new VizListComponent<IResource>();
		// Set up some dummy resources and add them to the list
		File file = new File("Isabelle");
		File file2 = new File("Zelda");
		File file3 = new File("Doctor");
		IResource res = new VisualizationResource(file);
		IResource res2 = new VisualizationResource(file2);
		IResource res3 = new VisualizationResource(file3);
		resList.add(res);
		resList.add(res2);
		resList.add(res3);
		file.deleteOnExit();
		file2.deleteOnExit();
		file3.deleteOnExit();

		// Write it to the stream
		outputStream = new ByteArrayOutputStream();
		xmlHandler.write(resList, classList, outputStream);
		inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		// Load it back up and check it
		VizListComponent<?> resLoadedList = (VizListComponent<?>) xmlHandler.read(
				classList, inputStream);
		assertEquals(resList.getId(), resLoadedList.getId());
		assertEquals(3, resList.size());
		assertEquals(res, resLoadedList.get(0));
		assertEquals(res2, resLoadedList.get(1));
		assertEquals(res3, resLoadedList.get(2));
		assertTrue(resList.equals(resLoadedList));

		return;
	}

	/**
	 * This operation checks the ListComponent class to insure that its equals()
	 * operation works.
	 */
	@Test
	public void checkEquality() {

		// Create an ListComponent
		VizListComponent<Integer> component = new VizListComponent<Integer>();
		// Create another ListComponent to assert Equality with the last
		VizListComponent<Integer> equalComponent = new VizListComponent<Integer>();
		// Create an ListComponent that is not equal to component
		VizListComponent<Integer> unequalComponent = new VizListComponent<Integer>();
		// Create a third ListComponent to test Transitivity
		VizListComponent<Integer> transitiveComponent = new VizListComponent<Integer>();

		// Set its data
		component.setId(12);
		component.setName("ICE ListComponent");
		component
				.setDescription("This is an ListComponent that will "
						+ "be used for testing equality with other AbstractListComponents.");
		// Add some integers to the list
		component.add(5);
		component.add(383400);

		// Setup the equal component
		equalComponent.setId(12);
		equalComponent.setName("ICE ListComponent");
		equalComponent
				.setDescription("This is an ListComponent that will "
						+ "be used for testing equality with other AbstractListComponents.");
		equalComponent.add(5);
		equalComponent.add(383400);

		// Assert that these two AbstractListComponents are equal
		assertTrue(component.equals(equalComponent));

		// Setup the unequal component
		unequalComponent.setId(52);
		unequalComponent.setName("Bill the ListComponent");
		unequalComponent
				.setDescription("This is an ListComponent to verify that "
						+ "ListComponent.equals() returns false for an object that is not "
						+ "equivalent to component.");

		// Assert that two unequal objects returns false
		assertFalse(component.equals(unequalComponent));

		// Setup the transitive list
		transitiveComponent.setId(12);
		transitiveComponent.setName("ICE ListComponent");
		transitiveComponent
				.setDescription("This is an ListComponent that will "
						+ "be used for testing equality with other AbstractListComponents.");
		transitiveComponent.add(5);
		transitiveComponent.add(383400);

		// Check that equals() is Reflexive
		// x.equals(x) = true
		assertTrue(component.equals(component));

		// Check that equals() is Symmetric
		// x.equals(y) = true iff y.equals(x) = true
		assertTrue(component.equals(equalComponent)
				&& equalComponent.equals(component));

		// Check that equals() is Transitive
		// x.equals(y) = true, y.equals(z) = true => x.equals(z) = true
		if (component.equals(equalComponent)
				&& equalComponent.equals(transitiveComponent)) {
			assertTrue(component.equals(transitiveComponent));
		} else {
			fail();
		}

		// Check the Consistent nature of equals()
		assertTrue(component.equals(equalComponent)
				&& component.equals(equalComponent)
				&& component.equals(equalComponent));
		assertTrue(!component.equals(unequalComponent)
				&& !component.equals(unequalComponent)
				&& !component.equals(unequalComponent));

		// Assert checking equality with null value returns false
		assertFalse(component == null);

		// Assert that two equal objects have the same hashcode
		assertTrue(component.equals(equalComponent)
				&& component.hashCode() == equalComponent.hashCode());

		// Assert that hashcode is consistent
		assertTrue(component.hashCode() == component.hashCode());

		// Assert that hashcodes are different for unequal objects
		assertFalse(component.hashCode() == unequalComponent.hashCode());

		return;
	}

	/**
	 * This operation tests the ListComponent to insure that it can properly
	 * dispatch notifications when it receives an update that changes its state.
	 */
	@Test
	public void checkNotifications() {

		// Setup the listeners
		TestComponentListener firstListener = new TestComponentListener();
		TestComponentListener secondListener = new TestComponentListener();

		// Setup the component
		VizListComponent<Object> component = new VizListComponent<Object>();

		// Register the listener
		component.register(firstListener);

		// Add the second listener
		component.register(secondListener);

		// Change the name of the object
		component.setName("Warren Buffett");

		// Check the listeners to make sure they updated
		assertTrue(firstListener.wasNotified());
		assertTrue(secondListener.wasNotified());
		// Reset the listeners
		firstListener.reset();
		secondListener.reset();

		// Unregister the second listener so that it no longer receives updates
		component.unregister(secondListener);

		// Change the id of the object
		component.setId(899);
		assertTrue(firstListener.wasNotified());
		// Make sure the second listener was not updated
		assertFalse(secondListener.wasNotified());

		// Reset the listener
		firstListener.reset();
		// Change the description of the object
		component.setDescription("New description");
		// Make sure the listener was notified
		assertTrue(firstListener.wasNotified());

		// Reset the listener
		firstListener.reset();
		// Add a value to the list
		component.add("fred");
		// Make sure the listener was notified
		assertTrue(firstListener.wasNotified());

		return;
	}

	/**
	 * This method checks that glazed list listeners (ListEventListeners) can be
	 * added and removed from the ListComponent. When registered, any updates to
	 * the ListComponent should be passed to them.
	 */
	@Test
	public void checkListNotifications() {

		// Set up two listeners.
		TestListComponentListener<String> listener1 = new TestListComponentListener<String>();
		TestListComponentListener<String> listener2 = new TestListComponentListener<String>();

		// Set up a component to test.
		VizListComponent<String> component = new VizListComponent<String>();

		// Register the first listener. Adding a value to the list should
		// trigger a notification.
		component.addListEventListener(listener1);
		component.add("Some String");
		assertTrue(listener1.wasNotified());

		// Reset the listener's flags (the second one hasn't been used yet).
		listener1.reset();

		// Register the second listener. Updating the value should trigger
		// a notification for both listeners.
		component.addListEventListener(listener2);
		component.set(0, "A different string.");
		assertTrue(listener1.wasNotified());
		assertTrue(listener2.wasNotified());

		// Reset the listeners' flags.
		listener1.reset();
		listener2.reset();

		// Remove the first listener. Removing the value should trigger a
		// notification for only the remaining listener.
		component.removeListEventListener(listener1);
		component.clear();
		assertFalse(listener1.wasNotified());
		assertTrue(listener2.wasNotified());

		// Reset the listeners' flags (the first one is no longer used).
		listener2.reset();

		// Updating a feature of the ListComponent that is NOT a glazed list
		// feature shouldn't be sent to the listener. For that, we need an
		// IUpdateableListener.
		component.setId(99);

		return;
	}

	/**
	 * This operation checks the IElementSource handle of the ListComponent.
	 */
	@Test
	public void checkElementSource() {

		// Create a new AdaptiveTreeComposite to visit
		VizListComponent<Integer> component = new VizListComponent<Integer>(this);

		// Check that the component received the IElementSource and that it
		// returns the correct one.
		IElementSource<Integer> source = component.getElementSource();
		assertNotNull(source);
		assertTrue(source == this);

		return;
	}

	/**
	 * This operation is responsible for making sure that the ListComponent can
	 * properly behave as a WritableTableFormat.
	 */
	@Test
	public void checkTableFormat() {

		// Create the list and set the table format
		VizListComponent<Integer> component = new VizListComponent<Integer>();
		component.setTableFormat(this);

		// Check everything
		assertNotNull(component.getTableFormat());
		assertTrue(this == component.getTableFormat());
		assertEquals(getColumnCount(), component.getColumnCount());
		assertEquals(getColumnName(0), component.getColumnName(0));
		assertEquals(getColumnValue(0, 1), component.getColumnValue(0, 1));
		assertEquals(isEditable(0, 1), component.isEditable(0, 1));
		// Check setting the value
		component.setColumnValue(1, 2, 0);
		assertEquals(getColumnValue(1, 0), component.getColumnValue(1, 0));

		return;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ice.datastructures.ICEObject.IElementSource#getElements()
	 */
	@Override
	public EventList<Integer> getElements() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ice.datastructures.ICEObject.IElementSource#getTableFormat()
	 */
	@Override
	public TableFormat<Integer> getTableFormat() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.odell.glazedlists.gui.TableFormat#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return 5;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.odell.glazedlists.gui.TableFormat#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		return "Six";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.odell.glazedlists.gui.TableFormat#getColumnValue(java.lang.Object,
	 * int)
	 */
	@Override
	public Object getColumnValue(Integer baseObject, int column) {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.odell.glazedlists.gui.WritableTableFormat#isEditable(java.lang.Object,
	 * int)
	 */
	@Override
	public boolean isEditable(Integer baseObject, int column) {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.odell.glazedlists.gui.WritableTableFormat#setColumnValue(java.lang
	 * .Object, java.lang.Object, int)
	 */
	@Override
	public Integer setColumnValue(Integer baseObject, Object editedValue,
			int column) {
		value = (Integer) editedValue;
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.odell.glazedlists.event.ListEventListener#listChanged(ca.odell.glazedlists
	 * .event.ListEvent)
	 */
	@Override
	public void listChanged(ListEvent<Integer> listChanges) {
		System.out.println("ListComponentTester Message: "
				+ "ListEventListener callback executed.");
		notified = true;
	}

}
