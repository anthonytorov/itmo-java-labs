package eterna.uni.secondsem;

import eterna.uni.secondsem.client.ClientInitializer;
import eterna.uni.secondsem.server.ServerInitializer;

public class Lab6_Launcher {
    public static void main(String[] args) {

        if (args.length < 1) {
            LogPrinter.log("Please specify the configuration! (server/client)");
            return;
        } 

        boolean serverMode = args[0].startsWith("s");
        boolean clientMode = args[0].startsWith("c");

        if (serverMode == clientMode) {
            LogPrinter.log("Unidentifed mode! Aborting...");
            return;
        }

        LogPrinter.log(String.format("Lab6 starting in %s mode", (serverMode ? "server" : "client")));
        if (serverMode) {
            if (args.length > 1) {
                ServerInitializer.start(args[1]);
            } else {
                LogPrinter.log("Please enter the database path!");
            }
        } else {
            ClientInitializer.start();
        }
    }
}
