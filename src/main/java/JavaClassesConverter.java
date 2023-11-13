import java.util.ArrayList;
import java.util.List;

public class JavaClassesConverter {
    private static List<InternetShopJSON.Category> processCategoriesXmlToJson(List<InternetShopXML.Category> xmlCategoryList, List<InternetShopXML.Promotion> xmlPromotionList){
        List<InternetShopJSON.Category> jsonCategories = new ArrayList<>();
        for (InternetShopXML.Category xmlCategory : xmlCategoryList) {
            InternetShopJSON.Category jsonCategory = new InternetShopJSON.Category();
            jsonCategory.setId(xmlCategory.getId());
            jsonCategory.setName(xmlCategory.getName());
            List<InternetShopXML.Product> xmlProductList = xmlCategory.getProducts().getProductList();
            List<InternetShopJSON.Product>jsonProductList = processProductsXmlToJson(xmlProductList,xmlPromotionList);
            jsonCategory.setProducts(jsonProductList);
            jsonCategories.add(jsonCategory);
        }
        return jsonCategories;
    }
    private static List<InternetShopJSON.Product> processProductsXmlToJson(List<InternetShopXML.Product> xmlProductList, List<InternetShopXML.Promotion> xmlPromotionList){
        List<InternetShopJSON.Product> jsonProductList = new ArrayList<>();
        for (InternetShopXML.Product xmlProduct : xmlProductList) {
            InternetShopJSON.Product jsonProduct = new InternetShopJSON.Product();
            jsonProduct.setId(xmlProduct.getId());
            jsonProduct.setName(xmlProduct.getName());
            jsonProduct.setPrice(xmlProduct.getPrice());
            jsonProduct.setDescription(xmlProduct.getDescription());
            List<InternetShopJSON.Promotion> jsonPromotionList = processPromotionsXmlToJson(xmlPromotionList,xmlProduct);
            jsonProduct.setPromotions(jsonPromotionList);
            jsonProductList.add(jsonProduct);
        }
        return jsonProductList;
    }
    private static List<InternetShopJSON.Promotion> processPromotionsXmlToJson(List<InternetShopXML.Promotion>xmlPromotionList, InternetShopXML.Product xmlProduct){
        List<InternetShopJSON.Promotion> jsonPromotions = new ArrayList<>();
        for (InternetShopXML.Promotion xmlPromotion : xmlPromotionList) {
            // Проверяем, участвует ли текущий продукт в данной акции
            if (xmlPromotion.getProducts().getProductList().stream()
                    .anyMatch(product -> product.getId() == xmlProduct.getId())) {
                InternetShopJSON.Promotion jsonPromotion = new InternetShopJSON.Promotion();
                jsonPromotion.setId(xmlPromotion.getId());
                jsonPromotion.setName(xmlPromotion.getName());
                InternetShopXML.Discount xmlDiscount = xmlPromotion.getDiscount();
                if (xmlDiscount != null) {
                    InternetShopJSON.Discount jsonDiscount = new InternetShopJSON.Discount();
                    jsonDiscount.setPercentage(xmlDiscount.getPercentage());
                    jsonPromotion.setDiscount(jsonDiscount);
                }
                //В json структуре вместо класса FreeShipping просто флаг boolean
                jsonPromotion.setFreeShipping(xmlPromotion.getFreeShipping() != null);
                jsonPromotions.add(jsonPromotion);
            }
        }
        return jsonPromotions;
    }

