package nd.rw.jint.ast;

import nd.rw.jint.token.Token;

public class ExpressionStatement implements Statement {

    private Token token;
    private Expression expression;

    @Override
    public void statementNode() {

    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return expression != null ? expression.toString() : "";
    }
}
