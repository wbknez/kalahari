package com.willoutwest.kalahari.math.noise.speakers

import com.willoutwest.kalahari.math.noise.Speaker

/**
 * Represents an implementation of [Speaker] that modifies noise values using
 * fractal Brownian motion (fBm).
 *
 * @property lacunarity
 *           The ratio of successive spatial frequencies.
 */
class MotionSpeaker(source: Speaker, gain: Float, octaves: Int,
                    val lacunarity: Float) :
    AbstractFractalSpeaker(source, gain, octaves), Cloneable {

    constructor(speaker: MotionSpeaker?) :
        this(speaker!!.source, speaker.gain, speaker.octaves,
             speaker.lacunarity)

    override fun clone(): MotionSpeaker = MotionSpeaker(this)

    override fun output(x: Float, y: Float, z: Float): Float {
        var amplitude = 1f
        var frequency = 1f
        var motion    = 0f

        for(i in 0 until this.octaves) {
            val fX = frequency * x
            val fY = frequency * y
            val fZ = frequency * z

            motion    += amplitude * this.source.output(fX, fY, fZ)
            amplitude *= this.gain
            frequency *= this.lacunarity
        }

        return (motion - this.minBounds) / (this.maxBounds - this.minBounds)
    }
}