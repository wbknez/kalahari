package com.willoutwest.kalahari.util

import com.xenomachina.text.trimNewline
import java.lang.IllegalArgumentException
import java.text.MessageFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.ResourceBundle
import java.util.logging.Formatter
import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.LogRecord

/**
 * Represents an implementation [Formatter] that writes log records to an
 * HTML file with a simplified textual presentation.
 */
class HtmlFormatter : Formatter() {

    companion object {

        /**
         * Represents a mapping of logging levels to HTML format strings.
         */
        private val LevelFormat = mapOf<Level, String>(
            Level.CONFIG  to "<em class=\"config\">>></em>&nbsp;",
            Level.FINE    to "&nbsp;-&nbsp;",
            Level.FINER   to "&nbsp;-&nbsp;",
            Level.FINEST  to "&nbsp;-&nbsp;",
            Level.INFO    to "",
            Level.WARNING to "<em class=\"warning\">**</em>&nbsp;",
            Level.SEVERE  to "<em class=\"error\">!!</em>&nbsp;"
        )
    }

    override fun format(record: LogRecord?): String {
        val message = this.formatLogMessage(record!!.message, record.level,
                                            record.resourceBundle)
        val params  = this.formatParameters(record.parameters)

        return when(this.isParameterized(message, params)) {
            false -> message
            true  -> try {
                MessageFormat.format(message, *params!!)
            }
            catch(iaEx: IllegalArgumentException) {
                message
            }
        }
    }

    /**
     * Returns the current date and time as a formatted string.
     *
     * @return The current date and time.
     */
    fun formatCurrentDateTime(): String =
        DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                         .format(LocalDateTime.now())

    /**
     * Utilizes the specified resource bundle, if any, to localize the
     * specified message pattern; otherwise, the pattern is returned unaltered.
     *
     * @param pattern
     *        The message pattern to use.
     * @param bundle
     *        The localization bundle to use.
     * @return A localized message.
     */
    fun formatLocalizedMessage(pattern: String,
                               bundle: ResourceBundle?): String =
        try {
            bundle!!.getString(pattern)
        }
        catch(ignored: Exception) {
            pattern
        }

    /**
     * Creates a log message with the specified pattern whose prefix depends
     * on the specified log level and with the specified localization, if any.
     *
     * @param pattern
     *        The message pattern to use.
     * @param level
     *        The log level to use.
     * @param bundle
     *        The localization bundle to use.
     * @return A formatted log message.
     */
    fun formatLogMessage(pattern: String, level: Level,
                         bundle: ResourceBundle?): String =
        """
        ${LevelFormat[level]}
        ${this.formatLocalizedMessage(pattern, bundle)}
        <br />
        """.trimIndent()

    /**
     * Formats the specified collection of parameters, pre- and post-fixing
     * them with HTML tags to emphasize them when viewed in a web browser.
     *
     * @param params
     *        The collection of parameters to format.
     * @return A collection of formatted parameters.
     */
    fun formatParameters(params: Array<out Any>?): Array<String>? =
        params?.map{
            val repr = when(it is Array<*>) {
                false -> it.toString()
                true  -> it.contentToString()
            }

            "<em class=\"variable\">$repr</em>"
        }?.toTypedArray()

    override fun getHead(h: Handler?): String =
        """
        <!doctype html>
        
        <html lang="en">
            <head>
                <meta charset="utf-8">
                <title>Kalahari Log File</title>
                <style>
                em.config {
                    color:       green;
                    font-weight: bold;
                }
                em.error {
                    color:       red;
                    font-weight: bold;
                }
                em.variable {
                    color:       purple;
                    font-weight: bold;
                }
                em.warning {
                    color:       orange;
                    font-weight: bold;
                }
                p {
                    font-family: "Lucida Console", Monaco, monospace;
                    font-size:   18px;
                }
                </style>
            </head>
            <body>
            <header>
                <p style="text-align: center">
                    ${this.formatCurrentDateTime()}
                </p>
            </header>
            <p>
        """.trimIndent()

    override fun getTail(h: Handler?): String =
        """
            
            </p>
            </body>
        </html>
        """.trimIndent()

    /**
     * Returns whether or not the specified log message is parameterized; that
     * is, if any variable substitutions using the specified collection of
     * parameters need to be made before formatting is finalized.
     *
     * @param message
     *        The log message to use.
     * @param params
     *        The collection of
     * @return Whether or not a log message needs variable substitution.
     */
    fun isParameterized(message: String, params: Array<String>?): Boolean =
        message.indexOf("{0") >= 0 && params?.isNotEmpty() ?: false
}