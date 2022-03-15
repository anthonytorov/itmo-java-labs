package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.ConsolePromptPerson;
import eterna.uni.secondsem.LogPrinter;

public class CommandUpdate extends Command {

    private final Integer ID;

    public CommandUpdate(String[] args) {
        super(args);
        if (args.length < 2) throw new NullPointerException("No ID entered!");
        ID = Integer.parseInt(args[1]);
    }

    @Override
    public void invoke(AppManager appManager) {
        new ConsolePromptPerson(appManager.collectionManager.fetch(ID));
        LogPrinter.log("Updated entry with id " + ID);
    }
}
