package xmlFilesHandling.convertedCte;

import bruteForce.DictionaryDecipher;
import generated.CTEDecipher;

import java.util.*;

public class ConvertCteToDecipher {
    private String excludeChars;
    private List<String> dictionaryWordList;
    private DictionaryDecipher dictionary;
    private int numberOfAgents;

    public ConvertCteToDecipher(CTEDecipher cteDecipher) {
        this.excludeChars = cteDecipher.getCTEDictionary().getExcludeChars();
        dictionaryWordList = new ArrayList<>();
        buildDictionaryWords(cteDecipher.getCTEDictionary().getWords());
        this.dictionary = new DictionaryDecipher(dictionaryWordList, excludeChars);
        numberOfAgents = cteDecipher.getAgents();
    }

    public void buildDictionaryWords(String dictionaryWord) {
        Set<String> setWords = new HashSet<>(Arrays.asList(dictionaryWord.split(" ")));
        List<String> dictionaryWordListWithWrongWords = new ArrayList<>(setWords);
        deleteWordsWithExcludeChars(dictionaryWordListWithWrongWords);

    }

    public void deleteWordsWithExcludeChars(List<String> dictionaryWordListWithWrongWords) {
        boolean isWrongWord = false;
        for (String word : dictionaryWordListWithWrongWords) {
            for (int i = 0; i < excludeChars.length(); i++) {
                if (word.contains(String.valueOf(excludeChars.charAt(i)))) {
                    isWrongWord = true;
                    break;
                }
            }
            if (!isWrongWord)
                dictionaryWordList.add(word.toUpperCase().trim());
            else
                isWrongWord = false;
        }
    }

    public List<String> getDictionaryWordList() {
        return dictionaryWordList;
    }

    public String getExcludeChars() {
        return excludeChars;
    }

    public int getNumberOfAgents() {
        return numberOfAgents;
    }
}
