package eterna.uni.secondsem;

public class Sanitizer {
    public static String sanitizeString(String s) {
        return s.replace(",", "\\,");
    }
}
