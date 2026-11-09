package service;

public class CurrencyService {

    private final CurrencyDao dao = new CurrencyDao();

    public List<CurrencyResponse> findAll(){
        return dao.findAll().stream().map(this::toResponse).toList();
    }

    public CurrencyResponse findByCode(String code){
        return dao.findByCode(code).map(this::toResponse).orElseThrow(() -> new NotFoundException("Currency not found"));
    }

    public CurrencyResponse create(String code, String name, String sign){
        return toResponse(dao.save(code, name, sign));
    }

    private CurrencyResponse toResponse(Currency currency) {
        return new CurrencyResponse(currency.id(), currency.fullName(), currency.code(), currency.sign());
    }


}
