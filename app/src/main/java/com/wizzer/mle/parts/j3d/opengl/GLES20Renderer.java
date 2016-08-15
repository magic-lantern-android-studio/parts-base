package com.wizzer.mle.parts.j3d.opengl;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;

import android.opengl.GLES20;
import android.content.res.Resources;

import com.wizzer.mle.parts.j3d.min3d.ObjLoader;
import com.wizzer.mle.parts.j3d.min3d.Model;

public class GLES20Renderer extends GLRenderer
{
    private Resources m_resources;
    private Model m_model;

    @Override
    public void onCreate(int width, int height,
                         boolean contextLost)
    {
        GLES20.glClearColor(0f, 0f, 0f, 1f);

        try {
            // Retrieve .obj file data. Actual file is 'box.obj', but raw resource type
            // ignores file extensions.
            //int rID = m_resources.getIdentifier("box", "raw", "com.wizzer.mle.parts.min3d");
            String rID = new String("com.wizzer.mle:raw/box");
            ObjLoader loader = new ObjLoader(m_resources, rID, false);
            m_model = loader.loadObject();
        } catch (IOException ex) {

        }
    }

    @Override
    public void onDrawFrame(boolean firstDraw)
    {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT
                | GLES20.GL_DEPTH_BUFFER_BIT);
    }

    public void setResources(Resources resources)
    { m_resources = resources; }

    public Resources getResources()
    { return m_resources; }


    // Load file from apps res/raw folder or Assets folder.
    private byte[] loadFile(String filename, boolean loadFromRawFolder) throws IOException
    {
        // Create a InputStream to read the file into.
        InputStream input;

        if (loadFromRawFolder)
        {
            // Get the resource id from the file name.
            int rID = m_resources.getIdentifier(filename, "raw", "com.wizzer.mle");
             // Get the file as a stream.
            if (rID != 0)
                input = m_resources.openRawResource(rID);
            else
                return null;
        }
        else
        {
            // Get the file as a stream.
            input = m_resources.getAssets().open(filename);
        }

        // Create a buffer that has the same size as the InputStream.
        byte[] buffer = new byte[input.available()];

        // Read the text file as a stream, into the buffer.
        input.read(buffer);

        // Close the Input and Output streams.
        input.close();

        // Return the output stream as a byte array.
        return buffer;
    }
}
