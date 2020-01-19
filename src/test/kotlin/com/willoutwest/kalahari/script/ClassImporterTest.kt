package com.willoutwest.kalahari.script

import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.ShouldSpec
import org.luaj.vm2.LuaString
import org.luaj.vm2.lib.jse.CoerceJavaToLua

/**
 * Test suite for [ClassImporter].
 */
class ClassImporterTest : ShouldSpec() {

    class TestClassToFind

    private val importer = ClassImporter()

    init {

        "Importing a class that is present on the classpath" {
            val cls     = TestClassToFind::class.java
            val clsName = LuaString.valueOf(cls.name)

            should("find the class and return it correctly.") {
                importer.call(clsName) shouldBe CoerceJavaToLua.coerce(cls)
            }
        }

        "Importing a class that is not present on the classpath" {
            should("throw an exception.") {
                shouldThrow<NoSuchImportException> {
                    importer.call(LuaString.valueOf("javax.JFrame2"))
                }
            }
        }
    }
}