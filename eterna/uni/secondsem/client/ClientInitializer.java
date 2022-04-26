package eterna.uni.secondsem.client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import eterna.uni.secondsem.CommandReader;
import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.commands.Command;
import eterna.uni.secondsem.commands.CommandExit;
import eterna.uni.secondsem.commands.CommandSave;
import eterna.uni.secondsem.networking.NetworkObjectExchanger;
import eterna.uni.secondsem.networking.NetworkSettings;
import eterna.uni.secondsem.networking.ServerResponse;

public class ClientInitializer {
    private static ClientInitializer instance;

    public static ClientInitializer get_instance() {
        return instance;
    }

    private final CommandReader commandReader;
    private boolean run;

    public static void start() { 
        instance = new ClientInitializer();
        instance.executionLoop(); 
    }

    private ClientInitializer() {
        
        try {
            NetworkSettings.initialize_localhost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }  

        commandReader = new CommandReader(System.in, true);
    }

    private void executionLoop() {

        run = true;
        LogPrinter.log("Attempting connection to " + NetworkSettings.get_address() + ":" + NetworkSettings.get_port());

        Socket socket = attemptConnection(0);
        if (socket == null) {
            LogPrinter.log("Failed to connect.");
            return;
        }
        NetworkObjectExchanger noe = new NetworkObjectExchanger(socket);
        LogPrinter.log("Connection successful.");

        while (run && commandReader.canReadNextCommand()) {

            Command command = commandReader.readCommand();

            if (command == null || command.getClass() == CommandSave.class) {
                LogPrinter.log("Command not recognised. Use \"help\" to see available commands.");
            } else if (command.getClass() == CommandExit.class) {
                run = false;
            } else {
                noe.pushObject(command);
                ((ServerResponse)noe.readResponse()).execute();
            }
        }

        noe.closeSocket();
    }

    private Socket attemptConnection(int height) {
        
        if (height == 10) return null;
        
        try {
            return new Socket(NetworkSettings.get_address(), NetworkSettings.get_port());
        } catch (ConnectException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return attemptConnection(height + 1);
    }
}
