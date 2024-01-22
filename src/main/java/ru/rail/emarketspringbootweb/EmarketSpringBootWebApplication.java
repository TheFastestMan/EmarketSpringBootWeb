package ru.rail.emarketspringbootweb;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.dto.UserDto;
import ru.rail.emarketspringbootweb.entity.Gender;
import ru.rail.emarketspringbootweb.entity.Role;
import ru.rail.emarketspringbootweb.service.CartService;
import ru.rail.emarketspringbootweb.service.ProductService;
import ru.rail.emarketspringbootweb.service.UserService;

@SpringBootApplication
public class EmarketSpringBootWebApplication {

    public static void main(String[] args) throws Exception {
        var c = SpringApplication.run(EmarketSpringBootWebApplication.class, args);

//        var user = c.getBean(CartService.class);
//
//        String login = "user1@example.com";
//        String password = "password1";
//
//        UserDto userDto = UserDto.builder()
//                .id(111L)
//                .username("Kutak")
//                .email("dfg")
//                .role(Role.USER)
//                .gender(Gender.MALE)
//                .password("defrger")
//                .build();
//
//        ProductDto productDto = ProductDto.builder()
//                .id(112L)
//                .productName("sdef")
//                .price(123.123)
//                .quantity(123)
//                .build();
//
//        user.addProductToCart(userDto, productDto, 1);

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
