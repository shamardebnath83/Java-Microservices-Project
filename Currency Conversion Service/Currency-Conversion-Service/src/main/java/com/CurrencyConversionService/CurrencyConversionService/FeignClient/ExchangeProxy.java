package com.CurrencyConversionService.CurrencyConversionService.FeignClient;

import com.CurrencyConversionService.CurrencyConversionService.Model.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Currency-Exchange-Service")
public interface ExchangeProxy {
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion getRate(@PathVariable String from, @PathVariable String to);
}