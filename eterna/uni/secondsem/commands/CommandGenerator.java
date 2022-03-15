package eterna.uni.secondsem.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.TreeMap;

import eterna.uni.secondsem.LogPrinter;

/**
 * Class responsible for generating commands from arguments
 */
public class CommandGenerator {
    private TreeMap<String, Class<? extends Command>> commands;
    
    public TreeMap<String, Class<? extends Command>> get_commandMap() { return commands; } 

    public CommandGenerator() {
        commands = new TreeMap<String, Class<? extends Command>>();
    }

    public void registerCommand(String key, Class<? extends Command> cmdClass) {
        if (key == null) LogPrinter.log("Null key while registering command " + cmdClass.toString());
        commands.put(key, cmdClass);
    }
    
    public Command generate(String[] args) {
        if (args.length < 1) throw new NullPointerException("No command entered!");
        try {
            Object[] variableArguments = new Object[] { args };
            return commands.get(args[0]).getConstructor(String[].class).newInstance(variableArguments);
        } catch (InstantiationException iex) {
            
        } catch (IllegalAccessException iaex) {

        } catch (InvocationTargetException itex) {
            
        } catch (NoSuchMethodException nsmex) {

        }
        return null;
    }


}
