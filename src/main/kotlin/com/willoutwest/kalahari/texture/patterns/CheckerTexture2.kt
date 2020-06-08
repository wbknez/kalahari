package com.willoutwest.kalahari.texture.patterns

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.MathUtils.Companion.floori
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.texture.Texture

/**
 * Represents an implementation of [AbstractCheckerTexture] that creates
 * a checker pattern in two-dimensional space with outline(s).
 *
 * This checker texture is primarily intended for flat surfaces such as
 * planes and rectangles but may be used for other surfaces as necessary.
 */
class CheckerTexture2(evenColor: Texture, oddColor: Texture, scale: Float,
                      val outlineColor: Texture, val outlineWidth: Float) :
    AbstractCheckerTexture(evenColor, oddColor, scale), Cloneable, Texture {

    /**
     * Constructor.
     *
     * @param tex
     *        The 2D checkerboard texture to copy from.
     */
    constructor(tex: CheckerTexture2?) :
        this(tex!!.evenColor, tex.oddColor, tex.scale, tex.outlineColor,
             tex.outlineWidth)

    init {
        require(this.outlineWidth >= 0f) {
            "Checker outline width cannot be less than zero."
        }
    }

    public override fun clone(): CheckerTexture2 = CheckerTexture2(this)

    override fun getColor(record: Intersection, store: Color3): Color3 {
        val d  = 1f / this.scale

        val x  = record.localPosition.x * d
        val z  = record.localPosition.z * d

        val iX = floori(x)
        val iZ = floori(z)

        val fX = x - iX
        val fZ = z - iZ

        val width     = 0.5f * this.outlineWidth * d

        return when((fX < width || fX > (1f - width)) ||
                    (fZ < width || fZ > (1f - width))) {
            false -> when((iX + iZ) % 2 == 0) {
                false -> this.oddColor.getColor(record, store)
                true  -> this.evenColor.getColor(record, store)
            }
            true  -> this.outlineColor.getColor(record, store)
        }
    }
}