package repository;

import components.Keyboard;
import components.Reflector;
import components.Rotor;

public class RepositoryOfComponents {
    private final Reflector[] reflectors;
    private final Rotor[] rotors;
    private final Keyboard keyboard;
    private int numberOfRotorsInUse;

    //copy ctor
    public RepositoryOfComponents(RepositoryOfComponents repository){
        Reflector copyReflectorArr[]=new Reflector[repository.getReflectors().length];
        for (int i = 1; i < repository.getReflectors().length; i++) {
            copyReflectorArr[i]=new Reflector(repository.reflectors[i]);
        }
        Rotor copyRotor[]=new Rotor[repository.rotors.length];
        for (int i = 1; i < repository.rotors.length; i++) {
            copyRotor[i]=new Rotor(repository.rotors[i]);
        }
        Keyboard copyKeyBoard=new Keyboard(repository.getKeyboard());
        this.rotors=copyRotor;
        this.reflectors=copyReflectorArr;
        this.keyboard=copyKeyBoard;
        this.numberOfRotorsInUse=repository.getNumberOfRotorsInUse();
    }
    //ctor
    public RepositoryOfComponents(Keyboard keyboard,Reflector[] reflectors, Rotor[] rotors,int numberOfRotorsInUse) {
        this.keyboard=keyboard;
        this.reflectors = reflectors;
        this.rotors = rotors;
        this.numberOfRotorsInUse =numberOfRotorsInUse;
    }
    // get functions
    public Rotor getRotorInIndexInd(int index) {
        return rotors[index];
    }
    public Keyboard getKeyboard() {
        return keyboard;
    }
    public Rotor[] getRotors() {
        return rotors;
    }
    public Reflector[] getReflectors() {
        return reflectors;
    }
    public int getNumberOfRotors(){
        return rotors.length;
    }
    public int getNumberOfReflectors(){
        return reflectors.length-1;
    }
    public int getNumberOfRotorsInUse() {
        return numberOfRotorsInUse;
    }
}


