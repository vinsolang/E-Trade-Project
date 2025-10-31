package backend.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //  FIX 2: Correct the path mapping to serve files from the 'uploads' directory.
        // We map the URL path "/images/products/**" to the file system path "file:uploads/images/products/"
        
        // This makes images accessible via: http://localhost:8081/images/products/unique-name.jpg
        // and physically serves them from the `uploads/images/products` directory in the project root.
        registry.addResourceHandler("/images/products/**")
                .addResourceLocations("file:uploads/images/products/");
    }
}