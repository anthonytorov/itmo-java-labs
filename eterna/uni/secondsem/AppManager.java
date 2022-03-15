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
    public CommandGenerator commandGenerator;

    private boolean applicationRunning;
    private ConsolePrompter consolePrompter;
    private FilePrompter filePrompter;

    public static Prompter getTopPrompter() { return (instance.filePrompter == null) ? instance.consolePrompter : instance.filePrompter; }
    
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

        consolePrompter = new ConsolePrompter();

        commandGenerator = new CommandGenerator();
        commandGenerator.registerCommand("help", CommandHelp.class);
        commandGenerator.registerCommand("info", CommandInfo.class);
        commandGenerator.registerCommand("show", CommandShow.class);
        commandGenerator.registerCommand("add", CommandAdd.class);
        commandGenerator.registerCommand("update", CommandUpdate.class);
        commandGenerator.registerCommand("remove_by_id", CommandRemove.class);
        commandGenerator.registerCommand("clear", CommandClear.class);
        commandGenerator.registerCommand("save", CommandSave.class);
        commandGenerator.registerCommand("execute_script", CommandExecuteScript.class);
        commandGenerator.registerCommand("exit", CommandExit.class);
        commandGenerator.registerCommand("add_if_max", CommandAddIfMax.class);
        commandGenerator.registerCommand("add_if_min", CommandAddIfMin.class);
        commandGenerator.registerCommand("shuffle", CommandShuffle.class);
        commandGenerator.registerCommand("count_by_hair_color", CommandCountByHairColor.class);
        commandGenerator.registerCommand("filter_less_than_location", CommandFilterLessThanLocation.class);
        commandGenerator.registerCommand("print_field_descending_nationality", CommandPrintFieldDescendingNationality.class);
    }

    /**
     * This function starts the main application loop, and returns only when the application has been aborted.
     */
    private void run() {
        applicationRunning = true;
        while (applicationRunning) {
            try {        
                Command command = commandGenerator.generate(System.console().readLine().split(" "));
                command.invoke(this);
            } catch (NullPointerException npex) {
                LogPrinter.log("Could not recognise command!");
                LogPrinter.logException(npex);
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
        Scanner inputScanner = AppManager.tryScanFile(filepath, false);
        if (inputScanner == null) return false;

        filePrompter = new FilePrompter(inputScanner);

        while (inputScanner.hasNextLine()) {
            Command command = commandGenerator.generate(inputScanner.nextLine().split(" "));
            try {
                command.invoke(this);
            } catch (Exception ex) {
                LogPrinter.logException(ex);
                break;
            }
        }

        filePrompter = null;

        return true;
    }

    /**
     * Attempts to create a Scanner for a file.
     * @param path path to the file being scanned (must exist)
     * @return a Scanner instance if successful, otherwise null 
     */
    public static Scanner tryScanFile(String path, boolean createIfDoesntExist) {
        try {
            File dbfile = new File(path);
            if (!dbfile.exists() && createIfDoesntExist) {
                dbfile.createNewFile();
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

    public void quit() {
        applicationRunning = false;
    }
}
