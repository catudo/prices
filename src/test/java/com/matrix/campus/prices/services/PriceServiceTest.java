package com.matrix.campus.prices.services;

import com.matrix.campus.prices.dto.PriceDTO;
import com.matrix.campus.prices.models.Brand;
import com.matrix.campus.prices.models.Price;
import com.matrix.campus.prices.repositories.PriceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    public PriceServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindApplicablePrices() {

        LocalDateTime date = LocalDateTime.of(2024, 3, 22, 12, 0);
        Long productId = 123L;
        Long brandId = 456L;
        Price price = new Price();
        price.setId(1L);

        price.setBrand(new Brand(1L, "ZARA"));
        price.setProductId(productId);
        price.setStartDate(LocalDateTime.of(2024, 3, 22, 0, 0));
        price.setEndDate(LocalDateTime.of(2024, 3, 22, 23, 59, 59));
        price.setPriceList(1L);
        price.setPriority(1);
        price.setPrice(35.50);
        price.setCurrency("EUR");
        List<Price> prices = new ArrayList<>();
        prices.add(price);


        when(priceRepository.findPrices(brandId, productId, date)).thenReturn(prices);


        PriceDTO result = priceService.findApplicablePrices(date, productId, brandId);

        assertNotNull(result);
        assertEquals(price.getBrand().getId(), result.getBrandId());
        assertEquals(price.getProductId(), result.getProductId());
        assertEquals(price.getStartDate(), result.getStartDate());
        assertEquals(price.getEndDate(), result.getEndDate());
        assertEquals(price.getPrice(), result.getPrice());
    }

    @Test
    public void testFindApplicablePricesNull() {
        LocalDateTime date = LocalDateTime.of(2024, 3, 22, 12, 0);
        Long productId = 123L;
        Long brandId = 456L;

        when(priceRepository.findPrices(any(), any(), any())).thenReturn(List.of());

        PriceDTO result = priceService.findApplicablePrices(date, productId, brandId);
        assertNull(result);
    }

}