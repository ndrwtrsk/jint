package nd.rw.jint.lexer;

import nd.rw.jint.token.Token;
import nd.rw.jint.token.TokenType;

class IdentifierExtractor extends TokenExtractor{

    @Override
    boolean isApplicable(Character character) {
        return Character.isLetter(character) || character == '_';
    }

    @Override
    protected Token result(String literal) {
        return Token.of(TokenType.lookupTokenType(literal), literal);
    }

}
