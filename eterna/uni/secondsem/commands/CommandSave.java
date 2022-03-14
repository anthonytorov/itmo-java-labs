package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.LogPrinter;

public class CommandSave extends Command {
    @Override
    public String getKey() {
        return "save";
    }

    @Override
    public void invoke(AppManager appManager) {
        appManager.collectionManager.save();
        LogPrinter.log("Saved collection");
    }

    @Override
    public void configure(String[] arguments) {}
}
