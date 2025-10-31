package backend.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {
        private Long id;
        private String customerName;
        private String customerEmail;
        private String customerAddress;
        private Double totalAmount;
        private LocalDateTime createdAt;
        private List<OrderItemDTO> orderItems;
}
