package eterna.uni.secondsem;

/**
 * Wrapper class for various text outputs.
 */
public class LogPrinter {
    /**
     * Prints message in the console.
     * @param message
     */
    public static void log(String message) {
        System.out.println(message);
    }

    /**
     * Prints exception in the console.
     * @param exception
     */
    public static void logException(Exception exception) {
        System.out.println(String.format("Encountered exception: %s", exception.toString()));
    }

    /**
     * Prints message asking to enter a non-empty string for an id.
     */
    public static void logEnterId() { log("please enter an id!"); }
    /**
     * Prints message notifying about an error in id.
     */
    public static void logIdError() { log("please enter an integer id from the collection!"); }
    /**
     * Prints message asking to enter a non-empty file path.
     */
    public static void logEnterPath() { log("please enter a path!"); }
    /**
     * Prints message notyfing about an error in the path.
     */
    public static void logPathError() { log("please enter a valid path!"); }

    /**
     * @param commandKey key of a certain command
     * @return description of the command with key commandKey
     * @throws UnsupportedOperationException if commandKey is unsupported
     */
    public static String getDescriptionFor(String commandKey) throws UnsupportedOperationException {
        switch (commandKey) {
            case "help":
                return "prints this message";
            case "info":
                return "prints information about the stored collection";
            case "show":
                return "displays the contents of the collection";
            case "add":
                return "prompts for a new Person instance, and adds it to collection";
            case "update":
                return "prompts for a new Person instance, and replaces element of a specified id with it";
            case "remove_by_id":
                return "removes element of a specified id from the collection";
            case "clear":
                return "clears the collection";
            case "save":
                return "saves the collection to file";
            case "execute_script":
                return "executes commands read from a specified file";
            case "exit":
                return "quits the application (without saving)";
            case "add_if_max":
                return "prompts for a new Person instance, and adds it if it wins the comparison against every Person in collection";
            case "add_if_min":
                return "prompts for a new Person instance, and adds it if it loses the comparison against every Person in collection";
            case "shuffle":
                return "shuffles elements by changing their id";
            case "count_by_hair_color":
                return "counts how many elements with a specified hair color the collection contains";
            case "filter_less_than_location":
                return "prompts for a new Location instance, and prints all elements with a smaller Location";
            case "print_field_descending_nationality":
                return "prints the nationalities of the elements of the collection in descending order";
        }

        throw new UnsupportedOperationException("unexpected command key");
    }

    public static String getCommandHelpString() {
        String[] keys = new String[] {
            "help",
            "info",
            "show",
            "add",
            "update",
            "remove_by_id",
            "clear",
            "execute_script",
            "exit",
            "add_if_max",
            "add_if_min",
            "shuffle",
            "count_by_hair_color",
            "filter_less_than_location",
            "print_field_descending_nationality"
        };

        String value = "";
        for (String key : keys) {
            value += String.format(
                "%s: %s\n",
                key,
                getDescriptionFor(key)
            );
        }
        return value;
    }
}
