package com.willoutwest.kalahari.scene.camera

import com.willoutwest.kalahari.scene.camera.lenses.FishLens
import com.willoutwest.kalahari.scene.camera.lenses.PinholeLens
import com.willoutwest.kalahari.scene.camera.lenses.SphericalLens
import com.willoutwest.kalahari.scene.camera.lenses.StereoLens
import com.willoutwest.kalahari.scene.camera.lenses.ThinLens

/**
 * Represents a collection of utility methods for creating and working with
 * [Camera] objects.
 */
sealed class Cameras {

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
            Type.Fish      to FishLens(),
            Type.Pinhole   to PinholeLens(),
            Type.Spherical to SphericalLens(),
            Type.Stereo    to StereoLens(),
            Type.Thin      to ThinLens()
        )
    }
}