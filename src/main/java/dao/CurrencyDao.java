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

    public Currency save(String code, String fullName, String sign){
        String sql = "INSERT INTO Currencies (Code, FullName, Sign) VALUES (?,?,?)";
        try(Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, code);
            statement.setString(2, fullName);
            statement.setString(3, sign);
            statement.executeUpdate();
            try(ResultSet keys  = statement.getGeneratedKeys()){
                keys.next();
                return new Currency(keys.getInt(1),code,fullName,sign);
            }
        } catch (SQLException exception){
            if (exception instanceof SQLiteException sqLiteException &&
                    sqLiteException.getResultCode() == SQLiteErrorCode.SQLITE_CONSTRAINT_UNIQUE){
                throw new ConflictException("A currency with this code already exists");
            }
            throw new DatabaseException("Couldn't add the currency", exception);
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
