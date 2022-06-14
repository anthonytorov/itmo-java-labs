package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.FailedToInvokeCommandException;
import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.server.CollectionManager;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandAddIfMin extends Command {

    private final Person person;

    public CommandAddIfMin(Person _person) { 
        person = _person;
    }

    @Override
    public Boolean invoke() {
        CollectionManager collectionManager = ServerInitializer.getCollectionManager();
        int comparison = collectionManager.compareToCollectionBounds(person);
        if (comparison < 0) {
            try {
                collectionManager.add(person);
                return true;
            } catch (FailedToInvokeCommandException ex) {
                return false;
            }
        }
        return false;
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { Person.class };
    }
}
