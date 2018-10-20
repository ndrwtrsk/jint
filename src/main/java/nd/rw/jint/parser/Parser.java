package nd.rw.jint.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import nd.rw.jint.ast.Expression;
import nd.rw.jint.ast.ExpressionStatement;
import nd.rw.jint.ast.Identifier;
import nd.rw.jint.ast.IntegerLiteralExpression;
import nd.rw.jint.ast.LetStatement;
import nd.rw.jint.ast.Program;
import nd.rw.jint.ast.ReturnStatement;
import nd.rw.jint.ast.Statement;
import nd.rw.jint.lexer.Lexer;
import nd.rw.jint.token.Token;
import nd.rw.jint.token.TokenType;

import java.util.List;
import java.util.Map;

import static nd.rw.jint.parser.PrecedenceOperator.LOWEST;
import static nd.rw.jint.token.TokenType.ASSIGN;
import static nd.rw.jint.token.TokenType.EOF;
import static nd.rw.jint.token.TokenType.IDENT;
import static nd.rw.jint.token.TokenType.INT;
import static nd.rw.jint.token.TokenType.SEMICOLON;

class Parser {

    private Lexer lexer;

    private Token currentToken;
    private Token peekToken;

    @Getter
    private List<String> errors = Lists.newLinkedList();

    private Map<TokenType, InfixParseFunction> infixParseFunctions = Maps.newHashMap();
    private Map<TokenType, PrefixParseFunction> prefixParseFunctions = Maps.newHashMap();

    Parser(Lexer lexer) {
        this.lexer = lexer;
        nextToken();
        nextToken();
        registerPrefixParseFunction(IDENT, () -> Identifier.of(currentToken, currentToken.getLiteral()));
        registerPrefixParseFunction(INT, this::parseIntegerLiteralExpression);
    }

    private void nextToken() {
        currentToken = peekToken;
        peekToken = lexer.next();
    }

    Program parseProgram() {
        var program = new Program();
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
                return parseExpressionStatement();
            }
        }
    }

    private ExpressionStatement parseExpressionStatement() {
        var token = currentToken;
        var expression = parseExpression(LOWEST);
        var expressionStatement = ExpressionStatement.of(token, expression);

        if (peekTokenIs(SEMICOLON)) {
            nextToken();
        }

        return expressionStatement;
    }

    private Expression parseExpression(PrecedenceOperator precedenceOperator) {
        var prefixParseFunction = prefixParseFunctions.get(currentToken.getTokenType());

        if (prefixParseFunction == null) {
            return null;
        }

        var leftExpression = prefixParseFunction.get();

        return leftExpression;
    }

    private IntegerLiteralExpression parseIntegerLiteralExpression() {
        long number = 0;
        try {
            number = Long.parseLong(currentToken.getLiteral());
        } catch (NumberFormatException nfe) {
            var message = String.format("Error parsing %s to number", currentToken.getLiteral());
            errors.add(message);
        }
        return IntegerLiteralExpression.of(currentToken, number);
    }

    private ReturnStatement parseReturnStatement() {
        var returnStatement = ReturnStatement.of(currentToken, null);
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

        var identifier = Identifier.of(currentToken, currentToken.getLiteral());
        var statement = LetStatement.of(letToken, identifier, null);

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
        var message = String.format("Expected next token to be: %s, but got %s instead.", tokenType, peekToken.getTokenType());
        errors.add(message);
    }

    private void registerPrefixParseFunction(TokenType tokenType, PrefixParseFunction fn) {
        prefixParseFunctions.put(tokenType, fn);
    }

    private void registerInfixParseFunction(TokenType tokenType, InfixParseFunction fn) {
        infixParseFunctions.put(tokenType, fn);
    }

    boolean hasErrors() {
        return !errors.isEmpty();
    }
}
