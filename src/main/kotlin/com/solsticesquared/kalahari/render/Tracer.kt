package com.solsticesquared.kalahari.render

import com.solsticesquared.kalahari.math.Coords
import com.solsticesquared.kalahari.math.color.Color3

/**
 * Represents a mechanism for computing the reflected color of light based on
 * the path of a single ray cast into a scene.
 */
interface Tracer {

    /**
     * Computes the reflected color of light based on the path of the
     * specified ray cast into the specified scene.
     *
     * @param coord
     *        The coordinates to trace.
     * @return The reflected color.
     */
    fun trace(coord: Coords): Color3
}
