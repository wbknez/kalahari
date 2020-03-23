package com.willoutwest.kalahari.scene.camera.lenses

import com.willoutwest.kalahari.math.MathUtils.Companion.Pi
import com.willoutwest.kalahari.math.MathUtils.Companion.PiOver180
import com.willoutwest.kalahari.math.MathUtils.Companion.cos
import com.willoutwest.kalahari.math.MathUtils.Companion.sin
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.render.Coords
import com.willoutwest.kalahari.scene.camera.Camera
import com.willoutwest.kalahari.scene.camera.Lens
import com.willoutwest.kalahari.scene.camera.Viewport

/**
 * Represents an implementation of [Lens] that creates camera rays for a
 * scene as if viewing it through a spherical panoramic projection.
 */
class SphericalLens : AbstractLens(), Lens {

    override fun capture(coords: Coords, camera: Camera,
                         viewport: Viewport): Ray3 {
        val height = viewport.bounds.height
        val ps     = viewport.pixelSize
        val sp     = viewport.sampler.nextSample()
        val width  = viewport.bounds.width

        val pX = ps * (coords.x - 0.5f * width  + sp.x) + camera.xShift
        val pY = ps * (coords.y - 0.5f * height + sp.y)

        val nX = 2.0f / (ps * width)  * pX
        val nY = 2.0f / (ps * height) * pY

        val lambda = nX * camera.lambdaMax * PiOver180
        val psi    = nY * camera.psiMax    * PiOver180

        val phi   = Pi        - lambda
        val theta = 0.5f * Pi - psi

        val cosPhi   = cos(phi)
        val cosTheta = cos(theta)
        val sinPhi   = sin(phi)
        val sinTheta = sin(theta)

        return Ray3(camera.uvw.u.clone()
                        .timesSelf(sinPhi * sinTheta)
                        .plusTimesSelf(cosTheta, camera.uvw.v)
                        .plusTimesSelf(cosPhi * sinTheta, camera.uvw.w)
                        .normalizeSelf(),
                    camera.eye.clone())
    }
}