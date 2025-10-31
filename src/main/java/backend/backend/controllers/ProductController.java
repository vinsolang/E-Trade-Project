
package backend.backend.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import backend.backend.models.Product;
import backend.backend.repository.ProductRepository;

@RestController
@RequestMapping("/api/admin/products")
@CrossOrigin(origins = "https://e-trade-shop.vercel.app") // allow React dev server
public class ProductController {

    private final ProductRepository repo;

    // Define the base path for saving images (e.g., inside your project's public folder)
    // NOTE: Change this path to your desired local storage location!
    private static final String UPLOAD_DIR = "uploads/images/products/";

    public ProductController(ProductRepository repo) {
        this.repo = repo;
        // Ensure the upload directory exists on startup
        try {
            // FIX: Use resolve to ensure path is absolute for reliability
            Path uploadPath = Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);
            System.out.println("Image upload directory created at: " + uploadPath);
        } catch (IOException e) {
            System.err.println("Could not create upload directory: " + e.getMessage());
            // Optionally, rethrow as a RuntimeException to halt startup if dir creation is critical
        }
    }

    // --- DEDICATED FILE UPLOAD ENDPOINT ---
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImages(@RequestParam("files") List<MultipartFile> files) {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            try {
                // 1. Generate a unique file name
                String originalFilename = file.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
                
                // 2. Define the absolute target path
                Path path = Paths.get(UPLOAD_DIR + uniqueFileName).toAbsolutePath().normalize();
                
                // 3. Save the file to the local directory
                Files.write(path, file.getBytes());

                // 4. Record the publicly accessible URL/path for the React frontend
                // This URL corresponds to the handler path defined in WebConfig
                String publicUrl = "/images/products/" + uniqueFileName;
                imageUrls.add(publicUrl);

            } catch (IOException e) {
                // Log and return a server error if saving fails
                System.err.println("File upload failed: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body("Failed to upload file(s): " + e.getMessage());
            }
        }

        // Return the list of public URLs back to the React component
        return ResponseEntity.ok().body(imageUrls);
    }
    // --------------------------------------

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

        // The 'product' body received here should contain the JSON list of image URLs 
        // returned by the /upload endpoint, and the new 'category' field.
        Product saved = repo.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product body) {
        Product p = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        
        // --- Update all fields, including the new 'category' ---
        p.setName(body.getName());
        p.setImages(body.getImages());
        p.setCategory(body.getCategory()); //  NEW FIELD
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
        // Optional: Implement logic to delete associated physical files here
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
























// package backend.backend.controllers;


// import java.util.List;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.server.ResponseStatusException;

// import backend.backend.models.Product;
// import backend.backend.repository.ProductRepository;

// @CrossOrigin(origins = "http://localhost:5173") // allow React dev server
// @RestController
// @RequestMapping("/api/admin/products")
// public class ProductController {

//     private final ProductRepository repo;

//     public ProductController(ProductRepository repo) {
//         this.repo = repo;
//     }

//     // GET all
//     @GetMapping
//     public List<Product> getAll() {
//         return repo.findAll();
//     }

//     // GET one
//     @GetMapping("/{id}")
//     public Product getOne(@PathVariable Long id) {
//         return repo.findById(id)
//                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
//     }

//     // CREATE
//     @PostMapping
//     public ResponseEntity<Product> create(@RequestBody Product product) {
//         product.setId(null); // ensure create

//         // just use currentPrice and oldPrice
//         Product saved = repo.save(product);
//         return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//     }

//     // UPDATE
//     @PutMapping("/{id}")
//     public Product update(@PathVariable Long id, @RequestBody Product body) {
//         Product p = repo.findById(id)
//                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
//         p.setName(body.getName());
//         p.setImages(body.getImages());
//         p.setStockStatus(body.getStockStatus());
//         p.setCurrentPrice(body.getCurrentPrice());
//         p.setOldPrice(body.getOldPrice());
//         p.setDiscountPercent(body.getDiscountPercent());
//         p.setDescription(body.getDescription());
//         p.setColor(body.getColor());
//         return repo.save(p);
//     }

//     // DELETE
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> delete(@PathVariable Long id) {
//         if (!repo.existsById(id)) {
//             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
//         }
//         repo.deleteById(id);
//         return ResponseEntity.noContent().build();
//     }
// }

