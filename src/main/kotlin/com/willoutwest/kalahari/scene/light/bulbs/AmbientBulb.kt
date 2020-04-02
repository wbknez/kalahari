package com.willoutwest.kalahari.scene.light.bulbs

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.scene.light.Bulb
import com.willoutwest.kalahari.scene.light.Light

/**
 * Represents an implementation of [Bulb] that models the output of
 * radiance as a constant, omnidirectional source.
 */
class AmbientBulb : AbstractBulb(), Bulb {

    override fun L(light: Light, record: Intersection, store: Color3): Color3 =
        light.cL.getColor(record, store).timesSelf(light.kL)
}