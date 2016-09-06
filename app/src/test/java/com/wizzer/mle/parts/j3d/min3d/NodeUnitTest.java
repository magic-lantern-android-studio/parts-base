package com.wizzer.mle.parts.j3d.min3d;

import com.wizzer.mle.parts.j3d.props.I3dNodeTypeProperty;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by msm on 9/6/16.
 */
public class NodeUnitTest
{
    @Test
    public void testConstructor1() throws Exception
    {
        Node node = new Node(I3dNodeTypeProperty.NodeType.NONE, null);
        assertNotNull(node);
    }

    @Test
    public void testConstructor2() throws Exception
    {
        Node node = new Node(I3dNodeTypeProperty.NodeType.NONE, null, null);
        assertNotNull(node);
    }

    @Test
    public void testCopyConstructor() throws Exception
    {
        Node node = new Node(I3dNodeTypeProperty.NodeType.NONE, null);
        assertNotNull(node);
        Node copy = new Node(node);
        assertNotNull(copy);

        //assertTrue(copy.equals(node));
    }
}
