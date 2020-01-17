package com.willoutwest.kalahari.asset

import com.willoutwest.kalahari.asset.sources.ClasspathStreamSource
import io.kotlintest.TestCase
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import java.io.InputStream
import java.nio.file.Paths

/**
 * Test suite for [AssetCache].
 */
class AssetCacheTest : ShouldSpec() {

    companion object {

        private class StringAssetReader : AssetReader {

            override fun load(key: AssetKey, stream: InputStream,
                              assets: AssetCache): Any {
                return String(stream.readAllBytes())
            }
        }
    }

    private var cache: AssetCache = AssetCache()

    override fun beforeTest(testCase: TestCase) {
        super.beforeTest(testCase)

        this.cache.close()
        this.cache = AssetCache()
    }

    init {
        "loading and registering an example string asset from a file" {
            should("return the string as expected.") {
                cache.loader.associateReader("txt", StringAssetReader())
                cache.loader.appendSource(ClasspathStreamSource())

                val key   = AssetKey("example_asset", "searchforme.txt")
                val asset = cache.load(key, true)

                asset shouldBe "I am the walrus!"
            }

            should("add the asset to the cache.") {
                val key   = AssetKey("example_asset", "searchforme.txt")

                cache.loader.associateReader("txt", StringAssetReader())
                cache.loader.appendSource(ClasspathStreamSource())
                cache.load(key, true)

                (key in cache).shouldBeTrue()
            }
        }

        "loading and not registering an example string asset from a file" {
            should("return the string as expected.") {
                cache.loader.associateReader("txt", StringAssetReader())
                cache.loader.appendSource(ClasspathStreamSource())

                val key   = AssetKey("example_asset", "searchforme.txt")
                val asset = cache.load(key, false)

                asset shouldBe "I am the walrus!"
            }

            should("not add the asset to the cache.") {
                val key   = AssetKey("example_asset", "searchforme.txt")

                cache.loader.associateReader("txt", StringAssetReader())
                cache.loader.appendSource(ClasspathStreamSource())
                cache.load(key, false)

                (key in cache).shouldBeFalse()
            }
        }
    }
}