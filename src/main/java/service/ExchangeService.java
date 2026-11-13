package service;

public class ExchangeService {

    private final ExchangeRateDao rateDao = new ExchangeRateDao();
    private final CurrencyDao currencyDao = new CurrencyDao();


    public ExchangeResponse exchange(String fromCode , String toCode, String rawAmount){
        
        String base = fromCode.trim().toUpperCase();
        String target = toCode.trim().toUpperCase();
        BigDecimal amount = parseAmount(rawAmount);
        if (base.equals(target)){
            throw new BadRequestException("The source and target currencies should be different");
        }
        Currency baseCurrency = currencyDao.findByCode(base).orElse(null);
        Currency targetCurrency = currencyDao.findByCode(target).orElse(null);

        if (baseCurrency == null || targetCurrency == null){
            throw new NotFoundException("One or both currency from a currency pair does not exist in the database");
        }
        BigDecimal rate = findRate(baseCurrency, targetCurrency).orElseThrow(() -> new NotFoundException("Exchange rate for pair not found"));
        BigDecimal convertedAmount = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        return new ExchangeResponse(toCurrency(baseCurrency), toCurrency(targetCurrency), amount,rate,convertedAmount);
    }

    private Optional<BigDecimal> findRate(Currency from, Currency to) {
        Optional<ExchangeRate> direct = rateDao.findByCodes(from.code(),to.code());
        if (direct.isPresent()){
            return Optional.of(direct.get().rate());
        }
        Optional<ExchangeRate> reverseDirect = rateDao.findByCodes(to.code(), from.code());
        if (reverseDirect.isPresent()){
            return Optional.of(BigDecimal.ONE.divide(reverseDirect.get().rate(),6, RoundingMode.HALF_UP));
        }

        return Optional.empty();
    }

    private BigDecimal parseAmount(String rawAmount) {
        try{
            BigDecimal amount = new BigDecimal(rawAmount.trim()).setScale(6, RoundingMode.HALF_UP);
            if (amount.signum() <= 0){
                throw new BadRequestException("Amount should be positive integer");
            }
            return amount;
        } catch (NumberFormatException exception){
            throw new BadRequestException("amount should be integer");
        }
    }

    private CurrencyResponse toCurrency(Currency currency) {
        return new CurrencyResponse(currency.id(),currency.fullName(),currency.code(),currency.sign());
    }
}
