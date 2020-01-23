package com.willoutwest.kalahari.util

/**
 * Represents an modifiable [Float] object that may be passed up and down a
 * call stack.
 */
data class FloatContainer(var value: Float) : Cloneable
