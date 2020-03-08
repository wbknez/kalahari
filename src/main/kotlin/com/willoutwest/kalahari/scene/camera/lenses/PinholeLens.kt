package com.willoutwest.kalahari.scene.camera.lenses

import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.render.Coords
import com.willoutwest.kalahari.scene.camera.Camera
import com.willoutwest.kalahari.scene.camera.Lens
import com.willoutwest.kalahari.scene.camera.Viewport

/**
 * Represents an implementation of [Lens] that creates camera rays for a
 * scene as if viewing it through an aperture with no actual lens.
 */
class PinholeLens : AbstractLens(), Lens {

    override fun capture(coords: Coords, camera: Camera,
                         viewport: Viewport): Ray3 {
        val height = viewport.bounds.height
        val ps     = viewport.pixelSize * (1f / camera.zoom)
        val sp     = viewport.sampler.nextSample()
        val width  = viewport.bounds.width

        val pX  = ps * (coords.x - 0.5f * width  + sp.x) + camera.xShift
        val pY  = ps * (coords.y - 0.5f * height + sp.y)

        return Ray3(camera.uvw.u.clone()
                        .timesSelf(pX)
                        .plusTimesSelf(pY, camera.uvw.v)
                        .minusTimesSelf(camera.distance, camera.uvw.w)
                        .normalizeSelf(),
                    camera.eye.clone())
    }
}