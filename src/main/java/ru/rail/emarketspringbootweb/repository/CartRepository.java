package ru.rail.emarketspringbootweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rail.emarketspringbootweb.entity.Cart;
import ru.rail.emarketspringbootweb.entity.User;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("select c from Cart c join fetch c.cartItems where c.user = :user")
    Optional<Cart> findCartForUser(@Param("user") User user);

}
