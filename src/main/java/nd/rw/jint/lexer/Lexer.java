package nd.rw.jint.lexer;

import com.google.common.collect.Maps;
import lombok.NonNull;
import nd.rw.jint.token.Token;
import nd.rw.jint.token.TokenType;

import java.util.Map;

import static nd.rw.jint.token.TokenType.*;

class Lexer {

    private final String input;
    private int currentInputPosition;
    private int currentReadingPosition;
    private char currentChar;

    private final Map<String, TokenType> keywords = Maps.newHashMap();

    public Lexer(@NonNull String input) {
        this.input = input;
        readNextCharacter();
        initializeKeywordMap();
    }

    private void initializeKeywordMap(){
        keywords.put("let", LET);
        keywords.put("fn", FUNCTION);
    }

    public Token nextToken() {
        Token token;
        eatWhitespace();
        switch (currentChar) {
            case '+':
                token = new Token(PLUS, "+");
                break;
            case '=':
                token = new Token(ASSIGN, "=");
                break;
            case '(':
                token = new Token(LPAREN, "(");
                break;
            case ')':
                token = new Token(RPAREN, ")");
                break;
            case '{':
                token = new Token(LBRACE, "{");
                break;
            case '}':
                token = new Token(RBRACE, "}");
                break;
            case ';':
                token = new Token(SEMICOLON, ";");
                break;
            case ',':
                token = new Token(COMMA, ",");
                break;
            case 0:
                token = new Token(EOF, "");
                break;
            default: {
                token = isCharacterIdentifier(currentChar) ? readIdentifier() : new Token(ILLEGAL, "");
                break;
            }
        }
        readNextCharacter();
        return token;
    }

    private void eatWhitespace() {
        while(Character.isWhitespace(currentChar)){
            readNextCharacter();
        }
    }

    private boolean isCharacterIdentifier(char currentChar) {
        return Character.isLetter(currentChar) || currentChar == '_';
    }

    private Token readIdentifier() {
        int identifierStart = currentInputPosition;
        while(isCharacterIdentifier(currentChar)) {
            readNextCharacter();
        }
        String identifier = input.substring(identifierStart, currentInputPosition);
        TokenType tokenType = lookupTokenType(identifier);
        return new Token(tokenType, identifier);
    }

    private TokenType lookupTokenType(String identifier) {
        return keywords.getOrDefault(identifier, IDENT);
    }

    private void readNextCharacter() {
        if (currentReadingPosition >= input.length()) {
            currentChar = 0;
        } else {
            currentChar = input.charAt(currentReadingPosition);
        }
        currentInputPosition = currentReadingPosition;
        currentReadingPosition++;
    }

}
