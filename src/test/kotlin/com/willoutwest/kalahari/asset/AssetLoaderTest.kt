package com.willoutwest.kalahari.asset

import com.willoutwest.kalahari.asset.sources.ClasspathStreamSource
import io.kotlintest.IsolationMode
import io.kotlintest.matchers.collections.shouldHaveSingleElement
import io.kotlintest.matchers.collections.shouldHaveSize
import io.kotlintest.matchers.string.shouldBeEmpty
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.matchers.types.shouldBeTypeOf
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotThrow
import io.kotlintest.shouldThrow
import io.kotlintest.specs.ShouldSpec
import java.io.InputStream
import java.nio.file.Paths

/**
 * Test suite for [AssetLoader].
 */
class AssetLoaderTest : ShouldSpec() {

    private class TestReader : AssetReader {

        override fun load(key: AssetKey, stream: InputStream,
                          assets: AssetCache): Any {
            return Any()
        }
    }

    override fun isolationMode(): IsolationMode? =
        IsolationMode.InstancePerTest

    private val loader: AssetLoader = AssetLoader()

    init {

        "Extracting the extension of a valid file path with a single period" {
            val path = Paths.get("test_file.json")
            val ext  = loader.getExtension(path)

            should("be as expected.") {
                ext shouldBe "json"
            }

            should("produce a single component only.") {
                ext.split(".").shouldHaveSize(1)
            }
        }

        "Extracting the extension of a valid file path with multiple periods" {
            val path = Paths.get("test_file.test.example.json")
            val ext  = loader.getExtension(path)

            should("be as expected.") {
                ext shouldBe "test.example.json"
            }

            should("produce the same number of components as periods.") {
                ext.split('.').shouldHaveSize(3)
            }
        }

        "Extracting the extension of an empty file path" {
            val path = Paths.get("")
            val ext  = loader.getExtension(path)

            should("be empty.") {
                ext.shouldBeEmpty()
            }

            should("produce no components.") {
                ext.split('.').shouldHaveSize(1)
                ext.split('.').shouldHaveSingleElement("")
            }
        }

        "Obtaining a reader for an extension that has an association" {
            loader.associateReader("xml", TestReader())

            should("not throw an exception.") {
                shouldNotThrow<NoSuchReaderException> {
                    loader.getReader(Paths.get("test.xml"))
                }
            }

            should("return an instance of the correct reader type.") {
                loader.getReader(Paths.get("test.xml"))
                        .shouldBeTypeOf<TestReader>()
            }
        }

        "Obtaining a reader for an extension that has no association" {
            should("throw an exception.") {
                shouldThrow<NoSuchReaderException> {
                    loader.getReader(Paths.get("test.json"))
                }
            }
        }

        "Obtaining a stream for a path that has a source" {
            loader.prependSource(ClasspathStreamSource())

            should("return a valid input stream.") {
                loader.getStream(Paths.get("searchforme.txt"))
                      .shouldBeInstanceOf<InputStream>()
            }

            should("not throw an exception.") {
                shouldNotThrow<NoSuchStreamException> {
                    loader.getStream(Paths.get("searchforme.txt"))
                }
            }
        }

        "Obtaining a stream for a path that has no source" {
            should("throw an exception.") {
                shouldThrow<NoSuchStreamException> {
                    loader.getStream(Paths.get("example.csv"))
                }
            }
        }
    }
}