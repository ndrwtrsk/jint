package nd.rw.jint.lexer;

import nd.rw.jint.token.Token;
import nd.rw.jint.token.TokenType;

class IdentifierExtractor extends TokenExtractor{

    @Override
    Token extract(CharacterIterator characterIterator,
                  Integer startingIndexOfIdentifier,
                  Character iteratedCharacterFromOutSide) {

        Character iteratedCharacter = iteratedCharacterFromOutSide;
        int currentIndex = startingIndexOfIdentifier;

        while (isIteratedCharacterValidForIdentifier(iteratedCharacter)) {
            CurrentCharacterAndPosition iteratedCharacterAndPosition = characterIterator.readNextCharacter();
            iteratedCharacter = iteratedCharacterAndPosition.getCurrentCharacter();
            currentIndex = iteratedCharacterAndPosition.getCurrentInputPosition();
        }

        String identifier = characterIterator.extractLexicalInputSubstring(startingIndexOfIdentifier, currentIndex);

        return new Token(TokenType.lookupTokenType(identifier), identifier);
    }

    @Override
    boolean isApplicable(Character character) {
        return isIteratedCharacterValidForIdentifier(character);
    }

    private boolean isIteratedCharacterValidForIdentifier(Character character) {
        return Character.isLetter(character) || character == '_';
    }

}
