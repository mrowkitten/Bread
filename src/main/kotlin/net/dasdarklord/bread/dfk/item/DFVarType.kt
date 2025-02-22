package net.dasdarklord.bread.dfk.item

enum class DFVarType(i: String, adi: String) {
    STRING("txt", "TEXT"),
    STYLED_TEXT("comp", "COMPONENT"),
    NUMBER("num", "NUMBER"),
    VARIABLE("var", "VARIABLE"),
    LOCATION("loc", "LOCATION"),
    ITEM("item", "ITEM"),
    ANY("any", "ANY"),
    GAME_VALUE("g_val", ""),
    VECTOR("vec", "VECTOR"),
    POTION("pot", "POTION"),
    SOUND("snd", "SOUND"),
    PART("part", "PARTICLE"),
    PARAMETER("pn_el", ""),
    LIST("list", "LIST"),
    DICT("dict", "DICT");

    val id: String = i
    val actionDumpId: String = adi

    companion object {
        fun fromId(id: String): DFVarType {
            val altNames = mapOf(
                "str" to "txt",
                "string" to "txt",
                "styled" to "comp",
                "sound" to "snd",
                "particle" to "part",
                "potion" to "pot",
                "vector" to "vec",
                "number" to "num",
                "variable" to "var",
                "dictionary" to "dict",
            )
            val newId = altNames[id] ?: id

            for (t in entries) {
                if (t.id == newId) return t
                if (t.actionDumpId == id.uppercase()) return t
            }
            return ANY
        }
    }

}