package nd.rw.jint.parser;

import nd.rw.jint.ast.Program;
import nd.rw.jint.lexer.Lexer;
import nd.rw.jint.token.Token;

class Parser {

    private Lexer lexer;

    private Token currentToken;
    private Token peekToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        nextToken();
        nextToken();
    }

    public void nextToken() {
        currentToken = peekToken;
        peekToken = lexer.next();
    }

    public Program parseProgram() {
        return null;
    }

}
