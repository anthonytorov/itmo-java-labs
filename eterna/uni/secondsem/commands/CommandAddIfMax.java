package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.ConsolePromptPerson;
import eterna.uni.secondsem.LogPrinter;

public class CommandAddIfMax extends Command {

    @Override
    public void invoke(AppManager appManager) {
        ConsolePromptPerson prompt = new ConsolePromptPerson(null);
        int comparison = appManager.collectionManager.compareToCollectionBounds(prompt.person);
        if (comparison > 0) {
            appManager.collectionManager.add(prompt.person);
            LogPrinter.log("Successfully added the new person to the collection");
        } else {
            LogPrinter.log("Failed to add person to the collection");
        }
    }

    @Override
    public void configure(String[] arguments) {}

    @Override
    public String getKey() {
        return "add_if_max";
    }
    
}