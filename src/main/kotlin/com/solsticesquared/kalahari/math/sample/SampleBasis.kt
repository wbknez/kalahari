package com.solsticesquared.kalahari.math.sample

import com.solsticesquared.kalahari.math.Point2
import com.solsticesquared.kalahari.math.Point3

/**
 * Represents a collection of sample points organized as an array of multiple
 * distinct sets.
 *
 * @property numSamples
 *           The number of samples per set.
 * @property numSets
 *           The number of sample sets.
 * @property samples
 *           The list of sample points to choose from.  The size of this list
 *           should equal to be the number of samples times the number of sets.
 */
data class SampleBasis<T>(val numSamples: Int,
                          val numSets: Int,
                          val samples: List<T>) : Cloneable {

    /**
     * Constructor.
     *
     * @param basis
     *        The sample basis to copy from.
     */
    constructor(basis: SampleBasis<T>?)
        : this(basis!!.numSamples, basis.numSets, basis.samples)

    init {
        require(this.samples.size == (this.numSamples * this.numSets)) {
            "There is a mismatch between the expected size of the samples " +
            "list and how many samples there are: " +
            "${this.numSamples * this.numSets} is not $this.samples.size."
        }
    }

    public override fun clone(): SampleBasis<T> = SampleBasis(this)
}

/**
 * Represents a sample basis in two dimensional space.
 */
typealias SampleBasis2 = SampleBasis<Point2>

/**
 * Represents a sample basis in three dimensional space.
 */
typealias SampleBasis3 = SampleBasis<Point3>
