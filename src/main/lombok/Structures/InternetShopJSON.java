package Structures;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Data
public class InternetShopJSON {

    private List<Category> categories;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Category {

        private int id;
        private String name;
        private List<Product> products;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {

        private int id;
        private String name;
        private int price;
        private String description;
        private List<Promotion> promotions;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Promotion {

        private int id;
        private String name;
        private Discount discount;
        private boolean freeShipping;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Discount {

        private int percentage;
    }
}
