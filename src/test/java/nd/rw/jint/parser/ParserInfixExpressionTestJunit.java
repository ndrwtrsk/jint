package nd.rw.jint.parser;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
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
    public void test(String input, int left, String operator, int right) {
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


    }
}
