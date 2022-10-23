package xmlFilesHandling;

import generated.CTEEnigma;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ReadXmlFile {
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";

    private final String fileName;
    private CTEEnigma cteEnigma;

    public ReadXmlFile(String xmlFileName) throws JAXBException, FileNotFoundException {
        this.fileName=xmlFileName;
        readXml();
    }

    public CTEEnigma getCteEnigma() {
        return cteEnigma;
    }

    public void readXml() throws FileNotFoundException, JAXBException {
            InputStream inputStream = new FileInputStream(new File(fileName));
            this.cteEnigma = deserializeFrom(inputStream);
    }

    private static CTEEnigma deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (CTEEnigma) u.unmarshal(in);
    }
}



