package com.wizzer.mle.parts.j3d.min3d;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
//import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by msm on 9/22/16.
 */
//@RunWith(MockitoJUnitRunner.class)
@RunWith(AndroidJUnit4.class)
public class ObjLoaderUnitTest extends ActivityTestCase
{
    public ObjLoaderUnitTest()
    {
        super();
    }

    //@Mock
    //Context m_MockContext;

    @Test
    public void testObjLoader() throws Exception
    {
        Context context = getInstrumentation().getContext();
        Resources resources = context.getResources();
        String rID = new String("com.wizzer.mle.parts.test:raw/box");

        ObjLoader loader = new ObjLoader(resources, rID, false);
        assertNotNull(loader);

        Model model = loader.loadObject();
        assertNotNull(model);
        assertNotNull(model.vertices());
        assertEquals(36, model.vertices().size());
        assertNotNull(model.faces());
        assertEquals(12, model.faces().size());
        assertTrue(model.hasVertexColors());
        // ObjLoader created default colors even though none existed in the box.obj file.
        assertTrue(model.hasUvs());
        // ObjLoader created default texture coordinates event though none existed in the box.obj file.
        assertTrue(model.hasNormals());
        assertEquals(36, model.normals().size());
    }
}
