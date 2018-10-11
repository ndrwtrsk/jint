package nd.rw.jint.lexer;

import nd.rw.jint.token.TokenType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NumbersExtractorTest {

    @Test
    public void testExtractingNumber() {
        //  given
        var iterator = iterator("256 ");
        var numberExtractor = new NumbersExtractor();

        //  when
        var extracted = numberExtractor.extract(iterator, 0, '2');

        //  then
        assertThat(extracted.getTokenType())
                .isEqualTo(TokenType.INT);

        assertThat(extracted.getLiteral())
                .isEqualTo("256");
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