package cart.controller;

import cart.dto.request.ProductRequestDto;
import cart.dto.response.ProductResponseDto;
import cart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequestMapping("/products")
@RestController
public final class ProductApiController {

    private final ProductService productService;

    public ProductApiController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findProducts() {
        final List<ProductResponseDto> result = productService.findProducts();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void> registerProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        final Long savedProductId = productService.registerProduct(productRequestDto);
        return ResponseEntity.created(URI.create("/products/" + savedProductId)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable(name = "id") Long productId,
            @RequestBody ProductRequestDto productRequestDto
    ) {
        productService.updateProduct(productId, productRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable(name = "id") Long productId) {
        productService.removeProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
