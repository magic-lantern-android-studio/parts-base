package com.wizzer.mle.parts.j3d.min3d;

import android.util.Log;

import com.wizzer.mle.min3d.core.Object3d;
import com.wizzer.mle.min3d.interfaces.IObject3dContainer;
import com.wizzer.mle.runtime.MleTitle;

/**
 * Created by msm on 8/12/16.
 */
public class Model extends Object3d
{
    public Model(Object3d obj)
    {
        super(obj.vertices().size(), obj.faces().size());

        _vertices = obj.vertices().clone();
        _faces = obj.faces().clone();
        // Todo: sharing texture list, should clone it instead?
        _textures = obj.textures();

        this.position().x = obj.position().x;
        this.position().y = obj.position().y;
        this.position().z = obj.position().z;

        this.rotation().x = obj.rotation().x;
        this.rotation().y = obj.rotation().y;
        this.rotation().z = obj.rotation().z;

        this.scale().x = scale().x;
        this.scale().y = scale().y;
        this.scale().z = scale().z;

        // Don't copy children since this is not an Object3dContainer.
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
