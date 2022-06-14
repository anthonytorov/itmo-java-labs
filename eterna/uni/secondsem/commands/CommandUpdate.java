package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.FailedToInvokeCommandException;
import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandUpdate extends Command {

    private final Integer id;
    private final Person person;

    public CommandUpdate(Integer _id, Person _person) {
        id = _id;
        person = _person;
    }

    @Override
    public Integer invoke() {
        try {
            if (ServerInitializer.getCollectionManager().replace(id, person)) {
                return id;
            } else {
                return new Integer(0);
            }
        } catch (FailedToInvokeCommandException ex) {
            return new Integer(-1);
        }
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { Integer.class };
    }
}
