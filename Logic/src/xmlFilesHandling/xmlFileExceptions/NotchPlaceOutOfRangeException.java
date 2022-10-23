package xmlFilesHandling.xmlFileExceptions;

public class NotchPlaceOutOfRangeException extends RuntimeException{

    private String EXCEPTION_MESSAGE;

    public NotchPlaceOutOfRangeException(int idOfRotor,int notchIndex){
        EXCEPTION_MESSAGE = "Notch place in rotor "+idOfRotor+" is " + notchIndex +" OUT OF ROTOR RANGE!";
    }
    @Override
    public String getMessage(){
        return EXCEPTION_MESSAGE;
    }
}
