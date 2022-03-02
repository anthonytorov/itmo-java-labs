package eterna.uni.secondsem;

public class Logger {
    public static void log(String message) {
        System.out.println(message);
    }

    public static void logCommandsHelp() {
        System.out.print(String.join("\n",
            "help : prints this message",
            "info : prints the collection's type, init date and size",
            "show : prints database's contents",
            "add {element} : prompts user for a new instance of Person, and adds it to the collection",
            "update id {element} : prompts user for a new instance of Person, and replaces item of id with it",
            "remove_by_id id : removes item with id from the list",
            "clear : empties the loaded collection",
            "save : saves loaded collection",
            "exit : quits without saving",
            "add_if_max {element} : prompts user for a new instance of Person, and adds it to the collection if it's larger than any element in it",
            "add_if_min {element} : prompts user for a new instance of Person, and adds it to the collection if it's smaller than any element in it",
            ""
        ));
    }
}
