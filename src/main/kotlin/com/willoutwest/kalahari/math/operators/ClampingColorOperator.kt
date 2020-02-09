package com.willoutwest.kalahari.math.operators

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.ColorOperator
import com.willoutwest.kalahari.math.MathUtils

/**
 * Represents a [ColorOperator] that clamps each component of a color to a
 * specific range.
 *
 * @property max
 *           The maximum color, or upper bound, to use.
 * @property min
 *           The minimum color, or lower bound, to use.
 */
class ClampingColorOperator(val min: Color3 = Color3(0f, 0f, 0f),
                            val max: Color3 = Color3(1f, 1f, 1f))
    : ColorOperator {

    override fun operate(color: Color3, store: Color3): Color3 =
        store.set(MathUtils.clamp(color.red, this.min.red, this.max.red),
                  MathUtils.clamp(color.green, this.min.green, this.max.green),
                  MathUtils.clamp(color.blue, this.min.blue, this.max.blue))
}