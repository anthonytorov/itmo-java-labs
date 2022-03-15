package eterna.uni.secondsem;

/**
 * Class for streamlined reading of Coordinate instances from the console.
 */
public class ConsolePromptCoordinates {
    /**
     * The instance to read into. Sequentially filled with input values. 
     */
    public Coordinates coords;
    /**
     * Current field index.
     */
    private int height;
    /**
     * Total fields in a Coordinate instance.
     */
    private final int STOP_HEIGHT = 2;

    public ConsolePromptCoordinates(Coordinates _coords) {
        if (_coords == null) {
            coords = new Coordinates();
        } else {
            coords = _coords;
        }
        height = 0;

        while (pushInput(AppManager.getTopPrompter()));
    }

    /**
     * Requests input for the next field, and writes it into the corresponding field of coords.
     * @param prompt the console prompt to retrieve input from
     * @return true if the Coordinate instance is incomplete, otherwise false
     */
    public boolean pushInput(Prompter prompt) {
        
        prompt.showPrompt(getFieldPrompt());
        
        try {
            String line = prompt.getOutput();
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
     * @return a prompt for the field being currently read
     */
    private String getFieldPrompt() {
        switch (height) {
            default:
                return "Unexpected field prompt!";
            case 0:
                return "Enter an integer for the x coordinate: ";
            case 1:
                return "Enter a float for the y coordinate: ";
        }
    }
}
