/**
 * generated by Xtext 2.10.0
 */
package org.eclipse.january.geometry.xtext.mTL;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Material</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.january.geometry.xtext.mTL.Material#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.january.geometry.xtext.mTL.Material#getAmbient <em>Ambient</em>}</li>
 *   <li>{@link org.eclipse.january.geometry.xtext.mTL.Material#getDiffuse <em>Diffuse</em>}</li>
 *   <li>{@link org.eclipse.january.geometry.xtext.mTL.Material#getSpecular <em>Specular</em>}</li>
 *   <li>{@link org.eclipse.january.geometry.xtext.mTL.Material#getSpecularExponent <em>Specular Exponent</em>}</li>
 *   <li>{@link org.eclipse.january.geometry.xtext.mTL.Material#getOpaque <em>Opaque</em>}</li>
 *   <li>{@link org.eclipse.january.geometry.xtext.mTL.Material#getTransparent <em>Transparent</em>}</li>
 *   <li>{@link org.eclipse.january.geometry.xtext.mTL.Material#getIllumination <em>Illumination</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.january.geometry.xtext.mTL.MTLPackage#getMaterial()
 * @model
 * @generated
 */
public interface Material extends EObject
{
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see org.eclipse.january.geometry.xtext.mTL.MTLPackage#getMaterial_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.eclipse.january.geometry.xtext.mTL.Material#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Ambient</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Ambient</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Ambient</em>' containment reference.
   * @see #setAmbient(Color)
   * @see org.eclipse.january.geometry.xtext.mTL.MTLPackage#getMaterial_Ambient()
   * @model containment="true"
   * @generated
   */
  Color getAmbient();

  /**
   * Sets the value of the '{@link org.eclipse.january.geometry.xtext.mTL.Material#getAmbient <em>Ambient</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Ambient</em>' containment reference.
   * @see #getAmbient()
   * @generated
   */
  void setAmbient(Color value);

  /**
   * Returns the value of the '<em><b>Diffuse</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Diffuse</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Diffuse</em>' containment reference.
   * @see #setDiffuse(Color)
   * @see org.eclipse.january.geometry.xtext.mTL.MTLPackage#getMaterial_Diffuse()
   * @model containment="true"
   * @generated
   */
  Color getDiffuse();

  /**
   * Sets the value of the '{@link org.eclipse.january.geometry.xtext.mTL.Material#getDiffuse <em>Diffuse</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Diffuse</em>' containment reference.
   * @see #getDiffuse()
   * @generated
   */
  void setDiffuse(Color value);

  /**
   * Returns the value of the '<em><b>Specular</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Specular</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Specular</em>' containment reference.
   * @see #setSpecular(Color)
   * @see org.eclipse.january.geometry.xtext.mTL.MTLPackage#getMaterial_Specular()
   * @model containment="true"
   * @generated
   */
  Color getSpecular();

  /**
   * Sets the value of the '{@link org.eclipse.january.geometry.xtext.mTL.Material#getSpecular <em>Specular</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Specular</em>' containment reference.
   * @see #getSpecular()
   * @generated
   */
  void setSpecular(Color value);

  /**
   * Returns the value of the '<em><b>Specular Exponent</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Specular Exponent</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Specular Exponent</em>' attribute.
   * @see #setSpecularExponent(double)
   * @see org.eclipse.january.geometry.xtext.mTL.MTLPackage#getMaterial_SpecularExponent()
   * @model
   * @generated
   */
  double getSpecularExponent();

  /**
   * Sets the value of the '{@link org.eclipse.january.geometry.xtext.mTL.Material#getSpecularExponent <em>Specular Exponent</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Specular Exponent</em>' attribute.
   * @see #getSpecularExponent()
   * @generated
   */
  void setSpecularExponent(double value);

  /**
   * Returns the value of the '<em><b>Opaque</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Opaque</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Opaque</em>' attribute.
   * @see #setOpaque(double)
   * @see org.eclipse.january.geometry.xtext.mTL.MTLPackage#getMaterial_Opaque()
   * @model
   * @generated
   */
  double getOpaque();

  /**
   * Sets the value of the '{@link org.eclipse.january.geometry.xtext.mTL.Material#getOpaque <em>Opaque</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Opaque</em>' attribute.
   * @see #getOpaque()
   * @generated
   */
  void setOpaque(double value);

  /**
   * Returns the value of the '<em><b>Transparent</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Transparent</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Transparent</em>' attribute.
   * @see #setTransparent(double)
   * @see org.eclipse.january.geometry.xtext.mTL.MTLPackage#getMaterial_Transparent()
   * @model
   * @generated
   */
  double getTransparent();

  /**
   * Sets the value of the '{@link org.eclipse.january.geometry.xtext.mTL.Material#getTransparent <em>Transparent</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Transparent</em>' attribute.
   * @see #getTransparent()
   * @generated
   */
  void setTransparent(double value);

  /**
   * Returns the value of the '<em><b>Illumination</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Illumination</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Illumination</em>' attribute.
   * @see #setIllumination(int)
   * @see org.eclipse.january.geometry.xtext.mTL.MTLPackage#getMaterial_Illumination()
   * @model
   * @generated
   */
  int getIllumination();

  /**
   * Sets the value of the '{@link org.eclipse.january.geometry.xtext.mTL.Material#getIllumination <em>Illumination</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Illumination</em>' attribute.
   * @see #getIllumination()
   * @generated
   */
  void setIllumination(int value);

} // Material