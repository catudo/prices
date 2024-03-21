package com.matrix.campus.prices.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PriceDTO {

    private Long productId;

    private Long brandId;

    private Double price;

    private LocalDateTime startDate;
    private LocalDateTime endDate;


}
