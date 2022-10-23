package test;

import components.Keyboard;

public class CheckLogic {
    //ctor
    public CheckLogic(){}

    //checks
    public boolean checkIfCharsIsInTheLanguage(String stringOfPlugInUse, Keyboard keyboard){
        boolean isValid=true;

        for (int i = 0; i < stringOfPlugInUse.length(); i++) {
            if (!keyboard.findInKeyboard(Character.toUpperCase(stringOfPlugInUse.charAt(i)))) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }
    public boolean checkIfNumberOfPlugsIsEven(String checkedInitialPositionsOfRotors){
        return checkedInitialPositionsOfRotors.length()%2==0;
    }
}
