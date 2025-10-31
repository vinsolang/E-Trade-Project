package backend.backend.dto;

// Standard imports for Spring/Java
import lombok.Data; // Using Lombok for brevity (optional)

@Data // Lombok annotation for getters, setters, toString, etc.
public class CartItemDto {
    
    private Long productId;
    private String name;
    private Double price;
    private Integer discount;
    private String images; // The single image path
    private Integer quantity;
    private String userId;
    
    // If you are not using Lombok, you need to manually add 
    // public getters and setters for all fields.
}