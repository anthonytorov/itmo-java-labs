package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;
import eterna.uni.secondsem.server.CollectionManager;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandRemove extends Command {

    private final Integer id;

    public CommandRemove(Integer _id) {
        id = _id;
    }

    @Override
    public ServerResponse invoke() {
        CollectionManager collectionManager = ServerInitializer.getCollectionManager();
        if (collectionManager.remove(id)) {
            return new ServerResponseMessage("Removed entry with id " + id);
        } else {
            return new ServerResponseMessage("Entry with ID " + id + " is missing from the collection");
        }
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { Integer.class };
    }
}
