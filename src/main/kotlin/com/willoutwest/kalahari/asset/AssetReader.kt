package com.willoutwest.kalahari.asset

import java.io.InputStream

/**
 * Represents a mechanism for creating one or more assets from a single input
 * stream.
 */
@FunctionalInterface
interface AssetReader {

    /**
     * Creates a new asset from the specified input stream.
     *
     * @param key
     *        The asset key to use.
     * @param stream
     *        The input stream to read from.
     * @param assets
     *        The asset cache to use for additional assets.
     * @return A new, loaded asset.
     * @throws MalformedAssetException
     *         If there was a problem loading the asset.
     */
    fun load(key: AssetKey, stream: InputStream, assets: AssetCache): Any
}
