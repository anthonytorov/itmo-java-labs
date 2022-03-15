package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.LogPrinter;

public class CommandHelp extends Command {

    public CommandHelp(String[] args) {
        super(args);
    }

    @Override
    public void invoke(AppManager appManager) {
        for (String key : appManager.commandGenerator.get_commandMap().keySet()) {
            LogPrinter.log(String.format("%s: %s", key, LogPrinter.getDescriptionFor(key)));
        }
    }
}
