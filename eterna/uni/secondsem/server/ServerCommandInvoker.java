package eterna.uni.secondsem.server;

import java.io.FileInputStream;
import java.io.IOException;

import eterna.uni.secondsem.CommandReader;
import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.commands.Command;
import eterna.uni.secondsem.networking.NetworkObjectExchanger;
import eterna.uni.secondsem.networking.ServerResponse;

public class ServerCommandInvoker {
    
    private final NetworkObjectExchanger noe;
    
    public ServerCommandInvoker(NetworkObjectExchanger _noe) {
        noe = _noe;
    }

    /**
     * This function reads a set of instructions from a file, and executes them.
     * @param filepath path to the script text file.
     * @return true if the script was successfully executed, otherwise false.  
     */
    public boolean siphonInputFromFile(String filepath) {

        try (FileInputStream inputStream = FileUtils.openFileInputStream(filepath, false)) {
            if (inputStream == null) return false;
            
            CommandReader fileCommandReader = new CommandReader(inputStream, false);

            while (fileCommandReader.canReadNextCommand()) {
                Command command = fileCommandReader.readCommand();
                
                if (command != null) {
                    ServerResponse response = command.invoke();
                    if (response != null)
                        noe.pushObject(response);
                }
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }

        return true;
    }

    public boolean executeNextClientCommand() {
        Command command = (Command)noe.readResponse();
        if (command == null) return false;

        LogPrinter.log("Received next client command: " + command.getClass().toString());

        ServerResponse response = command.invoke();
        if (response == null) return false;
        
        LogPrinter.log("Executed command, sending response.");
        
        noe.pushObject(response);
        return true;
    }
}
