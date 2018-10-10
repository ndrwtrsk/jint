package nd.rw.jint.token;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

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

    private static Map<String, TokenType> keywords = Maps.newHashMap();

    static{
        keywords.put("let", LET);
        keywords.put("fn", FUNCTION);
    }

    public static TokenType lookupTokenType(String possibleToken) {
        return keywords.getOrDefault(possibleToken, IDENT);
    }

}
