package nd.rw.jint.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import nd.rw.jint.ast.Expression;
import nd.rw.jint.ast.ExpressionStatement;
import nd.rw.jint.ast.Identifier;
import nd.rw.jint.ast.InfixExpression;
import nd.rw.jint.ast.IntegerLiteralExpression;
import nd.rw.jint.ast.LetStatement;
import nd.rw.jint.ast.PrefixExpression;
import nd.rw.jint.ast.Program;
import nd.rw.jint.ast.ReturnStatement;
import nd.rw.jint.ast.Statement;
import nd.rw.jint.lexer.Lexer;
import nd.rw.jint.token.Token;
import nd.rw.jint.token.TokenType;

import java.util.List;
import java.util.Map;

import static nd.rw.jint.parser.PrecedenceOperator.LOWEST;
import static nd.rw.jint.parser.PrecedenceOperator.PREFIX;
import static nd.rw.jint.token.TokenType.ASSIGN;
import static nd.rw.jint.token.TokenType.ASTERISK;
import static nd.rw.jint.token.TokenType.BANG;
import static nd.rw.jint.token.TokenType.EOF;
import static nd.rw.jint.token.TokenType.EQ;
import static nd.rw.jint.token.TokenType.GT;
import static nd.rw.jint.token.TokenType.IDENT;
import static nd.rw.jint.token.TokenType.INT;
import static nd.rw.jint.token.TokenType.LT;
import static nd.rw.jint.token.TokenType.MINUS;
import static nd.rw.jint.token.TokenType.NOT_EQ;
import static nd.rw.jint.token.TokenType.PLUS;
import static nd.rw.jint.token.TokenType.SEMICOLON;
import static nd.rw.jint.token.TokenType.SLASH;

class Parser {

    private static Map<TokenType, PrecedenceOperator> PRECEDENCE_TABLE = Maps.newHashMap();

    static {
        PRECEDENCE_TABLE.put(EQ, PrecedenceOperator.EQUALS);
        PRECEDENCE_TABLE.put(NOT_EQ, PrecedenceOperator.EQUALS);
        PRECEDENCE_TABLE.put(LT, PrecedenceOperator.LESS_GREATER);
        PRECEDENCE_TABLE.put(GT, PrecedenceOperator.LESS_GREATER);
        PRECEDENCE_TABLE.put(PLUS, PrecedenceOperator.SUM);
        PRECEDENCE_TABLE.put(MINUS, PrecedenceOperator.SUM);
        PRECEDENCE_TABLE.put(ASTERISK, PrecedenceOperator.PRODUCT);
        PRECEDENCE_TABLE.put(SLASH, PrecedenceOperator.PRODUCT);
    }

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
        registerPrefixParseFunction(BANG, this::parsePrefixExpression);
        registerPrefixParseFunction(MINUS, this::parsePrefixExpression);
        registerInfixParseFunction(PLUS, this.parseInfixExpression());
        registerInfixParseFunction(MINUS, this.parseInfixExpression());
        registerInfixParseFunction(ASTERISK, this.parseInfixExpression());
        registerInfixParseFunction(SLASH, this.parseInfixExpression());
        registerInfixParseFunction(EQ, this.parseInfixExpression());
        registerInfixParseFunction(NOT_EQ, this.parseInfixExpression());
        registerInfixParseFunction(LT, this.parseInfixExpression());
        registerInfixParseFunction(GT, this.parseInfixExpression());
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
            noPrefixParseFunctionError(currentToken.getTokenType());
            return null;
        }

        var leftExpression = prefixParseFunction.get();

        while (!peekTokenIs(SEMICOLON) && precedenceOperator.isLowerThan(peekPrecedence())) {
            var infixParseFunction = infixParseFunctions.get(peekToken.getTokenType());
            if (infixParseFunction == null) {
                return null;
            }
            nextToken();
            leftExpression = infixParseFunction.apply(leftExpression);
        }

        return leftExpression;
    }

    private PrefixExpression parsePrefixExpression() {
        var token = currentToken;
        nextToken();
        var expression = parseExpression(PREFIX);
        return PrefixExpression.of(token, token.getLiteral(), expression);
    }

    private InfixParseFunction parseInfixExpression() {
        return left -> {
            var currentToken = this.currentToken;
            var precedence = currentPrecedence();
            nextToken();
            var right = parseExpression(precedence);
            return InfixExpression.of(currentToken, left, currentToken.getLiteral(), right);
        };
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

    private void noPrefixParseFunctionError(TokenType tokenType) {
        var message = String.format("No prefix parse function found for %s", tokenType);
        errors.add(message);
    }

    boolean hasErrors() {
        return !errors.isEmpty();
    }

    private PrecedenceOperator peekPrecedence() {
        return PRECEDENCE_TABLE.getOrDefault(peekToken.getTokenType(), LOWEST);
    }

    private PrecedenceOperator currentPrecedence() {
        return PRECEDENCE_TABLE.getOrDefault(currentToken.getTokenType(), LOWEST);
    }
}
