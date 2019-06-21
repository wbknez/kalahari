package com.solsticesquared.kalahari

import com.solsticesquared.kalahari.script.KhluaLib
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import org.luaj.vm2.Globals
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
         * Creates and initializes a set of [Globals] for use with a Lua
         * interpreter that also includes a custom utility library for this
         * project.
         *
         * @return A set of initialized Lua libraries.
         */
        private fun initLibraryGlobals(): Globals {
            val globals = JsePlatform.standardGlobals()

            globals.load(KhluaLib())

            return globals
        }

        /**
         * The application entry point.
         *
         * @param args
         *        The array of command line arguments, if any.
         * @return An exit code.
         */
        @JvmStatic
        fun main(args: Array<String>): Unit = mainBody("kalahari") {
            ArgParser(args).parseInto(::Arguments).run {
                val globals = initLibraryGlobals()
                val chunk   = globals.loadfile(this.file)

                chunk.call()
            }
        }
    }
}
