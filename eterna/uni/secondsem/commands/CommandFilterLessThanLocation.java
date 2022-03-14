package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.ConsolePromptLocation;
import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.Person;

public class CommandFilterLessThanLocation extends Command {

    @Override
    public void invoke(AppManager appManager) {
        ConsolePromptLocation prompt = new ConsolePromptLocation(null);
        
        for (Person p : appManager.collectionManager.get_list()) {
            if (p.get_location().compareTo(prompt.location) < 0) {
                LogPrinter.log(p.toString());
            }
        }
    }

    @Override
    public void configure(String[] arguments) {}

    @Override
    public String getKey() {
        return "filter_less_than_location";
    }
    
}
