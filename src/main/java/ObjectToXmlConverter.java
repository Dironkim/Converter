import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
public class ObjectToXmlConverter {
    public static void convertObjectToXml(InternetShopXML internetShop, String xmlFilePath) {
        try {
            // Создание объекта JAXBContext для класса InternetShop
            JAXBContext jaxbContext = JAXBContext.newInstance(InternetShopXML.class);

            // Создание объекта Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // Форматирование вывода (необязательно, делает XML более читаемым)
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Преобразование объекта Java в XML и запись в файл
            jaxbMarshaller.marshal(internetShop, new File(xmlFilePath));

            System.out.println("Object successfully converted to XML and saved to: " + xmlFilePath);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // Предположим, у вас есть объект internetShop
        InternetShopXML internetShop = new InternetShopXML();
        // Здесь вы можете заполнить поля объекта, если необходимо

        // Укажите путь для сохранения XML-файла
        String xmlFilePath = "C:/Users/dd/Desktop/Auto.xml"; // Замените на ваш путь

        // Вызов метода для конвертации объекта в XML и сохранения в файл
        convertObjectToXml(internetShop, xmlFilePath);
    }
}
