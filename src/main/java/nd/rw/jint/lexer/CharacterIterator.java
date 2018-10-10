package nd.rw.jint.lexer;

interface CharacterIterator {

    CurrentCharacterAndPosition readNextCharacter();

    String extractLexicalInputSubstring(int beginIndexInclusive, int endIndexExclusive);

}
