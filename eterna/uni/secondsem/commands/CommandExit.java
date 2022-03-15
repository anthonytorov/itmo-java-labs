package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;

public class CommandExit extends Command {

    public CommandExit(String[] args) {
        super(args);
    }

    @Override
    public void invoke(AppManager appManager) {
        appManager.quit();
    }
}
