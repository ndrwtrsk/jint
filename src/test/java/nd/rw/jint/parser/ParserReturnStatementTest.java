package nd.rw.jint.parser;

import nd.rw.jint.ast.ReturnStatement;
import nd.rw.jint.lexer.Lexer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserReturnStatementTest {

    @Test
    public void testReturnStatement() {
        //  given
        var letStatementInput = "return 5;";
        var lexer = new Lexer(letStatementInput);
        var parser = new Parser(lexer);

        //  when
        var program = parser.parseProgram();

        //  then
        assertThat(parser.getErrors()).isEmpty();
        assertThat(program).isNotNull();
        var statements = program.getStatements();
        assertThat(statements).hasSize(1);

        var statement = statements.get(0);

        assertThat(statement).isInstanceOf(ReturnStatement.class);
        var returnStatement = (ReturnStatement) statement;
        assertThat(returnStatement.tokenLiteral()).isEqualTo("return");
    }

}
