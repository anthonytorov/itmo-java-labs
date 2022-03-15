package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.LogPrinter;

public class CommandExecuteScript extends Command {

    private final String PATH;
    private static int executingScripts = 0;

    public CommandExecuteScript(String[] args) {
        super(args);
        if (args.length < 2) throw new IllegalArgumentException("Must enter a path for the script file!");
        PATH = args[1];
    }

    @Override
    public void invoke(AppManager appManager) {
        if (executingScripts == 1) throw new StackOverflowError("Can't execute more than one script!");
        executingScripts++;
        if (!appManager.siphonInputFromFile(PATH)) {
            LogPrinter.logPathError();
        }
        executingScripts--;
    }
}
