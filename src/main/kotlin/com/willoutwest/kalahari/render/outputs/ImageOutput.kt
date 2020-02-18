package com.willoutwest.kalahari.render.outputs

import com.willoutwest.kalahari.render.Bounds
import com.willoutwest.kalahari.render.PipelineListener
import com.willoutwest.kalahari.render.Pixel
import java.awt.image.BufferedImage
import java.nio.file.Path
import java.nio.file.Paths
import javax.imageio.ImageIO

/**
 * Represents an implementation of [PipelineListener] that outputs emitted
 * pixels to an image before writing it to disk in its entirety after rendering
 * is complete.
 *
 * @property basePath
 *           The extension-less path to disk.
 * @property format
 *           The type of image to produce.
 * @property img
 *           The constructed image from emitted pixels.
 */
class ImageOutput(val basePath: Path, val format: Format) :
    PipelineListener {

    /**
     * Represents a collection of supported formats for image output.
     *
     * @property extension
     *           The file extension to use with ImageIO.
     */
    enum class Format(val extension: String) {

        /**
         * Represents a Windows(tm) bitmap image.
         */
        BMP("bmp"),

        /**
         * Represents a Joint Photographic Experts Group image.
         */
        JPG("jpg"),

        /**
         * Represents a Joint Photographic Experts Group image.
         */
        JPEG("jpeg"),

        /**
         * Represents a Portable Network Graphics image.
         */
        PNG("png"),

        /**
         * Represents a Tagged Image File Format image.
         */
        TIFF("tiff")
    }

    companion object {

        /**
         * Returns the specified file path without its extension as a path
         * object.
         *
         * @param fileStr
         *        The file path to use.
         * @return A path without an extension.
         */
        fun getExtensionlessPathFromString(fileStr: String): Path =
            Paths.get(fileStr.substring(0, fileStr.lastIndexOf('.')))

        /**
         * Searches for and returns the image format associated with the
         * extension of the specified file path.
         *
         * @param fileStr
         *        The file path to use.
         * @return An image format.
         */
        fun getFormatFromString(fileStr: String): Format =
            Format.valueOf(
                fileStr.substring(fileStr.lastIndexOf('.') + 1).toUpperCase()
            )
    }

    /**
     * Constructor.
     *
     * @param filePath
     *        The image file path to use.
     */
    constructor(filePath: String) :
        this(getExtensionlessPathFromString(filePath),
             getFormatFromString(filePath))

    private var img: BufferedImage? = null

    override fun onComplete() {
        val imgPath = Paths.get(
            "${this.basePath.toString()}.${this.format.extension}"
        )
        ImageIO.write(this.img!!, this.format.extension, imgPath.toFile())
    }

    override fun onEmit(pixel: Pixel) {
        this.img!!.setRGB(pixel.x, pixel.y, pixel.rgb)
    }

    override fun onStart(bounds: Bounds) {
        this.img = BufferedImage(bounds.width, bounds.height,
                                 BufferedImage.TYPE_INT_RGB)
    }
}