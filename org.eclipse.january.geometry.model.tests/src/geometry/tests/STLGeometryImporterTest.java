/**
 */
package geometry.tests;

import org.eclipse.january.geometry.STLGeometryImporter;
import org.eclipse.january.geometry.GeometryFactory;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>ASCIISTL Geometry Importer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link org.eclipse.january.geometry.IGeometryImporter#load(java.nio.file.Path) <em>Load</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class STLGeometryImporterTest extends TestCase {

	/**
	 * The fixture for this ASCIISTL Geometry Importer test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STLGeometryImporter fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(STLGeometryImporterTest.class);
	}

	/**
	 * Constructs a new ASCIISTL Geometry Importer test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public STLGeometryImporterTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this ASCIISTL Geometry Importer test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(STLGeometryImporter fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this ASCIISTL Geometry Importer test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected STLGeometryImporter getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(GeometryFactory.eINSTANCE.createASCIISTLGeometryImporter());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	/**
	 * Tests the '{@link org.eclipse.january.geometry.IGeometryImporter#load(java.nio.file.Path) <em>Load</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.january.geometry.IGeometryImporter#load(java.nio.file.Path)
	 * @generated
	 */
	public void testLoad__Path() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
		fail();
	}

} //ASCIISTLGeometryImporterTest
