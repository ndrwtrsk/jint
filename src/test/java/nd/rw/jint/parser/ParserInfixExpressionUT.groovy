package nd.rw.jint.parser

import nd.rw.jint.ast.ExpressionStatement
import nd.rw.jint.ast.InfixExpression
import nd.rw.jint.lexer.Lexer
import spock.lang.Specification
import spock.lang.Unroll

class ParserInfixExpressionUT extends Specification {

    @Unroll
    def "should parse infix expression for: #input"() {
        given:
            def lexer = new Lexer(input)
            def parser = new Parser(lexer)

        when:
            def program = parser.parseProgram()

        then:
            !parser.hasErrors()
            program
            def statements = program.getStatements();
            statements.size() == 1

        and:
            def statement = statements.get(0);
            statement instanceof ExpressionStatement
            def expressionStatement = (ExpressionStatement) statement
            expressionStatement.getExpression() instanceof InfixExpression

        and:
            def infixExpression = (InfixExpression) expressionStatement.getExpression()
            infixExpression.getLeft().tokenLiteral() == left
            infixExpression.getOperator() == operator
            infixExpression.getRight().tokenLiteral() == right

        where:
            input   | left | operator | right
            "5 + 5" | "5"  | "+"      | "5"
            "5 - 5" | "5"  | "-"      | "5"
            "5 * 5" | "5"  | "*"      | "5"
            "5 / 5" | "5"  | "/"      | "5"

    }

}
