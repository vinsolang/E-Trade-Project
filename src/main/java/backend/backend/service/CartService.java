package backend.backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.backend.models.CartItem;

@Service
public class CartService {

    @Autowired
    private backend.backend.repository.CartRepository cartRepository;

    public List<CartItem> getAllByUser(String userId) {
        return cartRepository.findByUserId(userId);
    }

    public CartItem addToCart(CartItem item) {
        return cartRepository.save(item);
    }

    public CartItem updateQuantity(Long id, int quantity) {
        CartItem item = cartRepository.findById(id).orElseThrow();
        item.setQuantity(quantity);
        return cartRepository.save(item);
    }

    public void removeCartItem(Long id) {
        cartRepository.deleteById(id);
    }
}