package eterna.uni.secondsem.server;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;

import eterna.uni.secondsem.Color;
import eterna.uni.secondsem.Coordinates;
import eterna.uni.secondsem.Country;
import eterna.uni.secondsem.FailedToInitializeException;
import eterna.uni.secondsem.FailedToInvokeCommandException;
import eterna.uni.secondsem.Location;
import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.StringSanitizer;

/**
 * Main class overseeing the LinkedList of Persons
 */
public class CollectionManager {

    public static CollectionManager instance;
    
    private LinkedList<Person> _list;
    public LinkedList<Person> get_list() { return _list; }
    private String _path;
    public String get_databasePath() { return _path; }
    private Date _initializationDate;
    public Date get_initializationDate() { return _initializationDate; }

    private final Connection _connection;

    private final String[] COLUMN_NAMES = new String[] {
        "NAME", "COORD_X", "COORD_Y", "CREATION_DATE", "HEIGHT", "BIRTHDAY", "HAIR_COLOR", "NATIONALITY", "LOCATION_X", "LOCATION_Y", "LOCATION_Z", "LOCATION_NAME"
    };

    public CollectionManager(Properties info) throws FailedToInitializeException {

        if (instance != null) {
            instance = this;
        }

        try {
            Class.forName("org.postgresql.Driver");
            _connection = DriverManager.getConnection(info.getProperty("url"), info);
            _initializationDate = new Date();

            String sqlString = String.format("CREATE TABLE IF NOT EXISTS Person (%s);",
            String.join(",\n", 
            "ID serial PRIMARY KEY", 
            "NAME VARCHAR(255) NOT NULL", 
            "COORD_X INT CHECK(COORD_X > -21)",
            "COORD_Y REAL",
            "CREATION_DATE TIMESTAMP",
            "HEIGHT REAL CHECK(HEIGHT > 0)",
            "BIRTHDAY DATE",
            "HAIR_COLOR VARCHAR(7)",
            "NATIONALITY VARCHAR(15)",
            "LOCATION_X REAL",
            "LOCATION_Y REAL",
            "LOCATION_Z REAL",
            "LOCATION_NAME VARCHAR(238)"));
            Statement statement = _connection.createStatement();
            statement.execute(sqlString);
            LogPrinter.log("ensured table created");

        } catch (SQLException | ClassNotFoundException ex) {
            throw new FailedToInitializeException(ex);
        }

        _list = new LinkedList<Person>();
        load();
    }

    /**
     * @return the absolute path to the directory containing the database file
     */
    public String getAbsoluteCollectionDirectory() {
        return (new File(_path)).getParentFile().getAbsolutePath();
    }

    /**
     * Loads collection from the active file.
     */
    public void load() throws FailedToInitializeException {
        try {
            PreparedStatement statement = _connection.prepareStatement("SELECT * FROM Person");
            ResultSet rSet = statement.executeQuery();
            
            if (!rSet.isClosed()) {
                LogPrinter.log("reading row from table");
                while (rSet.next()) {
                    Coordinates newCoordinates = new Coordinates(rSet.getInt(3), rSet.getFloat(4));
                    Location newLocation = new Location(rSet.getFloat(10), rSet.getFloat(11), rSet.getFloat(12), rSet.getString(13)); 
                    Person newPerson = new Person(rSet.getInt(1), 
                    rSet.getString(2), 
                    newCoordinates, 
                    rSet.getDate(5), 
                    rSet.getFloat(6), 
                    Country.valueOf(rSet.getString(9)), 
                    newLocation);

                    newPerson.set_birthday(rSet.getDate(7));
                    newPerson.set_hairColor(Color.valueOf(rSet.getString(8)));
                    
                    newPerson.register();
                    _list.add(newPerson);
                }
            }
        } catch (SQLException ex) {
            throw new FailedToInitializeException(ex);
        }
        LogPrinter.log(String.format("Loaded %d elements from file.", _list.size()));
    }

    /**
     * Saves collection to the active file.
     */
    public void save() {
    }

    /**
     * Clears the collection and frees all ids of the contained Person instances.
     */
    public void clear() throws FailedToInvokeCommandException {

        try {
            Statement statement = _connection.createStatement();
            statement.execute("TRUNCATE Person");
        } catch (SQLException ex) {
            throw new FailedToInvokeCommandException(ex);
        }
        
        _list.stream().forEach(person -> person.unregister());
        _list.clear();
    }

