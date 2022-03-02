package eterna.uni.secondsem;

import java.util.Scanner;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

public class CollectionManager {
    
    private LinkedList<Person> list;
    private String path;
    private Date initializationDate;

    public CollectionManager(String _path) {
        list = new LinkedList<Person>();
        initializationDate = new Date();
        path = _path;
    }

    public void load() {
        CollectionLoader.loadCollectionFromFile(list, path);
    }

    public void save() {
        CollectionLoader.saveCollectionToFile(list, path);
    }

    public void clear() {
        list.clear();
    }

    public void add(Person person) {
        list.add(person);
        list.sort(null);
    }

    public int compareToCollectionBounds(Person person) {
        if (list.getLast().compareTo(person) < 0) return 1;
        else if (list.getFirst().compareTo(person) > 0) return -1;

        return 0;
    }

    public boolean update(Integer id, Person other) {
        Person person = fetch(id);
        if (person == null) return false;

        person.copyDataFrom(other);
        list.sort(null);
        return true;
    }

    public boolean remove(Integer id) {
        Person person = fetch(id);
        if (person != null) {
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

    public void show() {
        for (Person person : list) {
            Logger.log(person.toString());
        }
    }

    public String getInformationString() {
        return String.join("\n",
            "Collection type : " + list.getClass().toString(),
            "Initialization date : " + initializationDate.toString(),
            "Size : " + list.size()
        );
    }

    private static class CollectionLoader {
        public static void loadCollectionFromFile(Collection<Person> collection, String path) {
            Scanner lineScanner = AppManager.tryScanFile(path);
            if (lineScanner != null) {
                while (lineScanner.hasNextLine()) {
                    Scanner csvScanner = new Scanner(lineScanner.nextLine());
                    csvScanner.useDelimiter(",");
                    collection.add(readPerson(csvScanner));
                    csvScanner.close();
                }
                lineScanner.close();
            }
        }

        private static Person readPerson(Scanner csvScanner) {
            String _name = csvScanner.next();
            return new Person(_name);
        }
        

        public static void saveCollectionToFile(Collection<Person> collection, String path) {
            PrintWriter writer = AppManager.tryCreateWriter(path);
            
            for (Person person : collection) {
                writePersonToCSV(writer, person);
            }
            
            writer.flush();
            writer.close();
        }

        private static void writePersonToCSV(PrintWriter writer, Person person) {
            writer.write(String.join(
                ",",
                person.get_name()
            ));
        }
    }
}
