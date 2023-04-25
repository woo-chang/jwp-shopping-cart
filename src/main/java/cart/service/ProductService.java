package cart.service;

import cart.dao.CategoryDao;
import cart.dao.ProductCategoryDao;
import cart.dao.ProductDao;
import cart.dto.ProductDto;
import cart.dto.response.CategoryResponseDto;
import cart.dto.response.ProductResponseDto;
import cart.entity.CategoryEntity;
import cart.entity.ProductCategoryEntity;
import cart.entity.product.ProductEntity;
import cart.mapper.ProductEntityMapper;
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
        final ProductEntity productEntity = ProductEntityMapper.from(productDto);
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
        return productCategoryDao.findAllByProductId(productEntity.getId())
                .stream()
                .map(ProductCategoryEntity::getCategoryId)
                .collect(Collectors.toList());
    }

    public void update(final Long id, final ProductDto productDto) {
        final ProductEntity productEntity = findProductEntity(id);
        productEntity.update(
                productDto.getName(),
                productDto.getImageUrl(),
                productDto.getPrice(),
                productDto.getDescription()
        );
        productDao.update(productEntity);

        for (final ProductCategoryEntity productCategoryEntity : productCategoryDao.findAllByProductId(id)) {
            productCategoryDao.deleteById(productCategoryEntity.getId());
        }

        for (final Long categoryId : productDto.getCategoryIds()) {
            productCategoryDao.save(new ProductCategoryEntity(id, categoryId));
        }
    }

    private ProductEntity findProductEntity(Long id) {
        return productDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 ID를 입력하였습니다."));
    }

    public void delete(final Long id) {
        final ProductEntity productEntity = findProductEntity(id);
        final List<ProductCategoryEntity> productCategoryEntities = productCategoryDao.findAllByProductId(productEntity.getId());
        for (ProductCategoryEntity productCategoryEntity : productCategoryEntities) {
            productCategoryDao.deleteById(productCategoryEntity.getId());
        }
        productDao.deleteById(productEntity.getId());
    }

    public List<CategoryResponseDto> findCategories() {
        return categoryDao.findAll().stream()
                .map(CategoryResponseDto::from)
                .collect(Collectors.toList());
    }
}
