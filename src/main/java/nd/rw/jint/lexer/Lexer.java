package nd.rw.jint.lexer;

import lombok.NonNull;
import nd.rw.jint.token.Token;
import nd.rw.jint.token.TokenType;

import static nd.rw.jint.token.TokenType.ILLEGAL;

class Lexer implements CharacterIterator {

    private final String input;
    private int currentInputPosition;
    private int currentReadingPosition;
    private char currentChar;

    private final IdentifierExtractor identifierExtractor = new IdentifierExtractor();
    private final DigitsExtractor digitsExtractor = new DigitsExtractor();

    Lexer(@NonNull String input) {
        this.input = input;
        readNextCharacter();
    }

    Token nextToken() {
        eatWhitespace();
        if (TokenType.isValidSimpleTokenType(currentChar)) {
            TokenType type = TokenType.findTokenType(currentChar);
            String literal = currentChar != 0 ? Character.toString(currentChar) : "EOF"; // todo dirty!!!
            readNextCharacter();
            return Token.of(type, literal);
        } else {
            return identifyToken(); // tricky place here... ignores next readNextCharacter() below and
            // while it implicitly readsNext in subsequent calls to identify token
        }
    }

    private void eatWhitespace() {
        while (Character.isWhitespace(currentChar)) {
            readNextCharacter();
        }
    }

    private Token identifyToken() {
        if (identifierExtractor.isApplicable(currentChar)) {
            return identifierExtractor.extract(this, currentInputPosition, currentChar);
        } else if (digitsExtractor.isApplicable(currentChar)) {
            return digitsExtractor.extract(this, currentInputPosition, currentChar);
        }
        return Token.of(ILLEGAL, "");
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