    /**
     * Adds a new person to the collection, and assignes it a new id.
     * @param person
     */
    public void add(Person person) throws FailedToInvokeCommandException{

        try {
            String sql = (String.format("INSERT INTO Person (%1$s) VALUES (%2$s)", 
                String.join(",", COLUMN_NAMES),
                personToSQL(person))
            );

            _connection.createStatement().execute(sql);

            person.register();
            _list.add(person);
        } catch (SQLException ex) {
            throw new FailedToInvokeCommandException(ex);
        }
    }

    private String personToSQL(Person person) {
        SimpleDateFormat creationDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy-MM-dd");

        return String.join(",",
            "\'"+StringSanitizer.sanitizeString(person.get_name())+"\'",
            coordinatesToSql(person.get_coordinates()),
            "\'"+creationDateFormat.format(person.get_creationDate())+"\'",
            ""+person.get_height(),
            (person.get_birthday() == null ? "" : "\'"+birthdayFormat.format(person.get_birthday())+"\'"),
            (person.get_hairColor() == null ? "" : "\'"+person.get_hairColor().toString()+"\'"),
            "\'"+person.get_nationality().toString()+"\'",
            locationToSql(person.get_location())
        );
    }
    private String coordinatesToSql(Coordinates coordinates) {
        return String.join(",",
            ""+coordinates.get_x(),
            ""+coordinates.get_y()
        );
    }
    private String locationToSql(Location location) {
        return String.join(",",
            ""+location.get_x(),
            ""+location.get_y(),
            ""+location.get_z(),
            "\'"+StringSanitizer.sanitizeString(location.get_name())+"\'"
        );
    }


    /**
     * Compares a new person to all elements inside the collection.
     * @param person the person to compare to
     * @return 1 if person is greater than any element in the collection, -1 if less and 0 if person is inside collection bounds.
     */
    public int compareToCollectionBounds(Person person) {
        if (_list.getLast().compareTo(person) < 0) return 1;
        else if (_list.getFirst().compareTo(person) > 0) return -1;

        return 0;
    }

    /**
     * Removes Person instance with a specified id from the collection.
     * @param id the id of the Person to remove
     * @return true if a person with the specified id was found and removed, otherwise false 
     */
    public boolean remove(Integer id) {
        try {
            _connection.createStatement().execute("DELETE FROM Person WHERE ID = " + id);

            Person person = fetch(id);
            if (person != null) {
                person.unregister();
                _list.remove(person);
                return true;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Fetches a person with a specified id from the collection.
     * @param id id to search for
     * @return a Person instance if the collection contains element with the specified id, otherwise null
     */
    public Person fetch(Integer id) {
        for (Person person : _list) {
            if (person.get_id().equals(id)) return person;
        }

        return null;
    }

    /**
     * Replaces a person with a specified id with another person's data
     * @return true if replacement was successful, else false
     */
    public boolean replace(Integer id, Person newPerson) throws FailedToInvokeCommandException {

        SimpleDateFormat creationDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat birthdayFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            _connection.createStatement().execute(String.format("UPDATE Person SET %s WHERE ID = " + id,
                "NAME = " + newPerson.get_name(),
                "COORD_X = " + newPerson.get_coordinates().get_x(), 
                "COORD_Y = " + newPerson.get_coordinates().get_y(), 
                "CREATION_DATE = " + creationDateFormat.format(newPerson.get_creationDate()), 
                "HEIGHT = " + newPerson.get_height(), 
                "BIRTHDAY = " + birthdayFormat.format(newPerson.get_birthday()), 
                "HAIR_COLOR = " + newPerson.get_hairColor(), 
                "NATIONALITY = " + newPerson.get_nationality(), 
                "LOCATION_X = " + newPerson.get_location().get_x(), 
                "LOCATION_Y = " + newPerson.get_location().get_y(), 
                "LOCATION_Z = " + newPerson.get_location().get_z(), 
                "LOCATION_NAME = " + newPerson.get_location().get_name()
            ));

            if (remove(id)) {
                add(newPerson);
                return true;
            }
            return false;
        } catch (SQLException ex) {
            throw new FailedToInvokeCommandException(ex);
        }
    }

    /**
     * Randomly rearranges entries in the collection by shuffling their ids.
     */
    public void shuffle() throws FailedToInvokeCommandException {
        LinkedList<Person> coll = new LinkedList<Person>(_list);
        Collections.shuffle(coll);

        clear();
        for (Person p : coll) {
            add(p);
        }
    }
}
