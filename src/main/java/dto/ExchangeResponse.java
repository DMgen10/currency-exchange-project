package dto;

public record ExchangeResponse(CurrencyResponse baseCurrency, CurrencyResponse targetCurrency, int amount) {
}
