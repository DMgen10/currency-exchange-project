package dao;

import model.Currency;

import java.util.List;

public class CurrencyDao {

    public List<Currency> findAll(){
        String sql = "SELECT ID, Code, FullName, Sign FROM Currencies";
        return result;
    }
}
