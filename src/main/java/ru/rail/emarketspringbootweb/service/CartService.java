package ru.rail.emarketspringbootweb.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.dto.UserDto;
import ru.rail.emarketspringbootweb.entity.Cart;
import ru.rail.emarketspringbootweb.entity.CartItem;
import ru.rail.emarketspringbootweb.entity.Product;
import ru.rail.emarketspringbootweb.entity.User;
import ru.rail.emarketspringbootweb.repository.CartItemRepository;
import ru.rail.emarketspringbootweb.repository.CartRepository;
import ru.rail.emarketspringbootweb.repository.ProductRepository;
import ru.rail.emarketspringbootweb.repository.UserRepository;


@Service
public class CartService {
    @Autowired
    private final ModelMapper modelMapper;
    @Autowired
    private final CartRepository cartRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CartItemRepository cartItemRepository;

    @Autowired
    private final ProductRepository productRepository;

    public CartService(ModelMapper modelMapper, CartRepository cartRepository, UserRepository userRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    private Product convertProductDtoToProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        product.setId(productDto.getId());
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        return product;
    }

    private User convertUserDtoToUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }


    @Transactional
    public void addProductToCart(UserDto userDto, ProductDto productDto, int quantity) {

        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findCartForUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);

        //cartRepository.save(cart);



    }


}
