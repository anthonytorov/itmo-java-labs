package eterna.uni.secondsem.commands;

import java.io.Serializable;

/**
 * The parent class for all commands.
 */
public abstract class Command implements Serializable {
    /**
     * Method executing the command.
     * @param collectionManager the CollectionManager instance managing the currently loaded collection
     */
    public abstract Serializable invoke();
    public static Class<?>[] getConstuctorClasses() { return new Class<?>[0]; }
}
