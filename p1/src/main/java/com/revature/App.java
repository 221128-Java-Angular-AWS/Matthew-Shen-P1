package com.revature;
import com.revature.javalin.JavalinApp;
import io.javalin.Javalin;
/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        Javalin app = JavalinApp.getApp(8080);
    }
}
