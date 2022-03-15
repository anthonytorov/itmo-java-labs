package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;

/**
 * The parent class for all commands.
 */
public abstract class Command {
    public Command(String[] args) {
        
    }
    /**
     * Method executing the command.
     * @param appManager the AppManager instance that invoked the command
     */
    public abstract void invoke(AppManager appManager);
}
