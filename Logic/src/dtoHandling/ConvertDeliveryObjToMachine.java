package dtoHandling;
import Utilies.LogicUtilities;
import components.*;
import enigmaMachine.Machine;
import repository.RepositoryOfComponents;

public class ConvertDeliveryObjToMachine {
    private MachineCode machineCodeDto;
    private Machine enigmaMachine;
    private RepositoryOfComponents repo;

    public ConvertDeliveryObjToMachine(MachineCode machineCodeDto, RepositoryOfComponents repo) {
        this.repo=new RepositoryOfComponents(repo);
        this.machineCodeDto =new MachineCode(machineCodeDto);
        this.enigmaMachine=new Machine(repo.getKeyboard(),InitializingPlugBoard(),InitializingReflector(), InitializingRotors());
    }
    private RotorsComponent InitializingRotors() {
        int[] rotorsNumberId = reverseRotorsNumberId(machineCodeDto.getRotorsNumbers());
        char[] rotorsInitialPosition = reverseInitialPositionsOfRotors(machineCodeDto.getInitialPositionsOfRotors());
        Rotor[] ChosenRotors = new Rotor[rotorsNumberId.length + 1];
        for (int i = 1; i < rotorsNumberId.length + 1; i++) {
            ChosenRotors[i] = repo.getRotorInIndexInd(rotorsNumberId[i - 1]);
            ChosenRotors[i].resetByStartPosition(rotorsInitialPosition[i - 1]);
        }
        return new RotorsComponent(ChosenRotors);
    }
    private Reflector InitializingReflector(){
        int idOfReflector= LogicUtilities.convertInputOfReflectorNumber(machineCodeDto.getReflectorGreekNumber());
        return repo.getReflectors()[idOfReflector];
    }
    private PlugBoard InitializingPlugBoard(){
        return new PlugBoard(machineCodeDto.getPlugsInUse());
    }
    private char[] reverseInitialPositionsOfRotors(char[] rotorsPositions) {
       return LogicUtilities.reverseArrayChar(rotorsPositions,0);
    }
    private int[] reverseRotorsNumberId (int[] rotorsOrder) {
        return LogicUtilities.reverseArrayInt(rotorsOrder,0);
    }

    public Machine getEnigmaMachine() {
        return enigmaMachine;
    }

}