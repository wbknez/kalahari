package com.willoutwest.kalahari.asset

import java.nio.file.Path
import java.nio.file.Paths

/**
 * Represents a collection of unique identifying information about an asset.
 *
 * @property id
 *           A unique identifier.
 * @property path
 *           The path to an input stream.
 */
data class AssetKey(val id: String, val path: Path) : Cloneable {

    /**
     * Constructor.
     *
     * @param id
     *        The unique identifier to use.
     * @param filename
     *        The file path to use.
     */
    constructor(id: String, filename: String) : this(id, Paths.get(filename))

    constructor(key: AssetKey?) : this(key!!.id, key.path)

    override fun clone(): AssetKey = AssetKey(this)
}
