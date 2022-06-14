package eterna.uni.secondsem;

import java.io.Serializable;

public class Coordinates implements Serializable {
    /**
     * Must be larger than -21.
     */
    private int x;
    public int get_x() { return x; }
    /**
     * Sets the value of x to be value, unless it's not larger than -21
     * @param value new value of x
     * @throws IllegalArgumentException if value is not > -21
     */
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

    public Coordinates(int x, float y) {
        this.x = x;
        this.y = y;
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
