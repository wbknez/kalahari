package com.willoutwest.kalahari.asset.sources

import com.willoutwest.kalahari.asset.NoSuchStreamException
import com.willoutwest.kalahari.asset.StreamSource
import java.io.InputStream
import java.nio.file.Path

/**
 * Represents an implementation of [StreamSource] that utilizes the JVM
 * classpath to provide new input streams for a given path.
 */
class ClasspathStreamSource : StreamSource {

    override fun close() {
    }

    override fun open(path: Path): InputStream {
        val classLoader = Thread.currentThread().contextClassLoader
        val fileName    = path.normalize().toString()

        return classLoader.getResourceAsStream(fileName) ?:
            throw NoSuchStreamException(
                "No input stream on classpath: $path."
            )
    }
}
