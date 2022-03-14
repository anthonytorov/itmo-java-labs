package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.LogPrinter;

public class CommandHelp extends Command {

    @Override
    public String getKey() {
        return "help";
    }

    @Override
    public void invoke(AppManager appManager) {
        
        for (Command command : appManager.input.get_commandMap().values()) {
            printHelpAbout(command);
        }
    }

    public static void printHelpAbout(Command command) {
        String key = command.getKey();

        try {
            LogPrinter.log(String.format("%s: %s", key, LogPrinter.getDescriptionFor(key)));
        } catch (IllegalArgumentException iaex) {}
    }

    @Override
    public void configure(String[] arguments) {}
}
