package com.wizzer.mle.parts.j3d.roles;

import com.wizzer.mle.runtime.core.IMleRole;
import com.wizzer.mle.runtime.core.MleRuntimeException;

/**
 * This interface defines a 3D role.
 */
public interface I3dRole
{
    /**
     * Initialize the role.
     */
    void init();

    /**
     * Dispose of role resources.
     */
    void dispose();

    /**
     * Add a child role to the 3D role hierarchy.
     *
     * @param child The child to add to the Role hierarchy.
     */
    void addChild(IMleRole child);

    /**
     * Remove a child from the 3D Role hierarchy.
     *
     * @param child The child to remove from the Role hierarchy.
     */
    void removeChild(IMleRole child);

    /**
     * Get the child located at the specified index in the 3D Role hierarchy.
     *
     * @param index The index of the child to retrieve.
     *
     * @return A Role is returned. <b>null</b> will be returned if the index
     * is out of range.
     */
    IMleRole getChildAt(int index);

    /**
     * Remove all children from the 3D Role hierarchy.
     */
    void clearChildren();

    /**
     * Retrieve the number of children in the 3D Role hierarchy.
     *
     * @return An integer is returned indicating the number of children.
     */
    int numChildren();

    /**
     * Initialize rendering in the role.
     *
     * @throws MleRuntimeException This exception is thrown if an issues occurs while
     * initializing rendering.
     */
    void initRender() throws MleRuntimeException;

    /**
     * Render the role.
     *
     * @throws MleRuntimeException This exception is thrown if an error occurs while
     * rendering the role.
     */
    void render() throws MleRuntimeException;
}
