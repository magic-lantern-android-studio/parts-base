/*
 * I3dStage.java
 * Created on Aug 8, 2016
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
package com.wizzer.mle.parts.j3d.stages;

// Import Magic Lantern Runtime Engine classes.
import com.wizzer.mle.runtime.core.MleSet;
import com.wizzer.mle.runtime.core.MleSize;
import com.wizzer.mle.runtime.scheduler.MleTask;

// Import Magic Lantern Parts classes.
import com.wizzer.mle.parts.j3d.MleRenderEngine;

/**
 * This interface defines a simple Stage interface for 3D applications.
 *
 * @author Mark S. Millard
 */
public interface I3dStage
{
    /**
     * Registers a new Set with this Stage.
     *
     * @param renderer The rendering thread for the Set being registered.
     * @param set The Set to register.
     *
     * @return A reference to the scheduled renderer task is returned.
     */
    public MleTask addSet(MleRenderEngine renderer,MleSet set);

    /**
     * Get the size of the stage.
     *
     * @return The size of the stage's component is returned.
     * Magic Lantern 1.0 supports one component per stage: this is the
     * default component size.
     */
    public MleSize getSize();
}
