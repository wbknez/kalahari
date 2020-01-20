package com.willoutwest.kalahari.script

import io.kotlintest.IsolationMode
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.ShouldSpec
import org.luaj.vm2.LuaString
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaUserdata
import org.luaj.vm2.lib.jse.JsePlatform

/**
 * Test suite for [ProxyCreator].
 */
class ProxyCreatorTest : ShouldSpec() {

    interface TestInterfaceToFind {

        fun compute(value: Int): Int
    }

    companion object {

        val InterfaceName = "com.willoutwest.kalahari.script" +
                            ".ProxyCreatorTest\$TestInterfaceToFind"
    }

    override fun isolationMode(): IsolationMode? =
        IsolationMode.InstancePerTest

    private val creator = ProxyCreator()

    private val vm      = JsePlatform.standardGlobals()

    init {

        "Creating a proxy with a valid interface name" {
            val name   = LuaString.valueOf(InterfaceName)
            val source = """
                         function compute_it(value)
                             return value * 2
                         end
                                            
                         return {compute=compute_it}
                         """.trimIndent()
            val impl   = vm.load(source).call() as LuaTable

            val result = creator.invoke(arrayOf(name, impl))
            val proxy  = result.arg(1) as LuaUserdata
            val obj    = proxy.userdata()

            should("produce only one object.") {
                result.narg() shouldBe 1
            }

            should("produce a valid object with correct implementation") {
                (obj is TestInterfaceToFind).shouldBeTrue()
            }

            should("be callable.") {
                (obj as TestInterfaceToFind).compute(40) shouldBe 80
            }
        }

        "Creating a proxy with an invalid interface name" {
            val name   = LuaString.valueOf(InterfaceName + "2")
            val source = """
                         function compute_it(value)
                             return value * 2
                         end
                                            
                         return {compute=compute_it}
                         """.trimIndent()
            val impl   = vm.load(source).call() as LuaTable

            should("throw an exception.") {
                shouldThrow<NoSuchImportException> {
                    creator.invoke(arrayOf(name, impl))
                }
            }
        }

        "Creating a proxy without a function body" {
            val name   = LuaString.valueOf(InterfaceName)
            val source = """
                         return {compute=nil}
                         """.trimIndent()
            val impl   = vm.load(source).call() as LuaTable

            should("throw an exception.") {
                val result = creator.invoke(arrayOf(name, impl))
                val proxy  = result.arg(1) as LuaUserdata
                val obj    = proxy.userdata() as TestInterfaceToFind

                shouldThrow<NoSuchImplementationException> {
                    obj.compute(40)
                }
            }
        }
    }
}