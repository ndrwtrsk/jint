package nd.rw.jint.lexer;

import nd.rw.jint.token.Token;

abstract class TokenExtractor {

    final Token extract(CharacterIterator characterIterator,
                        Integer startingIndexOfIdentifier,
                        Character iteratedCharacterFromOutSide) {

        Character iteratedCharacter = iteratedCharacterFromOutSide;
        int currentIndex = startingIndexOfIdentifier;

        while (isApplicable(iteratedCharacter)) {
            CurrentCharacterAndPosition iteratedCharacterAndPosition = characterIterator.readNextCharacter();
            iteratedCharacter = iteratedCharacterAndPosition.getCurrentCharacter();
            currentIndex = iteratedCharacterAndPosition.getCurrentInputPosition();
        }

        String identifier = characterIterator.extractLexicalInputSubstring(startingIndexOfIdentifier, currentIndex);

        return result(identifier);
    }

    abstract boolean isApplicable(Character character);

    protected abstract Token result(String literal);

}
