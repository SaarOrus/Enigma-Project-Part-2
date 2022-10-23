package xmlFilesHandling.xmlFileExceptions;

public class NumberOfAgentIsTooSmallException extends RuntimeException {
    private String EXCEPTION_MESSAGE;

    public NumberOfAgentIsTooSmallException(int numberOfAgent){
        EXCEPTION_MESSAGE = "The number of agents is "+ numberOfAgent +"- less then 2!";
    }
    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}

