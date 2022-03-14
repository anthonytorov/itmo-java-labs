package eterna.uni.secondsem;

import java.util.TreeMap;

import eterna.uni.secondsem.commands.Command;

/**
 * Class responsible for handling console input and reading commands.
 */
public class InputManager {

    private TreeMap<String, Command> commandMap;
    public TreeMap<String, Command> get_commandMap() { return commandMap; }

    public InputManager() {
        commandMap = new TreeMap<String, Command>();
    }
    
    /**
     * @return instance of Command from the console. may be null
     */
    public Command readCommandFromConsole() {
        return convertLineToCommand(System.console().readLine());
    }

    /**
     * Adds a command to the TreeMap of possibly recognised commands.
     * @param cmd the command to register
     */
    public void registerCommand(Command cmd) {
        commandMap.put(cmd.getKey(), cmd);
    }

    /**
     * @param line Console command in String form
     * @return Command instance configured using line arguments
     */
    public Command convertLineToCommand(String line) {
        String[] arguments = line.split(" ");

        if (arguments.length < 1) return null;

        if (!commandMap.containsKey(arguments[0])) return null;

        Command command = commandMap.get(arguments[0]);
        command.configure(arguments);
        return command;
    }
}
