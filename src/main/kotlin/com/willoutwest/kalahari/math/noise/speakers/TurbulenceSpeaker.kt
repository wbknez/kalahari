package com.willoutwest.kalahari.math.noise.speakers

import com.willoutwest.kalahari.math.MathUtils
import com.willoutwest.kalahari.math.noise.Speaker

/**
 * Represents an implementation of [Speaker] that modifies noise values using
 * Perlin's turbulence function.
 */
class TurbulenceSpeaker(source: Speaker, gain: Float, octaves: Int) :
    AbstractFractalSpeaker(source, gain, octaves), Cloneable {

    constructor(speaker: TurbulenceSpeaker?) :
        this(speaker!!.source, speaker.gain, speaker.octaves)

    override fun clone(): TurbulenceSpeaker = TurbulenceSpeaker(this)

    override fun output(x: Float, y: Float, z: Float): Float {
        var amplitude  = 1f
        var frequency  = 1f
        var turbulence = 0f

        for(i in 0 until this.octaves) {
            val fX = frequency * x
            val fY = frequency * y
            val fZ = frequency * z

            val so = this.source.output(fX, fY, fZ)

            turbulence *= amplitude * MathUtils.abs(so)
            amplitude  *= 0.5f
            frequency  *= 2f
        }

        return turbulence / this.maxBounds
    }
}