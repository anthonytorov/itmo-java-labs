package eterna.uni.secondsem;

public class ConsolePromptLocation {
    public Location location;
    private int height;
    private final int STOP_HEIGHT = 4;

    public ConsolePromptLocation(Location _location) {
        if (_location == null) {
            location = new Location(0f, 0d, 0d, "Unnamed");
        } else {
            location = _location;
        }
        height = 0;

        while (pushInput(new ConsolePrompt(getFieldPrompt())));
    }

    public boolean pushInput(ConsolePrompt prompt) {
        try {
            String line = prompt.get_output();
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

    private String getFieldPrompt() {
        switch (height) {
            default:
                return "Unexpected field prompt!";
            case 0:
                return "Enter a float for the x coordinate: ";
            case 1:
                return "Enter a double for the y coordinate: ";
            case 2:
                return "Enter a double for the z coordinate: ";
            case 3:
                return "Enter the location's name (or \"\" for null)";
        }
    }
}
