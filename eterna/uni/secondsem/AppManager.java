package eterna.uni.secondsem;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class AppManager {

    private static CollectionManager collection;
    
    public static void start(String filepath) {
        collection = new CollectionManager(filepath);
        collection.load();

        while (processCommand(System.console().readLine().split(" ")));

        Logger.log("Quitting application...");
    }

    private static boolean processCommand(String[] cmdArguments) {

        if (cmdArguments.length == 0) {
            Logger.log("Please enter command!");
            return true;
        }

        switch (cmdArguments[0]) {
            default:
                Logger.log("Unknown command...");
                break;
            case "help":
                Logger.logCommandsHelp();
                break;
            case "info":
                Logger.log(collection.getInformationString());
                break;
            case "show":
                collection.show();
                break;
            case "add": 
                collection.add(promptPersonInConsole());
                Logger.log("Added a new entry to collection.");
                break;
            case "update":
                if (cmdArguments.length > 1) {
                    try {
                        Integer id = new Integer(cmdArguments[1]);
                        if (id <= 0) throw new NumberFormatException();

                        collection.update(id, promptPersonInConsole());   
                        Logger.log("Updated entry " + id);
                    }
                    catch (NumberFormatException nfex) {
                        Logger.log("Please enter a valid id integer!");
                    }
                } else {
                    Logger.log("Please enter id of the entry you'd like to update!");
                }
                break;
            case "remove_by_id":
                if (cmdArguments.length > 1) {
                    try {
                        Integer id = new Integer(cmdArguments[1]);
                        if (id <= 0) throw new NumberFormatException();

                        collection.remove(id);
                        Logger.log("Removed entry " + id + " from collection.");
                    }
                    catch (NumberFormatException nfex) {
                        Logger.log("Please enter a valid id integer!");
                    }
                } else {
                    Logger.log("Please enter id of the entry you'd like to update!");
                }

                break;
            case "clear":
                collection.clear();
                Logger.log("Emptied the loaded collection.");
                break;
            case "save":
                collection.save();
                Logger.log("Saved collection to file");
                break;
            case "exit":
                return false;
            case "add_if_max":
                Person person = promptPersonInConsole();
                if (collection.compareToCollectionBounds(person) > 0) {
                    collection.add(person);
                }
                break;
            case "add_if_min":
                person = promptPersonInConsole();
                if (collection.compareToCollectionBounds(person) < 0) {
                    collection.add(person);
                }
                break;
        }
        return true;
    }

    private static Person promptPersonInConsole() {
        Logger.log("Assembling instance of Person:\nEnter name: ");
        String _name = System.console().readLine();
        
        return new Person(_name);
    } 

    public static Scanner tryScanFile(String path) {
        try {
            File dbfile = new File(path);
            if (dbfile.exists()) {
                return new Scanner(dbfile);        
            } else {
                dbfile.createNewFile();
                return new Scanner(dbfile);
            }
        } catch (IOException ioex) {
            Logger.log("An I/O exception occured.");
        }

        return null;
    }

    public static PrintWriter tryCreateWriter(String path) {
        try {
            File dbfile = new File(path);
            if (dbfile.exists()) {
                return new PrintWriter(dbfile);        
            } else {
                dbfile.createNewFile();
                return new PrintWriter(dbfile);
            }
        } catch (IOException ioex) {
            Logger.log("An I/O exception occured.");
        }
        return null;
    }
}
