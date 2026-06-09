package com.medical.Services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.medical.entities.Product;
import com.medical.kafka.KafkaProducerService;
import com.medical.kafka.events.ProductEvent;
import com.medical.repositories.ProductRepository;

@Service
public class ProductServices {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public Product addProduct(Product p) {
        Product saved = this.productRepository.save(p);
        kafkaProducerService.sendProductEvent(new ProductEvent(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getStock(),
                "CREATED"
        ));
        return saved;
    }

    public void deleteProductById(Long id) {
        Product product = this.productRepository.findById(id).orElse(null);
        this.productRepository.deleteById(id);
        if (product != null) {
            kafkaProducerService.sendProductEvent(new ProductEvent(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock(),
                    "DELETED"
            ));
        }
    }

    public List<Product> getAllProducts() {
        return (List<Product>) this.productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }
}
