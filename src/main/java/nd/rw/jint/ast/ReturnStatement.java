package nd.rw.jint.ast;

import lombok.AllArgsConstructor;
import nd.rw.jint.token.Token;

@AllArgsConstructor
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
}
