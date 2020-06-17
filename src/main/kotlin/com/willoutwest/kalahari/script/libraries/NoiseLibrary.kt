package com.willoutwest.kalahari.script.libraries

import com.willoutwest.kalahari.math.noise.Speaker
import com.willoutwest.kalahari.math.noise.sources.CubicSource
import com.willoutwest.kalahari.math.noise.sources.SimplexSource
import com.willoutwest.kalahari.math.noise.speakers.MotionSpeaker
import com.willoutwest.kalahari.math.noise.speakers.TurbulenceSpeaker
import com.willoutwest.kalahari.script.ScriptingLibrary

/**
 * Represents a mechanism for creating and working with [Speaker] objects
 * in a Lua scripting environment.
 */
class NoiseLibrary : ScriptingLibrary {

    /**
     * Creates a new cubic noise source with a default table size of 
     * <code>256</code>.
     * 
     * @return A cubic noise source.
     */
    fun cubic(): Speaker = CubicSource()

    /**
     * Creates a new speaker that applies fractal Brownian motion using the 
     * specified parameters.
     * 
     * @param source
     *        A source of noise to use.
     * @param gain
     *        The ratio of successive amplitudes to use.
     * @param octaves
     *        The number of successive frequencies that differ by a constant
     *        ratio to use.
     * @param lacunarity
     *        The ratio of successive spatial frequencies to use.
     * @return A new noise-filled texture.
     */
    fun motion(source: Speaker, gain: Float, octaves: Int, lacunarity: Float):
        Speaker =
        MotionSpeaker(source, gain, octaves, lacunarity)

    /**
     * Creates a new simplex noise source with the time in milliseconds 
     * since epoch as the default seed.
     * 
     * @return A simplex noise source.
     */
    fun simplex(): Speaker = this.simplex(System.currentTimeMillis())

    /**
     * Creates a new simplex noise source with the specified seed.
     * 
     * @param seed
     *        The seed to use.
     * @return A simplex noise source.
     */
    fun simplex(seed: Long): Speaker = SimplexSource(seed)
    
    /**
     * Creates a new speaker that applies a fractal summation using the 
     * specified parameters.
     *
     * Fractal summation is a particular parameterization of Brownian motion
     * using a gain of <code>0.5</code> and a lacunarity (frequency) of
     * <code>2</code>.
     * 
     * @param source
     *        A source of noise to use.
     * @param octaves
     *        The number of successive frequencies that differ by a constant
     *        ratio to use.
     * @return A new fractal-summation speaker.
     */
    fun sum(source: Speaker, octaves: Int): Speaker =
        MotionSpeaker(source, 0.5f, octaves, 2.0f)

    /**
     * Creates a new speaker that applies Perlin's turbulence function using
     * the specified parameters.
     * 
     * @param source
     *        A source of noise to use.
     * @param gain
     *        The ratio of successive amplitudes to use.
     * @param octaves
     *        The number of successive frequencies that differ by a constant
     *        ratio to use.
     * @return A new turbulence speaker.
     */
    fun turbulence(source: Speaker, gain: Float, octaves: Int): Speaker =
        TurbulenceSpeaker(source, gain, octaves)
}