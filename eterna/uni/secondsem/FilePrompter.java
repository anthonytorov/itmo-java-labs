package eterna.uni.secondsem;

import java.util.Scanner;

public class FilePrompter extends Prompter {

    public final Scanner FILE_SCANNER;

    public FilePrompter(Scanner fileScanner) {
        FILE_SCANNER = fileScanner;
    }

    @Override
    public String getOutput() {
        if (FILE_SCANNER.hasNextLine()) return FILE_SCANNER.nextLine();
        return null;
    }

    @Override
    public void showPrompt(String prompt) {}
    
}
