import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;

public class XmlToObjectConverter {

    public static InternetShopXML convertXmlToObject(String xmlFilePath) {
        try {
            // Создание объекта JAXBContext для класса InternetShop
            JAXBContext jaxbContext = JAXBContext.newInstance(InternetShopXML.class);

            // Создание объекта Unmarshaller
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // Чтение XML-файла и преобразование его в объект Java
            File xmlFile = new File(xmlFilePath);
            InternetShopXML internetShop = (InternetShopXML) jaxbUnmarshaller.unmarshal(xmlFile);

            return internetShop;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

}
