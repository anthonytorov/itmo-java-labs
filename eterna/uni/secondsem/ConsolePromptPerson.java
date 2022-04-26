package eterna.uni.secondsem;

import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.text.ParseException;

/**
 * Class for streamlined reading of Person instances from the console.
 */
public class ConsolePromptPerson {
    /**
     * Current field index.
     */
    private static int height;
    /**
     * Total fields in a Person instance.
     */
    private static final int STOP_HEIGHT = 7;

    public static Person readFromScanner (Scanner inputScanner, boolean printPrompts) {
        Person person = new Person("", new Coordinates(), 0, Country.ITALY, new Location(0f, 0d, 0d, "Unnamed"));
        height = 0;

        do {
            if (printPrompts) LogPrinter.log(fieldPrompts[height + 1]);
        }
        while (pushInput(person, inputScanner, printPrompts));

        return person;
    }

    /**
     * Requests input for the next field, and writes it into the corresponding field of coords.
     * @param prompt the console prompt to retrieve input from
     * @return true if the Person instance is incomplete, otherwise false
     */
    private static boolean pushInput(Person person, Scanner inputScanner, boolean printPrompts) {

        try {
            switch (height) {
                case 0:
                    person.set_name(inputScanner.next());
                    break;
                case 1:
                    person.set_coordinates(ConsolePromptCoordinates.readFromScanner(inputScanner, printPrompts));
                    break;
                case 2:
                    person.set_height(Double.parseDouble(inputScanner.next()));
                    break;
                case 3:
                    String line = inputScanner.next();
                    if (!line.isEmpty()) {
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        person.set_birthday(format.parse(line));
                    }
                    break;
                case 4:
                    line = inputScanner.next();
                    if (!line.isEmpty()) {
                        person.set_hairColor(Color.valueOf(line));
                    }
                    break;
                case 5:
                    person.set_nationality(Country.valueOf(inputScanner.next()));
                    break;
                case 6:
                    person.set_location(ConsolePromptLocation.readFromScanner(inputScanner, printPrompts));
                default:
                    break;
            }     
            height++;
        }
        catch (ParseException pex) {
            LogPrinter.log(pex.getMessage());
        }
        catch (NumberFormatException nfex) {
            LogPrinter.log(nfex.getMessage());
        } 
        catch (IllegalArgumentException iaex) {
            LogPrinter.log(iaex.getMessage());
        }

        if (height == STOP_HEIGHT) {
            LogPrinter.log("Finished creating instance of Person.");
            return false;
        }
        return true;
    }

    /**
     * @return a prompt for the field being currently read
     */
    private final static String[] fieldPrompts = new String[] {
        "Unexpected field prompt!",
        "Enter person's name: ",
        "Enter person's coordinates: ",
        "Enter person's height (in meters): ",
        "Enter person's date of birth as dd-MM-yyyy (or nothing to skip): ",
        "Enter person's hair color (GREEN/RED/BLUE/WHITE/BROWN or nothing):",
        "Enter person's nationality (ITALY/JAPAN/RUSSIA/UNITED_KINGDOM):",
        "Enter person's location:"
    };
}
