package com.willoutwest.kalahari.script.libraries

import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.scene.camera.Camera
import com.willoutwest.kalahari.scene.camera.Lens
import com.willoutwest.kalahari.scene.camera.lenses.FishLens
import com.willoutwest.kalahari.scene.camera.lenses.PinholeLens
import com.willoutwest.kalahari.scene.camera.lenses.SphericalLens
import com.willoutwest.kalahari.scene.camera.lenses.StereoLens
import com.willoutwest.kalahari.scene.camera.lenses.ThinLens
import com.willoutwest.kalahari.script.ScriptingLibrary

/**
 * Represents a mechanism for creating and working with [Camera] objects
 * in a Lua scripting environment.
 */
class CameraLibrary : ScriptingLibrary {

    /**
     * Represents the type of cameras this project supports by default.
     */
    enum class Type : Camera.Type {

        /**
         * Represents a camera that views a scene through the eye of a fish.
         */
        Fish,

        /**
         * Represents a camera that views a scene through a small aperature.
         */
        Pinhole,

        /**
         * Represents a camera that views a scene as a spherical projection.
         */
        Spherical,

        /**
         * Represents a camera that views a scene through a pair of
         * stereoscopic lenses.
         */
        Stereo,

        /**
         * Represents a camera that views a scene through a circular lens.
         */
        Thin
    }

    companion object {

        /**
         * Represents a mapping of camera types to the lenses used to cast
         * rays into a scene.
         *
         * @return A mapping of camera types to lenses.
         */
        fun defaultLenses(): Map<Camera.Type, Lens> = mapOf(
            Type.Fish to FishLens(),
            Type.Pinhole to PinholeLens(),
            Type.Spherical to SphericalLens(),
            Type.Stereo to StereoLens(),
            Type.Thin to ThinLens()
        )
    }

    /**
     * Creates a new camera to serve as a basis using the specified eye and
     * look at locations.
     *
     * This method sets the up vector to the y-axis and the zoom factor to one.
     *
     * @param eye
     *        The eye location to use.
     * @param lookAt
     *        The location to look at.
     * @return A basis camera.
     */
    fun basis(eye: Point3, lookAt: Point3): Camera =
        basis(eye, lookAt, Vector3.Y, 1f)

    /**
     * Creates a new camera to serve as a basis using the specified eye and
     * look at locations and up vector.
     *
     * This method sets the zoom factor to one.
     *
     * @param eye
     *        The eye location to use.
     * @param lookAt
     *        The location to look at.
     * @param up
     *        The direction to use as "up".
     * @return A basis camera.
     */
    fun basis(eye: Point3, lookAt: Point3, up: Vector3): Camera =
        basis(eye, lookAt, up, 1f)

    /**
     * Creates a new camera to serve as a basis using the specified parameters.
     *
     * This method sets the zoom factor to one.
     *
     * @param eye
     *        The eye location to use.
     * @param lookAt
     *        The location to look at.
     * @param up
     *        The direction to use as "up".
     * @param zoom
     *        The amount of zoom (factor) to use.
     * @return A basis camera.
     */
    fun basis(eye: Point3, lookAt: Point3, up: Vector3, zoom: Float): Camera {
        val camera = Camera("basis")

        camera.eye = eye
        camera.lookAt = lookAt
        camera.up = up
        camera.zoom = zoom

        return camera
    }

    /**
     * Creates a new pinhole camera with the specified parameters.
     *
     * @param name
     *        The camera name to use.
     * @param basis
     *        The camera to use as a basis.
     * @param distance
     *        The distance from the viewing plane to use.
     * @return A pinhole camera.
     */
    fun pinhole(name: String, basis: Camera, distance: Float): Camera {
        val camera = Camera(name, Type.Pinhole)

        camera.distance = distance
        camera.eye = basis.eye
        camera.lookAt = basis.lookAt
        camera.up = basis.up
        camera.zoom = basis.zoom

        return camera
    }
}