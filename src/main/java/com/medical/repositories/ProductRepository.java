package com.medical.repositories;
import org.springframework.data.repository.CrudRepository;
import com.medical.entities.Product;
public interface ProductRepository extends CrudRepository<Product, Long>  {
    public Product findByName(String name);

}
