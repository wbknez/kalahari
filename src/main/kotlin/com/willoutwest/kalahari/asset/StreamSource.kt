package com.willoutwest.kalahari.asset

import java.io.InputStream
import java.nio.file.Path

/**
 * Represents a mechanism for obtaining an input stream from an arbitrary
 * source.
 */
interface StreamSource : AutoCloseable {

    /**
     * Opens a new input stream to the specified path.
     *
     * @param path
     *        The path to open a stream to.
     * @return A new (opened) input stream.
     * @throws StreamFailureException
     *         If there was a problem opening the stream.
     * @throws NoSuchStreamException
     *         If the stream to a path does not exist.
     */
    fun open(path: Path): InputStream
}