package com.willoutwest.kalahari.material

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.intersect.Intersection

/**
 * Represents an implementation of [ColorSource] that always returns a
 * single, constant color independent of any intersection.
 *
 * @property color
 *           The color to use.
 */
data class ConstantColor(private val color: Color3) : Cloneable, ColorSource {

    /**
     * Constructor.
     *
     * @param red
     *        The red color component to use.
     * @param green
     *        The green color component to use.
     * @param blue
     *        The blue color component to use.
     */
    constructor(red: Float, green: Float, blue: Float)
        : this(Color3(red, green, blue))

    /**
     * Constructor.
     *
     * @param constant
     *        The constant color to copy from.
     */
    constructor(constant: ConstantColor?) : this(constant!!.color)

    public override fun clone(): ConstantColor = ConstantColor(this)

    override fun getColor(record: Intersection, store: Color3): Color3 =
        store.set(this.color)
}