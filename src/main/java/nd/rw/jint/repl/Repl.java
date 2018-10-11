package nd.rw.jint.repl;

import com.google.common.base.Strings;
import lombok.SneakyThrows;
import nd.rw.jint.lexer.Lexer;
import nd.rw.jint.token.Token;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

public class Repl {

    private static final String PROMPT = ">>> ";

    @SneakyThrows
    public void start(InputStream in, OutputStream os) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        PrintStream pr = new PrintStream(os);

        while (true) {
            pr.print(PROMPT);
            var readLine = br.readLine();

            if (Strings.isNullOrEmpty(readLine)) {
                break;
            }

            var lexer = new Lexer(readLine);

            for (Token token : lexer) {
                pr.println(token);
            }

        }
    }

}
