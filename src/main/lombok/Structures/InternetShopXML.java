package Structures;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "internetShop")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternetShopXML {

    @XmlElement(name = "categories")
    private Categories categories;

    @XmlElement(name = "promotions")
    private Promotions promotions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Categories {

        @XmlElement(name = "category")
        private List<Category> categoryList;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {

        @XmlAttribute
        private int id;

        @XmlAttribute
        private String name;

        @XmlElement(name = "products")
        private Products products;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Products {

        @XmlElement(name = "product")
        private List<Product> productList;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlType(propOrder = {"price", "description"})
    public static class Product {

        @XmlAttribute
        private int id;

        @XmlAttribute
        private String name;

        @XmlElement(name = "price")
        private Integer price;

        @XmlElement(name = "description")
        private String description;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Promotions {

        @XmlElement(name = "promotion")
        private List<Promotion> promotionList;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Promotion {

        @XmlAttribute
        private int id;

        @XmlAttribute
        private String name;

        @XmlElement(name = "discount")
        private Discount discount;

        @XmlElement(name = "freeShipping")
        private FreeShipping freeShipping;

        @XmlElement(name = "products")
        private Products products;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Discount {

        @XmlAttribute
        private int percentage;
    }

    // Пустой класс, представляющий бесплатную доставку
    public static class FreeShipping {
    }
}
