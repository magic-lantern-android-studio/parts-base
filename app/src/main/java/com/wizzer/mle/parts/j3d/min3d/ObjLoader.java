package com.wizzer.mle.parts.j3d.min3d;

import android.content.res.Resources;

import java.io.IOException;
import java.io.InputStream;

import com.wizzer.mle.min3d.core.Object3dContainer;
import com.wizzer.mle.min3d.parser.ObjParser;

/**
 * Created by msm on 8/12/16.
 */
public class ObjLoader
{
    private ObjParser m_parser;

    public ObjLoader(Resources resources, String resourceID, boolean generateMipMap)
    {
        m_parser = new ObjParser(resources, resourceID, generateMipMap);
    }

    public ObjLoader(Resources resources, int id, boolean generateMipMap)
    {
        String resourceID = resources.getResourceName(id);
        m_parser = new ObjParser(resources, resourceID, generateMipMap);
    }

    public Model loadObject() throws IOException
    {
        // Parse the OBJ file and initialize container.
        m_parser.parse();
        Object3dContainer container = m_parser.getParsedObject();

        // The root of a Object3D scene graph hierarchy is returned. The container, itself,
        // does not contain the parsed model data; but the first child of the container
        // (or scene graph) does.
        return new Model(container.getChildAt(0));
    }

    public Model loadObject(InputStream input) throws IOException
    {
        // Todo: Should be able to load object from an input stream.
        return null;
    }
}
