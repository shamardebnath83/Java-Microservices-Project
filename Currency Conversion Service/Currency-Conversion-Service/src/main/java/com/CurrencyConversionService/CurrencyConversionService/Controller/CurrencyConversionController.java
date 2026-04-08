package com.CurrencyConversionService.CurrencyConversionService.Controller;

import com.CurrencyConversionService.CurrencyConversionService.FeignClient.ExchangeProxy;
import com.CurrencyConversionService.CurrencyConversionService.Model.CurrencyConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyConversionController {

    @Autowired
    private ExchangeProxy proxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{qty}")
    public CurrencyConversion convert(@PathVariable String from,
                                      @PathVariable String to,
                                      @PathVariable BigDecimal qty) {

        CurrencyConversion response = proxy.getRate(from, to);

        if (response == null || response.getConversionMultiple() == null) {
            throw new RuntimeException("Conversion rate not found");
        }

        return new CurrencyConversion(
                response.getId(),
                from,
                to,
                response.getConversionMultiple(),
                qty,
                qty.multiply(response.getConversionMultiple()),
                response.getPort()
        );

    }
}
