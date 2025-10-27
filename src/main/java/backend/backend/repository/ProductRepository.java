package backend.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.backend.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
     // Spring Data JPA will provide all the necessary CRUD methods
    // e.g., findAll(), findById(), save(), deleteById()
}

