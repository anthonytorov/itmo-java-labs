package eterna.uni.secondsem;

import java.io.IOException;
import java.util.Scanner;

public interface CSVFormattable {
    void readFromCSVLine(Scanner csvScanner) throws IOException;
    String toCSVString();
}
