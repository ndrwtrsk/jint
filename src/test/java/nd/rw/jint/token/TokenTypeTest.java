package nd.rw.jint.token;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class TokenTypeTest {

    @Test
    public void testTrueForPlus() {
        //  given
        char plus = '+';
        //  when
        boolean isValid = TokenType.isValidSimpleTokenType(plus);
        //  then
        assertThat(isValid).isTrue();
    }

    @Test
    public void testFalseForL() {
        //  given
        char plus = 'L';
        //  when
        boolean isValid = TokenType.isValidSimpleTokenType(plus);
        //  then
        assertThat(isValid).isFalse();
    }

    @Test
    public void testReturningCorrectTokenTypeForPlus() {
        //  given
        char plus = '+';
        //  when
        final TokenType tokenTypeForValue = TokenType.findTokenType(plus);
        //  then
        assertThat(tokenTypeForValue).isEqualTo(TokenType.PLUS);
    }

}