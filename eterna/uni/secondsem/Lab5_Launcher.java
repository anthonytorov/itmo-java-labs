package eterna.uni.secondsem;

import eterna.uni.secondsem.client.ClientInitializer;
import eterna.uni.secondsem.server.ServerInitializer;

/**
 * @author anton torov
 * The launcher class for the fifth lab.
 */
public class Lab5_Launcher {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a file path!");
        } else {
            System.out.println("Lab5 online.");
            ServerInitializer.start(args[0]);
            ClientInitializer.start();
        }
    }
}