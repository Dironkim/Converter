package Converters;

import Structures.InternetShopXML;

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

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
