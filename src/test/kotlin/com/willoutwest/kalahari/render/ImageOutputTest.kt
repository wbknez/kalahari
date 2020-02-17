package com.willoutwest.kalahari.render

import com.willoutwest.kalahari.render.outputs.ImageOutput
import io.kotlintest.data.forall
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.ShouldSpec
import io.kotlintest.tables.row
import java.nio.file.Paths

class ImageOutputTest : ShouldSpec() {

    init {

        "Obtaining the base path from a file path" {
            should("return the base name only") {
                assertAll(Gen.choose(2, 10)) { numParts: Int ->
                    val pathParts = List(numParts) {
                        Gen.string().random().first()
                    }
                    val filePath = pathParts.joinToString("/",
                                                          postfix = ".png")

                    val expected = Paths.get(pathParts.joinToString("/"))
                    val result   =
                        ImageOutput.getExtensionlessPathFromString(filePath)

                    result.toString().shouldBe(expected.toString())
                }
            }
        }

        "Obtaining an image format from a path" {
            should("return the correct format per extension") {
                forall(
                    row(".bmp", ImageOutput.Format.BMP),
                    row(".jpg", ImageOutput.Format.JPG),
                    row(".jpeg", ImageOutput.Format.JPEG),
                    row(".png", ImageOutput.Format.PNG),
                    row(".tiff", ImageOutput.Format.TIFF)
                ) { extension: String, expected: ImageOutput.Format ->
                    val numParts = Gen.choose(1, 10).random().first()
                    val pathParts = List(numParts) {
                        Gen.string().random().first()
                    }
                    val filePath = pathParts.joinToString("/",
                                                          postfix = extension)

                    val result = ImageOutput.getFormatFromString(filePath)

                    result.shouldBe(expected)
                }
            }

            should("throw an exception if the format is not found") {
                forall(
                    row(".tga"),
                    row(".")
                ) { extension: String ->
                    val numParts = Gen.choose(1, 10).random().first()
                    val pathParts = List(numParts) {
                        Gen.string().random().first()
                    }
                    val filePath = pathParts.joinToString("/",
                                                          postfix = extension)

                    shouldThrow<IllegalArgumentException> {
                        ImageOutput.getFormatFromString(filePath)
                    }
                }
            }
        }
    }
}