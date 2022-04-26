package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandUpdate extends Command {

    private final Integer id;
    private final Person person;

    public CommandUpdate(Integer _id, Person _person) {
        id = _id;
        person = _person;
    }

    @Override
    public ServerResponse invoke() {
        if (ServerInitializer.getCollectionManager().replace(id, person)) {
            return new ServerResponseMessage("Updated entry with id " + id);
        } else {
            return new ServerResponseMessage("Entry with id " + id + " is missing from the collection.");
        }
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { Integer.class };
    }
}
