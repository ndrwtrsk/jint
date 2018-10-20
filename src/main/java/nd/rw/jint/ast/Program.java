package nd.rw.jint.ast;

import com.google.common.collect.Lists;

import java.util.List;

public class Program implements Node {

    private List<Statement> statements = Lists.newArrayList();

    @Override
    public String tokenLiteral() {
        return statements.stream()
                .findFirst()
                .map(Node::tokenLiteral)
                .orElse("");
    }
}
