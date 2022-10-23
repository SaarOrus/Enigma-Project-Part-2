package enigmaMachine;
import components.*;

public class Machine {

    private final Keyboard keyboard;
    private final PlugBoard plugboard;
    private final Reflector reflector;
    private final RotorsComponent rotors;

    //ctor
    public Machine(Keyboard keyboard,PlugBoard plugboard, Reflector reflector, RotorsComponent rotors) {
        this.keyboard=keyboard;
        this.plugboard = plugboard;
        this.reflector = reflector;
        this.rotors = rotors;
    }
    public Machine(Machine enigmaMachine) {
        Keyboard copyKeyBoard=new Keyboard(enigmaMachine.keyboard);
        this.keyboard=copyKeyBoard;
        this.plugboard = enigmaMachine.plugboard;
        Reflector copyReflector=new Reflector(enigmaMachine.reflector);
        this.reflector=copyReflector;
        RotorsComponent copyRotorsComponent=new RotorsComponent(enigmaMachine.rotors);
        this.rotors=copyRotorsComponent;
    }
    //get functions
    public RotorsComponent getRotorsComponents() {
        return rotors;
    }
    //encode
    public char encodeChar(char enteredChar) {
        char exitChar = plugboard.matchConnectedPlug(enteredChar);
        int indexOfChar =keyboard.getIndexOfChar(exitChar);
        indexOfChar = rotors.RotorsAction(indexOfChar);
        indexOfChar = reflector.reflectIndex(indexOfChar);
        indexOfChar = rotors.RotorsAction(indexOfChar);
        exitChar = plugboard.matchConnectedPlug(keyboard.getCharInKeyboardByIndex(indexOfChar));

        return exitChar;
    }
    public String encodeString(String enteredString) {
        String encodedString="";

        for (int i = 0; i < enteredString.length(); i++) {
            encodedString+=encodeChar(enteredString.charAt(i));
        }

        return encodedString;
    }
}
