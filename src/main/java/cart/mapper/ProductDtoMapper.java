package cart.mapper;

import cart.dto.ProductDto;
import cart.dto.request.ProductRequestDto;

public class ProductDtoMapper {

    public static ProductDto from(final ProductRequestDto productRequestDto) {
        if (productRequestDto == null) {
            return null;
        }

        return new ProductDto(
                productRequestDto.getName(),
                productRequestDto.getImageUrl(),
                productRequestDto.getPrice(),
                productRequestDto.getDescription(),
                productRequestDto.getCategoryIds()
        );
    }
}
