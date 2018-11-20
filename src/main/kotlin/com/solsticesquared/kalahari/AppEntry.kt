package com.solsticesquared.kalahari

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody

/**
 * The main driver for the Kalahari ray tracing project.
 */
sealed class AppEntry {

    /**
     * Represents a collection of command line arguments and the quantity and
     * types of the values they accept.
     */
    private class Arguments(parser: ArgParser) {
    }

    companion object {

        /**
         * The application entry point.
         *
         * @param args
         *        The array of command line arguments, if any.
         */
        @JvmStatic
        fun main(args: Array<String>) = mainBody("kalahari") {
            ArgParser(args).parseInto(::Arguments).run {
            }
        }
    }
}
