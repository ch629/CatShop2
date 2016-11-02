package uk.ac.brighton.uni.ch629.catshop.database.model.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.brighton.uni.ch629.catshop.database.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
//    Product findByProductNumber(int productNumber);
}
