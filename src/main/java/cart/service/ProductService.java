package cart.service;

import cart.dao.CategoryDao;
import cart.dao.ProductCategoryDao;
import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.dto.response.ProductResponseDto;
import cart.entity.CategoryEntity;
import cart.entity.ProductCategoryEntity;
import cart.entity.product.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductDao productDao;
    private final CategoryDao categoryDao;
    private final ProductCategoryDao productCategoryDao;

    public ProductService(
            final ProductDao productDao,
            final CategoryDao categoryDao,
            final ProductCategoryDao productCategoryDao
    ) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
        this.productCategoryDao = productCategoryDao;
    }

    public Long register(final ProductDto productDto) {
        final ProductEntity productEntity = new ProductEntity(
                productDto.getName(),
                productDto.getImageUrl(),
                productDto.getPrice(),
                productDto.getDescription()
        );
        final Long savedProductId = productDao.save(productEntity);
        for (final Long categoryId : productDto.getCategoryIds()) {
            productCategoryDao.save(new ProductCategoryEntity(savedProductId, categoryId));
        }
        return savedProductId;
    }

    public List<ProductResponseDto> findProducts() {
        return productDao.findAll().stream()
                .map(productEntity -> {
                    final List<Long> categoryIds = getCategoryIds(productEntity);
                    final List<CategoryEntity> categoryEntities = categoryDao.findAllInId(categoryIds);
                    return ProductResponseDto.of(productEntity, categoryEntities);
                })
                .collect(Collectors.toList());
    }

    private List<Long> getCategoryIds(final ProductEntity productEntity) {
        return productCategoryDao.findAll(productEntity.getId())
                .stream()
                .map(ProductCategoryEntity::getCategoryId)
                .collect(Collectors.toList());
    }

    public void update(final Long id, final ProductDto productDto) {
        final ProductEntity productEntity = new ProductEntity(
                id,
                productDto.getName(),
                productDto.getImageUrl(),
                productDto.getPrice(),
                productDto.getDescription()
        );
        productDao.update(productEntity);
        for (ProductCategoryEntity productCategoryEntity : productCategoryDao.findAll(id)) {
            productCategoryDao.delete(productCategoryEntity.getId());
        }
        for (final Long categoryId : productDto.getCategoryIds()) {
            productCategoryDao.save(new ProductCategoryEntity(id, categoryId));
        }
    }

    public void delete(final Long id) {
        final List<ProductCategoryEntity> productCategoryEntities = productCategoryDao.findAll(id);
        for (ProductCategoryEntity productCategoryEntity : productCategoryEntities) {
            productCategoryDao.delete(productCategoryEntity.getId());
        }
        productDao.delete(id);
    }
}
