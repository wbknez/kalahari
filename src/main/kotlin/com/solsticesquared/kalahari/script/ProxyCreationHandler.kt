package com.solsticesquared.kalahari.script

import org.luaj.vm2.LuaError
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.CoerceJavaToLua
import org.luaj.vm2.lib.jse.CoerceLuaToJava
import java.lang.reflect.Array
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * Represents an implementation of [InvocationHandler] that builds dynamic
 * Java interfaces from Lua code and returns them for use with a Lua
 * interpreter.
 *
 * This class is based on the original
 * [org.luaj.vm2.lib.jse.LuajavaLib.ProxyInvocationHandler] but has been
 * rewritten in Kotlin for use with [KhluaLib].
 *
 * @property luaObj
 *           The Lua object to assign the resulting interface to.
 */
class ProxyCreationHandler(private val luaObj: LuaValue) : InvocationHandler {

    companion object {

        /**
         * Represents a flag denoting a method that has variable arguments.
         */
        const val MethodMask    = 0x80
    }

    override fun invoke(proxy: Any?, method: Method?,
                        args: kotlin.Array<out Any>?): Any {
        val name = method!!.name
        val func = this.luaObj.get(name)

        if(func.isnil()) {
            throw LuaError("Could not find proxy function: $func.")
        }

        val hasVarArgs = (method.modifiers and MethodMask) != 0
        val numArgs    = args?.size ?: 0

        val n = when(hasVarArgs) {
            false -> numArgs
            true  -> numArgs - 1
        }
        val m = when(hasVarArgs) {
            false -> 0
            true  -> Array.getLength(args!![n])
        }

        val argList = kotlin.Array<LuaValue>(n + m) {
            when(it < n) {
                false -> CoerceJavaToLua.coerce(Array.get(args!![n], it - n))
                true  -> CoerceJavaToLua.coerce(args!![it])
            }
        }

        return CoerceLuaToJava.coerce(func.invoke(argList).arg1(),
                                      method.returnType)
    }
}