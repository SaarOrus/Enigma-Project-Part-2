package xmlFilesHandling;

import Utilies.LogicUtilities;
import generated.*;
import xmlFilesHandling.xmlFileExceptions.*;

import java.util.List;

public class CheckXMLFile {
    private final CTEMachine machine;
    private final CTEDecipher decipher;
    private final int MAX_ROTOR_COUNT=99;
    // check valid xml function
    public CheckXMLFile(CTEEnigma cteEnigma)  {
        this.machine = cteEnigma.getCTEMachine();
        this.decipher=cteEnigma.getCTEDecipher();
        checkXmlExceptions();
    }
    public void checkXmlExceptions() {
        NumberOfCharIsZero();
        isNumberOfCharactersEven();
        isRotorCountBiggerThenNumbersOfRotors();
        isNumbersOfRotorsBiggerThenOne();
        isRotorCountBiggerThenMaximum();
        isReflectorIdIsUniqueAndValid();
        isReflectCharToAnotherCharValid();
        isRotorIdIsUnique();
        isValidNotch();
        isDuplicateMappingsInRotor();
        isNumberOfDecipherSmallerThenTwo();
        isNumberOfDecipherBiggerThenFifty();

    }

    public void isRotorCountBiggerThenMaximum() throws RotorCountBiggerThenMaximum {
        if (machine.getRotorsCount() > MAX_ROTOR_COUNT)
            throw new RotorCountBiggerThenMaximum(machine.getRotorsCount());
    }

    public void NumberOfCharIsZero() throws NumberOfCharIsZeroException {
        if (machine.getABC().trim().length() == 0)
            throw new NumberOfCharIsZeroException();
    }
    public void isNumberOfCharactersEven() throws NumberOfCharactersNotEvenException {
        if(machine.getABC().trim().length()%2!=0)
            throw new NumberOfCharactersNotEvenException();
    }
    public void isRotorCountBiggerThenNumbersOfRotors() throws RotorsCountIsBiggerThenRotorsNumberException {
        if (machine.getRotorsCount() > machine.getCTERotors().getCTERotor().size())
            throw new RotorsCountIsBiggerThenRotorsNumberException(machine.getRotorsCount(),machine.getCTERotors().getCTERotor().size());

    }
    public void isNumbersOfRotorsBiggerThenOne() throws NumberOfRotorsIsLessThenTwoException {
        if (machine.getRotorsCount() < 2) {
            throw new NumberOfRotorsIsLessThenTwoException(machine.getRotorsCount());
        }

    }
    public void isRotorIdIsUnique() throws RotorIdIsNotUniqueException, RotorIdIsMissingException, RotorIdOutOfRangeException {
        List<CTERotor> cteRotors = machine.getCTERotors().getCTERotor();
        boolean[] isFoundId=  new boolean[cteRotors.size()+1];
        for (int i = 0; i < cteRotors.size(); i++) {
            if(cteRotors.get(i).getId()>cteRotors.size()||cteRotors.get(i).getId()<1){
                throw new RotorIdOutOfRangeException(cteRotors.get(i).getId(),cteRotors.size());
            }
            else if(isFoundId[cteRotors.get(i).getId()])
                throw new RotorIdIsNotUniqueException(cteRotors.get(i).getId());
            else
                isFoundId[cteRotors.get(i).getId()]=true;
        }
        for (int i = 1; i <= cteRotors.size(); i++) {

            if(!isFoundId[cteRotors.get(i-1).getId()])
                throw new RotorIdIsMissingException(i);
        }
    }

