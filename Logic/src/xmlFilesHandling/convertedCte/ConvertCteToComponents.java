package xmlFilesHandling.convertedCte;
import Utilies.LogicUtilities;
import components.Keyboard;
import components.Reflector;
import components.Rotor;
import generated.CTEEnigma;
import generated.CTEMachine;
import generated.CTEReflector;
import generated.CTERotor;
import repository.RepositoryOfComponents;
import java.util.List;

public class ConvertCteToComponents {
    private final RepositoryOfComponents repository;

    public ConvertCteToComponents(CTEEnigma cteEnigma) {
        this.repository =convertCteMachineToEnigmaMachine(cteEnigma);
    }

    public RepositoryOfComponents getRepository() {
        return repository;
    }

    public RepositoryOfComponents convertCteMachineToEnigmaMachine(CTEEnigma cteEnigma) {
        CTEMachine machine = cteEnigma.getCTEMachine();
        Reflector[] reflectorsRepo = convertCteReflectorToReflector(machine);
        Rotor[] rotorsRepo = convertCteRotorsToRotors(machine);
        Keyboard keyboardRepo = convertAbcToKeyboard(machine);
        return new RepositoryOfComponents(keyboardRepo, reflectorsRepo, rotorsRepo,machine.getRotorsCount());
    }

    public Keyboard convertAbcToKeyboard(CTEMachine machine){
        String abc = machine.getABC().trim();
        char[] keyboard = new char[abc.length() + 1];
        keyboard[0] = '0';
        for (int i = 1; i < abc.length() + 1; i++) {
            keyboard[i] = Character.toUpperCase(abc.charAt(i - 1));
        }
        return new Keyboard(keyboard);
    }

    // convert cte components to to enigma machine components
    public Rotor[] convertCteRotorsToRotors(CTEMachine machine) {
        List<CTERotor> cteRotors = machine.getCTERotors().getCTERotor();
        Rotor[] convertedRotors= new Rotor[cteRotors.size()+1];
        int sizeOfCol = cteRotors.get(0).getCTEPositioning().size() + 1;//num of char
        for (int i = 0; i < cteRotors.size(); i++) {
            char [] rightCol = new char[sizeOfCol];
            char [] leftCol = new char[sizeOfCol];
            for (int j = 0; j < cteRotors.get(i).getCTEPositioning().size(); j++) {
                leftCol[j + 1] = Character.toUpperCase(cteRotors.get(i).getCTEPositioning().get(j).getLeft().charAt(0));
                rightCol[j + 1] = Character.toUpperCase(cteRotors.get(i).getCTEPositioning().get(j).getRight().charAt(0));
            }

            convertedRotors[cteRotors.get(i).getId()]=new Rotor(rightCol, leftCol, cteRotors.get(i).getNotch());
        }
        return convertedRotors;
    }

    public Reflector[] convertCteReflectorToReflector(CTEMachine machine) {
        List<CTEReflector> cteReflector = machine.getCTEReflectors().getCTEReflector();
        Reflector[] convertedReflectors = new Reflector[cteReflector.size() + 1];
        for (int i = 0; i < cteReflector.size(); i++) {
            int[] tmpReflectorArr = new int[cteReflector.get(0).getCTEReflect().size() * 2 + 1];
            for (int j = 0; j < cteReflector.get(i).getCTEReflect().size(); j++) {
                int input = cteReflector.get(i).getCTEReflect().get(j).getInput();
                int output = cteReflector.get(i).getCTEReflect().get(j).getOutput();
                tmpReflectorArr[input] = output;
                tmpReflectorArr[output] = input;
            }
            int idOfReflector = LogicUtilities.convertInputOfReflectorNumber(cteReflector.get(i).getId());
            convertedReflectors[idOfReflector] = new Reflector(tmpReflectorArr);
        }
        return convertedReflectors;
    }

}
