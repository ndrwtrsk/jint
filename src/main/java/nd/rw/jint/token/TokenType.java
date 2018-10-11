package nd.rw.jint.token;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

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

    static {
        keywords.put("let", LET);
        keywords.put("fn", FUNCTION);
    }

    public static TokenType lookupTokenType(String possibleToken) {
        return keywords.getOrDefault(possibleToken, IDENT);
    }

    public static boolean isEOF(char c) {
        return c == 0;
    }

    public static boolean isValidSimpleTokenType(final char c) {
        if (c == 0) {
            return true;
        }
        return Stream.of(TokenType.values())
                .anyMatch(validityPredicate(c));
    }

    public static TokenType findTokenType(final char c) {
        if (c == 0) {
            return EOF;
        }
        return Stream.of(TokenType.values())
                .filter(validityPredicate(c))
                .findFirst()
                .orElseThrow();
    }

    private static Predicate<TokenType> validityPredicate(char c) {
        return tokenType -> tokenType.value.length() <= 1 && tokenType.value.charAt(0) == c;
    }

}
