package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;

/**
 * The parent class for all commands.
 */
public abstract class Command {
    /**
     * Method executing the command.
     * @param appManager the AppManager instance that invoked the command
     */
    public abstract void invoke(AppManager appManager);
    /**
     * Method configuring the command based on command line arguments. 
     * @param arguments split by whitespace arguments from the command line
     */
    public abstract void configure(String[] arguments);
    /**
     * Function returning the key by which the function should be called.
     * @return the function key
     */
    public abstract String getKey();
}
