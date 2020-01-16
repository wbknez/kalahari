package com.willoutwest.kalahari.asset

import java.nio.file.Path

/**
 * Represents a collection of unique identifying information about an asset.
 *
 * @property id
 *           A unique identifier.
 * @property path
 *           The path to an input stream.
 */
data class AssetKey(val id: String, val path: Path) : Cloneable {

    constructor(key: AssetKey?) : this(key!!.id, key.path)

    override fun clone(): AssetKey = AssetKey(this)
}
