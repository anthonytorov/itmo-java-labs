package eterna.uni.secondsem.commands;

import java.io.Serializable;

public class CommandExit extends Command {

    @Override
    public Serializable invoke() { return null; }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
}
