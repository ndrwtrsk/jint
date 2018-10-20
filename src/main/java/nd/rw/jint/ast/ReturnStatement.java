package nd.rw.jint.ast;

import lombok.AllArgsConstructor;
import nd.rw.jint.token.Token;

@AllArgsConstructor(staticName = "of")
public class ReturnStatement implements Statement {

    private Token token;
    private Expression returnValue;

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public void statementNode() {

    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(token.getLiteral())
                .append(" ")
                .append(returnValue != null ? returnValue.toString() : "")
                .append(";")
                .toString();
    }
}
