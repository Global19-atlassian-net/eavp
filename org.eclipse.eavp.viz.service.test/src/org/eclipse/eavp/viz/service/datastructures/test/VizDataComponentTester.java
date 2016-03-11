/*******************************************************************************
 * Copyright (c) 2012, 2014 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Initial API and implementation and/or initial documentation - Jay Jay Billings,
 *   Jordan H. Deyton, Dasha Gorin, Alexander J. McCaskey, Taylor Patterson,
 *   Claire Saunders, Matthew Wang, Anna Wojtowicz
 *******************************************************************************/
package org.eclipse.eavp.viz.service.datastructures.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.eavp.viz.service.datastructures.DataComponent;
import org.eclipse.eavp.viz.service.datastructures.VizEntry;
import org.eclipse.eavp.viz.service.datastructures.VizObject.VizJAXBHandler;
import org.junit.Test;

/**
 * <p>
 * The DataComponentTester class is responsible for testing the DataComponent
 * class.
 * </p>
 * 
 * @author Jay Jay Billings
 */
public class VizDataComponentTester {
	/**
	 * <p>
	 * The DataComponent to be tested.
	 * </p>
	 * 
	 */
	private DataComponent dataComponent;
	/**
	 * <p>
	 * The TestVizComponentListener used to subscribe to and check notifications
	 * from the DataComponent.
	 * </p>
	 * 
	 */
	private TestVizComponentListener testComponentListener;

	/**
	 * <p>
	 * This operation tests the construction of the DataComponent class and the
	 * functionality inherited from ICEObject.
	 * </p>
	 */
	@Test
	public void checkCreation() {

		// Local declarations
		int id = 20110901;
		String name = "September 1st 2011";
		String description = "The 1st day of the ninth month in the year of "
				+ "our Lord 2011";

		// Create the DataComponent
		dataComponent = new DataComponent();

		// Set the id, name and description
		dataComponent.setId(id);
		dataComponent.setDescription(description);
		dataComponent.setName(name);

		// Check the id, name and description
		assertEquals(dataComponent.getDescription(), description);
		assertEquals(dataComponent.getId(), id);
		assertEquals(dataComponent.getName(), name);

		return;

	}

	/**
	 * <p>
	 * This operation tests the DataComponent class by making sure that VizEntry
	 * can be added to the component.
	 * </p>
	 */
	@Test
	public void checkAddingEntries() {

		// Location Declarations
		int i = 0, numEntries = 50;
		ArrayList<VizEntry> entries = new ArrayList<VizEntry>();
		ArrayList<VizEntry> retEntries = null;
		VizEntry VizEntry = null;

		// Setup the list of Entries
		for (i = 0; i < numEntries; i++) {
			entries.add(new VizEntry());
			(entries.get(i)).setId(i);
			(entries.get(i)).setName("Test VizEntry " + i);
		}
		(entries.get(4)).setReady(false);
		(entries.get(39)).setReady(false);

		// Add the Entries to the DataComponent
		dataComponent = new DataComponent();
		for (i = 0; i < numEntries; i++) {
			dataComponent.addEntry(entries.get(i));
		}

		// Retrieve the Entries one-by-one and check them
		for (i = 0; i < entries.size(); i++) {
			VizEntry = dataComponent.retrieveEntry("Test VizEntry " + i);
			assertEquals(VizEntry.getId(), (entries.get(i)).getId());
		}

		// Retrieve the Entries in a block and check them
		retEntries = dataComponent.retrieveAllEntries();
		assertNotNull(retEntries);
		assertEquals(entries.size(), retEntries.size());
		for (i = 0; i < numEntries; i++) {
			assertEquals((retEntries.get(i)).getId(), (entries.get(i)).getId());
			assertEquals((retEntries.get(i)).getName(),
					(entries.get(i)).getName());
		}

		// Get only the Entries that are ready
		retEntries = dataComponent.retrieveReadyEntries();
		assertEquals(48, retEntries.size());

		return;

	}

