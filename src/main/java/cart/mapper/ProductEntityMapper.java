package cart.mapper;

import cart.dto.ProductDto;
import cart.entity.product.ProductEntity;

public class ProductEntityMapper {

    public static ProductEntity from(final ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        return new ProductEntity(
                productDto.getName(),
                productDto.getImageUrl(),
                productDto.getPrice(),
                productDto.getDescription()
        );
    }
}
