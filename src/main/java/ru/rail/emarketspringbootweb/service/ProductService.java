package ru.rail.emarketspringbootweb.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.entity.Product;
import ru.rail.emarketspringbootweb.repository.ProductRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService {
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final ProductRepository productRepository;

    public ProductService(ModelMapper modelMapper, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    private ProductDto convertProductToProductDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setId(product.getId());
        productDto.setProductName(product.getProductName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

    private Product convertProductDtoToProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        product.setId(productDto.getId());
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        return product;
    }

    public List<ProductDto> getAllProducts() throws Exception {

        return productRepository.findAll().stream().map(product ->
                new ProductDto(product.getId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getQuantity(),
                        product.getPrice())).collect(Collectors.toList());
    }

    public Optional<ProductDto> getProductById(Long productId) {

        Optional<Product> productOptional = productRepository.findById(productId);
        return productOptional.map(this::convertProductToProductDto);
    }

    @Transactional
    public void addProduct(ProductDto productDto) {

//        var validationFactory = Validation.buildDefaultValidatorFactory();
//        var validator = validationFactory.getValidator();
//        var validationResult = validator.validate(productDto);
//
//        if (!validationResult.isEmpty()) {
//            throw new ConstraintViolationException(validationResult);
//        }
        Product product = convertProductDtoToProduct(productDto);
        productRepository.save(product);
    }

    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(this::convertProductToProductDto);
    }
}
