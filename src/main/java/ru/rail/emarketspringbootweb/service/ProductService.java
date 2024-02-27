package ru.rail.emarketspringbootweb.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.entity.CartItem;
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
        Page<Product> products = productRepository.findByProductDto(productDto, pageable);
        return products.map(productMapper::toDTO);
    }

    public List<ProductDto> getProductsByUserId(Long id) {
        List<Product> product = productRepository.findProductByUserId(id);
        return product.stream().map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Double calculateTotalCartValue(Long userId) {
        List<Product> products = productRepository.findProductByUserId(userId);
        return products.stream().mapToDouble(product -> product.getPrice()
        * getQuantityForProductInCart(product, userId))
                .sum();
    }

    private int getQuantityForProductInCart(Product product, Long userId) {
        // This method should return the quantity of the product in the user's cart
        // You will need to implement the logic to retrieve this from the database
        // For example, you could go through the CartItem entities that are linked to the user's Cart
        // And sum up the quantities for the given product
        // This is a placeholder implementation and should be replaced with actual logic
        return product.getCartItems().stream()
                .filter(cartItem -> cartItem.getCart().getUser().getId().equals(userId))
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
