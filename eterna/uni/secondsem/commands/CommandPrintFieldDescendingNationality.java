package eterna.uni.secondsem.commands;

import java.util.Comparator;
import java.util.stream.Collectors;

import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandPrintFieldDescendingNationality extends Command implements Comparator<Person> {

    @Override
    public ServerResponse invoke() {
        String output = ServerInitializer.getCollectionManager().get_list()
                                                .stream()
                                                .sorted(this)
                                                .map(p -> p.get_nationality().name())
                                                .collect(Collectors.joining("\n", "Collection (sorted descending by nationality (yikes)) :", "\n"));
        return new ServerResponseMessage(output);
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
    
    @Override
    public int compare(Person o1, Person o2) {
        return o1.get_nationality().name().compareTo(o2.get_nationality().name());
    }
}
