package nd.rw.jint.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nd.rw.jint.token.Token;

@AllArgsConstructor(staticName = "of")
public class ExpressionStatement implements Statement {

    @Getter
    private Token token;

    @Getter
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
