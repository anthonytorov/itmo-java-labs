package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.AppManager;
import eterna.uni.secondsem.Color;
import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.Person;

public class CommandCountByHairColor extends Command{

    private Color selectedColor;

    @Override
    public void invoke(AppManager appManager) {
        int count = 0;

        for (Person p : appManager.collectionManager.get_list()) {
            if (p.get_hairColor() == selectedColor) {
                count++;
            }
        }

        LogPrinter.log("People with hair color " + selectedColor + ": " + count);
    }

    @Override
    public void configure(String[] arguments) {
        if (arguments.length < 2) throw new IllegalArgumentException("Enter the hair color!");

        selectedColor = Color.valueOf(arguments[1]);
    }

    @Override
    public String getKey() {
        return "count_by_hair_color";
    }
}