	/**
	 * <p>
	 * This operation tests the DataComponent class to make sure that Entries
	 * can be cleared and deleted.
	 * </p>
	 */
	@Test
	public void checkClearingEntries() {

		// Location Declarations
		int i = 0, numEntries = 50;
		ArrayList<VizEntry> entries = new ArrayList<VizEntry>();
		ArrayList<VizEntry> retEntries = null;
		// Setup the list of Entries
		for (i = 0; i < numEntries; i++) {
			entries.add(new VizEntry());
			(entries.get(i)).setId(i);
			(entries.get(i)).setName("Test VizEntry " + i);
		}

		// Add the Entries to the DataComponent
		dataComponent = new DataComponent();
		for (i = 0; i < numEntries; i++) {
			dataComponent.addEntry(entries.get(i));
		}

		// Delete three Entries and make sure they were deleted
		dataComponent.deleteEntry(entries.get(23).getName());
		dataComponent.deleteEntry(entries.get(17).getName());
		dataComponent.deleteEntry(entries.get(32).getName());
		// retrieve the entries to see if the size changed.
		retEntries = dataComponent.retrieveAllEntries();
		assertEquals(numEntries - 3, retEntries.size());
		assertNotSame((retEntries.get(23)).getId(), 23);
		assertNotSame((retEntries.get(17)).getId(), 17);
		assertNotSame((retEntries.get(32)).getId(), 32);

		return;

	}

	/**
	 * <p>
	 * This operation tests the DataComponent class to insure that checking for
	 * VizEntry containment functions properly.
	 * </p>
	 */
	@Test
	public void checkContainment() {

		// Location Declarations
		int i = 0, numEntries = 50;
		ArrayList<VizEntry> entries = new ArrayList<VizEntry>();

		// Setup the list of Entries
		for (i = 0; i < numEntries; i++) {
			entries.add(new VizEntry());
			(entries.get(i)).setId(i);
			(entries.get(i)).setName("Test VizEntry " + i);
		}

		// Add the Entries to the DataComponent
		dataComponent = new DataComponent();
		for (i = 0; i < numEntries; i++) {
			dataComponent.addEntry(entries.get(i));
		}

		// Retrieve the Entries one-by-one and check them
		for (i = 0; i < numEntries; i++) {
			assertTrue(dataComponent.contains((entries.get(i)).getName()));
		}

	}

	/**
	 * <p>
	 * This operation checks the ability of the DataComponent to update its
	 * Entries.
	 * </p>
	 */
	@Test
	public void checkUpdate() {

		// Location Declarations
		int i = 0, numEntries = 10;
		ArrayList<VizEntry> entries = new ArrayList<VizEntry>();
		ArrayList<VizEntry> retEntries = null;
		String value = "3D";

		// Setup the list of Entries
		for (i = 0; i < numEntries; i++) {
			entries.add(new VizEntry() {
				@Override
				public void update(String key, String newValue) {
					if ("Blender".equals(key)) {
						this.value = newValue;
					}
				}
			});
			(entries.get(i)).setId(i);
			(entries.get(i)).setName("Test VizEntry " + i);
			(entries.get(i)).setValue("2D");
		}

		// Add the Entries to the DataComponent
		dataComponent = new DataComponent();
		for (i = 0; i < numEntries; i++) {
			dataComponent.addEntry(entries.get(i));
		}

		// Update the DataComponent
		dataComponent.update("Blender", value);

		// Check the updated Entries
		retEntries = dataComponent.retrieveAllEntries();
		assertEquals(numEntries, retEntries.size());
		for (i = 0; i < retEntries.size(); i++) {
			assertEquals(value, retEntries.get(i).getValue());
		}

	}

