package backend.backend.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;

    private Long productId;
    private String name;
    private Double price;
    private Integer quantity;
}
