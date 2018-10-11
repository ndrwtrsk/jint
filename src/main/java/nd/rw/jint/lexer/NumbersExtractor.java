package nd.rw.jint.lexer;

import nd.rw.jint.token.Token;

import static nd.rw.jint.token.TokenType.INT;

class NumbersExtractor extends TokenExtractor {

    @Override
    boolean isApplicable(Character character) {
        return Character.isDigit(character);
    }

    @Override
    protected Token result(String literal) {
        return Token.of(INT, literal);
    }
}
