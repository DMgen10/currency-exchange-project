package service;

public class ExchangeRateService {
    private final ExchangeRateDao rateDao = new ExchangeRateDao();
    private final CurrencyDao currencyDao = new CurrencyDao();

    public List<ExchangeRateResponse> findAll(){
        return rateDao.findAll().stream().map(this::toResponse).toList();
    }

    public ExchangeRateResponse findByCodes(String pair){
        String[] codes = splitPair(pair);
        return rateDao.findByCodes(codes[0], codes[1]).map(this::toResponse).orElseThrow(() -> new  NotFoundException("The exchange rate for the pair was not found"));
    }

    public ExchangeRateResponse create(String baseCode, String targetCode, String rawRate){
        String base = baseCode.trim().toUpperCase();
        String target = targetCode.trim().toUpperCase();
        if (base.equals(target)){
            throw new BadRequestException("Base and target currency equals");
        }
        String upper = pair.toUpperCase();
        return new String[]{upper.substring(0,3),upper.substring(0,3)};
    }

    private ExchangeRateResponse toResponse(ExchangeRate exchangeRate) {
        return new ExchangeRateResponse(exchangeRate.id(), toCurrency(exchangeRate.baseCurrency()),
                toCurrency(exchangeRate.targetCurrency()),exchangeRate.rate().stripTrailingZeros());
    }

    private CurrencyResponse toCurrency(Currency currency) {
        return new CurrencyResponse(currency.id(),currency.fullName(),currency.code(),currency.sign());
    }

    private String[] splitPair(String pair) {
        if (pair == null || pair.length() != 6){
            throw new BadRequestException("The currency codes of the pair are missing in the address");
        }
        String upper = pair.toUpperCase();
        return new String[]{upper.substring(0,3),upper.substring(0,3)};
    }

}
