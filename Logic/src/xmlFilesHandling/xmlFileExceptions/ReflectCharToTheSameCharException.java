package xmlFilesHandling.xmlFileExceptions;

public class ReflectCharToTheSameCharException extends RuntimeException{
    private String EXCEPTION_MESSAGE;

    public ReflectCharToTheSameCharException(String selfMappedReflector, int selfMappedIndex ) {
        EXCEPTION_MESSAGE = "Reflector "+selfMappedReflector+" Mapped "+selfMappedIndex+" to "+ selfMappedIndex+ " NOT VALID!";
    }

    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}
