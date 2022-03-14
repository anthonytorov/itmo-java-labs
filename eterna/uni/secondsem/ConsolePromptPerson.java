package eterna.uni.secondsem;

import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Class for streamlined reading of Person instances from the console.
 */
public class ConsolePromptPerson {
    /**
     * The instance to read into. Sequentially filled with input values. 
     */
    public Person person;
    /**
     * Current field index.
     */
    private int height;
    /**
     * Total fields in a Person instance.
     */
    private final int STOP_HEIGHT = 7;

    public ConsolePromptPerson(Person _person) {
        if (_person == null) {
            person = new Person("", new Coordinates(), 0, Country.ITALY, new Location(0f, 0d, 0d, "Unnamed"));
        } else {
            person = _person;
        }
        height = 0;

        while (pushInput(new ConsolePrompt(getFieldPrompt())));
    }

    /**
     * Requests input for the next field, and writes it into the corresponding field of coords.
     * @param prompt the console prompt to retrieve input from
     * @return true if the Person instance is incomplete, otherwise false
     */
    public boolean pushInput(ConsolePrompt prompt) {
        try {
            switch (height) {
                case 0:
                    person.set_name(prompt.get_output());
                    break;
                case 1:
                    ConsolePromptCoordinates coordinatePrompt = new ConsolePromptCoordinates(null);
                    person.set_coordinates(coordinatePrompt.coords);
                    break;
                case 2:
                    person.set_height(Double.parseDouble(prompt.get_output()));
                    break;
                case 3:
                    String line = prompt.get_output();
                    if (!line.isEmpty()) {
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        person.set_birthday(format.parse(line));
                    }
                    break;
                case 4:
                    line = prompt.get_output();
                    if (!line.isEmpty()) {
                        person.set_hairColor(Color.valueOf(line));
                    }
                    break;
                case 5:
                    person.set_nationality(Country.valueOf(prompt.get_output()));
                    break;
                case 6:
                    ConsolePromptLocation locationPrompt = new ConsolePromptLocation(null);
                    person.set_location(locationPrompt.location);
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
    private String getFieldPrompt() {
        switch (height) {
            default:
                return "Unexpected field prompt!";
            case 0:
                return "Enter person's name: ";
            case 1:
                return "Enter person's coordinates: ";
            case 2:
                return "Enter person's height (in meters): ";
            case 3:
                return "Enter person's date of birth as dd-MM-yyyy (or nothing to skip): ";
            case 4:
                return "Enter person's hair color (GREEN/RED/BLUE/WHITE/BROWN or nothing):";
            case 5:
                return "Enter person's nationality (ITALY/JAPAN/RUSSIA/UNITED_KINGDOM):";
            case 6:
                return "Enter person's location:";
        }
    }
}
