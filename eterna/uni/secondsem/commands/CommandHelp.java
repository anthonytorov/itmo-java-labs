package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;

public class CommandHelp extends Command {

    @Override
    public ServerResponse invoke() {
        return new ServerResponseMessage(LogPrinter.getCommandHelpString());
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }
}
