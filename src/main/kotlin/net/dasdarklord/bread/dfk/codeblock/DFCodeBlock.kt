package net.dasdarklord.bread.dfk.codeblock

import net.dasdarklord.bread.dfk.item.VarItem

class DFCodeBlock(var type: DFCodeType, var action: String = "", var target: String = "", var inverter: String = "") {
    val tags: MutableMap<String, String> = mutableMapOf()
    val contents: MutableMap<Int, VarItem> = mutableMapOf()
    var bracketOpening: Boolean = false
    var bracketRepeating: Boolean = false

    constructor(dfCodeBlock: DFCodeBlock) : this(dfCodeBlock.type, dfCodeBlock.action, dfCodeBlock.target, dfCodeBlock.inverter) {
        tags.putAll(dfCodeBlock.tags)
        contents.putAll(dfCodeBlock.contents)
        bracketRepeating = dfCodeBlock.bracketOpening
        bracketRepeating = dfCodeBlock.bracketRepeating
    }

    fun setTag(name: String, value: String): DFCodeBlock {
        tags[name] = value
        return this
    }

    fun setContent(vararg items: VarItem): DFCodeBlock {
        return setContent(0, *items)
    }

    fun setContent(slot: Int, vararg items: VarItem): DFCodeBlock {
        for ((index, item) in items.withIndex()) {
            contents[slot + index] = item
        }
        return this
    }

    fun setBracket(opening: Boolean, repeating: Boolean = false): DFCodeBlock {
        if (type != DFCodeType.BRACKET) throw IllegalStateException("Expected type 'bracket' but got '${type.jsonName}'")
        bracketOpening = opening
        bracketRepeating = repeating
        return this
    }

    companion object {
        fun bracket(opening: Boolean, repeating: Boolean = false): DFCodeBlock {
            return DFCodeBlock(DFCodeType.BRACKET).setBracket(opening, repeating)
        }
    }

}