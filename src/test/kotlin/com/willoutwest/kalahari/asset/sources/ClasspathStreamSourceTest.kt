package com.willoutwest.kalahari.asset.sources

import com.willoutwest.kalahari.asset.NoSuchStreamException
import com.willoutwest.kalahari.asset.StreamSource
import io.kotlintest.shouldNotThrow
import io.kotlintest.shouldThrow
import io.kotlintest.specs.ShouldSpec
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Test suite for [ClasspathStreamSource].
 */
class ClasspathStreamSourceTest : ShouldSpec() {

    private val source: StreamSource =
        ClasspathStreamSource()

    private val testPath: Path = Paths.get("searchforme.txt")

    init {

        "Opening a stream to a valid file on the classpath" {
            should("not throw an exception.") {
                shouldNotThrow<NoSuchStreamException> {
                    source.open(testPath)
                }
            }
        }

        "Opening a stream to an invalid file on the classpath" {
            should("throw an exception.") {
                shouldThrow<NoSuchStreamException> {
                    source.open(Paths.get("/nonexistent_path.txt"))
                }
            }
        }
    }
}