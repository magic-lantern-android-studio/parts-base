/*
 * MleImageMediaRef.java
 * Created on Feb 23, 2005
 */

// COPYRIGHT_BEGIN
//
//  Copyright (C) 2000-2007  Wizzer Works
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
package com.wizzer.mle.parts.j3d.mrefs;

// Import Android classes.

// Import Magic Lantern Runtime Engine classes.
import com.wizzer.mle.runtime.core.MleRuntimeException;

// Import Magic Lantern Parts classes.
import com.wizzer.mle.parts.j3d.min3d.Model;

/**
 * This interface defines a Media Reference that can load models
 * from a reference that specifies a file name.
 * 
 * @author Mark S. Millard
 */
public interface IModelMediaRef
{    
    /**
     * Read the Model data from a local file name.
     * 
     * @return The loaded model is returned as a <code>Model</code>.
     * 
     * @throws MleRuntimeException This exception is thrown if the
     * media reference data can not be successfully read.
     */
    public Model read() throws MleRuntimeException;
}
