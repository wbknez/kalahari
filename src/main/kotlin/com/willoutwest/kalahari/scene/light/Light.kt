package com.willoutwest.kalahari.scene.light

import com.willoutwest.kalahari.material.ColorSource
import com.willoutwest.kalahari.util.ParameterMap
import com.willoutwest.kalahari.util.Typeable
import com.willoutwest.kalahari.util.hash

/**
 * Represents a collection of lighting parameters that controls the emittance
 * of radiance throughout a scene.
 *
 * @property cL
 *        The radiance color.
 * @property kL
 *        The radiance intensity coefficient.
 */
class Light(name: String) :
    Cloneable, ParameterMap(name), Typeable<Light.Type> {

    /**
     * Represents a unique identifier that denotes the type of bulb that
     * should be used to determine how radiance is emitted.
     */
    interface Type

    var cL: ColorSource by this.params

    var kL: Float by this.params

    override var type: Type by this.params

    /**
     * Constructor.
     *
     * @param light
     *        The light to copy from.
     */
    constructor(light: Light?) : this(light!!.id) {
        this.params.putAll(light.params)
    }

    override fun clone(): Light = Light(this)

    override fun equals(other: Any?): Boolean =
        when(other) {
            is Light -> super.equals(other) &&
                        this.type == other.type
            else     -> false
        }

    override fun hashCode(): Int = hash(super.hashCode(), this.type)
}