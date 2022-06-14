package eterna.uni.secondsem;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;

import eterna.uni.secondsem.commands.*;

public class CommandReader {
    
    private TreeMap<String, Class<? extends Command>> commands;
    
    public TreeMap<String, Class<? extends Command>> get_commandMap() { return commands; } 

    private final Scanner inputScanner;
    private final boolean verbose;

    public CommandReader(InputStream _inputStream, boolean _verbose) {
        inputScanner = new Scanner(_inputStream);
        inputScanner.useDelimiter("\\s+");
        verbose = _verbose;

        initializeCommands();
    }

    public boolean canReadNextCommand() { return inputScanner.hasNext(); }

    public Command readCommand() {
        
        if (!inputScanner.hasNext()) return null;

        try {
            String commandKey = inputScanner.next();
            if (!commands.containsKey(commandKey)) return null;
            Class<? extends Command> commandClass = commands.get(commandKey);
            Class<?>[] result = (Class<?>[])(commandClass.getMethod("getConstuctorClasses").invoke(null));

            Object[] argObjects = new Object[result.length];

            for (int i = 0; i < result.length; i++) {
                argObjects[i] = parseInput(result[i]);
            }

            return commandClass.getConstructor(result).newInstance(argObjects);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Object parseInput(Class<?> expectedClass) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        
        if (expectedClass == Coordinates.class) {
            return ConsolePromptCoordinates.readFromScanner(inputScanner, verbose);
        }
        if (expectedClass == Location.class) {
            return ConsolePromptLocation.readFromScanner(inputScanner, verbose);
        }
        if (expectedClass == Person.class) {
            return ConsolePromptPerson.readFromScanner(inputScanner, verbose);
        }
        if (expectedClass == String.class) {
            return inputScanner.next();
        }

        Pattern parsePattern = Pattern.compile("parse");
        Method[] methods = expectedClass.getMethods();
        String input = null;

        for (Method method : methods) {
            if (parsePattern.matcher(method.getName()).matches()) {

                // try executing this method with the string as an arg
                if (input == null) input = inputScanner.next();

                return method.invoke(null, input);
            }
        }

        return null;
    }

    public void registerCommand(String key, Class<? extends Command> cmdClass) {
        if (key == null) LogPrinter.log("Null key while registering command " + cmdClass.toString());
        commands.put(key, cmdClass);
    }

    private void initializeCommands() {
        commands = new TreeMap<String, Class<? extends Command>>();

        registerCommand("help", CommandHelp.class);
        registerCommand("info", CommandInfo.class);
        registerCommand("show", CommandShow.class);
        registerCommand("add", CommandAdd.class);
        registerCommand("update", CommandUpdate.class);
        registerCommand("remove_by_id", CommandRemove.class);
        registerCommand("exit", CommandExit.class);
        registerCommand("clear", CommandClear.class);
        registerCommand("execute_script", CommandExecuteScript.class);
        registerCommand("add_if_max", CommandAddIfMax.class);
        registerCommand("add_if_min", CommandAddIfMin.class);
        registerCommand("shuffle", CommandShuffle.class);
        registerCommand("count_by_hair_color", CommandCountByHairColor.class);
        registerCommand("filter_less_than_location", CommandFilterLessThanLocation.class);
        registerCommand("print_field_descending_nationality", CommandPrintFieldDescendingNationality.class);
    }
}
