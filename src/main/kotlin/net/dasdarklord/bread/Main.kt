package net.dasdarklord.bread

import net.dasdarklord.bread.dfgen.convertAstToDF
import net.dasdarklord.bread.dfk.item.DFVarType
import net.dasdarklord.bread.dfk.template.CodeClient
import net.dasdarklord.bread.dfk.template.DFTemplate
import net.dasdarklord.bread.lexer.Lexer
import net.dasdarklord.bread.lexer.Token
import net.dasdarklord.bread.parser.Parser
import net.dasdarklord.bread.parser.preprocessImports
import net.dasdarklord.bread.parser.preprocessTemplates
import java.io.File
import java.net.ConnectException

val pairs = mutableListOf<Pair<String, MutableMap<String, DFVarType>>>()

fun fullLex(str: String, disallowedImports: MutableList<String> = mutableListOf()): Pair<MutableList<Token>, List<DFTemplate>> {
    val lexer = Lexer(str)

    val tokens = lexer.transform()
    println(tokens)

    val templates = mutableListOf<DFTemplate>()

    val importProcessed = preprocessImports(tokens, disallowedImports)
    templates.addAll(importProcessed.second)

    val templateProcessed = preprocessTemplates(importProcessed.first)
    println(templateProcessed)

    val templateProcessedTokens = templateProcessed.first
    templates.addAll(templateProcessed.second)
    println(templateProcessedTokens)

    return Pair(templateProcessedTokens, templates)
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        error("Needs atleast one argument for file")
    }
    println("you MIGHT see a little debug output")

    val text = File(args[0]).readLines().joinToString("\n", "", "")
    val pair = fullLex(text, mutableListOf(File(args[0]).nameWithoutExtension))
    val tokens = pair.first
    val impTemplates = pair.second

    val parser = Parser(tokens)
    pairs.clear()
    pairs.addAll(parser.getFunctions())
    println("Read $pairs")

    val events = parser.parseAll()

    for (event in events) println(event)

    val templates = convertAstToDF(events).toMutableList()
    templates.addAll(impTemplates)

    for (template in templates) {
        println(template.getJson())
    }

    try {
        CodeClient.buildTemplate(templates)
    } catch (exc: ConnectException) {
        exc.printStackTrace()
    }
}