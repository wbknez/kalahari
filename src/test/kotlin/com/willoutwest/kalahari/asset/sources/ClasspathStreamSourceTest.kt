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
        "a valid file located on disk" {
            should("be opened without error.") {
                shouldNotThrow<NoSuchStreamException> {
                    source.open(testPath)
                }
            }
        }

        "opening a file with an invalid path" {
            should("thrown an exception.") {
                shouldThrow<NoSuchStreamException> {
                    source.open(Paths.get("/nonexistent_path.txt"))
                }
            }
        }
    }
}