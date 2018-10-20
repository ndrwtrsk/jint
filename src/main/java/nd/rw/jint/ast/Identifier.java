package nd.rw.jint.ast;

import nd.rw.jint.token.Token;

class Identifier implements Expression {

    private Token token;
    private String value;


    @Override
    public void expressionNode() {

    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }
}
