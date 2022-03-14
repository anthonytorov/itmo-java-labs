package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;

public class CommandShuffle extends Command {

    @Override
    public void invoke(AppManager appManager) {
        appManager.collectionManager.shuffle();
    }

    @Override
    public void configure(String[] arguments) {}

    @Override
    public String getKey() {
        return "shuffle";
    }
    
}
