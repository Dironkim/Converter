package Converters;

import Structures.InternetShopJSON;
import Structures.InternetShopXML;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JavaClassesConverter {
    private static List<InternetShopJSON.Category> processCategoriesXmlToJson(
            List<InternetShopXML.Category> xmlCategoryList,
            List<InternetShopXML.Promotion> xmlPromotionList) {
        return xmlCategoryList.stream()
                .map(xmlCategory -> InternetShopJSON.Category.builder()
                        .id(xmlCategory.getId())
                        .name(xmlCategory.getName())
                        .products(processProductsXmlToJson(xmlCategory.getProducts().getProductList(), xmlPromotionList))
                        .build())
                .collect(Collectors.toList());
    }
    private static List<InternetShopJSON.Product> processProductsXmlToJson(
            List<InternetShopXML.Product> xmlProductList,
            List<InternetShopXML.Promotion> xmlPromotionList) {
        return xmlProductList.stream()
                .map(xmlProduct -> InternetShopJSON.Product.builder()
                        .id(xmlProduct.getId())
                        .name(xmlProduct.getName())
                        .price(xmlProduct.getPrice())
                        .description(xmlProduct.getDescription())
                        .promotions(processPromotionsXmlToJson(xmlPromotionList, xmlProduct))
                        .build())
                .collect(Collectors.toList());
    }
    private static List<InternetShopJSON.Promotion> processPromotionsXmlToJson(
            List<InternetShopXML.Promotion> xmlPromotionList,
            InternetShopXML.Product xmlProduct) {
        return xmlPromotionList.stream()
                .filter(xmlPromotion -> xmlPromotion.getProducts().getProductList().stream()
                        .anyMatch(product -> product.getId() == xmlProduct.getId()))
                .map(xmlPromotion -> InternetShopJSON.Promotion.builder()
                        .id(xmlPromotion.getId())
                        .name(xmlPromotion.getName())
                        .discount(xmlPromotion.getDiscount() != null ?
                                InternetShopJSON.Discount.builder()
                                        .percentage(xmlPromotion.getDiscount().getPercentage())
                                        .build() : null)
                        .freeShipping(xmlPromotion.getFreeShipping() != null)
                        .build())
                .collect(Collectors.toList());
    }
    public static InternetShopJSON convertToJsonStructure(InternetShopXML xmlShop) {
        List<InternetShopXML.Category> xmlCategories = xmlShop.getCategories().getCategoryList();
        List<InternetShopXML.Promotion> xmlPromotions = xmlShop.getPromotions().getPromotionList();
        return InternetShopJSON.builder()
                .categories(processCategoriesXmlToJson(xmlCategories, xmlPromotions))
                .build();
    }

    public static InternetShopXML convertToXmlStructure(InternetShopJSON jsonShop) {
        List<InternetShopJSON.Category> jsonCategories = jsonShop.getCategories();
        List<InternetShopXML.Promotion> xmlPromotionList = new ArrayList<>();
        List<InternetShopXML.Category> xmlCategoryList = processCategoriesJsonToXml(jsonCategories, xmlPromotionList);

        return InternetShopXML.builder()
                .categories(InternetShopXML.Categories.builder().categoryList(xmlCategoryList).build())
                .promotions(InternetShopXML.Promotions.builder().promotionList(xmlPromotionList).build())
                .build();
    }

    private static List<InternetShopXML.Category> processCategoriesJsonToXml(
            List<InternetShopJSON.Category> jsonCategoryList,
            List<InternetShopXML.Promotion> savedPromotionsXML) {
        return jsonCategoryList.stream()
                .map(jsonCategory -> InternetShopXML.Category.builder()
                        .id(jsonCategory.getId())
                        .name(jsonCategory.getName())
                        .products(new InternetShopXML.Products(processProductsJsonToXml(jsonCategory.getProducts(), savedPromotionsXML)))
                        .build())
                .collect(Collectors.toList());
    }

    private static List<InternetShopXML.Product> processProductsJsonToXml(
            List<InternetShopJSON.Product> jsonProductList,
            List<InternetShopXML.Promotion> savedPromotionsXML) {
        return jsonProductList.stream()
                .map(jsonProduct -> {
                    processPromotionsJsonToXml(jsonProduct, savedPromotionsXML);

                    return InternetShopXML.Product.builder()
                            .id(jsonProduct.getId())
                            .name(jsonProduct.getName())
                            .price(jsonProduct.getPrice())
                            .description(jsonProduct.getDescription())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private static void processPromotionsJsonToXml(
            InternetShopJSON.Product jsonProduct,
            List<InternetShopXML.Promotion> savedPromotionsXML) {
        if (jsonProduct != null && jsonProduct.getPromotions() != null) {
            jsonProduct.getPromotions().stream()
                    .filter(jsonPromotion -> savedPromotionsXML.stream()
                            .noneMatch(savedPromotion -> savedPromotion.getId() == jsonPromotion.getId()))
                    .forEach(jsonPromotion -> {
                        InternetShopXML.Promotion newPromotionXML = InternetShopXML.Promotion.builder()
                                .id(jsonPromotion.getId())
                                .name(jsonPromotion.getName())
                                .products(InternetShopXML.Products.builder().productList(new ArrayList<>()).build())
                                .freeShipping(jsonPromotion.isFreeShipping() ?
                                        new InternetShopXML.FreeShipping() : null)
                                .discount(jsonPromotion.getDiscount() != null ?
                                        InternetShopXML.Discount.builder().percentage(jsonPromotion.getDiscount().getPercentage()).build() : null)
                                .build();

                        savedPromotionsXML.add(newPromotionXML);
                    });

            savedPromotionsXML.forEach(savedPromotion -> {
                boolean productExistsInPromotion = savedPromotion.getProducts().getProductList().stream()
                        .anyMatch(product -> product.getId() == jsonProduct.getId());

                if (!productExistsInPromotion) {
                    InternetShopXML.Product newProductXML = InternetShopXML.Product.builder()
                            .id(jsonProduct.getId())
                            .build();
                    savedPromotion.getProducts().getProductList().add(newProductXML);
                }
            });
        }
    }
}
