/**
 */
package org.eclipse.eavp.tests.geometry.view.model;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>model</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new ModelTests("model Tests");
		suite.addTestSuite(MeshCacheTest.class);
		suite.addTestSuite(RenderObjectTest.class);
		suite.addTestSuite(DisplayOptionTest.class);
		suite.addTestSuite(OpacityOptionTest.class);
		suite.addTestSuite(ScaleOptionTest.class);
		suite.addTestSuite(WireframeOptionTest.class);
		suite.addTestSuite(ColorOptionTest.class);
		suite.addTestSuite(ComboDisplayOptionDataTest.class);
		suite.addTestSuite(DoubleTextDisplayOptionDataTest.class);
		suite.addTestSuite(IntegerTextDisplayOptionDataTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelTests(String name) {
		super(name);
	}

} //ModelTests
