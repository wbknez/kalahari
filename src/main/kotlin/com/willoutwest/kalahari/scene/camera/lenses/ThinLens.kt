package com.willoutwest.kalahari.scene.camera.lenses

import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.render.Coords
import com.willoutwest.kalahari.scene.camera.Camera
import com.willoutwest.kalahari.scene.camera.Lens
import com.willoutwest.kalahari.scene.camera.Viewport

/**
 * Represents an implementation of [Lens] that creates camera rays for a
 * scene as if viewing it through a circular lens of finite radius, thereby
 * allowing for depth of field
 */
class ThinLens : AbstractLens(), Lens {

    override fun capture(coords: Coords, camera: Camera,
                         viewport: Viewport): Ray3 {
        val height = viewport.bounds.height
        val ps     = viewport.pixelSize * (1f / camera.zoom)
        val width  = viewport.bounds.width

        val dp     = camera.sampler.nextSample()
        val sp     = viewport.sampler.nextSample()

        val pX = ps * (coords.x - width  / 2.0f * sp.x) + camera.xShift
        val pY = ps * (coords.y - height / 2.0f * sp.y)

        val lX = dp.x * camera.radius
        val lY = dp.y * camera.radius

        val dX = pX * camera.focus / camera.distance
        val dY = pY * camera.focus / camera.distance

        return Ray3(camera.uvw.u.clone()
                        .timesSelf(dX - lX)
                        .plusTimesSelf(dY - lY, camera.uvw.v)
                        .minusTimesSelf(camera.focus, camera.uvw.w)
                        .normalizeSelf(),
                    Point3(camera.eye.x + lX * camera.uvw.u.x +
                           lY * camera.uvw.v.x,
                           camera.eye.x + lX * camera.uvw.u.x +
                           lY * camera.uvw.v.x,
                           camera.eye.x + lX * camera.uvw.u.x +
                           lY * camera.uvw.v.x))
    }
}