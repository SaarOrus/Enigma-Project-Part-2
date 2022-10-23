package xmlFilesHandling.xmlFileExceptions;

public class ReflectorIdIsNotUniqueException extends RuntimeException {
    private String EXCEPTION_MESSAGE;

    public ReflectorIdIsNotUniqueException(String ReflectorId){
        EXCEPTION_MESSAGE = "Reflector id: "+ReflectorId+" belongs to two different Reflectors!";
    }
    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}
