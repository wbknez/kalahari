package com.willoutwest.kalahari.asset

import java.io.IOException
import java.nio.file.Path

/**
 * Represents a collection of assets in various forms and created
 * from arbitrary sources.
 *
 * @property cache
 *           The mapping of assets to unique identifier and file path pairs.
 * @property loader
 *           The collection of readers and stream sources used to provide
 *           I/O operations.
 */
class AssetCache : AutoCloseable {

    private val cache  = mutableMapOf<AssetKey, Any>()

    val loader         = AssetLoader()

    override fun close() {
        this.cache.clear()
        this.loader.close()
    }

    operator fun contains(key: AssetKey): Boolean = key in this.cache

    operator fun get(id: String, path: Path): Any = this[AssetKey(id, path)]

    operator fun get(key: AssetKey): Any =
        this.cache[key] ?: this.load(key, true)

    /**
     * Attempts to load the asset with the specified key identifier and file
     * path, registering it with this asset cache if necessary.
     *
     * If the specified key has already been used to load an asset, then
     * that asset will be returned and no additional operations will take
     * place.
     *
     * @param id
     *        The asset identifier to use.
     * @param path
     *        The asset file path to use.
     * @param register
     *        Whether or not to place the final asset into the cache.
     * @return A loaded asset.
     * @throws MalformedAssetException
     *         If there was a problem loading the asset.
     * @throws NoSuchReaderException
     *         If no reader is associated with an extension.
     * @throws NoSuchStreamException
     *         If a stream source could not be found.
     * @throws StreamFailureException
     *         If there was any other problem with I/O operations.
     */
    fun load(id: String, path: Path, register: Boolean): Any {
        return this.load(AssetKey(id, path), register)
    }

    /**
     * Attempts to load the asset with the specified key, registering it
     * with this asset cache if necessary.
     *
     * If the specified key has already been used to load an asset, then
     * that asset will be returned and no additional operations will take
     * place.
     *
     * @param key
     *        The asset identifier and file path to use.
     * @param register
     *        Whether or not to place the final asset into the cache.
     * @return A loaded asset.
     * @throws MalformedAssetException
     *         If there was a problem loading the asset.
     * @throws NoSuchReaderException
     *         If no reader is associated with an extension.
     * @throws NoSuchStreamException
     *         If a stream source could not be found.
     * @throws StreamFailureException
     *         If there was any other problem with I/O operations.
     */
    fun load(key: AssetKey, register: Boolean): Any {
        if(key in this.cache) {
            return this.cache[key]!!
        }

        val reader = this.loader.getReader(key.path)
        val stream = this.loader.getStream(key.path)

        try {
            val asset = reader.load(key, stream, this)

            if(register) {
                this.cache[key] = asset
            }

            return asset
        }
        catch(ioEx: IOException) {
            throw StreamFailureException("Could not load asset with key: " +
                                         "${key}.", ioEx)
        }
    }
}