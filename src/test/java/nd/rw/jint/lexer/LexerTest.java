package nd.rw.jint.lexer;

import nd.rw.jint.token.Token;
import nd.rw.jint.token.TokenType;
import org.assertj.core.util.Lists;
import org.junit.Test;

import static nd.rw.jint.token.TokenType.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

public class LexerTest {

    @Test
    public void testNextTokenBasicInput() {
        //  given
        var input = "+=(){};,";
        var lexer = new Lexer(input);

        List<Token> tokens = Lists.newArrayList();

        //  when
        for (int i = 0; i < input.length() + 1; i++) {
            tokens.add(lexer.nextToken());
        }

        //  then
        assertThat(tokens)
                .isNotEmpty()
                .containsExactly(
                        token(PLUS, "+"),
                        token(ASSIGN, "="),
                        token(LPAREN, "("),
                        token(RPAREN, ")"),
                        token(LBRACE, "{"),
                        token(RBRACE, "}"),
                        token(SEMICOLON, ";"),
                        token(COMMA, ","),
                        token(EOF, "")
                );
    }

    @Test
    public void testNextTokenBasicExpressions() {
        //  given
        var input = "let five = 5;let ten = 10;let add = fn(x,y){x+y};let result = add(five, ten);";
        var lexer = new Lexer(input);

        List<Token> tokens = Lists.newArrayList();

        //  when
        for (int i = 0; i < input.length() + 1; i++) {
            Token token = lexer.nextToken();
            tokens.add(token);
            if (token.getTokenType() == EOF) {
                break;
            }
        }

        //  then
        assertThat(tokens)
                .isNotEmpty()
                .containsExactly(
                        token(LET, "let"),
                        token(IDENT, "five"),
                        token(ASSIGN, "="),
                        token(INT, "5"),
                        token(SEMICOLON, ";"),
                        token(LET, "let"),
                        token(IDENT, "ten"),
                        token(ASSIGN, "="),
                        token(INT, "10"),
                        token(SEMICOLON, ";"),
                        token(LET, "let"),
                        token(IDENT, "add"),
                        token(ASSIGN, "="),
                        token(FUNCTION, "fn"),
                        token(LPAREN, "("),
                        token(IDENT, "x"),
                        token(COMMA, ","),
                        token(IDENT, "y"),
                        token(RPAREN, ")"),
                        token(LBRACE, "{"),
                        token(IDENT, "x"),
                        token(PLUS, "+"),
                        token(IDENT, "y"),
                        token(RBRACE, "}"),
                        token(SEMICOLON, ";"),
                        token(LET, "let"),
                        token(IDENT, "result"),
                        token(ASSIGN, "="),
                        token(IDENT, "add"),
                        token(LPAREN, "("),
                        token(IDENT, "five"),
                        token(COMMA, ","),
                        token(IDENT, "ten"),
                        token(RPAREN, ")"),
                        token(SEMICOLON, ";"),
                        token(EOF, "")
                );
    }

    private Token token(TokenType tokenType, String literal) {
        return new Token(tokenType, literal);
    }

}
