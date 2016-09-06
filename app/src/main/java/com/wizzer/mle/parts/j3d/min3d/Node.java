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
    // The type of Node.
    private I3dNodeTypeProperty.NodeType m_nodeType;
    // The associated 3d role.
    private I3dRole m_role;
    // The parent of this Node, may be null.
    private Node m_parent;
    // Children Nodes are handled indirectly by Object3dContainer.

    public Node(I3dNodeTypeProperty.NodeType nodeType, I3dRole role)
    {
        super();

        m_nodeType = nodeType;
        m_role = role;
        m_parent = null;
    }

    public Node(I3dNodeTypeProperty.NodeType nodeType, I3dRole role, Node parent)
    {
        super();

        m_nodeType = nodeType;
        m_role = role;
        m_parent = parent;
    }

    public Node(Node node)
    {
        super();

        // Copy Node fields.
        m_nodeType = node.getNodeType();
        m_role = node.m_role;
        m_parent = node.m_parent;

        // Copy Object3dContainer fields.
        if (node.vertices() != null)
            _vertices = node.vertices().clone();
        if (node.faces() != null)
            _faces = node.faces().clone();
        // Todo: sharing texture list, should clone it instead?
        _textures = node.textures();

        this.position().x = node.position().x;
        this.position().y = node.position().y;
        this.position().z = node.position().z;

        this.rotation().x = node.rotation().x;
        this.rotation().y = node.rotation().y;
        this.rotation().z = node.rotation().z;

        this.scale().x = node.scale().x;
        this.scale().y = node.scale().y;
        this.scale().z = node.scale().z;

        for (int i = 0; i < node.numChildren(); i++)
        {
            this.addChild(node.getChildAt(i));
        }
    }

    public void setAsModel(Model model, I3dRole role)
    {
        // Set Node fields.
        m_nodeType = I3dNodeTypeProperty.NodeType.GEOMETRY;
        m_role = role;
        m_parent = null;

        // Copy Object3dContainer fields.
        if (model.vertices() != null)
            _vertices = model.vertices().clone();
        if (model.faces() != null)
            _faces = model.faces().clone();
        // Todo: sharing texture list, should clone it instead?
        _textures = model.textures();

        this.position().x = model.position().x;
        this.position().y = model.position().y;
        this.position().z = model.position().z;

        this.rotation().x = model.rotation().x;
        this.rotation().y = model.rotation().y;
        this.rotation().z = model.rotation().z;

        this.scale().x = model.scale().x;
        this.scale().y = model.scale().y;
        this.scale().z = model.scale().z;

        for (int i = 0; i < model.numChildren(); i++)
        {
            this.addChild(model.getChildAt(i));
        }
    }

    public void setTextures(TextureMap texture) { _textures = texture.getTextures(); }

    public void setNodeType(I3dNodeTypeProperty.NodeType nodeType) { m_nodeType = nodeType; }
    public I3dNodeTypeProperty.NodeType getNodeType() { return m_nodeType; }

    public void setRole(I3dRole role) { m_role = role; }
    public I3dRole getRole() { return m_role; }

    public void setParent(Node parent) { m_parent = parent; }
    public Node getParent() { return m_parent; }

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

    // Hide the default constructor.
    private Node() {}
}
