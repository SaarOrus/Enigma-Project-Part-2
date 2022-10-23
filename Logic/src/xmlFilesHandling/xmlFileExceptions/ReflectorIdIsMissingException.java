package xmlFilesHandling.xmlFileExceptions;

public class ReflectorIdIsMissingException extends RuntimeException{
    private String EXCEPTION_MESSAGE;

    public ReflectorIdIsMissingException(String ReflectorId){
        EXCEPTION_MESSAGE = "Reflector : "+ReflectorId+" Does not exist!";
    }
    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}


