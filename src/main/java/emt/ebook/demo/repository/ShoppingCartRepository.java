package emt.ebook.demo.repository;

import emt.ebook.demo.model.ShoppingCart;
import emt.ebook.demo.model.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUserUsernameAndShoppingCartStatus(String username, ShoppingCartStatus status);
    boolean existsByUserUsernameAndShoppingCartStatus(String username, ShoppingCartStatus status);
    List<ShoppingCart> findAllByUserUsername(String username);
}
