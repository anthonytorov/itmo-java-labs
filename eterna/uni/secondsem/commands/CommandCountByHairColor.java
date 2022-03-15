package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.Color;
import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.Person;

public class CommandCountByHairColor extends Command{

    private final Color SELECTED_COLOR; 

    public CommandCountByHairColor(String[] args) {
        super(args);
        if (args.length < 2) throw new NullPointerException("No color entered!");
        SELECTED_COLOR = Color.valueOf(args[1]);
    }

    @Override
    public void invoke(AppManager appManager) {
        int count = 0;

        for (Person p : appManager.collectionManager.get_list()) {
            if (p.get_hairColor() == SELECTED_COLOR) {
                count++;
            }
        }

        LogPrinter.log("People with hair color " + SELECTED_COLOR + ": " + count);
    }
}
