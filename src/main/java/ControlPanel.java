import Converters.*;
import Structures.InternetShopJSON;
import Structures.InternetShopXML;

public class ControlPanel {
    public static void main(String[] args) {
        if (args.length!=2)
            System.out.println("Argument error: 2 file paths required");
        else
            convert(args[0],args[1]);
        //convertXmlToJson("D:\\java_exec\\webShop.xml","D:\\java_exec\\new3.json");
        //convertJsonToXml("D:\\java_exec\\new3.json","D:\\java_exec\\back3.xml");
    }
    public static void convert(String file1, String file2){
        if (file1.endsWith(".xml") && file2.endsWith(".json")){
            convertXmlToJson(file1,file2);
        } else if (file1.endsWith(".json") && file2.endsWith(".xml")) {
            convertJsonToXml(file1, file2);
        } else{
            System.out.println("File format error");
        }
    }
    public static void convertXmlToJson(String xmlFilePath, String jsonFilePath) {
        try {
            // Чтение и преобразование XML в объект Java
            InternetShopXML internetShopXML = XmlToObjectConverter.convertXmlToObject(xmlFilePath);
            InternetShopJSON internetShopJSON = JavaClassesConverter.convertToJsonStructure(internetShopXML);
            ObjectToJsonConverter.convertToJsonFile(internetShopJSON,jsonFilePath);
            System.out.println("Object successfully converted to JSON and saved to: " + jsonFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("XML to JSON error: " + e.getMessage());
        }
    }
    public static void convertJsonToXml(String jsonFilePath, String xmlFilePath) {
        try {
            InternetShopJSON internetShopJSON = JsonToObjectConverter.convert(jsonFilePath);
            InternetShopXML internetShopXML = JavaClassesConverter.convertToXmlStructure(internetShopJSON);
            ObjectToXmlConverter.convertObjectToXml(internetShopXML, xmlFilePath);
            System.out.println("Object successfully converted to XML and saved to: " + xmlFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("JSON to XML error: " + e.getMessage());
        }
    }
}
