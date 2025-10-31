package backend.backend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private Double totalAmount;

    // @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    // private List<OrderItem> orderItems;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @CreationTimestamp
    private LocalDateTime createdAt;
    // --- Constructors ---
    public Order() {
    }

    public Order(String customerName, String customerEmail, String customerAddress, Double totalAmount,
            List<OrderItem> orderItems) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
    }

    // --- Getters ---
    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    // --- Setters ---
    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
