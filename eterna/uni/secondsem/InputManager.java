package eterna.uni.secondsem;

import java.util.TreeMap;

import eterna.uni.secondsem.commands.Command;

public class InputManager {

    private TreeMap<String, Command> commandMap;
    public TreeMap<String, Command> get_commandMap() { return commandMap; }

    public InputManager() {
        commandMap = new TreeMap<String, Command>();
    }
    
    public Command readCommandFromConsole() {

        return convertLineToCommand(readInput());
    }

    public void registerCommand(Command cmd) {
        commandMap.put(cmd.getKey(), cmd);
    }

    public Command convertLineToCommand(String line) {
        String[] arguments = line.split(" ");

        if (arguments.length < 1) return null;

        if (!commandMap.containsKey(arguments[0])) return null;

        Command command = commandMap.get(arguments[0]);
        command.configure(arguments);
        return command;
    }

    private String readInput() {
        return System.console().readLine();
    } 
}
