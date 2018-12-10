package nd.rw.jint.parser;

import nd.rw.jint.ast.Expression;
import nd.rw.jint.ast.InfixExpression;

import java.util.function.Function;

interface InfixParseFunction extends Function<Expression, InfixExpression> {

}
