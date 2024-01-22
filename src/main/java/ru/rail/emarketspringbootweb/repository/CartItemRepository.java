package ru.rail.emarketspringbootweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rail.emarketspringbootweb.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
