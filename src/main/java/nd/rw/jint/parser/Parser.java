package nd.rw.jint.parser;

import com.google.common.collect.Lists;
import lombok.Getter;
import nd.rw.jint.ast.Identifier;
import nd.rw.jint.ast.LetStatement;
import nd.rw.jint.ast.Program;
import nd.rw.jint.ast.ReturnStatement;
import nd.rw.jint.ast.Statement;
import nd.rw.jint.lexer.Lexer;
import nd.rw.jint.token.Token;
import nd.rw.jint.token.TokenType;

import java.util.List;

import static nd.rw.jint.token.TokenType.ASSIGN;
import static nd.rw.jint.token.TokenType.EOF;
import static nd.rw.jint.token.TokenType.IDENT;
import static nd.rw.jint.token.TokenType.SEMICOLON;

class Parser {

    private Lexer lexer;

    private Token currentToken;
    private Token peekToken;

    @Getter
    private List<String> errors = Lists.newLinkedList();

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
            case RETURN: {
                return parseReturnStatement();
            }
            default: {
                return null;
            }
        }
    }

    private Statement parseReturnStatement() {
        ReturnStatement returnStatement = new ReturnStatement(currentToken, null);
        nextToken();

        while (currentTokenIsNot(SEMICOLON)) {
            nextToken();
        }

        return returnStatement;
    }

    private LetStatement parseLetStatement() {
        var letToken = currentToken;

        if (!expectPeekToken(IDENT)) {
            return null;
        }

        var statement = LetStatement.of(letToken, Identifier.of(currentToken, currentToken.getLiteral()));

        if (!expectPeekToken(ASSIGN)) {
            return null;
        }

        while (currentTokenIsNot(SEMICOLON)) {
            nextToken();
        }

        return statement;
    }

    private boolean expectPeekToken(TokenType tokenType) {
        if (peekTokenIs(tokenType)) {
            nextToken();
            return true;
        } else {
            peekError(tokenType);
            return false;
        }
    }

    private boolean peekTokenIs(TokenType tokenType) {
        return peekToken.getTokenType() == tokenType;
    }

    private boolean currentTokenIsNot(TokenType tokenType) {
        return !currentTokenIs(tokenType);
    }

    private boolean currentTokenIs(TokenType tokenType) {
        return currentToken.getTokenType() == tokenType;
    }

    private void peekError(TokenType tokenType) {
        String message = String.format("Expected next token to be: %s, but got %s instead.", tokenType, peekToken.getTokenType());
        errors.add(message);
    }

}
