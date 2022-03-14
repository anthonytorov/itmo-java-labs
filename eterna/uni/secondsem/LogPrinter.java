package eterna.uni.secondsem;

public class LogPrinter {
    public static void log(String message) {
        System.out.println(message);
    }

    public static void logException(Exception exception) {
        System.out.println(String.format("Encountered exception: %s", exception.toString()));
    }

    public static void logEnterId() { log("please enter an id!"); }
    public static void logIdError() { log("please enter an integer id from the collection!"); }
    public static void logEnterPath() { log("please enter a path!"); }
    public static void logPathError() { log("please enter a valid path!"); }

    public static String getDescriptionFor(String commandKey) throws IllegalArgumentException {
        switch (commandKey) {
            case "exit":
                return "quits the application (without saving)";
            case "help":
                return "prints this message";
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

        throw new IllegalArgumentException("unexpected command key");
    }
}
