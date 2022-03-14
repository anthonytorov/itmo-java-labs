package eterna.uni.secondsem;

import java.io.IOException;
import java.util.Scanner;

public class Location implements CSVFormattable, Comparable<Location> {
    /**
     * Cannot be null
     */
    private Float x;
    public Float get_x() { return x; }
    /**
     * Sets new value of x to be value, unless value is null.
     * @param value new value of x. cannot be null.
     * @throws IllegalArgumentException if value is null
     */
    public void set_x(Float value) throws IllegalArgumentException {
        if (value == null) throw new IllegalArgumentException("Value can't be null!");
        x = value;
    }

    private double y;
    public double get_y() { return y; }
    public void set_y(double value) { y = value; }

    private double z;
    public double get_z() { return z; }
    public void set_z(double value) { z = value; }

    /**
     * Cannot be longer than 238, but can be null.
     */
    private String name;
    public String get_name() { return name; }
    /**
     * Sets new name to be value, unless value is longer than 238 symbols.
     * @param value new value of name (cannot be longer than 238, but can be null)
     * @throws IllegalArgumentException if value is longer than 238 symbols
     */
    public void set_name(String value) throws IllegalArgumentException {
        if (value != null && value.length() > 238) throw new IllegalArgumentException("Location name can't be longer than 238 characters!");
        name = value;
    }
    
    public Location(Float _x, double _y, double _z, String _name) {
        x = _x;
        y = _y;
        z = _z;
        name = _name;
    }

    @Override
    public void readFromCSVLine(Scanner csvScanner) throws IOException {
        try {
            set_x(csvScanner.nextFloat());
            y = csvScanner.nextDouble();
            z = csvScanner.nextDouble();

            if (csvScanner.hasNext()) {
                set_name(Sanitizer.unsanitizeString(csvScanner.next()));
            } else {
                set_name(null);
            }
        } catch (NullPointerException npex) {
            throw new IOException(npex);
        } catch (IllegalArgumentException iaex) {
            throw new IOException(iaex);
        }
    }
    
    @Override
    public String toCSVString() {
        return String.join(",",
            x.toString(),
            ""+y,
            ""+z,
            (name == null ? "" : Sanitizer.sanitizeString(name))
        );
    }

    @Override
    public int compareTo(Location o) {
        if (o == null) return 1;
        
        if (o.x == x && o.y == o.y && o.z == z) return 0;
        
        return (o.x*o.x + o.y*o.y + o.z*o.z) < (x*x + y*y + z*z) ? 1 : -1;
    }

    @Override
    public String toString() {
        return String.format("Location(%.2f, %.2f, %.2f, %s", x, y, z, name);
    }

    @Override
    public int hashCode() {
        return (((x.hashCode() * 31) + Double.hashCode(y) * 31) + Double.hashCode(z) * 31) + name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other.getClass() != Location.class) return false;
        Location otherLocation = (Location)other;
        return otherLocation.x == x && otherLocation.y == y && otherLocation.z == z && otherLocation.name == name;
    }
}
