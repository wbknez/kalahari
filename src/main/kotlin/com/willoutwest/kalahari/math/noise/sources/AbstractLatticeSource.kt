package com.willoutwest.kalahari.math.noise.sources

import com.willoutwest.kalahari.math.noise.NoiseSource

/**
 * Represents an implementation of [NoiseSource] that provides basic
 * infrastructure for creating noise from a three-dimensional integer lattice.
 *
 * @property indexTable
 *           The table of lattice points.
 * @property valueTable
 *           The table of source-specific values.
 */
abstract class AbstractLatticeSource<T>(
    protected val indexTable: Array<Short>,
    protected val valueTable: Array<T>) : NoiseSource