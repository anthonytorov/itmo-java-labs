package eterna.uni.secondsem;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class CollectionManager {
    
    private LinkedList<Person> list;
    public LinkedList<Person> get_list() { return list; }
    private String path;
    private Date initializationDate;
    public Date get_initializationDate() { return initializationDate; }

    public CollectionManager(String _path) {
        list = new LinkedList<Person>();
        initializationDate = new Date();
        path = _path;
    }

    public String getAbsoluteCollectionDirectory() {
        return (new File(path)).getParentFile().getAbsolutePath();
    }

    public void load() {
        CollectionLoader.loadCollectionFromFile(list, path);
    }

    public void save() {
        CollectionLoader.saveCollectionToFile(list, path);
    }

    public void clear() {
        for (Person person : list) {
            person.unregister();
        }
        list.clear();
    }

    public void add(Person person) {
        person.register();
        list.add(person);
        list.sort(null);
    }

    public int compareToCollectionBounds(Person person) {
        if (list.getLast().compareTo(person) < 0) return 1;
        else if (list.getFirst().compareTo(person) > 0) return -1;

        return 0;
    }

    public boolean remove(Integer id) {
        Person person = fetch(id);
        if (person != null) {
            person.unregister();
            list.remove(person);
            return true;
        }
        return false;
    }

    public Person fetch(Integer id) {
        for (Person person : list) {
            if (person.get_id().equals(id)) return person;
        }

        return null;
    }

    public void shuffle() {
        for (Person person : list) {
            person.unregister();
        }
        Collections.shuffle(list);
        for (Person person : list) {
            person.register();
        }
        list.sort(null);
    }

    private static class CollectionLoader {
        public static void loadCollectionFromFile(Collection<Person> collection, String path) {
            Scanner lineScanner = AppManager.tryScanFile(path);
            if (lineScanner != null) {
                while (lineScanner.hasNextLine()) {
                    Scanner csvScanner = new Scanner(lineScanner.nextLine());
                    csvScanner.useDelimiter(",");
                    
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
        

        public static void saveCollectionToFile(Collection<Person> collection, String path) {
            PrintWriter writer = AppManager.tryCreateWriter(path);
            
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
