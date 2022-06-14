package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.FailedToInvokeCommandException;
import eterna.uni.secondsem.server.CollectionManager;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandClear extends Command {

    @Override
    public Boolean invoke() {
        CollectionManager collectionManager = ServerInitializer.getCollectionManager();
        try {
            collectionManager.clear();
            return true;
        } catch (FailedToInvokeCommandException ex) {
            return false;
        }
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
}
