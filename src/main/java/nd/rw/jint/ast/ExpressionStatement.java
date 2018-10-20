package nd.rw.jint.ast;

import lombok.Getter;
import lombok.Setter;
import nd.rw.jint.token.Token;

public class ExpressionStatement implements Statement {

    @Getter
    @Setter
    private Token token;

    @Getter
    @Setter
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
