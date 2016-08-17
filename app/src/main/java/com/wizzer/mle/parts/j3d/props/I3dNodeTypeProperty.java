package com.wizzer.mle.parts.j3d.props;

import com.wizzer.mle.parts.IMlePropPart;

/**
 * Created by msm on 8/16/16.
 */
public interface I3dNodeTypeProperty extends IMlePropPart
{
    public enum NodeType
    {
        TRANSFORM,
        CAMERA,
        LIGHT,
        GEOMETRY,
        BOUNDING_BOX,
        CLIPPING_PLANE
    }
}
