package com.matrix.campus.prices.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.matrix.campus.prices.dto.PriceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PriceIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("priceController"));
    }


    @Test
    public void givenFindApplicablePricesOK() throws Exception {
        this.mockMvc.perform(get("/price/find_applicable_prices")
                        .param("date", "2021-06-15T00:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1")
                )

                .andDo(print()).andExpect(status().isOk());
    }


    private static Stream<Arguments> provideParamTest() {
        return Stream.of(
                Arguments.of("2020-06-14T10:00:00", 35455L, 1L, 35.5),
                Arguments.of("2020-06-14T16:00:00", 35455L, 1L, 25.45),
                Arguments.of("2020-06-14T21:00:00", 35455L, 1L, 35.5),
                Arguments.of("2020-06-15T10:00:00", 35455L, 1L, 30.50),
                Arguments.of("2020-06-16T21:00:00", 35455L, 1L, 38.95)

        );
    }


    @ParameterizedTest
    @MethodSource("provideParamTest")
    public void givenFindApplicablePricesTest1( String date,Long productId,Long brandId , Double expectedPrice) throws Exception {

        var result = mockMvc.perform(MockMvcRequestBuilders.get("/price/find_applicable_prices")
                .param("date", date)
                .param("productId", String.valueOf(productId))
                .param("brandId", String.valueOf(brandId)));
        var content = result.andReturn().getResponse().getContentAsString();
        var priceDTO = objectMapper.readValue(content, PriceDTO.class);
        assertEquals(expectedPrice,priceDTO.getPrice() );

    }





}
