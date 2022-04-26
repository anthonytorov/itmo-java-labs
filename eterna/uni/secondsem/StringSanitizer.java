package eterna.uni.secondsem;

/**
 * Static class for ensuring safety of strings before writing them as CSV
 */
public class StringSanitizer {
    public static String sanitizeString(String s) {
        return s.replace(",", "\\,");
    }
    public static String unsanitizeString(String s) {
        return s.replace("\\,", ",");
    }
}
