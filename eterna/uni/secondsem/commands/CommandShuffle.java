package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;

public class CommandShuffle extends Command {

    public CommandShuffle(String[] args) {
        super(args);
    }

    @Override
    public void invoke(AppManager appManager) {
        appManager.collectionManager.shuffle();
    }
}
