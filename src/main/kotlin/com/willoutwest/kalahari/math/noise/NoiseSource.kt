package com.willoutwest.kalahari.math.noise


/**
 * Represents a source, or creator, of noise.
 */
interface NoiseSource : Cloneable, Speaker {

    public override fun clone(): NoiseSource
}