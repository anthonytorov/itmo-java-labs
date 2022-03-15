package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.LogPrinter;

public class CommandRemove extends Command {

    private final Integer ID;

    public CommandRemove(String[] args) {
        super(args);
        if (args.length < 2) throw new NullPointerException("No ID entered!");
        ID = Integer.parseInt(args[1]);
    }

    @Override
    public void invoke(AppManager appManager) {
        if (appManager.collectionManager.remove(ID)) {
            LogPrinter.log("Removed entry with id " + ID);
        } else {
            LogPrinter.log("Entry with ID " + ID + " is missing from the collection");
        }
    }
}
