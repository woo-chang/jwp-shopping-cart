package cart.dto.request;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

public class ProductRequestDto {

    @Size(min = 1, max = 50, message = "상품명은 {min}보다 크고 {max}보다 짧아야 합니다.")
    private final String name;
    @Size(min = 1, max = 255, message = "이미지 경로는 {min}보다 크고 {max}보다 짧아야 합니다.")
    private final String imageUrl;
    @PositiveOrZero(message = "상품 가격은 0보다 작을 수 없습니다.")
    private final Integer price;
    @Size(min = 1, max = 255, message = "상품 설명은 {min}보다 크고 {max}보다 짧아야 합니다.")
    private final String description;
    private final List<Long> categoryIds;

    public ProductRequestDto(
            final String name,
            final String imageUrl,
            final Integer price,
            final String description,
            final List<Long> categoryIds
    ) {
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
