package nd.rw.jint.lexer;

import lombok.NonNull;
import nd.rw.jint.token.Token;
import nd.rw.jint.token.TokenType;

import java.util.Optional;

import static nd.rw.jint.token.TokenType.EOF;
import static nd.rw.jint.token.TokenType.EQ;
import static nd.rw.jint.token.TokenType.ILLEGAL;
import static nd.rw.jint.token.TokenType.NOT_EQ;

class Lexer implements CharacterIterator {

    private final String input;
    private int currentInputPosition;
    private int currentReadingPosition;
    private char currentChar;

    private final IdentifierExtractor identifierExtractor = new IdentifierExtractor();
    private final NumbersExtractor numbersExtractor = new NumbersExtractor();

    Lexer(@NonNull String input) {
        this.input = input;
        readNextCharacter();
    }

    Token nextToken() {
        eatWhitespace();
        if (TokenType.isEOF(currentChar)) {
            return Token.of(EOF, EOF.getValue());
        } else if (TokenType.isValidSimpleTokenType(currentChar)) {
            Optional<Token> possibleEqNotEqTokenType = checkForPossibleEqNotEqToken();
            if (possibleEqNotEqTokenType.isPresent()) {
                readNextCharacter();
                return possibleEqNotEqTokenType.get();
            } else {
                TokenType type = TokenType.findTokenType(currentChar);
                String literal = Character.toString(currentChar);
                readNextCharacter();
                return Token.of(type, literal);
            }
        } else {
            return identifyToken();
        }
    }

    private void eatWhitespace() {
        while (Character.isWhitespace(currentChar)) {
            readNextCharacter();
        }
    }

    // TODO: 2018-10-11 create extractor for double char operators?
    private Optional<Token> checkForPossibleEqNotEqToken() {
        char peekedCharacter = peekNextCharacter();
        if (currentChar == '=') {
            if (peekedCharacter == '=') {
                readNextCharacter();
                return Optional.of(Token.of(EQ, "=="));
            }
        } else if (currentChar == '!') {
            if (peekedCharacter == '=') {
                readNextCharacter();
                return Optional.of(Token.of(NOT_EQ, "!="));
            }
        }
        return Optional.empty();
    }

    private Token identifyToken() {
        if (identifierExtractor.isApplicable(currentChar)) {
            return identifierExtractor.extract(this, currentInputPosition, currentChar);
        } else if (numbersExtractor.isApplicable(currentChar)) {
            return numbersExtractor.extract(this, currentInputPosition, currentChar);
        }
        return Token.of(ILLEGAL, ILLEGAL.getValue());
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

    private Character peekNextCharacter() {
        if (currentReadingPosition >= input.length())
            return 0;
        else
            return input.charAt(currentReadingPosition);
    }

    @Override
    public String extractLexicalInputSubstring(int beginIndexInclusive, int endIndexExclusive) {
        return input.substring(beginIndexInclusive, endIndexExclusive);
    }

}
