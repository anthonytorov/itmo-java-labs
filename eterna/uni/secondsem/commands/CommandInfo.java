package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;
import eterna.uni.secondsem.server.CollectionManager;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandInfo extends Command {

    @Override
    public ServerResponse invoke() {
        CollectionManager collectionManager = ServerInitializer.getCollectionManager();
        return new ServerResponseMessage(String.join("\n",
            "Collection type : " + collectionManager.get_list().getClass().toString(),
            "Initialization date : " + collectionManager.get_initializationDate().toString(),
            "Size : " + collectionManager.get_list().size()
        ));
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
}