	/**
	 * <p>
	 * This operation tests the DataComponent to insure that it can properly
	 * dispatch notifications when it receives an update that changes its state.
	 * </p>
	 * 
	 */
	@Test
	public void checkNotifications() {

		// Local Declarations
		VizEntry testVizEntry = new VizEntry();
		TestVizComponentListener secondTestVizComponentListener = new TestVizComponentListener();

		// Setup the listener
		testComponentListener = new TestVizComponentListener();

		// Setup the DataComponent
		dataComponent = new DataComponent();

		// Register the listener
		dataComponent.register(testComponentListener);

		// Create a new VizEntry in the DataComponent
		dataComponent.addEntry(testVizEntry);
		// Check the Listener
		assertTrue(testComponentListener.wasNotified());
		// Reset the listener
		testComponentListener.reset();

		// Change the value of the VizEntry
		testVizEntry.setValue("VizEntry Value Change Test");
		// Check the Listener
		assertTrue(testComponentListener.wasNotified());
		// Reset the listener
		testComponentListener.reset();

		// Add the second listener
		dataComponent.register(secondTestVizComponentListener);
		// Change the value of the VizEntry
		testVizEntry.setValue("Second listener test value");
		// Check the Listeners
		assertTrue(testComponentListener.wasNotified());
		assertTrue(secondTestVizComponentListener.wasNotified());
		// Reset the listeners
		testComponentListener.reset();
		secondTestVizComponentListener.reset();

		// Remove the VizEntry
		dataComponent.clearEntries();
		// Check the listener to make sure it was updated
		assertTrue(testComponentListener.wasNotified());
		// Reset the listener
		testComponentListener.reset();

		// Change the name of the component
		dataComponent.setName("Warren Buffett");
		// Check the listener to make sure it was updated
		assertTrue(testComponentListener.wasNotified());
		// Reset the listener
		testComponentListener.reset();

		// Change the id of the component
		dataComponent.setId(899);
		// Change the name of the component
		dataComponent.setName("Warren Buffett");
		// Check the listener to make sure it was updated
		assertTrue(testComponentListener.wasNotified());

		return;

	}

	/**
	 * <p>
	 * This operation checks the DataComponent to insure that its equals() and
	 * hashcode() operations work.
	 * </p>
	 * 
	 */
	@Test
	public void checkEquality() {

		// Create DataComponents to test
		DataComponent component = new DataComponent();
		DataComponent equalComponent = new DataComponent();
		DataComponent unEqualComponent = new DataComponent();
		DataComponent transitiveComponent = new DataComponent();

		// Create Entries to add to DataComponents
		ArrayList<VizEntry> entries = new ArrayList<VizEntry>();

		// Create list of Entries
		for (int i = 0; i < 10; i++) {
			// Create VizEntry, add to list, and set data
			entries.add(new VizEntry());
			(entries.get(i)).setId(i);
			(entries.get(i)).setName("Test VizEntry " + i);
			(entries.get(i)).setValue("Value" + i);

			// Create 3 equal DataComponents by adding Entries to DC's
			component.addEntry(entries.get(i));
			equalComponent.addEntry(entries.get(i));
			transitiveComponent.addEntry(entries.get(i));
		}

		// Add only half of the Entries to the unequal DataComponent
		for (int i = 0; i < 5; i++) {
			unEqualComponent.addEntry(entries.get(i));
		}

		// Set ICEObject data
		component.setId(1);
		equalComponent.setId(1);
		transitiveComponent.setId(1);
		unEqualComponent.setId(2);

		component.setName("DC Equal");
		equalComponent.setName("DC Equal");
		transitiveComponent.setName("DC Equal");
		unEqualComponent.setName("DC UnEqual");

		// Assert two equal DataComponents return true
		assertTrue(component.equals(equalComponent));

		// Assert two unequal DataComponents return false
		assertFalse(component.equals(unEqualComponent));

		// Assert equals() is reflexive
		assertTrue(component.equals(component));

		// Assert the equals() is Symmetric
		assertTrue(component.equals(equalComponent)
				&& equalComponent.equals(component));

		// Assert equals() is transitive
		if (component.equals(equalComponent)
				&& equalComponent.equals(transitiveComponent)) {
			assertTrue(component.equals(transitiveComponent));
		} else {
			fail();
		}

		// Assert equals is consistent
		assertTrue(component.equals(equalComponent)
				&& component.equals(equalComponent)
				&& component.equals(equalComponent));
		assertTrue(!component.equals(unEqualComponent)
				&& !component.equals(unEqualComponent)
				&& !component.equals(unEqualComponent));

		// Assert checking equality with null is false
		assertFalse(component==null);

		// Assert that two equal objects return same hashcode
		assertTrue(component.equals(equalComponent)
				&& component.hashCode() == equalComponent.hashCode());

		// Assert that hashcode is consistent
		assertTrue(component.hashCode() == component.hashCode());

		// Assert that hashcodes from unequal objects are different
		assertTrue(component.hashCode() != unEqualComponent.hashCode());

	}

