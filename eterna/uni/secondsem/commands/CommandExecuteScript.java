package eterna.uni.secondsem.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import eterna.uni.secondsem.CommandReader;
import eterna.uni.secondsem.server.FileUtils;

public class CommandExecuteScript extends Command {

    private final String scriptPath;
    private static int executingScripts = 0;

    public CommandExecuteScript(String pathToScript) {
        scriptPath = pathToScript;
    }

    @Override
    public Response invoke() {

        if (executingScripts == 1) throw new StackOverflowError("Can't execute more than one script!");
        
        executingScripts++;
        Response response = siphonInputFromFile(scriptPath);
        executingScripts--;
        return response;
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { String.class };
    }

    /**
     * This function reads a set of instructions from a file, and executes them.
     * @param filepath path to the script text file.
     * @return true if the script was successfully executed, otherwise false.  
     */
    public Response siphonInputFromFile(String filepath) {

        LinkedList<Class<? extends Command>> commandClasses = new LinkedList<Class<? extends Command>>();
        LinkedList<Object> responses = new LinkedList<Object>();

        try (FileInputStream inputStream = FileUtils.openFileInputStream(filepath, false)) {
            
            CommandReader fileCommandReader = new CommandReader(inputStream, false);

            while (fileCommandReader.canReadNextCommand()) {
                Command command = fileCommandReader.readCommand();
                
                if (command != null) {
                    commandClasses.add(command.getClass());
                    responses.add(command.invoke());
                }
            }

            return new Response((Class<? extends Command>[])commandClasses.toArray(), responses.toArray());
        } catch (IOException ioex) {
            return new Response();
        }
    }

    public class Response implements Serializable {
        public final Class<? extends Command>[] commandClasses;
        public final Object[] responses;
        public final boolean success;

        public Response(Class<? extends Command>[] commandClasses, Object[] responses) {
            this.commandClasses = commandClasses;
            this.responses = responses;
            this.success = false;
        }
        public Response() {
            commandClasses = null;
            responses = null;
            success = false;
        }
    }
}
