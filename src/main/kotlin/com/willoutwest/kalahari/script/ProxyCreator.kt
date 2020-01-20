package com.willoutwest.kalahari.script

import org.luaj.vm2.LuaValue
import org.luaj.vm2.Varargs
import org.luaj.vm2.lib.VarArgFunction
import java.lang.reflect.Proxy

/**
 * Represents an implementation of [VarArgFunction] that creates new,
 * functional Kotlin types from Lua using dynamic proxies.
 */
class ProxyCreator : VarArgFunction() {

    override fun invoke(args: Varargs?): Varargs {
        require(args!!.narg() >= 2) {
            "Proxies must have at least one interface name and implementation."
        }

        val clsLoader  = this.javaClass.classLoader
        val interfaces = Array(args.narg() - 1) {
            val clsName = args.checkjstring(it + 1)

            try {
                ClassImporter.findClassByName(clsName, clsLoader)
            }
            catch(cnfe: ClassNotFoundException) {
                throw NoSuchImportException("Could not import class for " +
                                            "proxy: ${clsName}.", cnfe)
            }
        }

        val obj     = args.checktable(args.narg())
        val handler = ProxyCreationHandler(obj)
        val proxy   = Proxy.newProxyInstance(clsLoader, interfaces, handler)

        return LuaValue.userdataOf(proxy)
    }
}