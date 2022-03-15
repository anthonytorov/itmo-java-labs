package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.CollectionManager;
import eterna.uni.secondsem.LogPrinter;

public class CommandInfo extends Command {

    public CommandInfo(String[] args) {
        super(args);
    }

    @Override
    public void invoke(AppManager appManager) {
        
        CollectionManager manager = appManager.collectionManager;
        LogPrinter.log(String.join("\n",
            "Collection type : " + manager.get_list().getClass().toString(),
            "Initialization date : " + manager.get_initializationDate().toString(),
            "Size : " + manager.get_list().size()
        ));
    }
}
