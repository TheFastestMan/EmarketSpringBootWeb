package ru.rail.emarketspringbootweb.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.rail.emarketspringbootweb.dto.ProductDto;
import ru.rail.emarketspringbootweb.entity.Product;
import ru.rail.emarketspringbootweb.entity.QCart;
import ru.rail.emarketspringbootweb.entity.QCartItem;
import ru.rail.emarketspringbootweb.entity.QProduct;

import java.util.List;

@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final EntityManager entityManager;

    @Override
    public Page<Product> findByProductDto(ProductDto productDto, Pageable pageable) {
        QProduct qProduct = QProduct.product;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        // Build the query for fetching data
        List<Product> products = queryFactory
                .selectFrom(qProduct)
                .where(buildWhereClause(productDto, qProduct))
                .orderBy(qProduct.price.asc()) //  sort by price
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Build the query for counting total elements
        long total = queryFactory
                .selectFrom(qProduct)
                .where(buildWhereClause(productDto, qProduct))
                .fetchCount();

        return new PageImpl<>(products, pageable, total);
    }

    private BooleanExpression buildWhereClause(ProductDto productDto, QProduct qProduct) {
        BooleanExpression predicate = null;

        if (productDto.getProductName() != null && !productDto.getProductName().isEmpty()) {
            predicate = (predicate == null ? qProduct.productName.eq(productDto
                    .getProductName()) : predicate.and(qProduct.productName.eq(productDto.getProductName())));
        }

        if (productDto.getDescription() != null && !productDto.getDescription().isEmpty()) {
            predicate = (predicate == null ? qProduct.description.eq(productDto
                    .getDescription()) : predicate.and(qProduct.description.eq(productDto.getDescription())));
        }

        if (productDto.getPrice() != null) {
            predicate = (predicate == null ? qProduct.price.eq(productDto
                    .getPrice()) : predicate.and(qProduct.price.eq(productDto.getPrice())));
        }

        return predicate;
    }

    @Override
    public List<Product> findProductByUserId(Long userId) {
        QProduct qProduct = QProduct.product;
        QCartItem qCartItem = QCartItem.cartItem;
        QCart qCart = QCart.cart;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        return queryFactory
                .select(qProduct)
                .from(qProduct)
                .join(qProduct.cartItems, qCartItem)
                .join(qCartItem.cart, qCart)
                .where(qCart.user.id.eq(userId))
                .distinct()
                .fetch();
    }

}
