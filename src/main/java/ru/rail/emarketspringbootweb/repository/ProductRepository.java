package ru.rail.emarketspringbootweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.rail.emarketspringbootweb.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {
    @Modifying
    @Query("update Product p set p.quantity = p.quantity - :quantityToDecrease where p.id = :productId")
        // TODO: 1/22/24 implement this method when create purchase method
    void decreaseQuantityByAmount(@Param("productId") Long productId, @Param("quantityToDecrease") int quantityToDecrease);




}
