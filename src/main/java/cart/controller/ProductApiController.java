package cart.controller;

import cart.dto.request.ProductRequestDto;
import cart.dto.response.ProductResponseDto;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class ProductApiController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    //CREATE
    @PostMapping("/products")
    public ResponseEntity<Void> registerProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        final Long productId = productService.register(productRequestDto);

        return ResponseEntity.created(URI.create("/products/" + productId)).build();
    }

    //READ
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> findProducts() {
        final List<ProductResponseDto> products = productService.findProducts();

        return ResponseEntity.ok(products);
    }

    //UPDATE
    @PutMapping("/products/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDto productRequestDto) {
        productService.update(id, productRequestDto);

        return ResponseEntity.ok().build();
    }

    //DELETE
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);

        return ResponseEntity.ok().build();
    }
}
