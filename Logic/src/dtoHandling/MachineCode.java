package dtoHandling;

import java.util.HashMap;
import java.util.Map;

public class MachineCode { // receive new code from user Dto
    private int[] rotorsNumbers;
    private char[] initialPositionsOfRotors;
    private String reflectorGreekNumber;
    private HashMap<Character,Character> plugsInUse;

    public MachineCode(){}

    public MachineCode(MachineCode deliveryInformation) {
        this.plugsInUse=new HashMap<>(deliveryInformation.plugsInUse);
        this.reflectorGreekNumber= deliveryInformation.reflectorGreekNumber;
        this.rotorsNumbers=new int[deliveryInformation.rotorsNumbers.length];
        this.initialPositionsOfRotors=new char[deliveryInformation.initialPositionsOfRotors.length];

        for (int i = 0; i < rotorsNumbers.length; i++) {
            rotorsNumbers[i]=deliveryInformation.rotorsNumbers[i];
        }

        for (int i = 0; i < initialPositionsOfRotors.length; i++) {
            initialPositionsOfRotors[i]=deliveryInformation.initialPositionsOfRotors[i];
        }
    }

    public void setRotorsNumbers(int[] rotorsNumbers) {
        this.rotorsNumbers = rotorsNumbers;
    }
    public int[] getRotorsNumbers(){
        return rotorsNumbers;
    }

    public int getLengthOfRotorsNumber(){
        return rotorsNumbers.length;
    }

    public void setInitialPositionsOfRotors(char[] initialPositionsOfRotors){
        this.initialPositionsOfRotors=initialPositionsOfRotors;
    }
    public char[] getInitialPositionsOfRotors() {
        return initialPositionsOfRotors;
    }

    public void setReflectorGreekNumber(String reflectorGreekNumber){
        this.reflectorGreekNumber=reflectorGreekNumber;
    }

    public String getReflectorGreekNumber() {
        return reflectorGreekNumber;
    }

    public void setPlugsInUse(HashMap<Character,Character> plugsInUse){
        this.plugsInUse=plugsInUse;
    }

    public HashMap<Character, Character> getPlugsInUse() {
        return plugsInUse;
    }

    @Override
    public String toString() {
        StringBuilder printString = new StringBuilder();

        printString.append("Rotor Id's: ");
        for (int i = 0; i < rotorsNumbers.length; i++) {
            printString.append(rotorsNumbers[i]);
            if (i != rotorsNumbers.length - 1) {
                printString.append(", ");
            }
        }
        printString.append("  ");

        printString.append("Positions: ");
        for (int i = 0; i < initialPositionsOfRotors.length; i++) {
            printString.append(initialPositionsOfRotors[i]);
            if (i != initialPositionsOfRotors.length - 1) {
                printString.append(", ");
            }
        }
        printString.append("  ");

        printString.append("Reflector Number: ");
        printString.append(reflectorGreekNumber);
        printString.append("  ");

        if (plugsInUse.size() > 0) {
            printString.append("Plugs In Use: ");
            for (Map.Entry<Character, Character> entry : plugsInUse.entrySet()) {
                printString.append(entry.getKey()).append("|").append(entry.getValue());
                printString.append(", ");
            }
            printString.delete(printString.length() - 2, printString.length());
        }

        return String.valueOf(printString);
    }

}
