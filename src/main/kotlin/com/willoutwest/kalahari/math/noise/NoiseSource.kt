package com.willoutwest.kalahari.math.noise


/**
 * Represents a source, or creator, of noise.
 *
 * Noise sources are expected to be terminal and may not contain other
 * [Speaker] objects (i.e. may not be nested further).
 */
interface NoiseSource : Cloneable, Speaker {

    public override fun clone(): NoiseSource
}