package nd.rw.jint.token;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value(staticConstructor = "of")
@EqualsAndHashCode(of = {"tokenType", "literal"})
public class Token {

    private TokenType tokenType;
    private String literal;

}
