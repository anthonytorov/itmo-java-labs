package eterna.uni.secondsem.client;

import java.util.Arrays;
import java.util.stream.Collectors;

import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.commands.CommandCountByHairColor;
import eterna.uni.secondsem.commands.CommandExecuteScript;
import eterna.uni.secondsem.commands.CommandFilterLessThanLocation;
import eterna.uni.secondsem.commands.CommandInfo;

public class ClientResponseHandler {
    public static void handleResponse(String className, Object object) {
        switch (className) {
            default:
                break;
            case "eterna.uni.secondsem.commands.CommandAdd":
            case "eterna.uni.secondsem.commands.CommandAddIfMin":
            case "eterna.uni.secondsem.commands.CommandAddIfMax":
                Boolean success = (Boolean)object;
                if (success) {
                    LogPrinter.log("Successfully added new person to collection.");
                } else {
                    LogPrinter.log("Failed to add person to collection.");
                }
                break; 
            case "eterna.uni.secondsem.commands.CommandClear":
                success = (Boolean)object;
                if (success) {
                    LogPrinter.log("Cleared collection.");
                } else {
                    LogPrinter.log("There has been an error clearing the collection.");
                }
                break;
            case "eterna.uni.secondsem.commands.CommandCountByHairColor":
                CommandCountByHairColor.Response cbhc_response = (CommandCountByHairColor.Response)object;
                LogPrinter.log(String.format("Found %1$d people with %2$s hair color.", cbhc_response.count, cbhc_response.color.toString())); 
                break;
            case "eterna.uni.secondsem.commands.CommandExecuteScript":
                CommandExecuteScript.Response ces_response = (CommandExecuteScript.Response)object;
                for (int i = 0; i < ces_response.commandClasses.length; i++) {
                    handleResponse(ces_response.commandClasses[i].getName(), ces_response.responses[i]);
                }
                break;
            case "eterna.uni.secondsem.commands.FilterLessThanLocation":
                CommandFilterLessThanLocation.Response fltl_response = (CommandFilterLessThanLocation.Response)object;
                if (fltl_response.results.length > 0) {
                    LogPrinter.log("No results!");
                } else {
                    LogPrinter.log(String.format("Found %1$d results with location less than %2$s:\n%3$s", 
                    fltl_response.results.length,
                    fltl_response.promptedLocation.toString(),
                    Arrays.stream(fltl_response.results).map(p -> p.toString()).collect(Collectors.joining("\n"))));
                }
                break;
            case "eterna.uni.secondsem.commands.CommandHelp":
                String[] helpkeys = (String[]) object;
                for (String key : helpkeys) {
                    LogPrinter.log(String.format("%1$s: %2$s", key, LogPrinter.getString(key)));
                }
                break;
            case "eterna.uni.secondsem.commands.CommandInfo":
                CommandInfo.Response inforesponse = (CommandInfo.Response)object;
                LogPrinter.log(String.format("Collection type: %1$s\nInitialization date: %2$s\nCollection size: %3$d", inforesponse.collectionType, inforesponse.initDate, inforesponse.size));
                break;
            case "eterna.uni.secondsem.commands.CommandPrintFieldDescendingNationality":
                String[] nations = (String[])object;
                LogPrinter.log(String.join("\n", nations));
                break;
            case "eterna.uni.secondsem.commands.CommandRemove":
                Integer removedId = (Integer)object;
                if (removedId == -1) {
                    LogPrinter.log("Failed to find object with requested id in collection.");
                } else {
                    LogPrinter.log("Successfully removed entry " + removedId + " from collection.");
                }
                break;
            case "eterna.uni.secondsem.commands.CommandShow":
                Person[] persons = (Person[])object;
                LogPrinter.log(String.format("Collection contains %1$d items: %2$s", 
                persons.length,
                Arrays.stream(persons).map(p -> p.toString()).collect(Collectors.joining("\n"))));
                break;
            case "eterna.uni.secondsem.commands.CommandShuffle":
                success = (Boolean)object;
                if (success) {
                    LogPrinter.log("Successfully shuffled collection.");
                } else {
                    LogPrinter.log("Failed to shuffle collection!");
                }
                break;
            case "eterna.uni.secondsem.commands.CommandUpdate":
                Integer code = (Integer)object;
                if (code == -1) {
                    LogPrinter.log("An error occured while updating database.");
                } else if (code == 0) {
                    LogPrinter.log("Failed to find object with requested id in collection.");
                } else {
                    LogPrinter.log("Updated element with id " + code + ".");
                }
                break;
        }
    }
}
