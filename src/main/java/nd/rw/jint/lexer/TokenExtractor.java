package nd.rw.jint.lexer;

import nd.rw.jint.token.Token;

abstract class TokenExtractor {

    abstract Token extract(CharacterIterator characterIterator,
                           Integer startingIndexOfIdentifier,
                           Character iteratedCharacter);

    abstract boolean isApplicable(Character character);

}
