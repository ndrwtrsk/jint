package nd.rw.jint.parser;

import nd.rw.jint.ast.Identifier;
import nd.rw.jint.ast.LetStatement;
import nd.rw.jint.ast.Program;
import nd.rw.jint.ast.Statement;
import nd.rw.jint.lexer.Lexer;
import nd.rw.jint.token.Token;
import nd.rw.jint.token.TokenType;

import static nd.rw.jint.token.TokenType.ASSIGN;
import static nd.rw.jint.token.TokenType.EOF;
import static nd.rw.jint.token.TokenType.IDENT;
import static nd.rw.jint.token.TokenType.SEMICOLON;

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
        Program program = new Program();
        var statements = program.getStatements();

        while (currentToken.getTokenType() != EOF) {
            var statement = parseStatement();
            if (statement != null) {
                statements.add(statement);
            }
            nextToken();
        }
        return program;
    }

    private Statement parseStatement() {
        switch (currentToken.getTokenType()) {
            case LET: {
                return parseLetStatement();
            }
            default: {
                return null;
            }
        }
    }

    private LetStatement parseLetStatement() {
        var letToken = currentToken;

        if (!expectToken(IDENT)) {
            return null;
        }

        var statement = LetStatement.of(letToken, Identifier.of(currentToken, currentToken.getLiteral()));

        if (!expectToken(ASSIGN)) {
            return null;
        }

        while (!currentTokenIs(SEMICOLON)) {
            nextToken();
        }

        return statement;
    }

    private boolean expectToken(TokenType tokenType) {
        if (peekTokenIs(tokenType)) {
            nextToken();
            return true;
        }
        return false;
    }

    private boolean peekTokenIs(TokenType tokenType) {
        return peekToken.getTokenType() == tokenType;
    }

    private boolean currentTokenIs(TokenType tokenType) {
        return currentToken.getTokenType() == tokenType;
    }

}
