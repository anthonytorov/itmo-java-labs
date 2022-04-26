package eterna.uni.secondsem.commands;

import java.io.IOException;

import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandExecuteScript extends Command {

    private final String scriptPath;
    private static int executingScripts = 0;

    public CommandExecuteScript(String pathToScript) {
        scriptPath = pathToScript;
    }

    @Override
    public ServerResponse invoke() {

        boolean success = false;
        String message = "Failed to execute script.";

        if (executingScripts == 1) throw new StackOverflowError("Can't execute more than one script!");
        
        try {
            executingScripts++;
            success = ServerInitializer.getCommandInvoker().siphonInputFromFile(scriptPath);
            if (success) message = "Successfully executed script";
            executingScripts--;
        } catch (IOException ioex) {
            success = false;
            message = ("Failed to execute script: " + ioex.getCause().toString());
        }

        return new ServerResponseMessage(message);
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { String.class };
    }
}
