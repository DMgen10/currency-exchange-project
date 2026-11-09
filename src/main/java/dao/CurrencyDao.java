package dao;

import model.Currency;

import java.util.List;

public class CurrencyDao {

    public List<Currency> findAll(){
        String sql = "SELECT ID, Code, FullName, Sign FROM Currencies";
        try(Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            List<Currency> result = new ArrayList<>();
            while (resultSet.next()){
                result.add(map(resultSet));
            }
            return result;
        } catch (SQLException exception){
            throw new DatabaseException("Couldn't get a list of currencies",exception);
        }
    }

    public Optional<Currency> findByCode(String code){
        String sql = "SELECT ID, Code, FullName, Sign FROM Currencies WHERE Code = ?";
        try(Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, code);
            try(ResultSet resultSet = statement.executeQuery()){
                return resultSet.next() ? Optional.of(map(resultSet)) : Optional.empty();
            }
        } catch (SQLException exception){
            throw new DatabaseException("Couldn't find the currency", exception);
        }
    }

    private Currency map(ResultSet resultSet) throws SQLException {
        return new Currency(
                resultSet.getInt("ID"),
                resultSet.getString("Code"),
                resultSet.getString("FullName"),
                resultSet.getString("Sign"));
    }
}
