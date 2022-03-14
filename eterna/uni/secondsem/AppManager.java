package eterna.uni.secondsem;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import eterna.uni.secondsem.commands.*;

/**
 * The main class responsible for the application and command handling.
 */
public class AppManager {

    private static AppManager instance;

    public CollectionManager collectionManager;
    public InputManager input;

    public boolean applicationRunning;
    
    /**
     * Starts the application. If it's already started, does nothing.
     * @param filepath path to the csv database file
     */
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

    /**
     * This function starts the main application loop, and returns only when the application has been aborted.
     */
    private void run() {
        applicationRunning = true;
        while (applicationRunning) {
            Command command = input.readCommandFromConsole();
            if (command != null) {
                command.invoke(this);
            }
        }
        LogPrinter.log("Quitting application...");
    }

    /**
     * This function reads a set of instructions from a file, and executes them.
     * @param filepath path to the script text file.
     * @return true if the script was successfully executed, otherwise false.  
     */
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

    /**
     * Attempts to create a Scanner for a file.
     * @param path path to the file being scanned (must exist)
     * @return a Scanner instance if successful, otherwise null 
     */
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

    /**
     * Attempts to create a PrintWriter for a file.
     * @param path file being written to - if it exists, it is overwritten
     * @return a PrintWriter instance if successful, otherwise null
     */
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
