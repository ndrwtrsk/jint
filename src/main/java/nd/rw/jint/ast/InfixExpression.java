package nd.rw.jint.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nd.rw.jint.token.Token;

@AllArgsConstructor(staticName = "of")
@Getter
public class InfixExpression implements Expression {

    private Token token;
    private Expression left;
    private String operator;
    private Expression right;

    @Override
    public void expressionNode() {

    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }
}
