package com.solsticesquared.kalahari.script

import org.luaj.vm2.LuaError
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue
import org.luaj.vm2.Varargs
import org.luaj.vm2.lib.VarArgFunction

sealed class ImportLib : VarArgFunction() {

    companion object {

        /**
         *
         */
        const val Init        = 0

        /**
         *
         */
        const val BindClass   = 1

        /**
         *
         */
        const val NewInstance = 2

        /**
         *
         */
        const val New         = 3

        /**
         *
         */
        const val CreateProxy = 4

        /**
         *
         */
        const val LoadLib     = 5

        /**
         *
         */
        val FuncNames = arrayOf(
            "init", "bindClass", "newInstance", "new", "createProxy", "loadLib"
        )

        /**
         *
         *
         * @param className
         *        The name of the class to find.
         * @return A Kotlin or Java class as a JVM-compatible [Class].
         */
        fun findClassByName(className: String): Class<*> =
            Class.forName(className, true,
                          ClassLoader.getSystemClassLoader()).javaClass
    }

    private val nameToAlias = mutableMapOf<String, String>()

    private val aliasToName = mutableMapOf<String, String>()

    private fun doBindClass(args: Varargs?): Varargs {
        // TODO: Implement me!
        return args!!
    }

    private fun doCreateProxy(args: Varargs?): Varargs {
        // TODO: Implement me!
        return args!!
    }

    private fun doInit(args: Varargs?): Varargs {
        val env     = args!!.arg(2)
        val table   = LuaTable()

        this.bind(table, this.javaClass, FuncNames, Init)
        env.set("implib", table)

        if(!env.get("package").isnil()) {
            env.get("package")
               .get("loaded")
               .set("implib", table)
        }

        return table
    }

    private fun doLoadLib(args: Varargs?): Varargs {
        val className  = args!!.checkjstring(1)
        val methodName = args.checkjstring(2)

        val classz     = findClassByName(className)
        val method     = classz.getMethod(methodName)
        val result     = method.invoke(classz)

        return when(result) {
            is LuaValue -> result
            else        -> NIL
        }
    }

    private fun doNew(args: Varargs?): Varargs {
        // TODO: Implement me!
        return args!!
    }

    private fun doNewInstance(args: Varargs?): Varargs {
        // TODO: Implement me!
        return args!!
    }

    override fun invoke(args: Varargs?): Varargs =
        when (this.opcode) {
            Init        -> this.doInit(args)
            BindClass   -> this.doBindClass(args)
            New         -> this.doNew(args)
            NewInstance -> this.doNewInstance(args)
            CreateProxy -> this.doCreateProxy(args)
            LoadLib     -> this.doLoadLib(args)
            else        -> throw LuaError("Function not supported: ${this}.")
        }
}