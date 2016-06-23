/**
 */
package geometry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>INode</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An INode represents a single node in a geometry tree.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link geometry.INode#getName <em>Name</em>}</li>
 *   <li>{@link geometry.INode#getId <em>Id</em>}</li>
 *   <li>{@link geometry.INode#getNodes <em>Nodes</em>}</li>
 *   <li>{@link geometry.INode#getType <em>Type</em>}</li>
 *   <li>{@link geometry.INode#getTriangles <em>Triangles</em>}</li>
 *   <li>{@link geometry.INode#getCenter <em>Center</em>}</li>
 * </ul>
 *
 * @see geometry.GeometryPackage#getINode()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface INode extends EObject {

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The name of the node.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see geometry.GeometryPackage#getINode_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link geometry.INode#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * An optional node id to identify the node numerically.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(long)
	 * @see geometry.GeometryPackage#getINode_Id()
	 * @model
	 * @generated
	 */
	long getId();

	/**
	 * Sets the value of the '{@link geometry.INode#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(long value);

	/**
	 * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link geometry.INode}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The list of nodes that compose the head node.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Nodes</em>' containment reference list.
	 * @see geometry.GeometryPackage#getINode_Nodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<INode> getNodes();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A string representing the type of entity this node represents in the tree in a human readable way. Example values might include "cube", "sphere" or "intersection."
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see geometry.GeometryPackage#getINode_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link geometry.INode#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Triangles</b></em>' containment reference list.
	 * The list contents are of type {@link geometry.Triangle}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * A series of triangles which comprise the mesh which will represent this object graphically. If this list is empty, it will be the responsibility of other classes to determine, based on this INode's "type", what mesh, if any, should be displayed for it.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Triangles</em>' containment reference list.
	 * @see geometry.GeometryPackage#getINode_Triangles()
	 * @model containment="true"
	 * @generated
	 */
	EList<Triangle> getTriangles();

	/**
	 * Returns the value of the '<em><b>Center</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Each shape is centered on a special vertex.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Center</em>' reference.
	 * @see #setCenter(Vertex)
	 * @see geometry.GeometryPackage#getINode_Center()
	 * @model required="true"
	 * @generated
	 */
	Vertex getCenter();

	/**
	 * Sets the value of the '{@link geometry.INode#getCenter <em>Center</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Center</em>' reference.
	 * @see #getCenter()
	 * @generated
	 */
	void setCenter(Vertex value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Fire a notification that will alert decorator pattern classes registered to this object that they should change their state.
	 * 
	 * This method is intended to cause change in the INode's graphical representation in a non-permanent way, by modifying qualities such as opacity which are purely concerned with rendering and do not belong to the modeling data itself.
	 * 
	 * "Property" should offer sufficient description for the client to determine "value"'s type.
	 * <!-- end-model-doc -->
	 * @model valueDataType="geometry.Object"
	 * @generated
	 */
	void changeDecoratorProperty(String property, Object value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get the names of all properties set for this shape.
	 * <!-- end-model-doc -->
	 * @model kind="operation"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='//Return a list of the properties\' keys.\r\nreturn new BasicEList<String>(properties.keySet());'"
	 * @generated
	 */
	EList<String> getPropertyNames();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Get the value for one of the shape's properties.
	 * @param property The name of the property whose value is to be returned.
	 * <!-- end-model-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='return properties.get(property);'"
	 * @generated
	 */
	double getProperty(String property);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Set one of the Shape's properties.
	 * @param property The name of the property whose value is being set.
	 * @param value The property's new value.
	 * <!-- end-model-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='properties.put(property, value);'"
	 * @generated
	 */
	void setProperty(String property, double value);
} // INode
