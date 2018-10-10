package nd.rw.jint.lexer;

import lombok.NonNull;
import nd.rw.jint.token.Token;

import static nd.rw.jint.token.TokenType.ASSIGN;
import static nd.rw.jint.token.TokenType.COMMA;
import static nd.rw.jint.token.TokenType.EOF;
import static nd.rw.jint.token.TokenType.ILLEGAL;
import static nd.rw.jint.token.TokenType.INT;
import static nd.rw.jint.token.TokenType.LBRACE;
import static nd.rw.jint.token.TokenType.LPAREN;
import static nd.rw.jint.token.TokenType.PLUS;
import static nd.rw.jint.token.TokenType.RBRACE;
import static nd.rw.jint.token.TokenType.RPAREN;
import static nd.rw.jint.token.TokenType.SEMICOLON;

class Lexer implements CharacterIterator{

    private final String input;
    private int currentInputPosition;
    private int currentReadingPosition;
    private char currentChar;

    private final IdentifierExtractor identifierExtractor = new IdentifierExtractor();

    Lexer(@NonNull String input) {
        this.input = input;
        readNextCharacter();
    }

    Token nextToken() {
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
                return identifyToken(); // trick place here... ignores next readNextCharacter() below and
                                        // while it implicitly readsNext in subsequent calls to identify token
            }
        }
        readNextCharacter();
        return token;
    }

    private void eatWhitespace() {
        while (Character.isWhitespace(currentChar)) {
            readNextCharacter();
        }
    }

    private Token identifyToken() {
        if (identifierExtractor.isApplicable(currentChar)){
            return identifierExtractor.extract(this, currentInputPosition, input.charAt(currentInputPosition));
        } else if (isDigit(currentChar)){
            return readIntToken();
        }
        return new Token(ILLEGAL, "");
    }

    private boolean isDigit(char currentChar) {
        return Character.isDigit(currentChar);
    }

    private Token readIntToken() {
        int identifierStart = currentInputPosition;
        while (isDigit(currentChar)) {
            readNextCharacter();
        }
        String identifier = input.substring(identifierStart, currentInputPosition);
        return new Token(INT, identifier);
    }

    @Override
    public CurrentCharacterAndPosition readNextCharacter() {
        if (currentReadingPosition >= input.length()) {
            currentChar = 0;
        } else {
            currentChar = input.charAt(currentReadingPosition);
        }
        currentInputPosition = currentReadingPosition;
        currentReadingPosition++;
        return CurrentCharacterAndPosition.of(currentChar, currentInputPosition);
    }

    @Override
    public String extractLexicalInputSubstring(int beginIndexInclusive, int endIndexExclusive) {
        return input.substring(beginIndexInclusive, endIndexExclusive);
    }

}
