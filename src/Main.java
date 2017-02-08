import example.Example1;
import lejos.nxt.Button;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        new Example1();
        Button.waitForAnyPress();
    }
}
