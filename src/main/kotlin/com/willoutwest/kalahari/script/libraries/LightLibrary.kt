package com.willoutwest.kalahari.script.libraries

import com.willoutwest.kalahari.texture.Texture
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.Vector3
import com.willoutwest.kalahari.scene.light.Bulb
import com.willoutwest.kalahari.scene.light.Light
import com.willoutwest.kalahari.scene.light.bulbs.AmbientBulb
import com.willoutwest.kalahari.scene.light.bulbs.DirectionalBulb
import com.willoutwest.kalahari.scene.light.bulbs.PointBulb
import com.willoutwest.kalahari.script.ScriptingLibrary
import com.willoutwest.kalahari.texture.FillTexture

/**
 * Represents a mechanism for creating and working with [Light] objects
 * in a Lua scripting environment.
 */
class LightLibrary : ScriptingLibrary {

    /**
     * Represents the type of lights this project supports by default.
     */
    enum class Type : Light.Type {

        /**
         * Represents a constant, omni-directional source of radiance.
         */
        Ambient,

        /**
         * Represents a singular, uni-directional source of radiance.
         */
        Directional,

        /**
         * Represents a singular, pointed source of radiance.
         */
        Point
    }

    companion object {

        /**
         * Represents a mapping of light types to the bulbs used to cast
         * radiance into a scene.
         *
         * @return A mapping of light types to bulbs.
         */
        fun defaultBulbs(): Map<Light.Type, Bulb> = mapOf(
            Type.Ambient     to AmbientBulb(),
            Type.Directional to DirectionalBulb(),
            Type.Point       to PointBulb()
        )
    }

    /**
     * Creates a new ambient light with a default name of "global", a
     * default color of white, and a default intensity of onecd.
     *
     * @return An ambient light.
     */
    fun ambient(): Light = ambient("global", FillTexture.White, 1f)

    /**
     * Creates a new ambient light with the specified properties, a
     * default name of "global", and a default intensity of one.
     *
     * @param cL
     *        The radiance color to use.
     * @return An ambient light.
     */
    fun ambient(cL: Texture): Light = ambient("global", cL, 1f)

    /**
     * Creates a new ambient light with the specified properties and a
     * default name of "global".
     *
     * @param cL
     *        The radiance color to use.
     * @param kL
     *        The radiance intensity to use.
     * @return An ambient light.
     */
    fun ambient(cL: Texture, kL: Float): Light = ambient("global", cL, kL)

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
    fun ambient(name: String, cL: Texture, kL: Float): Light {
        val light = Light(name, Type.Ambient)

        light.cL = cL
        light.kL = kL

        return light
    }

    /**
     * Creates a new directional light with the specified properties.
     *
     * @param name
     *        The light name to use.
     * @param cL
     *        The radiance color to use.
     * @param kL
     *        The radiance intensity to use.
     * @param dir
     *        The direction of the light to use.
     * @return A directional light.
     */
    fun directional(name: String, cL: Texture, kL: Float, dir: Vector3):
        Light {
        val light = Light(name, Type.Directional)

        light.cL  = cL
        light.dir = dir
        light.kL  = kL

        return light
    }

    /**
     * Creates a new point light with the specified properties.
     *
     * @param name
     *        The light name to use.
     * @param cL
     *        The radiance color to use.
     * @param kL
     *        The radiance intensity to use.
     * @param point
     *        The location of the light to use.
     * @return A point light.
     */
    fun point(name: String, cL: Texture, kL: Float, point: Point3): Light {
        val light = Light(name, Type.Point)

        light.cL = cL
        light.kL = kL
        light.point = point

        return light
    }
}