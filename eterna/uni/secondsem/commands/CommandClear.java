package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;

public class CommandClear extends Command {

    @Override
    public String getKey() {
        return "clear";
    }

    @Override
    public void invoke(AppManager appManager) {
        appManager.collectionManager.clear();
    }

    @Override
    public void configure(String[] arguments) {}
    
}
