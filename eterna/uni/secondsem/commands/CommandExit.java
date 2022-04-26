package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandExit extends Command {

    @Override
    public ServerResponse invoke() { ServerInitializer.exit(); return new ServerResponseMessage("Shutting down..."); }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
}
