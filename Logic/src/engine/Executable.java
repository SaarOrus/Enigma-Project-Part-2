package engine;

public interface Executable {

    void initialCodeManually();
    void initialCodeAutomatically();
    String encodeMessage(String stringToEncode, boolean isNeedToUpdate);
    void resetCodeToInitializeCode();
}