    public static InternetShopJSON convertToJsonStructure(InternetShopXML xmlShop) {
        InternetShopJSON jsonShop = new InternetShopJSON();
        List<InternetShopXML.Category> xmlCategories = xmlShop.getCategories().getCategoryList();
        List<InternetShopXML.Promotion> xmlPromotions = xmlShop.getPromotions().getPromotionList();
        List<InternetShopJSON.Category> jsonCategories = processCategoriesXmlToJson(xmlCategories,xmlPromotions);
        jsonShop.setCategories(jsonCategories);
        return jsonShop;
    }
    private static List<InternetShopXML.Category> processCategoriesJsonToXml(List<InternetShopJSON.Category> jsonCategoryList, List<InternetShopXML.Promotion> savedPromotionsXML){
        if (jsonCategoryList != null && !jsonCategoryList.isEmpty()) {
            List<InternetShopXML.Category> xmlCategoryList = new ArrayList<>();
            for (InternetShopJSON.Category jsonCategory : jsonCategoryList){
                InternetShopXML.Category xmlCategory = new InternetShopXML.Category();
                xmlCategory.setId(jsonCategory.getId());
                xmlCategory.setName(jsonCategory.getName());
                List<InternetShopJSON.Product>jsonProductList=jsonCategory.getProducts();
                InternetShopXML.Products xmlProducts= new InternetShopXML.Products();
                List<InternetShopXML.Product> xmlProductList= processProductsJsonToXml(jsonProductList,savedPromotionsXML);
                xmlProducts.setProductList(xmlProductList);
                xmlCategory.setProducts(xmlProducts);
                xmlCategoryList.add(xmlCategory);
            }
            return xmlCategoryList;
        }
        else return null;
    }
    private static List<InternetShopXML.Product> processProductsJsonToXml(List<InternetShopJSON.Product> jsonProductList, List<InternetShopXML.Promotion> savedPromotionsXML){
        if (jsonProductList != null && !jsonProductList.isEmpty()) {
            List<InternetShopXML.Product> xmlProductList = new ArrayList<>();
            for (InternetShopJSON.Product jsonProduct : jsonProductList) {
                InternetShopXML.Product xmlProduct = new InternetShopXML.Product();
                xmlProduct.setId(jsonProduct.getId());
                xmlProduct.setName(jsonProduct.getName());
                xmlProduct.setPrice(jsonProduct.getPrice());
                xmlProduct.setDescription(jsonProduct.getDescription());
                processPromotionsJsonToXml(jsonProduct,savedPromotionsXML);
                xmlProductList.add(xmlProduct);
            }
            return xmlProductList;
        }
        else return null;
    }
    private static void processPromotionsJsonToXml(InternetShopJSON.Product jsonProduct, List<InternetShopXML.Promotion> savedPromotionsXML){
        if (jsonProduct!=null) {
            List<InternetShopJSON.Promotion>jsonPromotionList=jsonProduct.getPromotions();
            if (jsonPromotionList != null && !jsonPromotionList.isEmpty()) {
                for (InternetShopJSON.Promotion jsonPromotion : jsonPromotionList) {
                    if (savedPromotionsXML.stream()
                            .noneMatch(savedPromotion -> savedPromotion.getId() == jsonPromotion.getId())){
                        InternetShopXML.Promotion newPromotionXML = new InternetShopXML.Promotion();
                        newPromotionXML.setId(jsonPromotion.getId());
                        newPromotionXML.setName(jsonPromotion.getName());
                        InternetShopXML.Products xmlProducts=new InternetShopXML.Products();
                        xmlProducts.setProductList(new ArrayList<>());
                        newPromotionXML.setProducts(xmlProducts);
                        if (jsonPromotion.isFreeShipping()) newPromotionXML.setFreeShipping(new InternetShopXML.FreeShipping());
                        if (jsonPromotion.getDiscount()!=null)
                        {
                            InternetShopXML.Discount xmlDiscount= new InternetShopXML.Discount();
                            xmlDiscount.setPercentage(jsonPromotion.getDiscount().getPercentage());
                            newPromotionXML.setDiscount(xmlDiscount);
                        }
                        savedPromotionsXML.add(newPromotionXML);
                    }
                    // Получаем акцию XML из ведущегося списка с нужным Id
                    InternetShopXML.Promotion existingPromotionXML = savedPromotionsXML.stream()
                            .filter(savedPromotion -> savedPromotion.getId() == jsonPromotion.getId())
                            .findFirst()
                            .orElse(null);
                    // Проверка наличия продукта в акции
                    boolean productExistsInPromotion = existingPromotionXML != null &&
                            existingPromotionXML.getProducts().getProductList().stream()
                                    .anyMatch(product -> product.getId() == jsonProduct.getId());
                    // Если продукта нет, добавляем его
                    if (!productExistsInPromotion && existingPromotionXML != null) {
                        InternetShopXML.Product newProductXML = new InternetShopXML.Product();
                        newProductXML.setId(jsonProduct.getId());
                        // Добавление нового продукта в акцию
                        existingPromotionXML.getProducts().getProductList().add(newProductXML);
                    }
                }
            }
        }
    }
    public static InternetShopXML convertToXmlStructure(InternetShopJSON jsonShop) {
        InternetShopXML xmlShop = new InternetShopXML();
        List<InternetShopJSON.Category> jsonCategories = jsonShop.getCategories();
        List<InternetShopXML.Promotion> xmlPromotionList=new ArrayList<>();
        List<InternetShopXML.Category> xmlCategoryList= processCategoriesJsonToXml(jsonCategories,xmlPromotionList);
        InternetShopXML.Categories xmlCategories = new InternetShopXML.Categories();
        xmlCategories.setCategoryList(xmlCategoryList);
        xmlShop.setCategories(xmlCategories);
        InternetShopXML.Promotions xmlPromotions =new InternetShopXML.Promotions();
        xmlPromotions.setPromotionList(xmlPromotionList);
        xmlShop.setPromotions(xmlPromotions);
        return xmlShop;
    }
}
