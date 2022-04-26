package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;
import eterna.uni.secondsem.server.CollectionManager;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandClear extends Command {

    @Override
    public ServerResponse invoke() {
        CollectionManager collectionManager = ServerInitializer.getCollectionManager();
        collectionManager.clear();

        return new ServerResponseMessage("Successfully cleared collection.");
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
}
