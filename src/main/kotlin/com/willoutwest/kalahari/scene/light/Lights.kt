package com.willoutwest.kalahari.scene.light

import com.willoutwest.kalahari.material.ColorSource
import com.willoutwest.kalahari.scene.light.bulbs.AmbientBulb
import com.willoutwest.kalahari.scene.light.bulbs.PointBulb

/**
 * Represents a collection of utility methods for creating and working with
 * [Light] objects.
 */
sealed class Lights {

    /**
     * Represents the type of lights this project supports by default.
     */
    enum class Type : Light.Type {

        /**
         * Represents a constant, omni-directional source of radiance.
         */
        Ambient
    }

    companion object {

        /**
         * Represents a mapping of light types to the bulbs used to cast
         * radiance into a scene.
         *
         * @return A mapping of light types to bulbs.
         */
        fun defaultBulbs(): Map<Light.Type, Bulb> = mapOf(
            Type.Ambient to AmbientBulb()
        )

        /**
         * Creates a new ambient light with the specified properties.
         *
         * @param name
         *        The light name to use.
         * @param cL
         *        The radiance color to use.
         * @param kL
         *        The radiance intensity to use.
         * @return An ambient light.
         */
        fun ambient(name: String, cL: ColorSource, kL: Float): Light {
            val light = Light(name, Type.Ambient)

            light.cL = cL
            light.kL = kL

            return light
        }
    }
}