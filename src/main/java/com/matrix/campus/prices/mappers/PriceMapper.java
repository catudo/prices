package com.matrix.campus.prices.mappers;


import com.matrix.campus.prices.dto.PriceDTO;
import com.matrix.campus.prices.models.Price;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceMapper {

    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);

    @Mapping(source = "brand.id", target = "brandId")
    PriceDTO toPriceResult(Price price);
}
