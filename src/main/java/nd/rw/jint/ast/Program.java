package nd.rw.jint.ast;

import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

public class Program implements Node {

    @Getter
    private List<Statement> statements = Lists.newArrayList();

    @Override
    public String tokenLiteral() {
        return statements.stream()
                .findFirst()
                .map(Node::tokenLiteral)
                .orElse("");
    }
}
