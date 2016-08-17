package com.wizzer.mle.parts.j3d.min3d;

import com.wizzer.mle.min3d.core.Object3d;
import com.wizzer.mle.min3d.core.Object3dContainer;
import com.wizzer.mle.min3d.interfaces.IObject3dContainer;
import com.wizzer.mle.parts.j3d.props.I3dNodeTypeProperty;

/**
 * Created by msm on 8/12/16.
 */
public class Node extends Object3dContainer
{
    private I3dNodeTypeProperty m_nodeType;

    public Node(Object3dContainer container)
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

    public void setNodeType(I3dNodeTypeProperty nodetype) { m_nodeType = nodetype; }

    public I3dNodeTypeProperty getNodeType() { return m_nodeType; }

    public static void dump(IObject3dContainer objContainer)
    {
        if (objContainer == null) return;

        System.out.println(objContainer.toString());

        // Process children.
        int numChildren = objContainer.numChildren();
        for (int i = 0; i < numChildren; i++) {
            Object3d object = objContainer.getChildAt(i);
            if (object instanceof IObject3dContainer)
                Node.dump((IObject3dContainer) object);
            else
                System.out.println(object.toString());
        }
    }
}
