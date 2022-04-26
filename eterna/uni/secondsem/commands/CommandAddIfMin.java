package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;
import eterna.uni.secondsem.server.CollectionManager;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandAddIfMin extends Command {

    private final Person person;

    public CommandAddIfMin(Person _person) { 
        person = _person;
    }

    @Override
    public ServerResponse invoke() {
        CollectionManager collectionManager = ServerInitializer.getCollectionManager();
        int comparison = collectionManager.compareToCollectionBounds(person);
        if (comparison < 0) {
            collectionManager.add(person);
            return new ServerResponseMessage("Successfully added the new person to the collection");
        } else {
            return new ServerResponseMessage("Failed to add person to the collection");
        }
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { Person.class };
    }
}
