package eterna.uni.secondsem.commands;

import java.util.Collection;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.Person;

public class CommandShow extends Command {

    @Override
    public String getKey() {
        return "show";
    }

    @Override
    public void invoke(AppManager appManager) {

        Collection<Person> list = appManager.collectionManager.get_list();

        if (list.size() == 0) {
            LogPrinter.log("Collection is empty!");
            return;
        }

        LogPrinter.log("Collection contains " + list.size() + " items:");
        for (Person person : list) {
            LogPrinter.log(person.toString());
        }
    }

    @Override
    public void configure(String[] arguments) {}
}
