package eterna.uni.secondsem;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Person implements Comparable<Person>, CSVFormattable, Serializable {
    /**
     * Cannot be null, Must be larger than zero, Unique, Automatic
     */
    private transient Integer id;
    public Integer get_id() { return id; }

    /**
     * Cannot be null or empty
     */
    private String name;
    public String get_name(String value) { return name; }
    /**
     * Sets name to value, unless value is null or empty
     * @param value
     * @throws IllegalArgumentException
     */
    public void set_name(String value) throws IllegalArgumentException {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value can't be empty!");
        }
        name = value;
    } 

    /**
     * Cannot be null
     */
    private Coordinates coordinates;
    public Coordinates get_coordinates() { return coordinates; }
    /**
     * Sets coordinates to value unless value is null
     * @param value
     * @throws NullPointerException
     */
    public void set_coordinates(Coordinates value) throws NullPointerException {
        if (value == null) throw new NullPointerException("Value can't be null!"); 
        coordinates = value;
    }

    /**
     * Cannot be null, Automatic
     */
    private Date creationDate;
    public Date get_creationDate() { return creationDate; }

    /**
     * Must be larger than zero
     */
    private double height;
    public double get_height() { return height; }
    /**
     * Sets height to value, unless value is not larger than zero
     * @param value
     * @throws IllegalArgumentException
     */
    public void set_height(double value) throws IllegalArgumentException {
        if (value <= 0) throw new IllegalArgumentException("Value must be positive!");
        height = value;
    }

    private Date birthday;
    public Date get_birthday() { return birthday; }
    public void set_birthday(Date date) {
        birthday = date;
    }

    private Color hairColor;
    public Color get_hairColor() { return hairColor; }
    public void set_hairColor(Color value) { 
        hairColor = value;
    }

    /**
     * Cannot be null
     */
    private Country nationality;
    public Country get_nationality() { return nationality; }
    /**
     * Sets nationality to value if value is not null
     * @param value
     * @throws NullPointerException
     */
    public void set_nationality(Country value) throws NullPointerException { 
        if (value == null) throw new NullPointerException("Value can't be null!"); 
        nationality = value;
    }

    /**
     * Cannot be null
     */
    private Location location;
    public Location get_location() { return location; }
    /**
     * Sets location to value if location is not null
     * @param value
     * @throws NullPointerException
     */
    public void set_location(Location value) throws NullPointerException {
        if (value == null) throw new NullPointerException("Value can't be null!"); 
        location = value;
    }

    public Person(String _name, Coordinates _coords, double _height, Country _nation, Location _location) {
        name = _name;
        coordinates = _coords;
        height = _height;
        creationDate = new Date();
        birthday = null;
        hairColor = null;
        nationality = _nation;
        location = _location;
    }
    
    @Override
    public void readFromCSVLine(Scanner csvScanner) throws IOException {
        try {
            set_name(StringSanitizer.unsanitizeString(csvScanner.next()));
        
            coordinates.readFromCSVLine(csvScanner);
            creationDate = new Date((csvScanner.nextLong()));

            set_height(csvScanner.nextDouble());
            set_birthday(new Date(csvScanner.nextLong()));

            String hairColorField = csvScanner.next();
            if (!hairColorField.isEmpty()) {
                set_hairColor(Color.valueOf(hairColorField));
            }

            set_nationality(Country.valueOf(csvScanner.next()));
            location.readFromCSVLine(csvScanner);
        } catch (IllegalArgumentException iaex) {
            throw new IOException(iaex);
        } catch (NoSuchElementException nseex) {
            throw new IOException(nseex);
        } catch (NullPointerException npex) {
            throw new IOException(npex);
        }
    }

    @Override
    public String toCSVString() {
        return String.join(",",
            StringSanitizer.sanitizeString(name),
            coordinates.toCSVString(),
            ""+creationDate.getTime(),
            ""+height,
            (birthday == null ? "" : ""+birthday.getTime()),
            (hairColor == null ? "" : hairColor.toString()),
            nationality.toString(),
            location.toCSVString()
        );
    }
    
    /**
     * Assigns a new id to this instance of Person, and prevents it from overlapping with others
     */
    public void register() {
        if (id != null) unregister();
        id = PersonIdPool.getNextId();
    }
    /**
     * Removes Person's id from uniqueness check and sets it to null
     */
    public void unregister() {
        PersonIdPool.freeId(id);
        id = null;
    }

    @Override
    public String toString() {
        return String.format(
            "Person(%d, %s, %s, created %s, %.2f m, birthday: %s, hair %s, %s, %s)",
            id,
            name,
            coordinates.toString(),
            creationDate.toString(),
            height,
            (birthday == null ? "null" : (new SimpleDateFormat("dd-MM-yyyy").format(birthday))),
            (hairColor == null ? "null" : hairColor.toString()),
            nationality.toString(),
            location.toString()
        );
    }

    @Override
    public int compareTo(Person o) {
        return id.compareTo(o.id);
    }

    /**
     * Private class managing the set of ids used by Persons across the application 
     */
    private static class PersonIdPool {
        private static final HashSet<Integer> OCCUPIED_IDS = new HashSet<Integer>();
        private static Integer lastId = 1;

        /**
         * @return next unocuppied, unique id
         */
        public static Integer getNextId() {
            while (!OCCUPIED_IDS.add(lastId)) { lastId++; }
            return lastId;
        }   

        /**
         * Removes id from pool
         * @param id
         * @return true if id was found in pool and removed, otherwise false
         */
        public static boolean freeId(Integer id) {
            lastId = Math.min(lastId, id);
            return OCCUPIED_IDS.remove(id);
        }
    }
}
