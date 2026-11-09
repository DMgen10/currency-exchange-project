package service;

public class CurrencyService {

    private final CurrencyDao dao = new CurrencyDao();

    public List<CurrencyResponse> findAll(){
        return dao.findAll().stream().map(this::toResponse).toList();
    }
}
