package service;

public class ExchangeRateService {
    private final ExchangeRateDao rateDao = new ExchangeRateDao();
    private final CurrencyDao currencyDao = new CurrencyDao();
}
