package eterna.uni.secondsem.commands;

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

        if (executingScripts == 1) throw new StackOverflowError("Can't execute more than one script!");
        executingScripts++;
        success = ServerInitializer.getCommandInvoker().siphonInputFromFile(scriptPath);
        executingScripts--;
        
        return new ServerResponseMessage(success ? "Successfully executed script" : "Failed to execute script");
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { String.class };
    }
}
