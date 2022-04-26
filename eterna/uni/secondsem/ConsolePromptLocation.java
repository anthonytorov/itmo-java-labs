package eterna.uni.secondsem;

import java.util.Scanner;

/**
 * Class for streamlined reading of Coordinate instances from the console.
 */
public class ConsolePromptLocation {
    /**
     * Current field index.
     */
    private static int height;
    /**
     * Total fields in a Location instance.
     */
    private static final int STOP_HEIGHT = 4;

    public static Location readFromScanner(Scanner inputScanner, boolean printPrompts) {
        
        Location location = new Location(0f, 0d, 0d, "Unnamed");
        height = 0;
        
        do {
            if (printPrompts) LogPrinter.log(fieldPrompts[height + 1]);
        }
        while (pushInput(location, inputScanner));

        return location;
    }

    /**
     * Requests input for the next field, and writes it into the corresponding field of location.
     * @param prompt the console prompt to retrieve input from
     * @return true if the Location instance is incomplete, otherwise false
     */
    private static boolean pushInput(Location location, Scanner inputScanner) {
        
        try {
            String line = inputScanner.next();
            switch (height) {
                case 0:
                    location.set_x(new Float(line));
                    break;
                case 1:
                    location.set_y(Double.parseDouble(line));
                    break;
                case 2:
                    location.set_z(Double.parseDouble(line));
                    break;
                case 3:
                    location.set_name(line);
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
            LogPrinter.log("Finished creating instance of Location.");
            return false;
        }
        return true;
    }

    /**
     * @return prompts for the fields
     */
    private final static String[] fieldPrompts = new String[] {
        "Unexpected field prompt!",
        "Enter a float for the x coordinate: ",
        "Enter a double for the y coordinate: ",
        "Enter a double for the z coordinate: ",
        "Enter the location's name (or \"\" for null)"
    };
}
