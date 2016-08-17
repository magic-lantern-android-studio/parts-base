package com.wizzer.mle.parts.j3d.min3d;

import com.wizzer.mle.min3d.core.Object3d;
import com.wizzer.mle.min3d.core.Object3dContainer;
import com.wizzer.mle.min3d.interfaces.IObject3dContainer;
import com.wizzer.mle.parts.j3d.props.I3dNodeTypeProperty;
import com.wizzer.mle.parts.j3d.roles.I3dRole;

/**
 * Created by msm on 8/12/16.
 */
public class Node extends Object3dContainer
{
    private I3dNodeTypeProperty.NodeType m_nodeType;
    private I3dRole m_role;

    public Node(I3dNodeTypeProperty.NodeType nodeType, I3dRole role)
    {
        super();

        m_nodeType = nodeType;
        m_role = role;
    }

    public Node(Node node)
    {
        m_nodeType = node.getNodeType();
        m_role = node.m_role;

        _vertices = node.vertices().clone();
        _faces = node.faces().clone();
        // Todo: sharing texture list, should clone it instead?
        _textures = node.textures();

        this.position().x = node.position().x;
        this.position().y = node.position().y;
        this.position().z = node.position().z;

        this.rotation().x = node.rotation().x;
        this.rotation().y = node.rotation().y;
        this.rotation().z = node.rotation().z;

        this.scale().x = scale().x;
        this.scale().y = scale().y;
        this.scale().z = scale().z;

        for (int i = 0; i < node.numChildren(); i++)
        {
            this.addChild(node.getChildAt(i));
        }
    }

    public void setNodeType(I3dNodeTypeProperty.NodeType nodeType) { m_nodeType = nodeType; }

    public I3dNodeTypeProperty.NodeType getNodeType() { return m_nodeType; }

    public I3dRole getRole() { return m_role; }

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

    private Node() {}
}
