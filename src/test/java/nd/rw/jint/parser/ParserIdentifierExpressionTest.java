package nd.rw.jint.parser;

import nd.rw.jint.ast.ExpressionStatement;
import nd.rw.jint.ast.Identifier;
import nd.rw.jint.ast.Statement;
import nd.rw.jint.lexer.Lexer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserIdentifierExpressionTest {

    @Test
    public void test() {
        //  when
        var inputString = "foobar;";
        var lexer = new Lexer(inputString);
        var parser = new Parser(lexer);

        //  given
        var program = parser.parseProgram();

        //  then
        assertThat(parser.hasErrors()).isFalse();
        assertThat(program).isNotNull();
        var statements = program.getStatements();
        assertThat(statements).hasSize(1);

        Statement statement = statements.get(0);

        assertThat(statement).isInstanceOf(ExpressionStatement.class);

        var expressionStatement = (ExpressionStatement) statement;
        var expression = expressionStatement.getExpression();
        assertThat(expression).isInstanceOf(Identifier.class);

        var identifier = (Identifier) expression;
        assertThat(identifier.getValue()).isEqualTo("foobar");
        assertThat(identifier.tokenLiteral()).isEqualTo("foobar");
    }

}
