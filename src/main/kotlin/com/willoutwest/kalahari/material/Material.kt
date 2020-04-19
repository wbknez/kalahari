package com.willoutwest.kalahari.material

import com.willoutwest.kalahari.math.sample.Sampler3
import com.willoutwest.kalahari.util.ParameterMap
import com.willoutwest.kalahari.util.Typeable
import com.willoutwest.kalahari.util.hash

/**
 * Represents a collection of rendering parameters that controls the
 * colorization of reflected light from an arbitrary geometric surface.
 *
 * @param name
 *        The material name to use.
 * @property cD
 *        The ambient and diffuse reflection color.
 * @property kA
 *        The ambient reflection coefficient.
 * @property kD
 *           The diffuse reflection coefficient.
 * @property sampler
 *
 */
class Material(name: String) :
    Cloneable, ParameterMap(name), Typeable<Material.Type> {

    /**
     * Represents a unique identifier that denotes the type of shader that
     * should be used to determine the color of light reflected from a
     * material.
     */
    interface Type

    var cD: ColorSource by this.params

    var kA: Float by this.params

    var kD: Float by this.params

    var sampler: Sampler3 by this.params

    override var type: Type by this.params

    /**
     * Constructor.
     *
     * @param name
     *        The material name to use.
     * @param type
     *        The material type to use.
     */
    constructor(name: String, type: Type) : this(name) {
        this.type = type
    }

    /**
     * Constructor.
     *
     * @param material
     *        The material to copy from.
     */
    constructor(material: Material?) : this(material!!.id) {
        this.params.putAll(material.params)
    }

    override fun clone(): Material = Material(this)

    override fun equals(other: Any?): Boolean =
        when(other) {
            is Material -> super.equals(other) &&
                           this.type == other.type
            else        -> false
        }

    override fun hashCode(): Int = hash(super.hashCode(), this.type)
}