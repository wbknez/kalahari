package com.solsticesquared.kalahari.script

import org.luaj.vm2.LuaError
import org.luaj.vm2.LuaString
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue
import org.luaj.vm2.Varargs
import org.luaj.vm2.lib.VarArgFunction
import org.luaj.vm2.lib.jse.CoerceJavaToLua
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Proxy

/**
 * Represents an implementation of [VarArgFunction] that mimics
 * [org.luaj.vm2.lib.jse.LuajavaLib] by providing Kotlin and Java class
 * loading capabilities with the addition of short aliases, making class
 * invocation work more like Kotlin and Java themselves.
 *
 * @property aliasToName
 *           The mapping of aliases to fully-qualified class names.
 */
sealed class KhluaLib : VarArgFunction() {

    companion object {

        /**
         * Represents a collection of function names that correspond to the
         * methods this Lua library supports and thus may be invoked.
         */
        val FuncNames = arrayOf(
            "init", "bindClass", "newInstance", "new", "createProxy", "loadLib"
        )

        /**
         * Represents the name of one or more constructors when initializing
         * an object in Lua.
         */
        val LuaConstructor = LuaValue.valueOf("new") as LuaString

        /**
         * Uses the current system [ClassLoader] to find and load the
         * corresponding JVM-compatible class object for the class with the
         * specified name.
         *
         * @param className
         *        The name of the class to find.
         * @return A Kotlin or Java class as a JVM-compatible [Class].
         */
        fun findClassByName(className: String): Class<*> =
            Class.forName(className, true,
                          ClassLoader.getSystemClassLoader()).javaClass
    }

    /**
     * Represents indices into a collection of operations this Lua library
     * supports.
     */
    object OpCodes {

        /**
         * Represents the index of the library initialization method, called
         * when this Lua library is first loaded into a Lua interpreter.
         */
        const val Init        = 0

        /**
         * Represents the index of the class binding method, called when
         * requesting a Kotlin or Java class definition from across the Lua
         * script boundary.
         */
        const val BindClass   = 1

        /**
         * Represents the index of the instance creation method, called when
         * a new instance of a Kotlin or Java type is desired.
         *
         * Please note that this method will fail unless the class object has
         * been bounded to the Lua interpreter first via [BindClass].
         */
        const val NewInstance = 2

        /**
         * Represents the index of the object creation method, called when a
         * new instance of a Kotlin or Java class object is desired.
         *
         * Please note that this method will fail unless the class object has
         * been bounded to the Lua interpreter first via [BindClass].
         */
        const val New         = 3

        /**
         * Represents the index of the interface creation method, called when
         * a new implementation of one or more Kotlin or Java interfaces is
         * desired.
         *
         * Please note that this method will fail unless any and all interface
         * objects have been bounded to the Lua interpreter first via
         * [BindClass].
         */
        const val CreateProxy = 4

        /**
         * Represents the index of the method to load a library, called when
         * additional Kotlin or Java resources are required.
         */
        const val LoadLib     = 5
    }

    private val aliasToName = mutableMapOf<String, String>()

    /**
     * Binds a class object and all its fields and methods to Lua; in
     * addition, an alias is associated with the fully-qualified class name
     * so that it may be used instead.
     *
     * @param args
     *        The collection of arguments to use.
     * @return A class object bound to Lua.
     */
    private fun doBindClass(args: Varargs?): Varargs {
        val className = args!!.checkjstring(1)
        val aliasName = when(args.narg() == 2) {
            false -> {
                val parts = className.split('.')
                parts[parts.lastIndex]
            }
            true  -> args.checkjstring(2)
        }

        when(aliasName in this.aliasToName) {
            false -> this.aliasToName[aliasName] = className
            true  -> throw LuaError("Name collision: $aliasName already bound.")
        }

        return CoerceJavaToLua.coerce(findClassByName(className))
    }

    /**
     * Creates an instance of a single object that implements one or more
     * Kotlin or Java interfaces given by name.
     *
     * @param args
     *        The collection of arguments to use.
     * @return A new instance of one or more interfaces (by name).
     */
    private fun doCreateProxy(args: Varargs?): Varargs {
        if(args!!.narg() <= 1) {
            throw LuaError("No interfaces were provided for implementation.")
        }

        val interfaces = Array(args.narg() - 1) {
            this.findClassByAlias(args.checkjstring(it + 1))
        }
        val obj = args.checktable(args.narg())

        val handler = ProxyCreationHandler(obj)
        val proxy   = Proxy.newProxyInstance(this.javaClass.classLoader,
                                             interfaces, handler)

        return LuaValue.userdataOf(proxy)
    }

    /**
     * Initializes this Lua library so that it may be used inside an
     * interpreter, adding all usable methods to a library-specific table.
     *
     * @param args
     *        The collection of arguments to use.
     * @return An initialized library as a Lua table.
     */
    private fun doInit(args: Varargs?): Varargs {
        val env     = args!!.arg(2)
        val table   = LuaTable()

        this.bind(table, this.javaClass, FuncNames, OpCodes.Init)
        env.set("khlua", table)

        if(!env.get("package").isnil()) {
            env.get("package")
               .get("loaded")
               .set("khlua", table)
        }

        return table
    }

    /**
     * Attempts to invoke a static method, or library function, with the
     * specified arguments.
     *
     * @param args
     *        The collection of arguments to use.
     * @return The result of a library invocation, otherwise
     * [org.luaj.vm2.LuaValue.NIL].
     */
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

    /**
     * Creates a new instance of a class given either a class name or type.
     *
     * If a class has a valid alias then that may be used instead.
     *
     * @param args
     *        The collection of arguments to use.
     * @return A new instance of a class (by name or type).
     */
    private fun doNew(args: Varargs?): Varargs {
        val classObj = args!!.checkvalue(1)
        val classz   = when(this.opcode) {
            OpCodes.New -> classObj.checkuserdata() as Class<*>
            else        -> this.findClassByAlias(classObj.tojstring())
        }

        val ctorArgs = args.subargs(2)
        val obj      = CoerceJavaToLua.coerce(classz)

        return obj.invokemethod(LuaConstructor, ctorArgs)
    }

    /**
     * Creates a new instance of a class given a class name.
     *
     * @param args
     *        The collection of arguments to use.
     * @return A new instance of a class (by name).
     */
    private fun doNewInstance(args: Varargs?): Varargs = this.doNew(args)

    /**
     * Searches this library's cache of class aliases to fully-qualified
     * names and loads the appropriate JVM-compatible class object.
     *
     * @param alias
     *        The alias to search for.
     * @return A class object.
     */
    private fun findClassByAlias(alias: String): Class<*> =
        when(alias in this.aliasToName) {
            false -> findClassByName(alias)
            true  -> findClassByName(this.aliasToName[alias]!!)
        }

    override fun invoke(args: Varargs?): Varargs {
        try {
            return when (this.opcode) {
                OpCodes.Init        -> this.doInit(args)
                OpCodes.BindClass   -> this.doBindClass(args)
                OpCodes.New         -> this.doNew(args)
                OpCodes.NewInstance -> this.doNewInstance(args)
                OpCodes.CreateProxy -> this.doCreateProxy(args)
                OpCodes.LoadLib     -> this.doLoadLib(args)
                else                -> throw LuaError("Function not " +
                                                      "supported: ${this}.")
            }
        }
        catch(le: LuaError) {
            throw le
        }
        catch(itEx: InvocationTargetException) {
            throw LuaError(itEx.targetException)
        }
        catch(ex: Exception) {
            throw LuaError(ex)
        }
    }
}