package ru.rail.emarketspringbootweb;

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

       var t =  c.getBean(ProductService.class);
      //  System.out.println("The total price is " + t.calculateTotalCartValue(8L));
    }

}
