package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.FailedToInvokeCommandException;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandShuffle extends Command {

    @Override
    public Boolean invoke() {
        try {
            ServerInitializer.getCollectionManager().shuffle();
            return (Boolean)true;
        } catch (FailedToInvokeCommandException ex) {
            ex.printStackTrace();
            return (Boolean)false;
        }
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
}
