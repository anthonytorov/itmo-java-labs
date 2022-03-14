package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.LogPrinter;

public class CommandRemove extends Command {

    private Integer id;

    @Override
    public String getKey() {
        return "remove_by_id";
    }

    @Override
    public void invoke(AppManager appManager) {
        appManager.collectionManager.remove(id);
        LogPrinter.log("Removed entry with id " + id);
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
