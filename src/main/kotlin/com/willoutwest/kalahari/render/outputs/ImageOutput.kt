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
        JPG("jpeg"),

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

    private var img: BufferedImage? = null

    override fun onComplete() {
        val imgPath = Paths.get(this.basePath.toString(), ".",
                                this.format.extension)
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