package nd.rw.jint.parser;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nd.rw.jint.ast.ExpressionStatement;
import nd.rw.jint.ast.InfixExpression;
import nd.rw.jint.lexer.Lexer;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ParserInfixExpressionTestJunit {

    @Test
    @Parameters({
            "5 + 5, 5, +, 5",
            "5 - 5, 5, -, 5",
            "5 * 5, 5, *, 5",
            "5 / 5, 5, /, 5"
    })
    public void test(String input, String left, String operator, String right) {
        //  when
        var lexer = new Lexer(input);
        var parser = new Parser(lexer);
        var program = parser.parseProgram();

        //  then
        assertThat(parser.hasErrors()).isFalse();
        assertThat(program).isNotNull();
        var statements = program.getStatements();
        assertThat(statements).hasSize(1);

        var statement = statements.get(0);
        assertThat(statement).isExactlyInstanceOf(ExpressionStatement.class);
        var expressionStatement = (ExpressionStatement) statement;
        assertThat(expressionStatement.getExpression()).isExactlyInstanceOf(InfixExpression.class);

        var infixExpression = (InfixExpression) expressionStatement.getExpression();

        assertThat(infixExpression.getLeft().tokenLiteral()).isEqualTo(left);
        assertThat(infixExpression.getOperator()).isEqualTo(operator);
        assertThat(infixExpression.getRight().tokenLiteral()).isEqualTo(right);
    }
}
