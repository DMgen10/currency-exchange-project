package dto;

import model.Currency;

import java.math.BigDecimal;

public record ExchangeRateResponse(int id, CurrencyResponse baseCurrency, CurrencyResponse targetCurrency, BigDecimal rate) {
}
