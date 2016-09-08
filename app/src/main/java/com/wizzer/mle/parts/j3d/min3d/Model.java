package com.wizzer.mle.parts.j3d.min3d;

import android.util.Log;

import com.wizzer.mle.min3d.core.Object3dContainer;
import com.wizzer.mle.min3d.core.Object3d;
import com.wizzer.mle.min3d.interfaces.IObject3dContainer;
import com.wizzer.mle.min3d.core.Vertices;
import com.wizzer.mle.min3d.core.FacesBufferedList;
import com.wizzer.mle.min3d.core.TextureList;
import com.wizzer.mle.runtime.MleTitle;

/**
 * Created by msm on 8/12/16.
 */
public class Model extends Object3dContainer
{
    public Model(Object3dContainer container)
    {
        _vertices = container.vertices().clone();
        _faces = container.faces().clone();
        // Todo: sharing texture list, should clone it instead?
        _textures = container.textures();

        this.position().x = container.position().x;
        this.position().y = container.position().y;
        this.position().z = container.position().z;

        this.rotation().x = container.rotation().x;
        this.rotation().y = container.rotation().y;
        this.rotation().z = container.rotation().z;

        this.scale().x = scale().x;
        this.scale().y = scale().y;
        this.scale().z = scale().z;

        for (int i = 0; i < container.numChildren(); i++)
        {
            this.addChild(container.getChildAt(i));
        }
    }

    public static void dump(IObject3dContainer objContainer)
    {
        if (objContainer == null) return;

        Log.i(MleTitle.DEBUG_TAG, objContainer.toString());

        // Process children.
        int numChildren = objContainer.numChildren();
        for (int i = 0; i < numChildren; i++) {
            Object3d object = objContainer.getChildAt(i);
            if (object instanceof IObject3dContainer)
                Model.dump((IObject3dContainer) object);
            else
                Log.i(MleTitle.DEBUG_TAG, object.toString());
        }
    }
}
