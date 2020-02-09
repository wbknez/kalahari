package com.willoutwest.kalahari.math.operators

import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.ColorOperator
import com.willoutwest.kalahari.math.MathUtils

/**
 * Represents a [ColorOperator] that scales a color by its largest component
 * if that component exceeds one.
 */
class MaxToOneColorOperator : ColorOperator {

    override fun operate(color: Color3, store: Color3): Color3 {
        val maxComponent =
            MathUtils.max(color.red, MathUtils.max(color.green, color.blue))

        store.set(color)

        if(maxComponent > 1f) {
            store.divSelf(maxComponent)
        }

        return store
    }
}
