// COPYRIGHT_BEGIN
// COPYRIGHT_END

// Declare package.
package com.wizzer.mle.parts.j3d.roles;

// Import Magic Lantern classes.
import com.wizzer.mle.math.MlTransform;
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
     * Set the Transform on the 3D Role.
     * <p>
     * The transform contains the translation, rotation and scale
     * parameters of the role.
     * </p>
     *
     * @param transform The Transform matrix to set.
     *
     * @return If the transform is successfully set, then <b>true</b>
     * will be returned. Otherwise, <b>false</b> will be returned.
     */
    boolean setTransform(MlTransform transform);

    /**
     * Get the Transform on the 3D Role.
     * <p>
     * The transform contains the translation, rotation and scale
     * parameters of the role.
     * </p>
     *
     * @param transform The Transform matrix to retrieve.
     *
     * @return If the transform is successfully retrieved, then <b>true</b>
     * will be returned. Otherwise, <b>false</b> will be returned.
     */
    boolean getTransform(MlTransform transform);

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
