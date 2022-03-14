package eterna.uni.secondsem;

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
            AppManager.start(args[0]);
        }
    }
}