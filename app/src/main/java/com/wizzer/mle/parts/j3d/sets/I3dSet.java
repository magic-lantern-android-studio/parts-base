/*
 * I3dSet.java
 * Created on Aug 19, 2016
 */

// COPYRIGHT_BEGIN
//
//  Copyright (C) 2000-2016  Wizzer Works
//
//  Wizzer Works makes available all content in this file ("Content").
//  Unless otherwise indicated below, the Content is provided to you
//  under the terms and conditions of the Common Public License Version 1.0
//  ("CPL"). A copy of the CPL is available at
//
//      http://opensource.org/licenses/cpl1.0.php
//
//  For purposes of the CPL, "Program" will mean the Content.
//
//  For information concerning this Makefile, contact Mark S. Millard,
//  of Wizzer Works at msm@wizzerworks.com.
//
//  More information concerning Wizzer Works may be found at
//
//      http://www.wizzerworks.com
//
// COPYRIGHT_END

// Declare package.
package com.wizzer.mle.parts.j3d.sets;

// Import Magic Lantern Runtime Engine classes.
import com.wizzer.mle.runtime.core.IMleRole;
import com.wizzer.mle.runtime.core.MleRuntimeException;

/**
 * This interface defines a Set that can be used for 3D applications.
 * 
 * @author Mark S. Millard
 */
public interface I3dSet
{
    /**
     * Attach <b>newRole</b> to <b>prevRole</b>.  If prevRole is <b>null</b>,
	 * newRole is attached to the end of the list.
	 * 
	 * @param prevRole The Role to attach to. May be <b>null</b>
	 * @param newRole The Role to attach.
	 * 
	 * @throws MleRuntimeException This exception is thrown if <b>newRole</b>
	 * is <b>null</b>.
	 */
    void attachRoles(IMleRole prevRole, IMleRole newRole)
    	throws MleRuntimeException;
    
    /**
     * Detach specified Role from the layer list.
     * 
     * @param role The Role to detach.
	 * 
	 * @throws MleRuntimeException This exception is thrown if <b>role</b>
	 * is <b>null</b>.
     */
    void detach(IMleRole role) throws MleRuntimeException;

	/**
	 * Retrieve the view matrix for the 3D Set.
	 * <p>
	 * Roles use this utility to calculate the combined MVP matrix
	 * for rendering.
	 * </p>
	 *
	 * @return An array of 16 floating-point values is returned representing
	 * the 4x4 view matrix.
     */
	float[] getViewMatrix();

	/**
	 * Retrieve the projection matrix for the 3D Set.
	 * <p>
	 * Roles use this utility to calculate the combined MVP matrix
	 * for rendering.
	 * </p>
	 *
	 * @return An array of 16 floating-point values is returned representing
	 * the 4x4 projection matrix.
	 */
	float[] getProjectionMatrix();
}
