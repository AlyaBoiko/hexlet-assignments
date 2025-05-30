package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import exercise.model.Product;

import org.springframework.data.domain.Sort;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // BEGIN
    List<Product> findAllByPriceBetweenOrderByPriceAsc(int min, int max);
    List<Product> findAllByPriceGreaterThanEqualOrderByPriceAsc(int min);
    List<Product> findAllByPriceLessThanEqualOrderByPriceAsc(int max);
    List<Product> findAllByOrderByPriceAsc();

    // END
}
