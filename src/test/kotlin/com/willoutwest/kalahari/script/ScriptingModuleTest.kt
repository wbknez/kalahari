package com.willoutwest.kalahari.script

import io.kotlintest.IsolationMode
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldBe
import io.kotlintest.specs.ShouldSpec
import org.luaj.vm2.LuaString
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue

/**
 * Test suite for [ScriptingModule].
 */
class ScriptingModuleTest : ShouldSpec() {

    override fun isolationMode(): IsolationMode? =
        IsolationMode.InstancePerTest

    private val library = ScriptingModule()

    init {

        "Loading a scripting library into a Lua table" {
            val modName  = LuaString.valueOf("khsl")
            val env      = LuaTable()
            val packages = LuaTable()

            packages.set("loaded", LuaTable())
            env.set("package", packages)

            val lib = library.call(modName, env)

            should("not place any functions in the table.") {
                env.get("importClass") shouldBe LuaValue.NIL
                env.get("proxyOf") shouldBe LuaValue.NIL
            }

            should("add all functions to the module itself.") {
                lib.get("importClass").shouldBeInstanceOf<ClassImporter>()
                lib.get("proxyOf").shouldBeInstanceOf<ProxyCreator>()
            }

            should("register itself as loaded if appropriate.") {
                val loaded = env.get("package").get("loaded")

                loaded.get(modName) shouldBe lib
            }
        }
    }
}