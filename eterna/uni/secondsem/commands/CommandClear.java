package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;

public class CommandClear extends Command {

    public CommandClear(String[] args) {
        super(args);
    }

    @Override
    public void invoke(AppManager appManager) {
        appManager.collectionManager.clear();
    }
}
