package nd.rw.jint.parser;

import nd.rw.jint.ast.LetStatement;
import nd.rw.jint.ast.Statement;
import nd.rw.jint.lexer.Lexer;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserLetStatementTest {

    @Test
    public void testParsingSingleLetStatement() {
        //  given
        var letStatementInput = "let x = 5;";
        var lexer = new Lexer(letStatementInput);
        var parser = new Parser(lexer);

        //  when
        var program = parser.parseProgram();

        //  then
        assertThat(parser.getErrors()).isEmpty();
        assertThat(program).isNotNull();
        List<Statement> statements = program.getStatements();
        assertThat(statements).hasSize(1);

        var statement = statements.get(0);
        assertThat(statement.tokenLiteral()).isEqualTo("let");
        assertThat(statement).isInstanceOf(LetStatement.class);

        var letStatement = (LetStatement) statement;
        assertThat(letStatement.getName().getValue()).isEqualTo("x");
        assertThat(letStatement.getName().tokenLiteral()).isEqualTo("x");

    }

    @Test
    public void testParsingThreeLetStatements() {
        //  given
        var letStatementInput = "let x = 5;let y = 12;let foobar = 123123;";
        var lexer = new Lexer(letStatementInput);
        var parser = new Parser(lexer);

        //  when
        var program = parser.parseProgram();

        //  then
        assertThat(parser.getErrors()).isEmpty();
        assertThat(program).isNotNull();
        List<Statement> statements = program.getStatements();
        assertThat(statements).hasSize(3);

        assertStatements(statements.get(0), "x", "x");
        assertStatements(statements.get(1), "y", "y");
        assertStatements(statements.get(2), "foobar", "foobar");
    }

    private void assertStatements(Statement statement, String value, String tokenLiteral) {
        assertThat(statement.tokenLiteral()).isEqualTo("let");
        assertThat(statement).isInstanceOf(LetStatement.class);

        var letStatement = (LetStatement) statement;
        assertThat(letStatement.getName().getValue()).isEqualTo(value);
        assertThat(letStatement.getName().tokenLiteral()).isEqualTo(tokenLiteral);
    }

    @Test
    public void testParsingYieldsErrors() {
        //  given
        var letStatementInputWithError = "let x 5;";
        var lexer = new Lexer(letStatementInputWithError);
        var parser = new Parser(lexer);

        //  when
        parser.parseProgram();

        //  then
        var errors = parser.getErrors();
        assertThat(errors).hasSize(1);
    }

}