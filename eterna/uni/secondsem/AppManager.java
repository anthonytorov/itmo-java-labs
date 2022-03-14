package eterna.uni.secondsem;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import eterna.uni.secondsem.commands.*;

public class AppManager {

    private static AppManager instance;

    public CollectionManager collectionManager;
    public InputManager input;

    public boolean applicationRunning;
    
    public static void start(String filepath) {
        if (instance != null) return;
        instance = new AppManager(filepath);
        instance.run();
    }

    private AppManager(String filepath) {
        collectionManager = new CollectionManager(filepath);
        collectionManager.load();
        LogPrinter.log(String.format("Loaded %d elements from file.", collectionManager.get_list().size()));

        input = new InputManager();
        input.registerCommand(new CommandHelp());
        input.registerCommand(new CommandInfo());
        input.registerCommand(new CommandShow());
        input.registerCommand(new CommandAdd());
        input.registerCommand(new CommandUpdate());
        input.registerCommand(new CommandRemove());
        input.registerCommand(new CommandClear());
        input.registerCommand(new CommandSave());
        input.registerCommand(new CommandExecuteScript());
        input.registerCommand(new CommandExit());
        input.registerCommand(new CommandAddIfMax());
        input.registerCommand(new CommandAddIfMin());
        input.registerCommand(new CommandShuffle());
        input.registerCommand(new CommandCountByHairColor());
        input.registerCommand(new CommandFilterLessThanLocation());
        input.registerCommand(new CommandPrintFieldDescendingNationality());
    }

    private void run() {
        applicationRunning = true;
        while (applicationRunning) {
            processCommand();
        }
        LogPrinter.log("Quitting application...");
    }

    private boolean processCommand() {
        
        Command command = input.readCommandFromConsole();
        if (command == null) return true;
        
        command.invoke(this);

        return true;
    }

    public boolean siphonInputFromFile(String filepath) {
        Scanner inputScanner = AppManager.tryScanFile(filepath);
        if (inputScanner == null) return false;

        while (inputScanner.hasNextLine()) {
            Command command = input.convertLineToCommand(inputScanner.nextLine());
            try {
                command.invoke(this);
            } catch (Exception ex) {
                LogPrinter.logException(ex);
                break;
            }
        }

        return true;
    }

    public static Scanner tryScanFile(String path) {
        try {
            File dbfile = new File(path);
            if (!dbfile.exists()) {
                dbfile = new File(instance.collectionManager.getAbsoluteCollectionDirectory() + "\\" + path);
                if (!dbfile.exists()) {
                    dbfile.createNewFile();
                }
            }
            return new Scanner(dbfile);
        } catch (IOException ioex) {
            LogPrinter.log("An I/O exception occured while reading " + path);
        } catch (SecurityException securityException) {
            LogPrinter.log("A security exception occurred while writing to " + path);
        }

        return null;
    }

    public static PrintWriter tryCreateWriter(String path) {
        try {
            File dbfile = new File(path);
            if (!dbfile.exists()) {
                dbfile.createNewFile();
            }
            return new PrintWriter(dbfile);
        } catch (IOException ioex) {
            LogPrinter.log("An I/O exception occured while reading " + path);
        } catch (SecurityException securityException) {
            LogPrinter.log("A security exception occurred while writing to " + path);
        }
        return null;
    }
}
