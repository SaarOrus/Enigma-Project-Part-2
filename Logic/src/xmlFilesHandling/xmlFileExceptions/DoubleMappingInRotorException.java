package xmlFilesHandling.xmlFileExceptions;

public class DoubleMappingInRotorException extends RuntimeException{
    private String EXCEPTION_MESSAGE;

    public DoubleMappingInRotorException(int idOfRotor){
        EXCEPTION_MESSAGE = "There is a double mapping in rotor "+idOfRotor+ " !";
    }
    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}
