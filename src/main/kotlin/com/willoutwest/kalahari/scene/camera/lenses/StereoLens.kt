package com.willoutwest.kalahari.scene.camera.lenses

import com.willoutwest.kalahari.math.MathUtils.Companion.PiOver180
import com.willoutwest.kalahari.math.MathUtils.Companion.tan
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.render.Coords
import com.willoutwest.kalahari.scene.camera.Camera
import com.willoutwest.kalahari.scene.camera.Lens
import com.willoutwest.kalahari.scene.camera.StereoMode
import com.willoutwest.kalahari.scene.camera.Viewport

/**
 * Represents an implementation of [Lens] that creates camera rays for a
 * scene as if viewing it through a pair of overlapping lenses.
 */
class StereoLens : AbstractLens(), Lens {

    override fun getCameras(camera: Camera): List<Camera> =
        when(camera.mode) {
            StereoMode.Parallel   -> listOf(camera.left, camera.right)
            StereoMode.Transverse -> listOf(camera.right, camera.left)
        }

    override fun capture(coords: Coords, camera: Camera,
                         viewport: Viewport): Ray3 = Ray3()

    override fun prepare(camera: Camera, viewport: Viewport) {
        super.prepare(camera, viewport)

        this.prepareOverlap(camera, viewport)
        this.prepareStereoscopy(camera)
    }

    /**
     * Prepares the specified camera for rendering by computing the pixel
     * offsets necessary to correctly overlap both sub-cameras (left and right)
     * depending on the viewing mode.
     *
     * @param camera
     *        The collection of viewing parameters to use.
     * @param viewport
     *        The viewing plane to use.
     */
    private fun prepareOverlap(camera: Camera, viewport: Viewport) {
        val r      = camera.eye.distanceTo(camera.lookAt)
        val xShift = r * tan(0.5f * camera.beta * PiOver180)
        val offSet = viewport.bounds.width + camera.gap

        camera.left.offSet = when(camera.mode) {
            StereoMode.Parallel   -> offSet
            StereoMode.Transverse -> 0
        }
        camera.left.xShift  =  xShift

        camera.right.offSet = when(camera.mode) {
            StereoMode.Parallel   -> 0
            StereoMode.Transverse -> offSet
        }
        camera.right.xShift = -xShift
    }

    /**
     * Prepares the specified camera for stereoscopic rendering by computing
     * the orthonormal basis for each individual sub-camera (left and right).
     *
     * @param camera
     *        The collection of viewing parameters to use.
     */
    private fun prepareStereoscopy(camera: Camera) {
        val r      = camera.eye.distanceTo(camera.lookAt)
        val xShift = r * tan(0.5f * camera.beta * PiOver180)

        camera.left.eye.set(camera.uvw.u)
            .timesSelf(-xShift)
            .plusSelf(camera.eye)
        camera.left.lookAt.set(camera.uvw.u)
            .timesSelf(-xShift)
            .plusSelf(camera.lookAt)

        camera.right.eye.set(camera.uvw.u)
            .timesSelf(xShift)
            .plusSelf(camera.eye)
        camera.right.lookAt.set(camera.uvw.u)
            .timesSelf(xShift)
            .plusSelf(camera.lookAt)
    }
}