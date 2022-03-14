package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.ConsolePromptPerson;
import eterna.uni.secondsem.LogPrinter;

public class CommandUpdate extends Command {

    private Integer id;

    @Override
    public String getKey() {
        return "update";
    }

    @Override
    public void invoke(AppManager appManager) {
        new ConsolePromptPerson(appManager.collectionManager.fetch(id));
        LogPrinter.log("Updated entry with id " + id);
    }

    @Override
    public void configure(String[] arguments) {
        if (arguments.length < 2) LogPrinter.logEnterId();

        try {
            id = Integer.parseInt(arguments[1]); 
        } catch (NumberFormatException nfex) {
            LogPrinter.logIdError();
        }
    }
    
}
