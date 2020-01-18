package com.willoutwest.kalahari

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import com.xenomachina.argparser.mainBody
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger

/**
 * The main driver for the Kalahari ray tracing project.
 */
sealed class AppEntry {

    companion object {

        /**
         * The number of cores available to the system on which the current
         * virtual machine is running.
         */
        val NumProcessors  = Runtime.getRuntime().availableProcessors()

        /**
         * Represents a collection of command line arguments and the quantity and
         * types of the values they accept.
         */
        private class Arguments(parser: ArgParser) {

            val file by parser.storing(
                "-f", "--file", help = "a Lua file that describes a scene"
            )

            val quiet by parser.flagging(
                "-q", "--quiet", help = "disable logging output"
            )

            val threads by parser.storing(
                "-t", "--threads", help = "number of threads"
            ) { toInt() }.default(NumProcessors)

            val verbose by parser.flagging(
                "-v", "--verbose", help = "enable all extra logging output"
            )
        }

        /**
         * Initializes the Java Logging Library using a properties
         * configuration file and with the user-specified command line
         * arguments for verbosity.
         *
         * @param quiet
         *        Whether or not to disable logging; this always takes
         *        priority over [verbose].
         * @param verbose
         *        Whether or not to include extra, or fine-grained, log
         *        messages that would normally not be output.
         */
        private fun initLogging(quiet: Boolean, verbose: Boolean) {
            val classLoader = AppEntry::class.java.classLoader
            val logFilename = "logging.properties"
            val rootName    = AppEntry::class.java.`package`.name

            if(!quiet) {
                classLoader.getResourceAsStream(logFilename).use {
                    LogManager.getLogManager().readConfiguration(it)
                }
            }

            val root = Logger.getLogger(rootName)

            if(verbose) {
                root.level = Level.FINEST
            }

            if(quiet) {
                root.level = Level.OFF
            }
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
                initLogging(this.quiet, this.verbose)
            }
        }
    }
}