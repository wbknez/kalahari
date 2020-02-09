package com.willoutwest.kalahari.math.sample

import com.willoutwest.kalahari.math.Point2
import com.willoutwest.kalahari.math.Point3
import com.willoutwest.kalahari.math.RandomUtils
import com.willoutwest.kalahari.math.RandomUtils.Companion.nextInt
import com.willoutwest.kalahari.math.sample.mappers.DiskSampleMapper
import com.willoutwest.kalahari.math.sample.mappers.HemisphereSampleMapper
import com.willoutwest.kalahari.math.sample.mappers.SphereSampleMapper
import com.willoutwest.kalahari.util.fisherShuffle
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

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
    val random  = RandomUtils.localRandom
    val samples = MutableList(numSamples) { it }

    for(i in 0 until numSets) {
        samples.fisherShuffle(random)
        indices.addAll(samples)
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
 * @property indices
 *           The collection of randomized indices to use for sample point
 *           selection.
 * @property jump
 *           The current sample set to select from.
 * @property numSamples
 *           The number of samples per set.
 * @property numSets
 *           The number of sample sets.
 */
class Sampler<T>(private val basis: SampleBasis<T>,
                 private val indices: IntArray) : Cloneable {

    companion object {

        /**
         * Creates a sampler with the specified number of samples and sets
         * and maps all points from the specified generator to a unit disk.
         *
         * @param numSamples
         *        The number of samples per set to use.
         * @param numSets
         *        The number of sample sets to use.
         * @param generator
         *        The sample point generator to use.
         * @return A sampler that draws from a unit disk.
         */
        fun diskOf(numSamples: Int, numSets: Int,
                   generator: SampleGenerator): Sampler2 {
            val indices = createShuffledIndices(numSamples, numSets)
            val points  = generator.generate(numSamples, numSets)
            val basis   = SampleBasis2(numSamples, numSets,
                                       DiskSampleMapper().map(points))

            return Sampler2(basis, indices)
        }

        /**
         * Creates a sampler with the specified number of samples and sets
         * and maps all points from the specified generator to a unit
         * hemisphere.
         *
         * @param numSamples
         *        The number of samples per set to use.
         * @param numSets
         *        The number of sample sets to use.
         * @param exponent
         *        The exponent to use.
         * @param generator
         *        The sample point generator to use.
         * @return A sampler that draws from a unit hemisphere.
         */
        fun hemisphereOf(numSamples: Int, numSets: Int, exponent: Float,
                         generator: SampleGenerator): Sampler3 {
            val indices = createShuffledIndices(numSamples, numSets)
            val points  = generator.generate(numSamples, numSets)
            val mapper  = HemisphereSampleMapper(exponent)
            val basis   = SampleBasis3(numSamples, numSets, mapper.map(points))

            return Sampler3(basis, indices)
        }

        /**
         * Creates a sampler with the specified number of samples and sets
         * and maps all points from the specified generator to a unit sphere.
         *
         * @param numSamples
         *        The number of samples per set to use.
         * @param numSets
         *        The number of sample sets to use.
         * @param generator
         *        The sample point generator to use.
         * @return A sampler that draws from a unit sphere.
         */
        fun sphereOf(numSamples: Int, numSets: Int,
                   generator: SampleGenerator): Sampler3 {
            val indices = createShuffledIndices(numSamples, numSets)
            val points  = generator.generate(numSamples, numSets)
            val basis   = SampleBasis3(numSamples, numSets,
                                       SphereSampleMapper().map(points))

            return Sampler3(basis, indices)
        }

        /**
         * Creates a sampler with the specified number of samples and sets
         * and generates all points using the specified generator.
         *
         * @param numSamples
         *        The number of samples per set to use.
         * @param numSets
         *        The number of sample sets to use.
         * @param generator
         *        The sample point generator to use.
         * @return A sampler that draws from a unit square.
         */
        fun squareOf(numSamples: Int, numSets: Int,
                   generator: SampleGenerator): Sampler2 {
            val indices = createShuffledIndices(numSamples, numSets)
            val basis   = SampleBasis2(numSamples, numSets,
                                       generator.generate(numSamples, numSets))

            return Sampler2(basis, indices)
        }
    }

    val count: AtomicLong = AtomicLong(0L)

    val jump: AtomicInteger = AtomicInteger(0)

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
    constructor(basis: SampleBasis<T>) :
        this(basis, createShuffledIndices(basis.numSamples, basis.numSets))

    /**
     * Constructor.
     *
     * @param sampler
     *        The sampler to copy from.
     */
    constructor(sampler: Sampler<T>?) :
        this(sampler!!.basis, sampler.indices) {
        this.count.set(sampler.count.get())
        this.jump.set(sampler.jump.get())
    }

    public override fun clone(): Sampler<T> = Sampler(this)

    /**
     * Randomly selects a sample point from this sampler's basis.
     *
     * @return A randomly selected sample.
     */
    fun nextSample(): T {
        val currentCount = this.count.get()
        val shouldJump = (currentCount % this.numSamples) == 0L
        val currentJump = when(shouldJump) {
            false -> this.jump.get()
            true  -> nextInt(this.numSets) * this.numSamples
        }

        val indiceIndex = currentJump +
                          currentCount.toInt() % this.basis.numSamples
        val sampleIndex = currentJump + this.indices[indiceIndex]

        this.count.incrementAndGet()

        if(shouldJump) {
            this.jump.set(currentJump)
        }

        return this.basis.points[sampleIndex]
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
