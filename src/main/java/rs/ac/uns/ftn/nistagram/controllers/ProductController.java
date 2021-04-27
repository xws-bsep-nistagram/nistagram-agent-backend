package rs.ac.uns.ftn.nistagram.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.nistagram.controllers.DTOs.ProductDTO;
import rs.ac.uns.ftn.nistagram.domain.Product;
import rs.ac.uns.ftn.nistagram.services.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/product")
public class ProductController {
    private final ProductService productService;
    private final ModelMapper mapper;

    public ProductController(ProductService productService, ModelMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasRole('ADMIN') && hasAuthority('GET_ALL_PRODUCTS')")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        List<ProductDTO> productDtoList =
                this.productService.getAll().stream()
                        .map(product -> mapper.map(product, ProductDTO.class))
                        .collect(Collectors.toList());

        return ResponseEntity.ok(productDtoList);
    }

    @PreAuthorize("hasRole('USER') && hasAuthority('GET_AVAILABLE_PRODUCTS')")
    @GetMapping("available")
    public ResponseEntity<List<ProductDTO>> getAvailable(){
        List<ProductDTO>  products = productService.getAvailable()
                                        .stream()
                                        .map(product -> mapper.map(product, ProductDTO.class))
                                        .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole('ADMIN') && hasAuthority('CREATE_PRODUCT')")
    @PostMapping
    public ProductDTO add(@Valid @RequestBody ProductDTO dto) {
        return mapper.map(
                productService.add(mapper.map(dto, Product.class)),
                ProductDTO.class);
    }

    @PreAuthorize("hasRole('ADMIN') && hasAuthority('GET_PRODUCT')")
    @GetMapping("{id}")
    public ProductDTO get(@PathVariable long id) {
        return mapper.map(this.productService.get(id), ProductDTO.class);
    }

    @PreAuthorize("hasRole('ADMIN') && hasAuthority('UPDATE_PRODUCT')")
    @PutMapping("{id}")
    public void update(@PathVariable long id, @Valid @RequestBody ProductDTO dto) {
        productService.update(id, mapper.map(dto, Product.class));
    }

    @PreAuthorize("hasRole('ADMIN') && hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        productService.delete(id);
    }
}
