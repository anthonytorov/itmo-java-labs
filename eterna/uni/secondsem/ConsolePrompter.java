package eterna.uni.secondsem;

/**
 * Class for streamlined handling of console inputs.
 */
public class ConsolePrompter extends Prompter {

    /**
     * @return next line from system console
     */
    @Override
    public String getOutput() {
        return System.console().readLine();
    }

    @Override
    public void showPrompt(String prompt) {
        LogPrinter.log(prompt);        
    }
}