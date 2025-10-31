package backend.backend.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // store images as JSON string (e.g. '["/img/a.jpg","/img/b.jpg"]')
    @Column(columnDefinition = "TEXT")
    private String images;

    private String name;

    // e.g. "smartphones", "laptops", etc.
    private String category; //  NEW FIELD FOR CATEGORY

    // e.g. "in_stock", "out_of_stock", "preorder"
    private String stockStatus;

    private Double currentPrice;
    private Double oldPrice;

    private Integer discountPercent;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String color;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // getters and setters
    public Product() {}

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    //  NEW GETTER AND SETTER FOR CATEGORY
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getStockStatus() { return stockStatus; }
    public void setStockStatus(String stockStatus) { this.stockStatus = stockStatus; }
    
    public Double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(Double currentPrice) { this.currentPrice = currentPrice; }
    
    public Double getOldPrice() { return oldPrice; }
    public void setOldPrice(Double oldPrice) { this.oldPrice = oldPrice; }
    
    public Integer getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(Integer discountPercent) { this.discountPercent = discountPercent; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}



// package backend.backend.models;


// import java.time.LocalDateTime;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.PrePersist;
// import jakarta.persistence.PreUpdate;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "products")
// public class Product {
    
 
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     // store images as JSON string (e.g. '["/img/a.jpg","/img/b.jpg"]')
//     @Column(columnDefinition = "TEXT")
//     private String images;

//     private String name;

//     // e.g. "in_stock", "out_of_stock", "preorder"
//     private String stockStatus;

//     private Double currentPrice;
//     private Double oldPrice;

//     private Integer discountPercent;

//     @Column(columnDefinition = "TEXT")
//     private String description;

//     private String color;

//     private LocalDateTime createdAt;
//     private LocalDateTime updatedAt;

//     @PrePersist
//     protected void onCreate() {
//         createdAt = LocalDateTime.now();
//         updatedAt = createdAt;
//     }

//     @PreUpdate
//     protected void onUpdate() {
//         updatedAt = LocalDateTime.now();
//     }

//     // getters and setters
//     public Product() {}

//     // all getters and setters below (omitted here for brevity — include in your file)
//     // ... generate with your IDE
//     // Example:
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }
//     public String getImages() { return images; }
//     public void setImages(String images) { this.images = images; }
//     public String getName() { return name; }
//     public void setName(String name) { this.name = name; }
//     public String getStockStatus() { return stockStatus; }
//     public void setStockStatus(String stockStatus) { this.stockStatus = stockStatus; }
//     public Double getCurrentPrice() { return currentPrice; }
//     public void setCurrentPrice(Double currentPrice) { this.currentPrice = currentPrice; }
//     public Double getOldPrice() { return oldPrice; }
//     public void setOldPrice(Double oldPrice) { this.oldPrice = oldPrice; }
//     public Integer getDiscountPercent() { return discountPercent; }
//     public void setDiscountPercent(Integer discountPercent) { this.discountPercent = discountPercent; }
//     public String getDescription() { return description; }
//     public void setDescription(String description) { this.description = description; }
//     public String getColor() { return color; }
//     public void setColor(String color) { this.color = color; }
//     public LocalDateTime getCreatedAt() { return createdAt; }
//     public LocalDateTime getUpdatedAt() { return updatedAt; }
// }
