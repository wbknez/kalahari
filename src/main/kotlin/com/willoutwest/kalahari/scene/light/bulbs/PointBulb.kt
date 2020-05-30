package com.willoutwest.kalahari.scene.light.bulbs

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.scene.Actor
import com.willoutwest.kalahari.scene.light.Bulb
import com.willoutwest.kalahari.scene.light.Light

/**
 * Represents an implementation of [Bulb] that models the output of radiance
 * from a single point in three-dimensional space.
 */
class PointBulb : AbstractBulb(), Bulb {

    override fun illuminate(light: Light, record: Intersection,
                            store: Vector3): Vector3 =
        store.set(light.point)
            .minusSelf(record.localPosition.x,
                       record.localPosition.y,
                       record.localPosition.z)
            .normalizeSelf()

    override fun shadowLength(ray: Ray3, light: Light): Float =
        light.point.distanceTo(ray.origin)
}