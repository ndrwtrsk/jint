package nd.rw.jint.lexer;

import lombok.Value;

@Value(staticConstructor = "of")
class CurrentCharacterAndPosition {
    private Character currentCharacter;
    private Integer currentInputPosition;
}
