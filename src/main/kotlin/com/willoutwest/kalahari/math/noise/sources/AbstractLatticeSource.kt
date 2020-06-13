package com.willoutwest.kalahari.math.noise.sources

import com.willoutwest.kalahari.math.noise.NoiseSource

/**
 * Represents an implementation of [NoiseSource] that provides basic
 * infrastructure for creating noise based on a three-dimensional integer
 * lattice.
 *
 * @property indices
 *           The table of lattice points.
 * @property values
 *           The table of source-specific values.
 */
abstract class AbstractLatticeSource(indexSize: Int,
                                     valueSize: Int) : NoiseSource {

    protected val indices: ShortArray = ShortArray(indexSize)

    protected val values: FloatArray = FloatArray(valueSize)
}