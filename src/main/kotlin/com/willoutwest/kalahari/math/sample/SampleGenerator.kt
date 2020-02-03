package com.willoutwest.kalahari.math.sample

import com.willoutwest.kalahari.math.Point2

/**
 * Represents a mechanism for generating sample points on a unit square, or
 * grid.
 *
 * According to the textbook a "good", or well-distributed, sampling
 * technique has the following characteristics:
 *
 *         1. The points are uniformly distributed over the 2D unit square so
 *         that clumping and gaps are minimized.
 *         2. The 1D projections of all sampling points are also uniformly
 *         distributed.
 *         3. There is some minimum distance, or epsilon, between sample points.
 *         However, in general this distance should be random and not regular
 *         (as is the case with Hammersley sampling) in order to avoid
 *         aliasing.
 *
 * Most of the sampling techniques in this package do not meet all of the
 * above criteria (if at all) but have various benefits and drawbacks in
 * relation to them.
 *
 * Sample generators operate exclusive in two dimensional space, creating
 * varied sample points across unit squares, or grids, only.  The output of a
 * sample generator is intended to be used as a basis for a mapping to other
 * types of geometry - specifically, disks, hemispheres, and spheres - but
 * are not themselves capable of doing so.
 */
interface SampleGenerator {

    /**
     * Creates a new two dimensional sample basis using some arbitrary logic.
     *
     * @param numSamples
     *        The number of samples per set to use.
     * @param numSets
     *        The number of sample sets to use.
     * @return A new sample basis defined in two dimensional space.
     */
    fun generate(numSamples: Int, numSets: Int): List<Point2>
}