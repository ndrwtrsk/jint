package nd.rw.jint.parser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
enum PrecedenceOperator {

    _BLANK(0),
    LOWEST(1),
    EQUALS(2), // ==
    LESS_GREATER(3), // < or >
    SUM(4), // +
    PRODUCT(5), // *
    PREFIX(6), // !x or -x
    CALL(7); // function(x)

    int precedence;

    public boolean isLowerThan(PrecedenceOperator precedenceOperator) {
        return this.precedence < precedenceOperator.precedence;
    }

}
