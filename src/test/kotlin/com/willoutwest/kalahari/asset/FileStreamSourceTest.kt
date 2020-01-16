package com.willoutwest.kalahari.asset

import io.kotlintest.TestCase
import io.kotlintest.TestResult
import io.kotlintest.shouldNotThrow
import io.kotlintest.shouldThrow
import io.kotlintest.specs.ShouldSpec
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Test suite for [FileStreamSource].
 */
class FileStreamSourceTest : ShouldSpec() {

    private val source: StreamSource = FileStreamSource()

    private var testPath: Path? = null

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)

        this.testPath =
            Files.createTempFile("FileStreamSourceTest", ".txt")
    }

    override fun afterTest(testCase: TestCase, result: TestResult) {
        super.afterTest(testCase, result)

        Files.deleteIfExists(this.testPath!!)
    }

    init {
        "a file located on disk with a valid path" {
            should("be opened without error.") {
                shouldNotThrow<NoSuchStreamException> {
                    source.open(testPath!!)
                }
            }
        }

        "opening a file with an invalid path" {
            should("thrown an exception.") {
                shouldThrow<NoSuchStreamException> {
                    source.open(Paths.get("nonexistent_path.txt"))
                }
            }
        }
    }
}