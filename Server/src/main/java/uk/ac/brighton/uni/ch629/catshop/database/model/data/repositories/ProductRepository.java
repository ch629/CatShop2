package uk.ac.brighton.uni.ch629.catshop.database.model.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.ac.brighton.uni.ch629.catshop.database.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAll();

    Product findByProductNumber(int productNumber);
}
