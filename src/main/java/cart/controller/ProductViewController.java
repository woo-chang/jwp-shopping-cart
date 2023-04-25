package cart.controller;

import cart.dto.response.CategoryResponseDto;
import cart.dto.response.ProductResponseDto;
import cart.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView index(final ModelAndView modelAndView) {
        final List<ProductResponseDto> productResponseDtos = productService.findProducts();
        modelAndView.addObject("products", productResponseDtos);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView admin(final ModelAndView modelAndView) {
        final List<ProductResponseDto> productResponseDtos = productService.findProducts();
        final List<CategoryResponseDto> categoryResponseDtos = productService.findCategories();
        modelAndView.addObject("products", productResponseDtos);
        modelAndView.addObject("categories", categoryResponseDtos);
        modelAndView.setViewName("admin");
        return modelAndView;
    }
}
