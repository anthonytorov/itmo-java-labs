package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.LogPrinter;

public class CommandExecuteScript extends Command {

    private String path;

    @Override
    public void invoke(AppManager appManager) {
        if (!appManager.siphonInputFromFile(path)) {
            LogPrinter.logPathError();
        }
    }

    @Override
    public String getKey() {
        return "execute_script";
    }

    @Override
    public void configure(String[] arguments) {
        if (arguments.length < 2) {
            LogPrinter.logEnterPath();
            throw new UnsupportedOperationException("Enter a path for the script file!");
        }
    }
    
}
