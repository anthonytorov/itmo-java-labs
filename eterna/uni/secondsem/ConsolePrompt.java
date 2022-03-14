package eterna.uni.secondsem;

/**
 * Class for streamlined handling of console inputs.
 */
public class ConsolePrompt {

    /**
     * True after the prompt has been read once.
     */
    private boolean read;

    /**
     * @return next line from system console
     */
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