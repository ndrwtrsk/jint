package nd.rw.jint.parser;

import nd.rw.jint.ast.Expression;
import nd.rw.jint.ast.ExpressionStatement;
import nd.rw.jint.ast.PrefixExpression;
import nd.rw.jint.lexer.Lexer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserPrefixExpressionTest {

    @Test
    public void test() {
        //  when
        var inputString = "-15;";
        var lexer = new Lexer(inputString);
        var parser = new Parser(lexer);

        //  given
        var program = parser.parseProgram();

        //  then
        assertThat(parser.hasErrors()).isFalse();
        assertThat(program).isNotNull();
        var statements = program.getStatements();
        assertThat(statements).hasSize(1);

        var statement = statements.get(0);
        assertThat(statement).isInstanceOf(ExpressionStatement.class);
        var expressionStatement = (ExpressionStatement) statement;
        Expression expression = expressionStatement.getExpression();
        assertThat(expression).isInstanceOf(PrefixExpression.class);
        var prefixExpression = (PrefixExpression) expression;
        assertThat(prefixExpression.getOperator()).isEqualTo("-");
        assertThat(prefixExpression.getRightExpression().tokenLiteral()).isEqualTo("15");
    }

}
