package com.willoutwest.kalahari.scene.camera.lenses

import com.willoutwest.kalahari.scene.camera.Camera
import com.willoutwest.kalahari.scene.camera.Lens
import com.willoutwest.kalahari.scene.camera.Viewport

/**
 * Represents an implementation of [Lens] that provides a default
 * implementation for deriving classes.
 */
abstract class AbstractLens : Lens {

    override fun getCameras(camera: Camera): List<Camera> = listOf(camera)

    override fun prepare(camera: Camera, viewport: Viewport) {
        camera.uvw.updateBasis(camera.eye, camera.lookAt, camera.up)
    }
}