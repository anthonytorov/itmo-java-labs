package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;

public class CommandExit extends Command {
    @Override
    public void invoke(AppManager appManager) {
        appManager.applicationRunning = false;
    }
    @Override
    public String getKey() { return "exit"; }
    @Override
    public void configure(String[] arguments) {}
}
