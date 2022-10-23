package bruteForce;

import java.util.List;

public class DictionaryDecipher {
    private List<String> dictionaryWordList;
    private final String excludeChars;
    private TrieDictionary dictionaryTrie;

    //ctor
    public DictionaryDecipher(List<String> dictionary, String excludeChars) {
        this.dictionaryWordList = dictionary;
        this.excludeChars = excludeChars;
        buildDictionaryTree();
    }

    //get functions
    public List<String> getDictionaryWordList() {
        return dictionaryWordList;
    }

    public void buildDictionaryTree() {
        this.dictionaryTrie = new TrieDictionary(dictionaryWordList);
    }

    public boolean checkIfStringIsExist(String encodeString) {
        String[] ListOfEncodeInput;
        ListOfEncodeInput = encodeString.split("\\s+");
        for (String encodeWord : ListOfEncodeInput) {
            if (!dictionaryWordList.contains(encodeWord))
                return false;
        }
        return true;
    }

    public List<String> getWordsFromDictionaryBySearch(String searchStr) {
        return dictionaryTrie.suggest(searchStr.toUpperCase());
    }

    public String deleteInvalidCharactersFromEncodeString(String encodeString) {
        encodeString=encodeString.trim();
        for (int i = 0; i < excludeChars.length(); i++) {
            encodeString = encodeString.replace(String.valueOf(excludeChars.charAt(i)), "");
        }
        return encodeString;
    }

}
