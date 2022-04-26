package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.networking.ServerResponse;

public class CommandExit extends Command {

    @Override
    public ServerResponse invoke() { return null; }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
}
