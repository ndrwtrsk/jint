package nd.rw.jint.ast;

import nd.rw.jint.token.Token;
import nd.rw.jint.token.TokenType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StatementToStringTest {

    @Test
    public void testLetStatementToString() {
        //  given
        var token = Token.of(TokenType.LET, "let");
        var identifier = Identifier.of(Token.of(TokenType.IDENT, "x"), "x");
        var value = Identifier.of(Token.of(TokenType.IDENT, "y"), "y");
        var letStatement = LetStatement.of(token, identifier, value);

        //  when
        String toString = letStatement.toString();

        //  then
        assertThat(toString).isEqualTo("let x = y;");
    }

    @Test
    public void testReturnStatementToString() {
        //  given
        var token = Token.of(TokenType.RETURN, "return");
        var identifier = Identifier.of(Token.of(TokenType.IDENT, "x"), "x");
        var letStatement = ReturnStatement.of(token, identifier);

        //  when
        String toString = letStatement.toString();

        //  then
        assertThat(toString).isEqualTo("return x;");
    }

}
