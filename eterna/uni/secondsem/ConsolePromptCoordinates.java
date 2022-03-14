package eterna.uni.secondsem;

public class ConsolePromptCoordinates {
    public Coordinates coords;
    private int height;
    private final int STOP_HEIGHT = 2;

    public ConsolePromptCoordinates(Coordinates _coords) {
        if (_coords == null) {
            coords = new Coordinates();
        } else {
            coords = _coords;
        }
        height = 0;

        while (pushInput(new ConsolePrompt(getFieldPrompt())));
    }

    public boolean pushInput(ConsolePrompt prompt) {
        try {
            String line = prompt.get_output();
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
