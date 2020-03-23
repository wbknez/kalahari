package com.willoutwest.kalahari.scene.camera.lenses

import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.MathUtils.Companion.cos
import com.willoutwest.kalahari.math.MathUtils.Companion.sin
import com.willoutwest.kalahari.math.MathUtils.Companion.sqrt
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.render.Coords
import com.willoutwest.kalahari.scene.camera.Camera
import com.willoutwest.kalahari.scene.camera.Lens
import com.willoutwest.kalahari.scene.camera.Viewport

/**
 * Represents an implementation of [Lens] that creates camera rays for a
 * scene as if viewing it through the eye of a fish.
 */
class FishLens : AbstractLens(), Lens {

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

        val rSquared = nX * nX + nY * nY
        val ray      = Ray3()

        if(rSquared <= 1f) {
            val r   = sqrt(rSquared)
            val psi = r * camera.psiMax * MathUtils.PiOver180

            val cosAlpha = cos(nX / r)
            val cosPsi   = cos(psi)
            val sinAlpha = sin(nY / r)
            val sinPsi   = sin(psi)

            ray.dir.set(camera.uvw.u)
                .timesSelf(cosAlpha * sinPsi)
                .plusTimesSelf(sinAlpha * sinPsi, camera.uvw.v)
                .minusTimesSelf(cosPsi, camera.uvw.w)
                .normalizeSelf()
            ray.origin.set(camera.eye)
        }

        return ray
    }
}