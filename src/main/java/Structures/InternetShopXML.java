package Structures;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlRootElement(name = "internetShop")
public class InternetShopXML {

    private Categories categories;
    private Promotions promotions;

    @XmlElement(name = "categories")
    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    @XmlElement(name = "promotions")
    public Promotions getPromotions() {
        return promotions;
    }

    public void setPromotions(Promotions promotions) {
        this.promotions = promotions;
    }

    public static class Categories {

        private List<Category> categoryList;

        @XmlElement(name = "category")
        public List<Category> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<Category> categoryList) {
            this.categoryList = categoryList;
        }
    }

    public static class Category {

        private int id;
        private String name;
        private Products products;

        @XmlAttribute
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @XmlAttribute
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement(name = "products")
        public Products getProducts() {
            return products;
        }

        public void setProducts(Products products) {
            this.products = products;
        }
    }

    public static class Products {

        private List<Product> productList;

        @XmlElement(name = "product")
        public List<Product> getProductList() {
            return productList;
        }

        public void setProductList(List<Product> productList) {
            this.productList = productList;
        }
    }

    public static class Product {

        private int id;
        private String name;
        private Integer price;
        private String description;
        @XmlAttribute
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @XmlAttribute
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement(name="price")
        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }
        @XmlElement
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
    public static class Promotions {

        private List<Promotion> promotionList;

        @XmlElement(name = "promotion")
        public List<Promotion> getPromotionList() {
            return promotionList;
        }

        public void setPromotionList(List<Promotion> promotionList) {
            this.promotionList = promotionList;
        }
    }

    public static class Promotion {

        private int id;
        private String name;
        private Discount discount;
        private FreeShipping freeShipping;
        private Products products;

        @XmlAttribute
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @XmlAttribute
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlElement(name = "discount")
        public Discount getDiscount() {
            return discount;
        }

        public void setDiscount(Discount discount) {
            this.discount = discount;
        }

        @XmlElement(name = "freeShipping")
        public FreeShipping getFreeShipping() {
            return freeShipping;
        }

        public void setFreeShipping(FreeShipping freeShipping) {
            this.freeShipping = freeShipping;
        }

        @XmlElement(name = "products")
        public Products getProducts() {
            return products;
        }

        public void setProducts(Products products) {
            this.products = products;
        }
    }

    public static class Discount {

        private int percentage;

        @XmlAttribute
        public int getPercentage() {
            return percentage;
        }

        public void setPercentage(int percentage) {
            this.percentage = percentage;
        }
    }

    public static class FreeShipping {

        // Пустой класс, представляющий бесплатную доставку
    }
}


