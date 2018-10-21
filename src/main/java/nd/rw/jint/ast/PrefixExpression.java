package nd.rw.jint.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nd.rw.jint.token.Token;

@AllArgsConstructor(staticName = "of")
@Getter
public class PrefixExpression implements Expression {

    private Token token;
    private String operator;
    private Expression rightExpression;

    @Override
    public void expressionNode() {

    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return "(" +
                operator +
                rightExpression.toString() +
                ")";
    }
}
