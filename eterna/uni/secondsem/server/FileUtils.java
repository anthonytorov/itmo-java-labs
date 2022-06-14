package eterna.uni.secondsem.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

import eterna.uni.secondsem.LogPrinter;

/**
 * Class with commands about reading files.
 */
public class FileUtils {

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
     * Attempts to create a FileInputStream for a file.
     * @param path path to the file being scanned
     * @return a Scanner instance if successful, otherwise null 
     */
    public static FileInputStream openFileInputStream(String path, boolean createIfDoesntExist) throws IOException {
        return new FileInputStream(Paths.get(System.getProperty("user.dir"), path).toString());
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