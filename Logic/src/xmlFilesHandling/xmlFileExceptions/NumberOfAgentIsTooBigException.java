package xmlFilesHandling.xmlFileExceptions;

public class NumberOfAgentIsTooBigException extends RuntimeException {
    private String EXCEPTION_MESSAGE;

    public NumberOfAgentIsTooBigException(int numberOfAgent){
        EXCEPTION_MESSAGE = "The number of agents is "+ numberOfAgent +" - more then 50!";
    }
    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}


