package nd.rw.jint.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nd.rw.jint.token.Token;

@AllArgsConstructor(staticName = "of")
public class LetStatement implements Statement {

    private Token token;

    @Getter
    private Identifier name;

    private Expression value;

    @Override
    public void statementNode() {

    }

    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    @Override
    public String toString() {
        var stringBuilder = new StringBuilder();
        return stringBuilder.append(tokenLiteral())
                .append(" ")
                .append(name.toString())
                .append(" = ")
                .append(value != null ? value.toString() : "")
                .append(";")
                .toString();
    }
}
