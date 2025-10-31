package backend.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String name;
    private Double price;
    private Integer quantity;

    // @ManyToOne
    // @JoinColumn(name = "order_id")
    // private Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    // --- Constructors ---
    public OrderItem() {
    }

    public OrderItem(Long productId, String name, Double price, Integer quantity, Order order) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
    }

    // --- Getters ---
    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Order getOrder() {
        return order;
    }

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
