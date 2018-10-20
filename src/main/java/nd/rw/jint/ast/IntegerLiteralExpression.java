package nd.rw.jint.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nd.rw.jint.token.Token;

@AllArgsConstructor(staticName = "of")
public class IntegerLiteralExpression implements Expression {

    @Getter
    private Token token;

    @Getter
    private long value;


    @Override
    public void expressionNode() {

    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        return token.getLiteral();
    }
}
