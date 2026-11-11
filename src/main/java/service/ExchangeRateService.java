package service;

public class ExchangeRateService {
    private final ExchangeRateDao rateDao = new ExchangeRateDao();
    private final CurrencyDao currencyDao = new CurrencyDao();

    public List<ExchangeRateResponse> findAll(){
        return rateDao.findAll().stream().map(this::toResponse).toList();
    }

    private ExchangeRateResponse toResponse(ExchangeRate exchangeRate) {
        return new ExchangeRateResponse(exchangeRate.id(), toCurrency(exchangeRate.baseCurrency()),
                toCurrency(exchangeRate.targetCurrency()),exchangeRate.rate().stripTrailingZeros());
    }

    private CurrencyResponse toCurrency(Currency currency) {
        return new CurrencyResponse(currency.id(),currency.fullName(),currency.code(),currency.sign());
    }
}
