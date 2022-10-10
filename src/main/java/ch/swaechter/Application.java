package ch.swaechter;

import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] arguments) {
        Micronaut.build(arguments).mainClass(Application.class).banner(false).start();
    }
}
