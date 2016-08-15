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

    public Model loadObject() throws IOException
    {
        m_parser.parse();
        Object3dContainer container = m_parser.getParsedObject();
        return new Model(container);
    }

    public Model loadObject(InputStream input) throws IOException
    {
        // Todo: Should be able to load object from an input stream.
        return null;
    }
}
