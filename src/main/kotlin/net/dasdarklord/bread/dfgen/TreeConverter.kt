package net.dasdarklord.bread.dfgen

import net.dasdarklord.bread.dfk.template.DFTemplate
import net.dasdarklord.bread.parser.TreeNode

class TreeConverter {
    companion object {
        fun convertTree(tree: TreeNode, template: DFTemplate, objects: MutableMap<String, DFLObject> = mutableMapOf()): Any? {
            for (entry in astConverters) {
                if (entry.key == tree.type) return entry.value.convert(tree, template, objects)
            }

            return tree
        }
    }
}