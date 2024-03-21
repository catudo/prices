package com.matrix.campus.prices.services;

import com.matrix.campus.prices.dto.PriceDTO;
import com.matrix.campus.prices.mappers.PriceMapper;
import com.matrix.campus.prices.models.Price;
import com.matrix.campus.prices.repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceService(final PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }


    public PriceDTO findApplicablePrices(final LocalDateTime date, final Long productId, final Long brandId) {

        List<Price> applicablePrices = priceRepository.findPrices(brandId, productId, date);

        Optional<Price> highestPriorityPrice = applicablePrices.stream()
                .max(Comparator.comparingInt(Price::getPriority));

        if (highestPriorityPrice.isPresent()) {
            return PriceMapper.INSTANCE.toPriceResult(highestPriorityPrice.get());
        }

        return null;
    }

}
