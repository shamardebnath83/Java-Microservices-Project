package com.CurrencyExchangeService.CurrencyExchangeService.Controller;

import com.CurrencyExchangeService.CurrencyExchangeService.Entity.ExchangeValue;
import com.CurrencyExchangeService.CurrencyExchangeService.Repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment env;

    @Autowired
    private ExchangeRepository repo;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ExchangeValue getRate(@PathVariable String from, @PathVariable String to) {

        ExchangeValue value = repo.findByFromCurrencyAndToCurrency(from, to);

        if (value == null) {
            throw new RuntimeException(
                    "Exchange rate not found for " + from + " to " + to
            );
        }

        value.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        return value;
    }
}
