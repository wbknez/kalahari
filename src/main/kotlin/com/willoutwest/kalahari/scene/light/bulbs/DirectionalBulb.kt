package com.willoutwest.kalahari.scene.light.bulbs

import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.scene.light.Bulb
import com.willoutwest.kalahari.scene.light.Light

/**
 * Represents an implementation of [Bulb] that models the output of radiance
 * from a single direction in three-dimensional space.
 */
class DirectionalBulb : AbstractBulb(), Bulb {

    override fun illuminate(light: Light, record: Intersection,
                            store: Vector3): Vector3 =
        store.set(light.dir)
}