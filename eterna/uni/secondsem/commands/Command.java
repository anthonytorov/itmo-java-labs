package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;

public abstract class Command {
    public abstract void invoke(AppManager appManager);
    public abstract void configure(String[] arguments);
    public abstract String getKey();
}
