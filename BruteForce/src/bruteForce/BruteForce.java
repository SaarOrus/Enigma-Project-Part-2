package bruteForce;

import bruteForce.dm.DecryptionManager;
import engine.Engine;
import xmlFilesHandling.CheckXMLFile;
import xmlFilesHandling.ReadXmlFile;
import xmlFilesHandling.convertedCte.ConvertCteToDecipher;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class BruteForce {
    private final DecryptionManager decryptionManager;
    private int numberOfAgents;
    private DictionaryDecipher dictionaryDecipher;

    //ctor
    public BruteForce(Engine engine,String pathOfXml) throws JAXBException, FileNotFoundException {
        createRepositoryFromXML(pathOfXml);
        this.decryptionManager=new DecryptionManager(engine, dictionaryDecipher);
    }

    public void createRepositoryFromXML(String pathOfXml) throws JAXBException, FileNotFoundException {
        ReadXmlFile readXml= new ReadXmlFile(pathOfXml);
        CheckXMLFile checkXMLFile= new CheckXMLFile(readXml.getCteEnigma());
        ConvertCteToDecipher convertCteToDecipher=new ConvertCteToDecipher(readXml.getCteEnigma().getCTEDecipher());
        this.dictionaryDecipher=new DictionaryDecipher(convertCteToDecipher.getDictionaryWordList(),convertCteToDecipher.getExcludeChars());
        this.numberOfAgents=convertCteToDecipher.getNumberOfAgents();
    }

    //get functions
    public DecryptionManager getDecryptionManager() {
        return decryptionManager;
    }
    public DictionaryDecipher getDictionaryDecipher() {
        return dictionaryDecipher;
    }
    public int getNumberOfAgents() {
        return numberOfAgents;
    }

}
