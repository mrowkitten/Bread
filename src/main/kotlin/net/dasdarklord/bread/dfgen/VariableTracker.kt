package net.dasdarklord.bread.dfgen

import net.dasdarklord.bread.dfk.item.DFVarType
import net.dasdarklord.bread.dfk.item.VarItem
import net.dasdarklord.bread.dfk.template.DFTemplate

object VariableTracker {

    private val saved = mutableMapOf<String, VarItem>()

    fun setSavedType(variable: VarItem, type: DFVarType) {
        saved[(variable.value as Map<*, *>)["name"] as String] = VarItem(type, -1)
    }

    fun setSavedType(variable: VarItem, item: VarItem) {
        saved[(variable.value as Map<*, *>)["name"] as String] = item
    }

    fun setSavedType(name: String, type: DFVarType) {
        saved[name] = VarItem(type, -1)
    }

    fun setSavedType(name: String, item: VarItem) {
        saved[name] = item
    }

    fun getSavedItem(item: VarItem): VarItem {
        if (item.value !is Map<*, *>) return item
        val name = item.value["name"] as? String ?: return item
        if (!saved.containsKey(name)) return item
        return saved[name]!!
    }

    fun getSavedItem(name: String): VarItem {
        if (!saved.containsKey(name)) return VarItem.num(0)
        return saved[name]!!
    }

    fun getSavedType(item: VarItem): DFVarType {
        if (item.value !is Map<*, *>) return item.type
        val name = item.value["name"] as? String ?: return item.type
        if (!saved.containsKey(name)) return item.type
        return getSavedType(name)
    }

    private fun getSavedType(name: String, level: Int): DFVarType {
        val item = saved[name] ?: return DFVarType.ANY
        if (level > 10) return DFVarType.ANY

        val type = saved[name]!!.type
        println(name)
        println(saved[name])
        println(type)
        if (type == DFVarType.VARIABLE) {
            return getSavedType((item.value as Map<*, *>)["name"] as String, level + 1)
        }
        if (type == DFVarType.GAME_VALUE) {
            val search = (item.value as Map<*, *>)["type"] as String
            val gameValues = DFTemplate.actionDump["gameValues"].asJsonArray
            val matches = gameValues.map { it.asJsonObject }.filter {
                val aliases = it["aliases"].asJsonArray
                val icon = it["icon"].asJsonObject
                val gvalName = icon["name"].asString

                gvalName == search || aliases.any { alias -> alias.asString == search }
            }

            if (matches.isEmpty()) return DFVarType.ANY
            val gval = matches[0]
            println(gval)
            return DFVarType.fromId(gval["icon"].asJsonObject["returnType"].asString)
        }

        return type
    }

    fun getSavedType(name: String): DFVarType {
        return getSavedType(name, 0)
    }

}