package eterna.uni.secondsem;

import java.util.Scanner;

/**
 * Class for streamlined reading of Coordinate instances from the console.
 */
public class ConsolePromptCoordinates {
    /**
     * Current field index.
     */
    private static int height;
    /**
     * Total fields in a Coordinate instance.
     */
    private static final int STOP_HEIGHT = 2;

    public static Coordinates readFromScanner(Scanner inputScanner, boolean printPrompts) {
        height = 0;
        Coordinates coords = new Coordinates();
        do {
            if (printPrompts) LogPrinter.log(fieldPrompts[height + 1]);
        }
        while (pushInput(coords, inputScanner));
        return coords;
    }

    /**
     * Requests input for the next field, and writes it into the corresponding field of coords.
     * @param prompt the console prompt to retrieve input from
     * @return true if the Coordinate instance is incomplete, otherwise false
     */
    private static boolean pushInput(Coordinates coords, Scanner inputScanner) {
        
        try {
            String line = inputScanner.next();
            switch (height) {
                case 0:
                    coords.set_x(Integer.parseInt(line));
                    break;
                case 1:
                    coords.set_y(Float.parseFloat(line));
                    break;
                default:
                    break;
            }     
            height++;
        }
        catch (NumberFormatException nfex) {
            LogPrinter.log(nfex.getMessage());
        } 
        catch (IllegalArgumentException iaex) {
            LogPrinter.log(iaex.getMessage());
        }

        if (height == STOP_HEIGHT) {
            LogPrinter.log("Finished creating instance of Coordinates.");
            return false;
        }
        return true;
    }

    /**
     * @return prompts for the fields
     */
    private final static String[] fieldPrompts =  new String[] {
        "Unexpected field prompt!",
        "Enter an integer for the x coordinate: ",
        "Enter a float for the y coordinate: "
    };
}
