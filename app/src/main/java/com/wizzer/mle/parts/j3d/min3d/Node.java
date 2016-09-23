package com.wizzer.mle.parts.j3d.min3d;

import com.wizzer.mle.min3d.core.Color4BufferList;
import com.wizzer.mle.min3d.core.Number3dBufferList;
import com.wizzer.mle.min3d.core.FacesBufferedList;
import com.wizzer.mle.min3d.core.Object3d;
import com.wizzer.mle.min3d.core.Object3dContainer;
import com.wizzer.mle.min3d.interfaces.IObject3dContainer;
import com.wizzer.mle.parts.j3d.props.I3dNodeTypeProperty;
import com.wizzer.mle.parts.j3d.roles.I3dRole;
import com.wizzer.mle.runtime.MleTitle;
import com.wizzer.mle.runtime.core.MleRuntimeException;

import android.opengl.Matrix;
import android.util.Log;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

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

    // Handle to OpenGL ES 2.0 program shader.
    private int m_programHandle;
    // This will be used to pass in the transformation matrix.
    private int m_MVPMatrixHandle;
    // This will be used to pass in model position information.
    private int m_positionHandle;
    // This will be used to pass in model color information.
    private int m_colorHandle;

    float m_color[] = { 0.2f, 0.709803922f, 0.898039216f, 1.0f };

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
    }

    public void setTextures(TextureMap texture)
    { _textures = texture.getTextures(); }

    public void setNodeType(I3dNodeTypeProperty.NodeType nodeType)
    { m_nodeType = nodeType; }

    public I3dNodeTypeProperty.NodeType getNodeType()
    { return m_nodeType; }

    public void setRole(I3dRole role)
    { m_role = role; }

    public I3dRole getRole()
    { return m_role; }

    public void setParent(Node parent)
    { m_parent = parent; }

    public Node getParent()
    { return m_parent; }

    public void init()
        throws MleRuntimeException
    {
        // Create buffer arrays.
        constructBuffers();
    }

    private FloatBuffer m_vertexBuffer;
    private ByteBuffer m_colorsBuffer;
    private FloatBuffer m_normalsBuffer;
    //private FloatBuffer m_texcoordsBuffer;
    private ShortBuffer m_facesBuffer;

    // Create the buffers we will use to model the data. Note that this data may already be
    // available in the Buffer List objects; so visit this routine later for optimizing
    // memory usage.
    private void constructBuffers()
    {
        // Create buffer from list of vertices.
        ByteBuffer vbb = ByteBuffer.allocateDirect(_vertices.size() * Number3dBufferList.PROPERTIES_PER_ELEMENT
            * Number3dBufferList.BYTES_PER_PROPERTY);  // # vertices x 3 elements x 4 bytes
        vbb.order(ByteOrder.nativeOrder());
        m_vertexBuffer = vbb.asFloatBuffer();
        m_vertexBuffer.put(_vertices.points().buffer());
        m_vertexBuffer.position(0);

        // Create buffer from list of colors.
        if (_vertices.hasColors()) {
            ByteBuffer cbb = ByteBuffer.allocateDirect(_vertices.size() * Color4BufferList.PROPERTIES_PER_ELEMENT
                * Color4BufferList.BYTES_PER_PROPERTY);  // # vertices * 4 elements * 1 byte
            cbb.order(ByteOrder.nativeOrder());
            m_colorsBuffer = cbb;
            m_colorsBuffer.put(_vertices.colors().buffer());
            m_colorsBuffer.position(0);
        }

        // Create buffer from list of normals.
        if (_vertices.hasNormals())
        {
            ByteBuffer nbb = ByteBuffer.allocateDirect(_vertices.size() * Number3dBufferList.PROPERTIES_PER_ELEMENT
                    * Number3dBufferList.BYTES_PER_PROPERTY);  // # vertices * 3 elements * 4 bytes
            nbb.order(ByteOrder.nativeOrder());
            m_normalsBuffer = nbb.asFloatBuffer();
            m_normalsBuffer.put(_vertices.normals().buffer());
            m_normalsBuffer.position(0);
        }

        // ToDo: create buffer from list of UVs (texture coordinates)

        // Create buffer from list of faces.
        ByteBuffer fbb = ByteBuffer.allocateDirect(_faces.size() * FacesBufferedList.PROPERTIES_PER_ELEMENT
                * FacesBufferedList.BYTES_PER_PROPERTY);  // # faces * 3 elements * 2 bytes
        fbb.order(ByteOrder.nativeOrder());
        m_facesBuffer = fbb.asShortBuffer();
        m_facesBuffer.put(_faces.buffer());
        m_facesBuffer.position(0);
    }

    /*
     * Store the model matrix. This matrix is used to move models from object space
     * (where each model can be thought of being located at the center of the universe)
     * to world space.
     */
    private float[] m_modelMatrix = new float[16];
    /* Allocate storage for the final combined matrix. This will be passed into the shader program. */
    private float[] m_MVPMatrix = new float[16];
    /* How many elements per vertex. Currently 3 elements * 4 bytes. */
    private final int m_strideBytes =
        Number3dBufferList.PROPERTIES_PER_ELEMENT * Number3dBufferList.BYTES_PER_PROPERTY;
    // ToDo: bump the number of stride bytes for color information.
    //   (Number3dBufferList.PROPERTIES_PER_ELEMENT * Number3dBufferList.BYTES_PER_PROPERTY) +
    //   (Color4BufferList.PROPERTIES_PER_ELEMENT * Color4BufferList.BYTES_PER_PROPERTY)
    /* Offset of the position data. */
    private final int m_positionOffset = 0;
    /* Size of the position data in elements (3). */
    private final int m_positionDataSize = Number3dBufferList.PROPERTIES_PER_ELEMENT;
    /* Offset of the color data (3). */
    private final int m_colorOffset = Number3dBufferList.PROPERTIES_PER_ELEMENT;
    /* Size of the color data in elements (4). */
    private final int m_colorDataSize = Color4BufferList.PROPERTIES_PER_ELEMENT;

    /**
     * Initialize rendering.
     * <p>
     * OpenGL ES shaders will be created, compiled and installed into the rendering
     * pipeline. Also, the shader program will be initialized and installed.
     * </p>
     * <p>
     * This call must be made during the OpenGL ES thread of execution. Otherwise, calls
     * to OpenGL ES will fail and the routine will throw an exception.
     * </p>
     *
     * @throws MleRuntimeException This exception is thrown if calls to OpenGL ES fail.
     */
    public void initRender()
        throws MleRuntimeException
    {
        // Initialize OpenGL ES 2.0 shaders.
        m_programHandle = initShaders();

        // Set program handles. These will later be used to pass in values to the program.
        m_MVPMatrixHandle = GLES20.glGetUniformLocation(m_programHandle, "u_MVPMatrix");
        m_positionHandle = GLES20.glGetAttribLocation(m_programHandle, "a_Position");
        m_colorHandle = GLES20.glGetUniformLocation(m_programHandle, "v_Color");

        // Tell OpenGL to use this program when rendering.
        GLES20.glUseProgram(m_programHandle);
    }

    /**
     * Render the Node.
     *
     * @param m_viewMatrix The view matrix to use during rendering.
     * @param m_projectionMatrix The project matrix to us during rendering.
     */
    public void render(float[] m_viewMatrix, float[] m_projectionMatrix)
    {
        float[] translation = new float[3];
        float[] rotation = new float[3];
        float[] scale = new float[3];

        translation[0] = this.position().x;
        translation[1] = this.position().y;
        translation[2] = this.position().z;
        rotation[0] = this.rotation().x;
        rotation[1] = this.rotation().y;
        rotation[2] = this.rotation().z;
        scale[0] = this.scale().x;
        scale[1] = this.scale().y;
        scale[2] = this.scale().z;

        Matrix.setIdentityM(m_modelMatrix, 0);
        Matrix.translateM(m_modelMatrix, 0, translation[0], translation[1], translation[2]);
        Matrix.rotateM(m_modelMatrix, 0, rotation[0], 1, 0, 0);
        Matrix.rotateM(m_modelMatrix, 0, rotation[1], 0, 1, 0);
        Matrix.rotateM(m_modelMatrix, 0, rotation[2], 0, 0, 1);
        Matrix.scaleM(m_modelMatrix, 0, scale[0], scale[1], scale[2]);

        // Pass in the position information.
        m_vertexBuffer.position(m_positionOffset);
        GLES20.glVertexAttribPointer(m_positionHandle, m_positionDataSize, GLES20.GL_FLOAT, false,
            m_strideBytes, m_vertexBuffer);

        GLES20.glEnableVertexAttribArray(m_positionHandle);

        /*
        if (this.hasVertexColors())
        {
            // Pass in the color information
            m_vertexBuffer.position(m_colorOffset);
            GLES20.glVertexAttribPointer(m_colorHandle, m_colorDataSize, GLES20.GL_FLOAT, false,
                m_strideBytes, m_vertexBuffer);

            GLES20.glEnableVertexAttribArray(m_colorHandle);
        }
        */

        // Set color for drawing the triangle
        GLES20.glUniform4fv(m_colorHandle, 1, m_color, 0);

        // This multiplies the view matrix by the model matrix, and stores the result in the MVP
        // matrix (which currently contains model * view).
        Matrix.multiplyMM(m_MVPMatrix, 0, m_viewMatrix, 0, m_modelMatrix, 0);

        // This multiplies the modelview matrix by the projection matrix, and stores the result in
        // the MVP matrix (which now contains model * view * projection).
        Matrix.multiplyMM(m_MVPMatrix, 0, m_projectionMatrix, 0, m_MVPMatrix, 0);

        GLES20.glUniformMatrix4fv(m_MVPMatrixHandle, 1, false, m_MVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

        // Disable handle to the triangle vertices.
        GLES20.glDisableVertexAttribArray(m_positionHandle);
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
                Node.dump((IObject3dContainer) object);
            else
                Log.i(MleTitle.DEBUG_TAG, object.toString());
        }
    }

    // Hide the default constructor.
    private Node() {}

    // Initialize vertex shader and return handle.
    private int initVertexShader()
        throws MleRuntimeException
    {
        final String vertexShader =
              "uniform mat4 u_MVPMatrix;      \n"     // A constant representing the combined model/view/projection matrix.
            + "attribute vec4 a_Position;     \n"     // Per-vertex position information we will pass in.

            + "void main()                    \n"     // The entry point for our vertex shader.
            + "{                              \n"
            + "   gl_Position = u_MVPMatrix   \n"     // gl_Position is a special variable used to store the final position.
            + "               * a_Position;   \n"     // Multiply the vertex by the matrix to get the final point in
            + "}                              \n";    // normalized screen coordinates.

        // Load in the vertex shader.
        int vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);

        if (vertexShaderHandle != 0)
        {
            // Pass in the shader source.
            GLES20.glShaderSource(vertexShaderHandle, vertexShader);

            // Compile the shader.
            GLES20.glCompileShader(vertexShaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(vertexShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == GLES20.GL_FALSE)
            {
                GLES20.glDeleteShader(vertexShaderHandle);
                vertexShaderHandle = 0;
            }
        }

        if (vertexShaderHandle == 0)
        {
            throw new MleRuntimeException("Error creating vertex shader.");
        }

        return vertexShaderHandle;
    }

    // Initialize fragment shader and return handle.
    private int initFragmentShader()
        throws MleRuntimeException
    {
        final String fragmentShader =
              "precision mediump float;       \n"     // Set the default precision to medium. We don't need as high of a
                                                      // precision in the fragment shader.
            + "uniform vec4 v_Color;          \n"     // This is the color from the vertex shader.
            + "void main()                    \n"     // The entry point for our fragment shader.
            + "{                              \n"
            + "   gl_FragColor = v_Color;     \n"     // Pass the color directly through the pipeline.
            + "}                              \n";

        // Load in the fragment shader.
        int fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        if (fragmentShaderHandle != 0)
        {
            // Pass in the shader source.
            GLES20.glShaderSource(fragmentShaderHandle, fragmentShader);

            // Compile the shader.
            GLES20.glCompileShader(fragmentShaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(fragmentShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == GLES20.GL_FALSE)
            {
                GLES20.glDeleteShader(fragmentShaderHandle);
                fragmentShaderHandle = 0;
            }
        }

        if (fragmentShaderHandle == 0)
        {
            throw new MleRuntimeException("Error creating fragment shader.");
        }

        return fragmentShaderHandle;
    }

    // Initialize shaders and return program handle.
    private int initShaders()
        throws MleRuntimeException
    {
        // Initialize vertex shader.
        int vertexShaderHandle = initVertexShader();

        // Initialize fragment shader.
        int fragmentShaderHandle = initFragmentShader();

        // Create a program object and store the handle to it.
        int programHandle = GLES20.glCreateProgram();

        if (programHandle != 0) {
            // Bind the vertex shader to the program.
            GLES20.glAttachShader(programHandle, vertexShaderHandle);

            // Bind the fragment shader to the program.
            GLES20.glAttachShader(programHandle, fragmentShaderHandle);

            // Bind attributes
            GLES20.glBindAttribLocation(programHandle, 0, "a_Position");

            // Link the two shaders together into a program.
            GLES20.glLinkProgram(programHandle);

            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, delete the program.
            if (linkStatus[0] == GLES20.GL_FALSE) {
                GLES20.glDeleteProgram(programHandle);
                programHandle = 0;
            }
        }

        if (programHandle == 0)
        {
            throw new MleRuntimeException("Error creating program for shaders.");
        }

        return programHandle;
    }
}
