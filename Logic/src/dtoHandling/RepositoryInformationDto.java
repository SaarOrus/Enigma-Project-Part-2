package dtoHandling;

import components.Keyboard;

public class RepositoryInformationDto { // receive information from repository
    private Keyboard keyboard;
    private int totalNumberOfRotors;
    private int numberOfReflectors;
    private int numbersOfRotorsInUse;

    public RepositoryInformationDto(Keyboard keyboard, int totalNumberOfRotors, int numberOfReflectors,int numbersOfRotorsInUse) {
        this.keyboard = keyboard;
        this.totalNumberOfRotors = totalNumberOfRotors;
        this.numberOfReflectors = numberOfReflectors;
        this.numbersOfRotorsInUse=numbersOfRotorsInUse;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public int getTotalNumberOfRotors() {
        return totalNumberOfRotors;
    }

    public int getGetNumberOfReflectors() {
        return numberOfReflectors ;
    }

    public int getNumbersOfRotorsInUse() {
        return numbersOfRotorsInUse;
    }
}

