package com.willoutwest.kalahari.asset

import java.io.InputStream
import java.nio.file.Path

/**
 * Represents a mechanism for managing asset readers and stream sources in a
 * manner distinct from direct asset management.
 *
 * @property readers
 *           The mapping of asset readers associated to file extensions.
 * @property searchPath
 *           The collection of available stream sources used to procure new
 *           input streams.
 */
class AssetLoader : AutoCloseable {

    private val readers    = mutableMapOf<String, AssetReader>()

    private val searchPath = mutableListOf<StreamSource>()

    /**
     * Adds the specified stream source to the back of this asset loader's
     * search path.
     *
     * @param source
     *        The stream source to append.
     */
    fun appendSource(source: StreamSource) {
        this.searchPath.add(source)
    }

    /**
     * Associates the specified asset reader with the specified file extension.
     *
     * @param extension
     *        The file extension to use.
     * @param reader
     *        The asset reader to associate.
     */
    fun associateReader(extension: String, reader: AssetReader) {
        this.readers[extension] = reader
    }

    override fun close() {
        for(source in this.searchPath) {
            source.close()
        }
    }

    /**
     * Searches for and extracts the longest possible extension from the
     * specified file path.
     *
     * Please note the specification on "longest" extension.  Given a
     * path such as <code>test_file.backup.json</code>, this function will
     * return <code>backup.json</code>, never <code>json</code>.  This is
     * done to support specializing asset readers based on extension for
     * simplicity.
     *
     * @param path
     *        The file path to extract the extension from.
     * @return The longest possible file extension.
     */
    fun getExtension(path: Path): String {
        val filename = path.toString().toLowerCase()
        val index    = filename.indexOf('.')

        return when((index >= 0) && (index < filename.length - 1)) {
            false -> ""
            true  -> filename.substring(index + 1)
        }
    }

    /**
     * Finds a reader associated with the extension of the specified file path.
     *
     * @param path
     *        The asset file path to find a reader for.
     * @return A reader for a specific file extension.
     * @throws NoSuchReaderException
     *         If no reader is associated with an extension.
     */
    fun getReader(path: Path): AssetReader {
        val ext = this.getExtension(path)
        return this.readers[ext] ?:
               throw NoSuchReaderException("Could not find asset reader for " +
                                           "path: ${path}.")
    }

    /**
     * Finds a stream for the specified file path by iterating over this
     * asset loader's search path until a stream source open operation
     * succeeds.
     *
     * @param path
     *        The asset file path to obtain an input stream for.
     * @return An input stream for a specific file path.
     * @throws NoSuchStreamException
     *         If a stream source could not be found.
     */
    fun getStream(path: Path): InputStream {
        this.searchPath.forEach {
            try {
                return it.open(path)
            }
            catch(ignored: NoSuchStreamException) {
            }
        }

        throw NoSuchStreamException("Could not find a stream source for " +
                                    "path: ${path}.")
    }

    /**
     * Adds the specified stream source to the front of this asset loader's
     * search path.
     *
     * @param source
     *        The stream source to prepend.
     */
    fun prependSource(source: StreamSource) {
        this.searchPath.add(0, source)
    }

    override fun toString(): String {
        return "AssetLoader(${this.readers.keys}, ${this.searchPath.size})"
    }
}