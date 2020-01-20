package com.willoutwest.kalahari.asset

import com.willoutwest.kalahari.asset.sources.ClasspathStreamSource
import io.kotlintest.IsolationMode
import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import java.io.InputStream

/**
 * Test suite for [AssetCache].
 */
class AssetCacheTest : ShouldSpec() {

    private class StringAssetReader : AssetReader {

        override fun load(key: AssetKey, stream: InputStream,
                          assets: AssetCache): Any {
            return String(stream.readAllBytes())
        }
    }

    override fun isolationMode(): IsolationMode? =
        IsolationMode.InstancePerTest

    private val cache: AssetCache = AssetCache()

    init {

        "Loading and registering an example string asset from a file" {
            val key   = AssetKey("example_asset", "searchforme.txt")

            cache.loader.associateReader("txt", StringAssetReader())
            cache.loader.appendSource(ClasspathStreamSource())

            should("return the string as expected.") {
                val asset = cache.load(key, true)

                asset shouldBe "I am an example asset!"
            }

            should("add the asset to the cache.") {
                cache.load(key, true)

                (key in cache).shouldBeTrue()
            }
        }

        "Loading and not registering an example string asset from a file" {
            val key   = AssetKey("example_asset", "searchforme.txt")

            cache.loader.associateReader("txt", StringAssetReader())
            cache.loader.appendSource(ClasspathStreamSource())

            should("return the string as expected.") {
                val asset = cache.load(key, false)

                asset shouldBe "I am an example asset!"
            }

            should("not add the asset to the cache.") {
                cache.load(key, false)

                (key in cache).shouldBeFalse()
            }
        }
    }
}