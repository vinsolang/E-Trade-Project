package backend.backend.repository;

import backend.backend.models.Order;



import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> { }
   
