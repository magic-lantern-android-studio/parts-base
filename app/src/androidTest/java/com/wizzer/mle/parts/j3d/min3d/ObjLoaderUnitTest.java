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
        assertNotNull(model.faces());
        assertFalse(model.hasVertexColors());
        assertFalse(model.hasUvs());
        assertTrue(model.hasNormals());
    }
}
