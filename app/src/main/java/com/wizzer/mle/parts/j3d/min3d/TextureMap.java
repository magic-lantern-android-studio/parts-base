package com.wizzer.mle.parts.j3d.min3d;

import com.wizzer.mle.min3d.core.Object3dContainer;
import com.wizzer.mle.min3d.core.TextureList;

/**
 * Created by msm on 8/26/16.
 */
public class TextureMap
{
    private TextureList m_textures;

    public TextureMap(Object3dContainer container)
    {
        // Todo: sharing texture list, should clone it instead?
        m_textures = container.textures();
    }

    public TextureList getTextures() { return m_textures; }
}