    public void isDuplicateMappingsInRotor() throws DoubleMappingInRotorException {
        List<CTERotor> cteRotors = machine.getCTERotors().getCTERotor();
        int sizeOfCol= cteRotors.get(0).getCTEPositioning().size()+1; //num of char
        for (int i = 0; i < cteRotors.size(); i++) {
            char [] rightCol= new char[sizeOfCol];
            char [] leftCol=new char[sizeOfCol];
            for (int j = 0; j < cteRotors.get(i).getCTEPositioning().size(); j++) {
                leftCol[j+1]= Character.toUpperCase(cteRotors.get(i).getCTEPositioning().get(j).getLeft().charAt(0));
                rightCol[j+1]=Character.toUpperCase(cteRotors.get(i).getCTEPositioning().get(j).getRight().charAt(0));
            }
            if(isDuplicateInArray(leftCol)||isDuplicateInArray(rightCol))
                throw new DoubleMappingInRotorException(cteRotors.get(i).getId());
        }
    }

    public boolean isDuplicateInArray(char[] charArray) {
        boolean[] isCharAppearInArr = new boolean[charArray.length];
        String keyboard = machine.getABC().trim().toUpperCase();
        for (int i = 1; i < charArray.length; i++) {
            if (isCharAppearInArr[keyboard.indexOf(charArray[i])])
                return true;
            else
                isCharAppearInArr[keyboard.indexOf(charArray[i])] = true;
        }
        return false;
    }
    public void isReflectCharToAnotherCharValid() throws ReflectCharToTheSameCharException {
        List<CTEReflector> cteReflector = machine.getCTEReflectors().getCTEReflector();
        for (int i = 0; i < cteReflector.size(); i++) {
            for (int j = 0; j < cteReflector.get(i).getCTEReflect().size(); j++) {
                int input = cteReflector.get(i).getCTEReflect().get(j).getInput();
                int output = cteReflector.get(i).getCTEReflect().get(j).getOutput();
                if (input == output)
                    throw new ReflectCharToTheSameCharException(cteReflector.get(i).getId(), input);
            }
        }
    }

    public void isValidNotch() throws NotchPlaceOutOfRangeException {
        List<CTERotor> cteRotors = machine.getCTERotors().getCTERotor();
        int sizeOfABC = machine.getABC().trim().length();
        for (int i = 0; i < cteRotors.size(); i++) {
            if (cteRotors.get(i).getNotch() > sizeOfABC || cteRotors.get(i).getNotch() < 1)
                throw new NotchPlaceOutOfRangeException(cteRotors.get(i).getId(), cteRotors.get(i).getNotch());
        }
    }
    public void isReflectorIdIsUniqueAndValid() throws ReflectorIdIsOutOfRangeException, ReflectorIdIsNotUniqueException, ReflectorIdIsMissingException {
        List<CTEReflector> cteReflector = machine.getCTEReflectors().getCTEReflector();
        boolean[] isFoundId = new boolean[cteReflector.size() + 1];
        for (int i = 0; i < cteReflector.size(); i++) {
            if (LogicUtilities.convertInputOfReflectorNumber(cteReflector.get(i).getId()) == -1 || LogicUtilities.convertInputOfReflectorNumber(cteReflector.get(i).getId()) > cteReflector.size())
                throw new ReflectorIdIsOutOfRangeException(cteReflector.get(i).getId(), cteReflector.size());
            else if (isFoundId[LogicUtilities.convertInputOfReflectorNumber(cteReflector.get(i).getId())])
                throw new ReflectorIdIsNotUniqueException(cteReflector.get(i).getId());
            else
                isFoundId[LogicUtilities.convertInputOfReflectorNumber(cteReflector.get(i).getId())] = true;
        }
        for (int i = 1; i <= cteReflector.size(); i++) {
            if (!isFoundId[i])
                throw new ReflectorIdIsMissingException(LogicUtilities.convertNumberToReflectorId(i));
        }
    }
    public void isNumberOfDecipherSmallerThenTwo() throws NumberOfAgentIsTooSmallException {
        if(decipher.getAgents()<2)
            throw new NumberOfAgentIsTooSmallException(decipher.getAgents());


    }
    public void isNumberOfDecipherBiggerThenFifty() throws NumberOfAgentIsTooBigException {
        if(decipher.getAgents()>50)
            throw new NumberOfAgentIsTooBigException(decipher.getAgents());
    }
}