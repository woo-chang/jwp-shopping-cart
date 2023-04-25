package cart.controller;

import cart.dto.request.ProductRequestDto;
import cart.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/products")
    void create(@RequestBody ProductRequestDto productRequestDto) {
        productService.register(productRequestDto);
    }
}
