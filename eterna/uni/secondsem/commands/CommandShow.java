package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandShow extends Command {

    @Override
    public Person[] invoke() {
        return ServerInitializer.getCollectionManager().get_list().toArray(new Person[0]);
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
}
