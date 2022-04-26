package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandShuffle extends Command {

    @Override
    public ServerResponse invoke() {
        ServerInitializer.getCollectionManager().shuffle();
        return new ServerResponseMessage("Shuffled collection.");
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
}
