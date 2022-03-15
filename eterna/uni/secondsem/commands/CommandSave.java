package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.LogPrinter;

public class CommandSave extends Command {
    public CommandSave(String[] args) {
        super(args);
    }

    @Override
    public void invoke(AppManager appManager) {
        appManager.collectionManager.save();
        LogPrinter.log("Saved collection");
    }
}
