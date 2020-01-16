package com.willoutwest.kalahari.asset.sources

import com.willoutwest.kalahari.asset.NoSuchStreamException
import com.willoutwest.kalahari.asset.StreamSource
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

/**
 * Represents an implementation of [StreamSource] that utilizes the local
 * filesystem to provide new input streams for a given path.
 */
class FileStreamSource :
    StreamSource {

    override fun close() {}

    override fun open(path: Path): InputStream {
        if(!Files.exists(path)) {
            throw NoSuchStreamException(
                "No input stream at path: $path.")
        }

        return Files.newInputStream(path)
    }
}