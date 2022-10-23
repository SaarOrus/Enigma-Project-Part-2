package xmlFilesHandling.xmlFileExceptions;

public class FileIsNotOfXMLTypeException extends RuntimeException{
    private String EXCEPTION_MESSAGE;
    public FileIsNotOfXMLTypeException(String xmlFileName){
        EXCEPTION_MESSAGE="The file "+ xmlFileName+ "is not of XML type!";
    }
    @Override
    public String getMessage() {
        return EXCEPTION_MESSAGE;
    }
}

