package com.willoutwest.kalahari.script

import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.jse.CoerceJavaToLua

/**
 * Represents an implementation of [OneArgFunction] that searches for,
 * loads, and returns a Java or Kotlin class as a Lua object.
 */
class ClassImporter : OneArgFunction() {

    companion object {

        /**
         * Uses the current system [ClassLoader] to find and load the
         * corresponding JVM-compatible class object for the class with the
         * specified name.
         *
         * @param clsName
         *        The name of the class to find.
         * @return A Kotlin or Java class as a JVM-compatible [Class].
         */
        fun findClassByName(clsName: String,
                            clsLoader: ClassLoader): Class<*> =
            Class.forName(clsName, true, clsLoader)
    }

    override fun call(arg: LuaValue?): LuaValue {
        val clsName   = arg!!.checkjstring()
        val clsLoader = this.javaClass.classLoader

        try {
            return CoerceJavaToLua.coerce(findClassByName(clsName, clsLoader))
        }
        catch(cnfe: ClassNotFoundException) {
            throw NoSuchImportException("Could not import class: " +
                                        "${clsName}.", cnfe)
        }
    }
}