package nd.rw.jint.lexer;

import nd.rw.jint.token.Token;
import nd.rw.jint.token.TokenType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class IdentifierExtractorTest {

    @Test
    public void testExtractingSimpleIdentifier() {
        //  given
        int startingIndex = 0;
        CharacterIterator iterator = iterator("five "); // MIND THE SPACE!!!!!
        IdentifierExtractor extractor = new IdentifierExtractor();

        //  when
        Token token = extractor.extract(iterator, startingIndex, 'f');

        //  then
        assertThat(token.getTokenType()).isEqualTo(TokenType.IDENT);
        assertThat(token.getLiteral()).isEqualTo("five");
    }

    @Test
    public void testExctractingLet() {
        //  given
        int startingIndex = 0;
        CharacterIterator iterator = iterator("let "); // MIND THE SPACE!!!!!
        IdentifierExtractor extractor = new IdentifierExtractor();

        //  when
        Token token = extractor.extract(iterator, startingIndex, 'l');

        //  then
        assertThat(token.getTokenType()).isEqualTo(TokenType.LET);
        assertThat(token.getLiteral()).isEqualTo("let");
    }

    private CharacterIterator iterator(final String lexicalInput) {
        return new CharacterIterator() {

            private String input = lexicalInput;
            private int currentInput = 0;

            @Override
            public CurrentCharacterAndPosition readNextCharacter() {
                final CurrentCharacterAndPosition characterAndPosition = CurrentCharacterAndPosition.of(input.charAt(currentInput), currentInput);
                currentInput++;
                return characterAndPosition;
            }

            @Override
            public String extractLexicalInputSubstring(int beginIndexInclusive, int endIndexExclusive) {
                return input.substring(beginIndexInclusive, endIndexExclusive);
            }
        };
    }


}