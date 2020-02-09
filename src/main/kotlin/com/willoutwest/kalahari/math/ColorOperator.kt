package com.willoutwest.kalahari.math

/**
 * Represents a mechanism for applying a mathematical operation to a [Color3].
 */
interface ColorOperator {

    /**
     * Applies some arbitrary operation to the specified input color and
     * stores the result in the specified storage color.
     *
     * Implementations should note that the input and output may be the same
     * object.
     *
     * @param color
     *        The color to operate on.
     * @param store
     *        The color to store the result in.
     * @return A reference to store for easy chaining.
     */
    fun operate(color: Color3, store: Color3): Color3
}