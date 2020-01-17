package com.willoutwest.kalahari.asset

import com.willoutwest.kalahari.asset.sources.ClasspathStreamSource
import io.kotlintest.TestCase
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

    companion object {

        private class TestReader : AssetReader {

            override fun load(key: AssetKey, stream: InputStream,
                              assets: AssetCache): Any {
                return Any()
            }
        }
    }

    private var loader: AssetLoader = AssetLoader()

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)

        this.loader.close()
        this.loader = AssetLoader()
    }

    init {
        "the extension of a valid file path with a single period" {
            val path = Paths.get("test_file.json")
            val ext  = loader.getExtension(path)

            should("be parsed correctly.") {
                ext shouldBe "json"
            }

            should("contain a single component only.") {
                ext.split(".").shouldHaveSize(1)
            }
        }

        "the extension of a valid file path with multiple periods" {
            val path = Paths.get("test_file.test.example.json")
            val ext  = loader.getExtension(path)

            should("be parsed correctly.") {
                ext shouldBe "test.example.json"
            }

            should("contain the same number of components as periods.") {
                ext.split('.').shouldHaveSize(3)
            }
        }

        "the extension of an empty file path" {
            val path = Paths.get("")
            val ext  = loader.getExtension(path)

            should("likewise be empty.") {
                ext.shouldBeEmpty()
            }

            should("have no components.") {
                ext.split('.').shouldHaveSize(1)
                ext.split('.').shouldHaveSingleElement("")
            }
        }

        "obtaining a reader for an extension that has an association" {
            should("not throw an exception.") {
                loader.associateReader("xml", TestReader())

                shouldNotThrow<NoSuchReaderException> {
                    loader.getReader(Paths.get("test.xml"))
                }
            }

            should("return an instance of the correct reader type.") {
                loader.associateReader("xml", TestReader())

                loader.getReader(Paths.get("test.xml"))
                        .shouldBeTypeOf<TestReader>()
            }
        }

        "obtaining a reader for an extension that has no association" {
            should("throw an exception.") {
                shouldThrow<NoSuchReaderException> {
                    loader.getReader(Paths.get("test.json"))
                }
            }
        }

        "obtaining a stream for a path that has a source" {
            should("return a valid input stream.") {
                loader.prependSource(ClasspathStreamSource())

                loader.getStream(Paths.get("searchforme.txt"))
                      .shouldBeInstanceOf<InputStream>()
            }

            should("not throw an exception.") {
                loader.prependSource(ClasspathStreamSource())

                shouldNotThrow<NoSuchStreamException> {
                    loader.getStream(Paths.get("searchforme.txt"))
                }
            }
        }

        "obtaining a stream for a path that has no source" {
            should("throw an exception.") {
                shouldThrow<NoSuchStreamException> {
                    loader.getStream(Paths.get("example.csv"))
                }
            }
        }
    }
}