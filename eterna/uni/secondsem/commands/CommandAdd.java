package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.ConsolePromptPerson;
import eterna.uni.secondsem.LogPrinter;

public class CommandAdd extends Command {

    public CommandAdd(String[] args) { super(args); }

    @Override
    public void invoke(AppManager appManager) {
        ConsolePromptPerson personPrompt = new ConsolePromptPerson(null);
        appManager.collectionManager.add(personPrompt.person);
        LogPrinter.log("Added the new Person to the collection");
    }
    
}
