package com.CurrencyExchangeService.CurrencyExchangeService.Repository;

import com.CurrencyExchangeService.CurrencyExchangeService.Entity.ExchangeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRepository extends JpaRepository<ExchangeValue, Long> {
    ExchangeValue findByFromCurrencyAndToCurrency(String from, String to);
}
