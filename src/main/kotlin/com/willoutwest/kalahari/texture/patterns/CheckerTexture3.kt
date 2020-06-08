package com.willoutwest.kalahari.texture.patterns

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.MathUtils.Companion.floori
import com.willoutwest.kalahari.math.intersect.Intersection
import com.willoutwest.kalahari.texture.Texture

/**
 * Represents an implementation of [AbstractCheckerTexture] that creates
 * a checker pattern in three-dimensional space.
 *
 * This is a generic implementation that is not optimized per-surface.  That
 * is, this texture does not produce a uniform checker pattern on all
 * surfaces.  A surface-specific implementation should be used instead if such
 * behavior is desired.
 */
class CheckerTexture3(evenColor: Texture, oddColor: Texture, scale: Float) :
    AbstractCheckerTexture(evenColor, oddColor, scale), Cloneable {

    companion object {

        /**
         * The off-set to use to avoid rounding issues.
         */
        const val Bias = -0.000187453738f
    }

    /**
     * Constructor.
     *
     * @param tex
     *        The checkerboard texture to copy from.
     */
    constructor(tex: CheckerTexture3?) : this(tex!!.evenColor, tex.oddColor,
                                                 tex.scale)

    override fun clone(): CheckerTexture3 = CheckerTexture3(this)

    override fun getColor(record: Intersection, store: Color3): Color3 {
        val d = 1f / this.scale

        val x = (record.localPosition.x + Bias) * d
        val y = (record.localPosition.y + Bias) * d
        val z = (record.localPosition.z + Bias) * d

        val result = floori(x) + floori(y) + floori(z)

        return when(result % 2 == 0) {
            false -> this.oddColor.getColor(record, store)
            true  -> this.evenColor.getColor(record, store)
        }
    }
}