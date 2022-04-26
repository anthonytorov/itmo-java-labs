package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.server.CollectionManager;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandAddIfMax extends Command {

    private final Person person;

    public CommandAddIfMax(Person _person) { 
        person = _person;
    }

    @Override
    public ServerResponse invoke() {
        CollectionManager collectionManager = ServerInitializer.getCollectionManager();
        int comparison = collectionManager.compareToCollectionBounds(person);
        if (comparison > 0) {
            collectionManager.add(person);
        }
        return new ServerResponse_CommandAddIfMax(comparison > 0);
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { Person.class };
    }

    public class ServerResponse_CommandAddIfMax implements ServerResponse {

        private final boolean success;

        public ServerResponse_CommandAddIfMax(boolean _success) {
            success = _success;
        }
        
        public void execute() {
            if (success) {
                LogPrinter.log("Successfully added the new person to the collection");
            } else {
                LogPrinter.log("Failed to add person to the collection");
            }
        }
    }
}
