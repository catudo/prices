package com.matrix.campus.prices.controllers;

import com.matrix.campus.prices.dto.PriceDTO;
import com.matrix.campus.prices.services.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/price")
public class PriceController {

    private final PriceService priceService;

    @Autowired
    public PriceController(final PriceService priceService) {
        this.priceService = priceService;
    }


    @GetMapping(value="/find_applicable_prices",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceDTO> find(@RequestParam("date") String date,
                                         @RequestParam("productId") Long productId,
                                         @RequestParam("brandId") Long brandId) {
        LocalDateTime localDateTime = LocalDateTime.parse(date);
        return new ResponseEntity<>(priceService.findApplicablePrices(localDateTime, productId, brandId), HttpStatus.OK);
    }

}