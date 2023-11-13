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

    public static void main(String[] args) {
        String xmlFilePath = "src/main/resources/webShop.xml"; // Замените на путь к вашему XML-файлу
        InternetShopXML internetShop = convertXmlToObject(xmlFilePath);

        if (internetShop != null) {
            // Теперь у вас есть объект InternetShop с заполненными полями
            System.out.println("InternetShop object created successfully:");
        } else {
            System.out.println("Failed to create InternetShop object.");
        }
        String xmlFilePath2 = "src/main/resources/New.xml"; // Замените на ваш путь

        // Вызов метода для конвертации объекта в XML и сохранения в файл
        ObjectToXmlConverter.convertObjectToXml(internetShop, xmlFilePath2);
    }
}
