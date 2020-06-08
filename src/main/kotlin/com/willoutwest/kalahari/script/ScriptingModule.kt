package com.willoutwest.kalahari.script

import com.willoutwest.kalahari.script.libraries.MaterialLibrary
import com.willoutwest.kalahari.script.libraries.ActorLibrary
import com.willoutwest.kalahari.script.libraries.CameraLibrary
import com.willoutwest.kalahari.script.libraries.ColorLibrary
import com.willoutwest.kalahari.script.libraries.LightLibrary
import com.willoutwest.kalahari.script.libraries.MathLibrary
import com.willoutwest.kalahari.script.libraries.TextureLibrary
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.TwoArgFunction
import org.luaj.vm2.lib.jse.CoerceJavaToLua
import org.luaj.vm2.lib.jse.CoerceLuaToJava

/**
 * Converts the specified Lua object to a JVM accessible form with the
 * specified class type.
 *
 * @param value
 *        The Lua value to convert.
 * @param type
 *        The Java/Kotlin VM class type to convert to.
 * @return A Java/Kotlin compatible value.
 */
@Suppress("UNCHECKED_CAST")
fun <T> fromLua(value: LuaValue, type: Class<T>): T =
    CoerceLuaToJava.coerce(value, type) as T

/**
 * Converts the specified JVM object to a Lua accessible form.
 *
 * @param obj
 *        The object to convert.
 * @return A Lua VM compatible value.
 */
fun toLua(obj: Any): LuaValue = CoerceJavaToLua.coerce(obj)

/**
 * Represents an implementation of [TwoArgFunction] that adds a small
 * library of core functions as a module to a Lua scripting environment in
 * addition to a multitude of resource-specific factory functions as
 * sub-modules (via tables).
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
 *
 * Additional sub-modules for the following resources are also included:
 * <ul>
 *     <li>Actors - including various geometries - via [ActorLibrary].</li>
 *     <li>Colors via [ColorLibrary].</li>
 *     <li>Lights via [LightLibrary].</li>
 *     <li>Materials such as matte, phong, and reflection via [MaterialLibrary]
 *     .</li>
 *     <li>Mathematical functions and objects via [MathematicsLibrary].
 *     <li>Textures - including those based on noise - via [TextureLibrary]
 *     .</li>
 * </ul>
 */
class ScriptingModule : TwoArgFunction() {

    override fun call(modname: LuaValue, env: LuaValue): LuaValue {
        val khsl = LuaTable()

        khsl.set("importClass", ClassImporter())
        khsl.set("proxyOf",     ProxyCreator())

        khsl.set("actors", toLua(ActorLibrary()))
        khsl.set("cameras", toLua(CameraLibrary()))
        khsl.set("colors", toLua(ColorLibrary()))
        khsl.set("lights", toLua(LightLibrary()))
        khsl.set("materials", toLua(MaterialLibrary()))
        khsl.set("math", toLua(MathLibrary()))
        khsl.set("textures", toLua(TextureLibrary()))

        if(!env.get("package").isnil()) {
            env.get("package")
                .get("loaded")
                .set("khsl", khsl)
        }

        return khsl
    }
}