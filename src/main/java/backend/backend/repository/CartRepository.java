package backend.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import backend.backend.models.CartItem;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUserId(String userId);
}