	/**
	 * <p>
	 * This operation checks the DataComponent to ensure that its copy() and
	 * clone() operations work as specified.
	 * </p>
	 * 
	 */
	@Test
	public void checkCopying() {
		/*
		 * The following sets of operations will be used to test the
		 * "clone and copy" portion of DataComponent.
		 */

		// Local declarations
		int id = 20110901;
		String name = "September 1st 2011";
		String description = "The 1st day of the ninth month in the year of "
				+ "our Lord 2011";
		DataComponent cloneData = new DataComponent();
		DataComponent copyData = new DataComponent();
		TestVizComponentListener listener = new TestVizComponentListener();

		// Create the DataComponent
		dataComponent = new DataComponent();

		// Set the id, name and description and listener
		dataComponent.setId(id);
		dataComponent.setDescription(description);
		dataComponent.setName(name);
		dataComponent.register(listener);

		// create entries
		VizEntry entry1 = new VizEntry();
		VizEntry entry2 = new VizEntry();

		// add entries to DataComponent
		dataComponent.addEntry(entry1);
		dataComponent.addEntry(entry2);

		// Test to show valid usage of clone

		// Run clone operation
		cloneData = (DataComponent) dataComponent.clone();

		// check Contents
		assertEquals(dataComponent.getDescription(), cloneData.getDescription());
		assertEquals(dataComponent.getId(), cloneData.getId());
		assertEquals(dataComponent.getName(), cloneData.getName());
		assertTrue(cloneData.contains(entry1.getName()));
		assertTrue(cloneData.contains(entry2.getName()));

		// check listeners
		cloneData.setName("Testing");
		// check the listener
		assertTrue(listener.wasNotified());
		cloneData.setName(dataComponent.getName());
		listener.reset();

		// Test to show valid usage of copy
		// Use dataComponent from above

		// run copy operation
		copyData.copy(dataComponent);

		// check Contents
		assertEquals(dataComponent.getDescription(), copyData.getDescription());
		assertEquals(dataComponent.getId(), copyData.getId());
		assertEquals(dataComponent.getName(), copyData.getName());
		assertTrue(copyData.contains(entry1.getName()));
		assertTrue(copyData.contains(entry2.getName()));

		/*----- Test to show an invalid use of copy - null args -----*/

		// Call copy with null, which should not change anything
		copyData.copy(null);

		// check contents, nothing has changed
		assertEquals(dataComponent.getDescription(), copyData.getDescription());
		assertEquals(dataComponent.getId(), copyData.getId());
		assertEquals(dataComponent.getName(), copyData.getName());
		assertTrue(copyData.contains(entry1.getName()));
		assertTrue(copyData.contains(entry2.getName()));

		return;
	}

	/**
	 * This operation checks the ability of the DataComponent to persist itself
	 * to XML and to load itself from an XML input stream.
	 * @throws IOException 
	 * @throws JAXBException 
	 * @throws NullPointerException 
	 */
	@Test
	public void checkLoadingFromXML() throws NullPointerException, JAXBException, IOException {
		/*
		 * The following sets of operations will be used to test the
		 * "read and write" portion of the DataComponent. It will demonstrate
		 * the behavior of reading and writing from an
		 * "XML (inputStream and outputStream)" file. It will use an annotated
		 * DataComponent to demonstrate basic behavior.
		 */

		// Local declarations
		int id = 20110901;
		String name = "September 1st 2011";
		String description = "The 1st day of the ninth month in the year of "
				+ "our Lord 2011";
		VizJAXBHandler xmlHandler = new VizJAXBHandler();
		ArrayList<Class> classList = new ArrayList<Class>();
		classList.add(DataComponent.class);

		// Create the DataComponent
		dataComponent = new DataComponent();
		DataComponent loadDataComponent = new DataComponent();

		// Set the id, name and description
		dataComponent.setId(id);
		dataComponent.setDescription(description);
		dataComponent.setName(name);

		// create entries
		VizEntry entry1 = new VizEntry();
		VizEntry entry2 = new VizEntry();

		// add entries to DataComponent
		dataComponent.addEntry(entry1);
		dataComponent.addEntry(entry2);

		// Demonstrate a basic "write" to file. Should not fail

		// persist to an output stream
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		xmlHandler.write(dataComponent, classList, outputStream);

		// Initialize object and pass inputStream to read()
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				outputStream.toByteArray());

		// create a new instance of a different variable to compare
		loadDataComponent = new DataComponent();

		// load into DataComponent();
		loadDataComponent = (DataComponent) xmlHandler.read(classList, inputStream);

		// check contents
		assertTrue(dataComponent.equals(loadDataComponent));
		
	}
}