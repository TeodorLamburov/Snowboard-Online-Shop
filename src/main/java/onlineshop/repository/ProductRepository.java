package onlineshop.repository;

import onlineshop.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,String> {

    Optional<Product> findByProductName(String productName);

    Optional<Product> findByProductNameAndProductBrandAndProductModel(String name, String brand, String model);
}
