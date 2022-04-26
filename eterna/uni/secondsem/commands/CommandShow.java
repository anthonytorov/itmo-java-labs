package eterna.uni.secondsem.commands;

import java.util.Collection;

import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandShow extends Command {

    @Override
    public ServerResponse invoke() {

        Collection<Person> list = ServerInitializer.getCollectionManager().get_list();

        if (list.size() == 0) {
            return new ServerResponseMessage("Collection is empty!");
        }

        String message = ("Collection contains " + list.size() + " items:\n");
        for (Person person : list) {
            message += (person.toString() + "\n");
        }

        return new ServerResponseMessage(message);
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
}
