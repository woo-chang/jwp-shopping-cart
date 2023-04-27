package cart.dao;

import cart.entity.ProductCategoryEntity;
import cart.entity.product.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ProductCategoryDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;
    private ProductCategoryDao productCategoryDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
        productCategoryDao = new ProductCategoryDao(jdbcTemplate);
    }

    @Test
    @DisplayName("상품 카테고리를 저장한다.")
    void save() {
        //given
        final ProductEntity productEntity = new ProductEntity(
                1L,
                "name",
                "image_url",
                1000,
                "description"
        );
        final Long savedProductId = productDao.save(productEntity);
        final ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(savedProductId, 1L);

        //when
        //then
        assertThat(productCategoryDao.save(productCategoryEntity)).isNotNull();
    }

    @Test
    @DisplayName("ID에 해당하는 상품 카테고리를 삭제한다.")
    void delete() {
        //given
        final ProductEntity productEntity = new ProductEntity(
                1L,
                "name",
                "image_url",
                1000,
                "description"
        );
        final Long savedProductId = productDao.save(productEntity);
        final ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(savedProductId, 1L);
        final Long savedProductCategoryId = productCategoryDao.save(productCategoryEntity);

        //when
        productCategoryDao.delete(savedProductCategoryId);

        //then
        final List<ProductCategoryEntity> productCategoryEntities = productCategoryDao.findAll(savedProductId);
        assertThat(productCategoryEntities).hasSize(0);
    }

    @Nested
    class FindAll {

        @Test
        @DisplayName("상품 ID에 대한 상품 카테고리 목록을 조회한다.")
        void findAll() {
            //given
            final ProductEntity productEntity = new ProductEntity(
                    1L,
                    "name",
                    "image_url",
                    1000,
                    "description"
            );
            final Long savedProductId = productDao.save(productEntity);
            final ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity(savedProductId, 1L);
            productCategoryDao.save(productCategoryEntity);

            //when
            final List<ProductCategoryEntity> productCategoryEntities = productCategoryDao.findAll(savedProductId);

            //then
            assertThat(productCategoryEntities).hasSize(1);
        }
    }
}
