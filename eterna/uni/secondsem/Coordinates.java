package eterna.uni.secondsem;

import java.io.IOException;
import java.util.Scanner;

public class Coordinates implements CSVFormattable {
    private int x; // >-21
    public int get_x() { return x; }
    public void set_x(int value) throws IllegalArgumentException {
        if (!(value > -21)) {
            throw new IllegalArgumentException("x must be > -21!");
        }
        x = value;
    }
    
    private float y;
    public float get_y() { return y; }
    public void set_y(float value) {
        y = value;
    }

    @Override
    public void readFromCSVLine(Scanner csvScanner) throws IOException {
        try {
            set_x(csvScanner.nextInt());
            set_y(csvScanner.nextFloat());
        } catch (NullPointerException npex) {
            throw new IOException(npex);
        } catch (IllegalArgumentException iaex) {
            throw new IOException(iaex);
        }
    }

    @Override
    public String toCSVString() {
        return x + "," + y;
    }

    @Override
    public String toString() {
        return String.format("Coordinates(%d, %.2f)", x, y);
    }

    @Override
    public int hashCode() {
        return x * 31 + Float.hashCode(y);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other.getClass() != Coordinates.class) return false;
        Coordinates otherCoords = (Coordinates)other;
        return x == otherCoords.x && y == otherCoords.y;
    }
}
