package backend.backend.controllers;

import backend.backend.models.Order;
import backend.backend.repository.OrderRepository;
import backend.backend.service.TelegramService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "https://e-trade-shop.vercel.app")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TelegramService telegramService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody Order order) {
        // Link each order item to parent order
        if (order.getOrderItems() != null) {
            order.getOrderItems().forEach(item -> item.setOrder(order));
        }

        Order saved = orderRepository.save(order); // now should work

        // Send message to Telegram
        StringBuilder msg = new StringBuilder();
        msg.append("🛒 *New Order Received!*\n\n")
                .append(" Customer Name: ").append(order.getCustomerName()).append("\n")
                .append(" Email: ").append(order.getCustomerEmail()).append("\n")
                .append(" Address: ").append(order.getCustomerAddress()).append("\n\n")
                .append("💰 Total: $").append(order.getTotalAmount()).append("\n\n")
                .append("📦 Items:\n");
        order.getOrderItems().forEach(i -> msg.append("- ").append(i.getName())
                .append(" ×").append(i.getQuantity())
                .append(" ($").append(i.getPrice()).append(")\n"));
        telegramService.sendMessage(msg.toString());

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // --- Get all orders ---
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.ok(orders);
    }

    // --- Get single order by ID ---
    // @GetMapping("/{id}")
    // public ResponseEntity<?> getOrderById(@PathVariable Long id) {
    // return orderRepository.findById(id)
    // .map(order -> ResponseEntity.ok(order))
    // .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
    // .body("Order not found with id: " + id));
    // }
    // --- Delete order by ID ---
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        return orderRepository.findById(id).map(order -> {
            orderRepository.delete(order);
            return ResponseEntity.ok("Order deleted successfully");
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Order not found with id: " + id));
    }

}
