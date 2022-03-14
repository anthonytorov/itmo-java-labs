package eterna.uni.secondsem;

public class ConsolePrompt {

    private boolean read;

    public String get_output() {
        if (read) throw new NullPointerException();
        read = true;
        return System.console().readLine();
    }
    
    public ConsolePrompt(String prompt) {
        if (prompt != null && !prompt.isEmpty()) LogPrinter.log(prompt);
        read = false;
    }
}