package com.willoutwest.kalahari.scene.camera

import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.render.Coords

/**
 * Represents a mechanism for creating camera rays from a viewing plane of
 * a ray traced scene.
 */
interface Lens {

    /**
     * Returns a list of cameras that this lens needs to correctly transform
     * rays for a scene.
     *
     * This is currently only used to discover the correct viewing order for
     * a stereoscopic lens.
     *
     * @param camera
     *        The collection of viewing parameters to extract other cameras
     *        from.
     * @return A list of in-order cameras to process.
     */
    fun getCameras(camera: Camera): List<Camera>

    /**
     * Applies some arbitrary pre-render logic to the specified camera and
     * viewport as needed.
     *
     * @param camera
     *        The collection of viewing parameters to use.
     * @param viewport
     *        The viewing plane to use.
     */
    fun prepare(camera: Camera, viewport: Viewport)

    /**
     * Transforms the specified pixel into to a camera ray whose origin has
     * the specified location and whose direction lies inwards toward a scene.
     *
     * @param coords
     *        The pixel origin to use.
     * @param camera
     *        The collection of viewing parameters to use.
     * @param viewport
     *        The viewing plane to use.
     * @return A new camera ray.
     */
    fun capture(coords: Coords, camera: Camera, viewport: Viewport): Ray3
}