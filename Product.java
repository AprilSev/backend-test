package market;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {

    private Long id;
    private Integer price;
    private String title;
    private String categoryTitle;

    public Product(ProductBuilder builder) {

        this.id = builder.id;
        this.price = builder.price;
        this.title = builder.title;
        this.categoryTitle = builder.categoryTitle;
    }


    public Long getId() {
        return id;
    }

    public Integer getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public static class ProductBuilder {

        private Long id;
        private Integer price;
        private String title;
        private String categoryTitle;

        public ProductBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder withPrice(Integer price) {
            this.price = price;
            return this;
        }

        public ProductBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        public ProductBuilder withCategoryTitle(String categoryTitle) {
            this.categoryTitle = categoryTitle;
            return this;
        }

        public Product build() {
            return new Product(this);
        }

    }
}
