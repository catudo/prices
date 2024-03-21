package com.matrix.campus.prices.repositories;

import com.matrix.campus.prices.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT p FROM Price p WHERE p.brand.id = ?1 AND p.productId = ?2 AND ?3 BETWEEN p.startDate AND p.endDate order by p.endDate desc")
    List<Price> findPrices(Long brandId, Long productId, LocalDateTime applicationDate);

    List<Price> findAll();


}
