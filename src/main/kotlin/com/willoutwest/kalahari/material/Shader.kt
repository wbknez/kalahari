package com.willoutwest.kalahari.material

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.render.Tracer
import com.willoutwest.kalahari.scene.Scene

/**
 * Represents a mechanism for computing the reflectance color of a ray
 * intersecting an arbitrary geometric surface.
 */
interface Shader {

    /**
     * Computes the reflectance color of the specified material from the
     * specified traced ray intersection.
     *
     * @param scene
     *        The scene to render.
     * @param tracer
     *        The tracer to render with.
     * @param record
     *        The intersection to use.
     * @param store
     *        The color to store the result in.
     * @return A reference to [store] for easy chaining.
     */
    fun shade(scene: Scene, tracer: Tracer, record: Intersection,
              store: Color3): Color3
}
