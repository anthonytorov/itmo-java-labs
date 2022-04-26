package eterna.uni.secondsem.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

import eterna.uni.secondsem.Coordinates;
import eterna.uni.secondsem.Country;
import eterna.uni.secondsem.Location;
import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.Person;

/**
 * Main class overseeing the LinkedList of Persons
 */
public class CollectionManager {

    public static CollectionManager instance;
    
    private LinkedList<Person> list;
    public LinkedList<Person> get_list() { return list; }
    private String path;
    public String get_databasePath() { return path; }
    private Date initializationDate;
    public Date get_initializationDate() { return initializationDate; }

    public CollectionManager(String _path) {

        if (instance != null) {
            instance = this;
        }
        
        list = new LinkedList<Person>();
        initializationDate = new Date();
        path = _path;

        load();
    }

    /**
     * @return the absolute path to the directory containing the database file
     */
    public String getAbsoluteCollectionDirectory() {
        return (new File(path)).getParentFile().getAbsolutePath();
    }

    /**
     * Loads collection from the active file.
     */
    public void load() {
        CollectionLoader.loadCollectionFromFile(list, path);
        LogPrinter.log(String.format("Loaded %d elements from file.", list.size()));
    }

    /**
     * Saves collection to the active file.
     */
    public void save() {
        CollectionLoader.saveCollectionToFile(list, path);
    }

    /**
     * Clears the collection and frees all ids of the contained Person instances.
     */
    public void clear() {
        list.stream().forEach(person -> person.unregister());
        list.clear();
    }

    /**
     * Adds a new person to the collection, and assignes it a new id.
     * @param person
     */
    public void add(Person person) {
        person.register();
        list.add(person);
        list.sort(null);
    }

    /**
     * Compares a new person to all elements inside the collection.
     * @param person the person to compare to
     * @return 1 if person is greater than any element in the collection, -1 if less and 0 if person is inside collection bounds.
     */
    public int compareToCollectionBounds(Person person) {
        if (list.getLast().compareTo(person) < 0) return 1;
        else if (list.getFirst().compareTo(person) > 0) return -1;

        return 0;
    }

    /**
     * Removes Person instance with a specified id from the collection.
     * @param id the id of the Person to remove
     * @return true if a person with the specified id was found and removed, otherwise false 
     */
    public boolean remove(Integer id) {
        Person person = fetch(id);
        if (person != null) {
            person.unregister();
            list.remove(person);
            return true;
        }
        return false;
    }

    /**
     * Fetches a person with a specified id from the collection.
     * @param id id to search for
     * @return a Person instance if the collection contains element with the specified id, otherwise null
     */
    public Person fetch(Integer id) {
        for (Person person : list) {
            if (person.get_id().equals(id)) return person;
        }

        return null;
    }

    /**
     * Replaces a person with a specified id with another person's data
     * @return true if replacement was successful, else false
     */
    public boolean replace(Integer id, Person newPerson) {
        if (remove(id)) {
            add(newPerson);
            return true;
        }
        return false;
    }

    /**
     * Randomly rearranges entries in the collection by shuffling their ids.
     */
    public void shuffle() {
        list.stream().forEach(person -> person.unregister());
        Collections.shuffle(list);
        list.stream().forEach(person -> person.register());
        list.sort(null);
    }

    /**
     * Class responsible for loading and unloading collections from CSV files.
     */
    private static class CollectionLoader {
        /**
         * Loads collection from a specified file using Scanner.
         * @param collection the collection to load into
         * @param path path to a CSV file to read from
         */
        public static void loadCollectionFromFile(Collection<Person> collection, String path) {
            Scanner lineScanner = FileUtils.tryScanFile(path, false);
            if (lineScanner != null) {
                while (lineScanner.hasNextLine()) {
                    Scanner csvScanner = new Scanner(lineScanner.nextLine());
                    csvScanner.useDelimiter("(?<!\\\\),");
                    
                    Person person = readPerson(csvScanner);
                    if (person != null) {
                        person.register();
                        collection.add(person);
                    }
                    
                    csvScanner.close();
                }
                lineScanner.close();
            }
        }

        /**
         * Attempts to read an Person instance from a line of CSV  
         * @param csvScanner Scanner instance over a line of CSV 
         * @return if read successfully, Person instance, otherwise null
         */
        private static Person readPerson(Scanner csvScanner) {
            Person person = new Person("", new Coordinates(), 0, Country.ITALY, new Location(0f, 0d, 0d, "Unnamed"));
            try {
                person.readFromCSVLine(csvScanner);

                if (csvScanner.hasNext()) {
                    throw new IOException("Encountered more entries in line than expected!");
                }

                return person;
            } catch (IOException ioex) {
                LogPrinter.logException(ioex);
                LogPrinter.log("Skipping entry...");
            }
            return null;
        }

        /**
         * Writes a collection to a specified file using PrintWriter. 
         * @param collection the collection to write from
         * @param path path to the file to write to
         */
        public static void saveCollectionToFile(Collection<Person> collection, String path) {
            PrintWriter writer = FileUtils.tryCreateWriter(path);
            
            if (writer == null) {
                LogPrinter.log("Unable to write to file " + path);
            }

            for (Person person : collection) {
                writer.println(person.toCSVString());
            }
            
            writer.flush();
            writer.close();
        }
    }
}
