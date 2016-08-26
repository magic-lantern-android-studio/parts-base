// Declare package.
package com.wizzer.mle.parts.j3d.mrefs;

// Import Magic Lantern Runtime Engine classes.
import com.wizzer.mle.runtime.core.MleRuntimeException;

// Import Magic Lantern Parts classes.
import com.wizzer.mle.parts.j3d.min3d.TextureMap;

/**
 * This interface defines a Media Reference that can load texture maps
 * from a reference that specifies a file name.
 *
 * @author Mark S. Millard
 */
public interface ITextureMapMediaRef
{
    /**
     * Read the Texture Map data from a local file name.
     *
     * @return The loaded texture map is returned as a <code>TextureMap</code>.
     *
     * @throws MleRuntimeException This exception is thrown if the
     * media reference data can not be successfully read.
     */
    public TextureMap read() throws MleRuntimeException;
}
