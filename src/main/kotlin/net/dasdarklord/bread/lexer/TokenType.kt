package net.dasdarklord.bread.lexer

enum class TokenType(val id: String, val word: String? = null, val regex: Boolean = false) {
    // Types
    NUMBER("number"),
    STRING("string"),
    STYLED_TEXT("styled"),
    WORD("word"),
    TRUE("true", "true"),
    FALSE("false", "false"),

    // Keywords
    IF("if", "if"),
    ELSE("else", "else"),
    REPEAT("repeat", "repeat"),
    EVENT("event", "event"),
    FUNCTION("func", "func"),
    PROCESS("proc", "proc"),
    GAME("game", "game"),
    LOCAL("local", "local"),
    SAVED("save", "save"),

    // Operators
    ASSIGNMENT("assign", "="),
    EQUALS("eq", "=="),
    NOT_EQUALS("neq", "!="),
    GREATER_EQUALS("geq", ">="),
    LESS_EQUALS("leq", "<="),
    GREATER("gt", ">"),
    LESS("lt", "<"),
    MATCHES("match", "matches"),
    IN("in", "in"),
    IS("is", "is"),
    REGEX("rex", "regex"),
    INCREMENT_TOKEN("inc", "++"),
    DECREMENT_TOKEN("dec", "--"),
    BANG("bang", "!"),
    ADD("add", "+"),
    SUB("sub", "-"),
    MUL("mul", "*"),
    DIV("div", "/"),
    POW("pow", "^"),
    MOD("mod", "mod"),
    REMAINDER("rem", "rem"),

    // Misc
    OPEN_PAREN("oparen", "("),
    CLOSE_PAREN("cparen", ")"),
    OPEN_CURLY("ocurl", "{"),
    CLOSE_CURLY("ccurl", "}"),
    OPEN_BRACKET("obrack", "["),
    CLOSE_BRACKET("cbrack", "]"),
    ACCESSOR("acc", "."),
    COMMA("comma", ","),
    COLON("colon", ":"),
    START("start_proc", "start"),
    RETURN("ret", "return"),
    STOP("stop", "stop"),
    IMPORT("import", "import"),
    TEMPLATE("template", "template"),
    ;

}