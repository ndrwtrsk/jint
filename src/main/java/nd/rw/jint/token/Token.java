package nd.rw.jint.token;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value(staticConstructor = "of")
@EqualsAndHashCode(of = {"tokenType", "literal"})
@ToString(includeFieldNames = false)
public class Token {

    private TokenType tokenType;
    private String literal;

}
