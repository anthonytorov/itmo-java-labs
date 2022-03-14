package eterna.uni.secondsem;

/**
 * @author anton torov
 * The launcher class for the fifth lab.
 */
public class Lab5_Launcher {
    public static void main(String[] args) {
        System.out.println("Lab5 online.");
        String path = "C:\\Users\\eterna dark\\Documents\\GitHub\\itmo-java-labs\\personDatabase.csv";
        AppManager.start(path);
    }
}