package cart.dto.response;

import cart.entity.CategoryEntity;
import cart.entity.product.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProductResponseDto {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;
    private final String description;
    private final List<String> categoryNames;

    private ProductResponseDto(
            final Long id,
            final String name,
            final String imageUrl,
            final Integer price,
            final String description,
            final List<String> categoryNames
    ) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.categoryNames = categoryNames;
    }

    public static ProductResponseDto of(final ProductEntity productEntity, final List<CategoryEntity> categoryEntities) {
        return new ProductResponseDto(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getImageUrl(),
                productEntity.getPrice(),
                productEntity.getDescription(),
                getCategoryNames(categoryEntities)
        );
    }

    private static List<String> getCategoryNames(final List<CategoryEntity> categoryEntities) {
        return categoryEntities.stream()
                .map(CategoryEntity::getName)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
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

    public List<String> getCategoryNames() {
        return categoryNames;
    }
}
