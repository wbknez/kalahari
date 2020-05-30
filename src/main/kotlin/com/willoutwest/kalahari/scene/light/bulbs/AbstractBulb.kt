package com.willoutwest.kalahari.scene.light.bulbs

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.Ray3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.scene.Actor
import com.willoutwest.kalahari.scene.light.Bulb
import com.willoutwest.kalahari.scene.light.Light

/**
 * Represents an implementation of [Bulb] that provides a default
 * implementation for deriving classes.
 */
abstract class AbstractBulb : Bulb {

    override fun G(light: Light, record: Intersection): Float = 1f

    override fun illuminate(light: Light, record: Intersection,
                            store: Vector3): Vector3 =
        store.set(Vector3.Zero)

    override fun L(light: Light, root: Actor,
                   record: Intersection, store: Color3): Color3 =
        light.cL.getColor(record, store)
            .timesSelf(light.kL)

    override fun pdf(light: Light, record: Intersection): Float = 1f

    override fun shadowLength(ray: Ray3, light: Light): Float = Float.MAX_VALUE
}