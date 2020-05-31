package com.willoutwest.kalahari.asset.readers

import com.willoutwest.kalahari.asset.AssetCache
import com.willoutwest.kalahari.asset.AssetKey
import com.willoutwest.kalahari.asset.AssetReader
import com.willoutwest.kalahari.math.Color3
import com.willoutwest.kalahari.math.intToComponent
import com.willoutwest.kalahari.texture.Image
import java.io.InputStream
import javax.imageio.ImageIO

/**
 * Represents an implementation of [AssetReader] that converts an input
 * stream to an image
 */
class ImageReader : AssetReader {

    override fun load(key: AssetKey, stream: InputStream,
                      assets: AssetCache): Any {
        val buffered = ImageIO.read(stream)

        val data     = mutableListOf<Color3>()

        for(y in 0 until buffered.height) {
            for(x in 0 until buffered.width) {
                val rgb   = buffered.getRGB(x, y)

                val blue  = intToComponent(rgb and 0xff)
                val green = intToComponent((rgb and 0xff00) shr 8)
                val red   = intToComponent((rgb and 0xff0000) shr 16)

                data.add(Color3(red, green, blue))
            }
        }

        return Image(buffered.width, buffered.height, data)
    }
}