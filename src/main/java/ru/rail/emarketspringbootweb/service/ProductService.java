package ru.rail.emarketspringbootweb.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.entity.Product;
import ru.rail.emarketspringbootweb.entity.QProduct;
import ru.rail.emarketspringbootweb.mapper.ProductMapper;
import ru.rail.emarketspringbootweb.repository.ProductRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService {

    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    public ProductService(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
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
        return productOptional.map(productMapper::toDTO);
    }

    @Transactional
    public void addProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        productRepository.save(product);
    }

    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDTO);
    }

    public Page<ProductDto> getAllProducts(ProductDto productDto, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Product> products = productRepository.findByProductDto(productDto,pageable);
        return products.map(productMapper::toDTO);


    }


}
