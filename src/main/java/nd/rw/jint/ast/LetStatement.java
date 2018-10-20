package nd.rw.jint.ast;

import nd.rw.jint.token.Token;

class LetStatement implements Statement {

    private Token token;
    private Identifier name;

    @Override
    public void statementNode() {

    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }
}
