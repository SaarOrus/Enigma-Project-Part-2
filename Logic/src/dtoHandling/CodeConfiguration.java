package dtoHandling;

import Utilies.LogicUtilities;

import java.util.Map;

public class CodeConfiguration {
    private final MachineCode dtoCode;
    private int[] notchLocationInRotor;

    public CodeConfiguration(MachineCode dto, int[] notchLocationInRotor)
    {
        this.dtoCode=dto;
        this.notchLocationInRotor=reverseNotchLocationInRotor(notchLocationInRotor);
    }

    public MachineCode getDtoCode() {
        return dtoCode;
    }

    private int[] reverseNotchLocationInRotor(int[] notchLocationInRotor) {
       return LogicUtilities.reverseArrayInt(notchLocationInRotor,0);
    }

    public void setNotchLocationInRotor(int[] notchLocationInRotor) {
        this.notchLocationInRotor = notchLocationInRotor;
    }

    public void setInitialPositionsOfRotors(char[] initialPositionsOfRotors){
        this.dtoCode.setInitialPositionsOfRotors(initialPositionsOfRotors);
    }

    @Override
    public String toString() {
        StringBuilder printString = new StringBuilder();

        printString.append("Rotor Id's: ");
        for (int i = 0; i < dtoCode.getLengthOfRotorsNumber(); i++) {
            printString.append(dtoCode.getRotorsNumbers()[i]);
            if (i != dtoCode.getLengthOfRotorsNumber() - 1) {
                printString.append(", ");
            }
        }
        printString.append("       ");

        printString.append("Initial Positions: ");
        for (int i = 0; i < dtoCode.getInitialPositionsOfRotors().length; i++) {
            printString.append(dtoCode.getInitialPositionsOfRotors()[i]);
            printString.append("(").append(notchLocationInRotor[i]).append(")");
            if (i != dtoCode.getLengthOfRotorsNumber() - 1) {
                printString.append(", ");
            }
        }
        printString.append("       ");

        printString.append("Reflector Number: ");
        printString.append(dtoCode.getReflectorGreekNumber());
        printString.append("       ");

        if (dtoCode.getPlugsInUse().size() > 0) {
            printString.append("Plugs In Use: ");
            for (Map.Entry<Character, Character> entry : dtoCode.getPlugsInUse().entrySet()) {
                printString.append(entry.getKey()).append("|").append(entry.getValue());
                printString.append(", ");
            }
            printString.delete(printString.length() - 2, printString.length());
            printString.append("       ");
        }

        return String.valueOf(printString);
    }
}
