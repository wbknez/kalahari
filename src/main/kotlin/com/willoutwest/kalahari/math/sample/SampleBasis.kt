package com.willoutwest.kalahari.math.sample

/**
 * Represents a collection of sample points organized as an array of sets.
 *
 * @property numSamples
 *           The number of samples per set.
 * @property numSets
 *           The number of sample sets.
 * @property points
 *           The collection of sample points to choose from.  The size of this
 *           collection should be equal to the product of the number of
 *           samples and the number of sets.
 */
data class SampleBasis<T>(val numSamples: Int,
                          val numSets: Int,
                          val points: List<T>) : Cloneable {

    /**
     * Constructor.
     *
     * @param basis
     *        The sample basis to copy from.
     */
    constructor(basis: SampleBasis<T>?)
        : this(basis!!.numSamples, basis.numSets, basis.points)

    init {
        require(this.points.size == (this.numSamples * this.numSets)) {
            "The number of points does not match the basis specification: " +
            "$this.samples.size is not: ${this.numSamples * this.numSets}."
        }
    }

    public override fun clone(): SampleBasis<T> = SampleBasis(this)
}
