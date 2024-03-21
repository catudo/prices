package com.matrix.campus.prices.controllers;

import com.matrix.campus.prices.dto.PriceDTO;
import com.matrix.campus.prices.services.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PriceControllerTest {

    @Mock
    private PriceService priceService;

    @InjectMocks
    private PriceController priceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findTest(){
        final String date = "2021-06-15T00:00:00";
        final Long productId = 35455L;
        final Long brandId = 35455L;

        final PriceDTO returningPrice = new PriceDTO();

        when(priceService.findApplicablePrices(any(),any(),any())).thenReturn(returningPrice);

        ResponseEntity<PriceDTO> response = priceController.find(date,productId,brandId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(returningPrice, response.getBody());
        verify(priceService, times(1)).findApplicablePrices(LocalDateTime.parse(date), productId,  brandId);

    }

}