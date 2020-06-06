package com.willoutwest.kalahari

import com.willoutwest.kalahari.asset.AssetCache
import com.willoutwest.kalahari.asset.readers.ImageReader
import com.willoutwest.kalahari.asset.sources.ClasspathStreamSource
import com.willoutwest.kalahari.asset.sources.FileStreamSource
import com.willoutwest.kalahari.render.Pipeline
import com.willoutwest.kalahari.render.Tracer
import com.willoutwest.kalahari.render.outputs.ImageOutput
import com.willoutwest.kalahari.scene.Scene
import com.willoutwest.kalahari.script.ScriptingModule
import com.willoutwest.kalahari.script.toLua
import com.willoutwest.kalahari.util.use
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import com.xenomachina.argparser.mainBody
import org.luaj.vm2.Globals
import org.luaj.vm2.lib.jse.JsePlatform
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger
import javax.imageio.ImageIO

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

        val output by parser.storing(
            "-o", "--output", help = "the output image file"
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

    companion object {

        /**
         * The number of cores available to the system on which the current
         * virtual machine is running.
         */
        val NumProcessors  = Runtime.getRuntime().availableProcessors()

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
         * Creates and initializes an asset cache with all potential objects
         * supported in this project associated with their readers, as well
         * as support for finding assets on the classpath and local filesystem.
         *
         * @return An asset cache with all supported readers.
         */
        private fun createDefaultCache(): AssetCache {
            val assets = AssetCache()

            assets.loader.appendSource(FileStreamSource())
            assets.loader.appendSource(ClasspathStreamSource())

            ImageIO.getReaderFileSuffixes().forEach {
                assets.loader.associateReader(it, ImageReader())
            }

            return assets
        }

        /**
         * Creates and initializes a set of global values for use with a Lua
         * interpreter, loading all necessary libraries into the global
         * namespace.
         *
         * @return A set of initialized Lua libraries.
         */
        private fun initScriptingEnvironment(): Globals {
            val globals = JsePlatform.standardGlobals()

            globals.load(ScriptingModule())

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
                initLogging(this.quiet, this.verbose)

                val assets   = createDefaultCache()
                val pipeline = Pipeline(this.threads)

                arrayOf(assets, pipeline).use {
                    pipeline.addListener(ImageOutput(this.output))

                    val globals = initScriptingEnvironment()
                    val chunk   = globals.loadfile(this.file)

                    val scene  = Scene()
                    val tracer = Tracer()

                    globals.set("assets", toLua(assets))
                    globals.set("scene",  toLua(scene))
                    globals.set("tracer", toLua(tracer))

                    chunk.call()

                    pipeline.prepare(scene, tracer)
                    pipeline.submit(scene, tracer)
                }
            }
        }
    }
}