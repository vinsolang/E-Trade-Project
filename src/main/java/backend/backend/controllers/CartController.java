package backend.backend.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import backend.backend.models.CartItem;
import backend.backend.service.CartService;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "https://e-trade-shop.vercel.app")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getUserCarts(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.getAllByUser(userId));
    }

    @PostMapping
    public ResponseEntity<CartItem> addToCart(@RequestBody CartItem cartItem) {
        CartItem saved = cartService.addToCart(cartItem);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CartItem> updateQuantity(@PathVariable Long id, @RequestBody CartItem body) {
        CartItem updated = cartService.updateQuantity(id, body.getQuantity());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long id) {
        cartService.removeCartItem(id);
        return ResponseEntity.noContent().build();
    }
}