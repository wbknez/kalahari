package com.willoutwest.kalahari.script

import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.TwoArgFunction

/**
 * Represents an implementation of [TwoArgFunction] that adds a small
 * library of functions as a module to a Lua scripting environment.
 *
 * This module is named the Kalahari Scripting Library (KHSL) and contains
 * functions that support the following operations:
 *  <ul>
 *      <li>Loading Kotlin classes into Lua by name.</li>
 *      <li>Implementing Kotlin interfaces and extending Kotlin classes
 *      using Lua with dynamic proxies.</li>
 *  </ul>
 * These functions may be accessed with the <code>khsl</code> identifier
 * inside a Lua expression or file.
 */
class ScriptingLibrary : TwoArgFunction() {

    override fun call(modname: LuaValue, env: LuaValue): LuaValue {
        val khsl = LuaTable()

        khsl.set("importClass", ClassImporter())
        khsl.set("proxyOf",     ProxyCreator())

        if(!env.get("package").isnil()) {
            env.get("package")
                .get("loaded")
                .set("khsl", khsl)
        }

        return khsl
    }
}