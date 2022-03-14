package eterna.uni.secondsem;

import java.io.IOException;
import java.util.Scanner;

/**
 * Interface implementing formatting of classes to CSV format.
 */
public interface CSVFormattable {
    /**
     * Reads values from the line and assignes them to own fields.
     * @param csvScanner scanner over a line of CSV
     * @throws IOException if there has been a mismatch in formatting
     */
    void readFromCSVLine(Scanner csvScanner) throws IOException;
    /**
     * @return String of CSV writable fields of this class
     */
    String toCSVString();
}
