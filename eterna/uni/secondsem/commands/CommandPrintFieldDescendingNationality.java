package eterna.uni.secondsem.commands;

import java.util.Comparator;

import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandPrintFieldDescendingNationality extends Command implements Comparator<Person> {

    @Override
    public String[] invoke() {
        String[] output = ServerInitializer.getCollectionManager().get_list()
                                                .stream()
                                                .sorted(this)
                                                .map(p -> p.get_nationality().name())
                                                .toArray(String[]::new);
        return output;
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
    
    @Override
    public int compare(Person o1, Person o2) {
        return o1.get_nationality().name().compareTo(o2.get_nationality().name());
    }
}
