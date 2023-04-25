package cart.dto;

import java.util.List;

public class ProductDto {

    private final String name;
    private final String imageUrl;
    private final Integer price;
    private final String description;
    private final List<Long> categoryIds;

    public ProductDto(String name, String imageUrl, Integer price, String description, List<Long> categoryIds) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.categoryIds = categoryIds;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }
}
