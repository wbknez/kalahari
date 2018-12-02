package com.solsticesquared.kalahari.math.sample

import com.solsticesquared.kalahari.math.Point2
import com.solsticesquared.kalahari.math.Point3
import com.solsticesquared.kalahari.math.random.RandomUtils.Companion.nextInt

/**
 * Creates a new array of indices for the specified number of samples and
 * shuffles each set of samples independently, creating a randomized grouping
 * of iteration orders.
 *
 * @param numSamples
 *        The number of samples per set to use.
 * @param numSets
 *        The number of sample sets to use.
 * @return An array of shuffled indice sets.
 */
fun createShuffledIndices(numSamples: Int, numSets: Int): IntArray {
    val indices = MutableList(0) { 0 }
    val shuffledIndices = MutableList(numSamples) { it }

    for(i in 0..(numSets - 1)) {
        shuffledIndices.shuffle()
        indices.addAll(shuffledIndices)
    }

    return indices.toIntArray()
}

/**
 * Represents a mechanism for obtaining sample points from various unit
 * geometries (disk, hemisphere, sphere, and square).
 *
 * @property basis
 *           The source of sample points to select from.
 * @property count
 *           The next sample to select in a set.
 * @property jump
 *           The current sample set to select from.
 * @property numSamples
 *           The number of samples per set.
 * @property numSets
 *           The number of sample sets.
 */
class Sampler<T>(private val basis: SampleBasis<T>,
                 private val indices: IntArray) : Cloneable {

    @Volatile
    private var count = 0L

    @Volatile
    private var jump = 0

    val numSamples: Int
        get() = this.basis.numSamples

    val numSets: Int
        get() = this.basis.numSets

    /**
     * Constructor.
     *
     * @param basis
     *        The sample basis to use.
     */
    constructor(basis: SampleBasis<T>)
        : this(basis, createShuffledIndices(basis.numSamples, basis.numSets))

    /**
     * Constructor.
     *
     * @param sampler
     *        The sampler to copy from.
     */
    constructor(sampler: Sampler<T>?) : this(sampler!!.basis, sampler.indices) {
        this.jump  = sampler.jump
        this.count = sampler.count
    }

    public override fun clone(): Sampler<T> {
        val cloned = Sampler(this)

        cloned.count = this.count
        cloned.jump  = this.jump

        return cloned
    }

    /**
     * Randomly selects a sample point from this sampler's basis.
     *
     * @return A randomly selected sample.
     */
    fun nextSample(): T {
        val currentCount = this.count
        val shouldJump = (currentCount % this.basis.numSamples) == 0L
        val currentJump = when(shouldJump) {
            false -> this.jump
            true  -> nextInt(this.basis.numSets) * this.basis.numSamples
        }

        val indiceIndex = currentJump +
                          currentCount.toInt() % this.basis.numSamples
        val sampleIndex = currentJump + this.indices[indiceIndex]

        this.count = currentCount + 1

        if(shouldJump) {
            this.jump = currentJump
        }

        return this.basis.samples[sampleIndex]
    }
}

/**
 * Represents a sampler that operates on sample points in two dimensional space.
 */
typealias Sampler2 = Sampler<Point2>

/**
 * Represents a sampler that operates on sample points in three dimensional
 * space.
 */
typealias Sampler3 = Sampler<Point3>
