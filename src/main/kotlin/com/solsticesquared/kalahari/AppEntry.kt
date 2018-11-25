package com.solsticesquared.kalahari

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import org.luaj.vm2.lib.jse.JsePlatform

/**
 * The main driver for the Kalahari ray tracing project.
 */
sealed class AppEntry {

    /**
     * Represents a collection of command line arguments and the quantity and
     * types of the values they accept.
     */
    private class Arguments(parser: ArgParser) {

        val file by parser.storing(
            "-f", "--file", help = "a Lua file that describes a scene"
        )
    }

    companion object {

        /**
         * The application entry point.
         *
         * @param args
         *        The array of command line arguments, if any.
         */
        @JvmStatic
        fun main(args: Array<String>): Unit = mainBody("kalahari") {
            ArgParser(args).parseInto(::Arguments).run {
                val globals = JsePlatform.standardGlobals()
                val chunk   = globals.loadfile(this.file)

                chunk.call()
            }
        }
    }
}
