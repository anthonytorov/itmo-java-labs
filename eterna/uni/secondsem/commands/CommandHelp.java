package eterna.uni.secondsem.commands;

public class CommandHelp extends Command {

    @Override
    public String[] invoke() {
        return new String[] {
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
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
}
