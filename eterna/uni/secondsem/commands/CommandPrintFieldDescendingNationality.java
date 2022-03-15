package eterna.uni.secondsem.commands;

import java.util.Comparator;
import java.util.LinkedList;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.Person;

public class CommandPrintFieldDescendingNationality extends Command {

    public CommandPrintFieldDescendingNationality(String[] args) {
        super(args);
    }

    @Override
    public void invoke(AppManager appManager) {
        LinkedList<Person> list = appManager.collectionManager.get_list();
        
        PersonByCountryComparator comparator = new PersonByCountryComparator();
        list.sort(comparator);
        for (Person p : list) {
            LogPrinter.log(p.get_nationality().name());
        }

        list.sort(null);
    }

    private class PersonByCountryComparator implements Comparator<Person> {

        @Override
        public int compare(Person o1, Person o2) {
            return o1.get_nationality().name().compareTo(o2.get_nationality().name());
        }
        
    }
}
