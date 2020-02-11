package com.willoutwest.kalahari.render

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.scene.camera.Camera
import com.willoutwest.kalahari.scene.camera.Viewport

/**
 * Represents a mechanism for applying simplistic tone mapping to colors
 * produced by the rendering pipeline.
 *
 * True tone mapping uses both global and local operators to achieve maximum
 * effect.  However, for this project the rendering pipeline exclusively uses
 * a producer/consumer stream-based model that makes global state tracking
 * cumbersome.  As such, tone mapping is only applied on a local, per-pixel
 * basis with the requisite somewhat limited results.
 */
interface Toner {

    /**
     * Applies some arbitrary tone-related logic to the specified color.
     *
     * @param camera
     *        The camera that produced the original traced rays.
     * @param viewport
     *        The viewing plane to use.
     * @param input
     *        The color to tone map.
     * @param store
     *        The color to store the result in.
     * @return A reference to [store] for easy chaining.
     */
    fun tone(camera: Camera, viewport: Viewport,
             input: Color3, store: Color3): Color3
}