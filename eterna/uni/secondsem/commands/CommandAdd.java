package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandAdd extends Command {

    private final Person person;

    public CommandAdd(Person _person) { 
        person = _person;
    }

    @Override
    public ServerResponse invoke() {
        ServerInitializer.getCollectionManager().add(person);
        return new ServerResponseMessage("Added the new Person to the collection");
    }

    public static Class<?>[] getConstuctorClasses() { return new Class<?>[] { Person.class }; }
}
