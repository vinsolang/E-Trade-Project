package backend.backend.controllers;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import backend.backend.models.Product;
import backend.backend.repository.ProductRepository;

@CrossOrigin(origins = "http://localhost:5173") // allow React dev server
@RestController
@RequestMapping("/api/admin/products")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    // GET all
    @GetMapping
    public List<Product> getAll() {
        return repo.findAll();
    }

    // GET one
    @GetMapping("/{id}")
    public Product getOne(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        product.setId(null); // ensure create

        // just use currentPrice and oldPrice
        Product saved = repo.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product body) {
        Product p = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        p.setName(body.getName());
        p.setImages(body.getImages());
        p.setStockStatus(body.getStockStatus());
        p.setCurrentPrice(body.getCurrentPrice());
        p.setOldPrice(body.getOldPrice());
        p.setDiscountPercent(body.getDiscountPercent());
        p.setDescription(body.getDescription());
        p.setColor(body.getColor());
        return repo.save(p);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

