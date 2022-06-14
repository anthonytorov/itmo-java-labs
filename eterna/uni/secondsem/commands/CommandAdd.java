package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.FailedToInvokeCommandException;
import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandAdd extends Command {

    private final Person person;

    public CommandAdd(Person _person) { 
        person = _person;
    }

    @Override
    public Boolean invoke() {
        try {
            ServerInitializer.getCollectionManager().add(person);
            return true;
        } catch (FailedToInvokeCommandException ex) {
            return false;
        }
    }

    public static Class<?>[] getConstuctorClasses() { return new Class<?>[] { Person.class }; }
}
