package nd.rw.jint.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {


    ILLEGAL("ILLEGAL"),
    EOF("EOF"),

    //  identifiers + literals
    IDENT("IDENT"),
    INT("INT"),

    //  operators
    ASSIGN("="),
    PLUS("+"),

    //  delimiters
    COMMA(","),
    SEMICOLON(";"),
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),

    //  keywords
    FUNCTION("fn"),
    LET("let");



    private final String value;

}
