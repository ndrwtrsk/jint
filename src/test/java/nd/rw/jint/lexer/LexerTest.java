package nd.rw.jint.lexer;

import nd.rw.jint.token.Token;
import nd.rw.jint.token.TokenType;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

import static nd.rw.jint.token.TokenType.ASSIGN;
import static nd.rw.jint.token.TokenType.ASTERISK;
import static nd.rw.jint.token.TokenType.BANG;
import static nd.rw.jint.token.TokenType.COMMA;
import static nd.rw.jint.token.TokenType.EOF;
import static nd.rw.jint.token.TokenType.FUNCTION;
import static nd.rw.jint.token.TokenType.GT;
import static nd.rw.jint.token.TokenType.IDENT;
import static nd.rw.jint.token.TokenType.INT;
import static nd.rw.jint.token.TokenType.LBRACE;
import static nd.rw.jint.token.TokenType.LET;
import static nd.rw.jint.token.TokenType.LPAREN;
import static nd.rw.jint.token.TokenType.LT;
import static nd.rw.jint.token.TokenType.MINUS;
import static nd.rw.jint.token.TokenType.PLUS;
import static nd.rw.jint.token.TokenType.RBRACE;
import static nd.rw.jint.token.TokenType.RPAREN;
import static nd.rw.jint.token.TokenType.SEMICOLON;
import static nd.rw.jint.token.TokenType.SLASH;
import static org.assertj.core.api.Assertions.assertThat;

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
                        token(EOF, "EOF")
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
                        token(EOF, "EOF")
                );
    }

    @Test
    public void testNextTokenExtendedExpressions() {
        //  given
        var input = "!-/*5;";
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
        assertThat(tokens).containsExactly(
                token(BANG, "!"),
                token(MINUS, "-"),
                token(SLASH, "/"),
                token(ASTERISK, "*"),
                token(INT, "5"),
                token(SEMICOLON, ";"),
                token(EOF, "EOF")

        );
    }

    @Test
    public void testNextTokenGT_LT() {
        //  given
        var input = "4 < 5; 5 > 4;";
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
        assertThat(tokens).containsExactly(
                token(INT, "4"),
                token(LT, "<"),
                token(INT, "5"),
                token(SEMICOLON, ";"),
                token(INT, "5"),
                token(GT, ">"),
                token(INT, "4"),
                token(SEMICOLON, ";"),
                token(EOF, "EOF")

        );

    }

    private Token token(TokenType tokenType, String literal) {
        return Token.of(tokenType, literal);
    }

}
