package nd.rw.jint;

import nd.rw.jint.repl.Repl;

public class JintMain {

    public static void main(String[] args) {
        Repl repl = new Repl();
        repl.start(System.in, System.out);
    }

}
