package dao;

public class ExchangeRateDao {

    private static final String SELECT = """
        SELECT er.ID, er.Rate,
               b.ID AS bId, b.Code AS bCode, b.FullName AS bName, b.Sign AS bSign,
               t.ID as tId, t.Code AS tCode, t.FullName AS tName, t.Sign AS tSign
               FROM ExchangeRates er
               JOIN Currencies b ON er.BaseCurrencyId  = b.ID
               JOIN Currencies t ON er.TargtCurrencyId = t.ID
""";

    public List<ExchangeRate> findAll(){
        try(Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT);
            ResultSet resultSet = statement.executeQuery()){
            List<ExchangeRate> result = new ArrayList<>();
            while (resultSet.next()){
                result.add(map(resultSet));
            }
            return result;
        } catch (SQLException exception){
            throw new DatabaseException("couldn't get the exchange rates", exception);
        }
    }

    public Optional<ExchangeRate> findByCodes(String baseCode, String targetCode){
        String sql = SELECT + " WHERE b.code = ? AND t.Code = ?";
        try(Connection connection = Database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, baseCode);
            statement.setString(2, targetCode);
            try(ResultSet resultSet = statement.executeQuery()){
                return resultSet.next() ? Optional.of(map(resultSet)) : Optional.empty();
            }
        } catch (SQLException exception){
            throw new DatabaseException("couldn t found exchange rates", exception);
        }
    }

    private ExchangeRate map(ResultSet resultSet) throws SQLException {
        Currency base = new Currency(
                resultSet.getInt("bId"),
                resultSet.getString("bCode"),
                resultSet.getString("bName"),
                resultSet.getString("bSign"));
        Currency target = new Currency(
                resultSet.getInt("tId"),
                resultSet.getString("tCode"),
                resultSet.getString("tName"),
                resultSet.getString("bSign"));
        return new ExchangeRate(resultSet.getInt("ID"), base, target, resultSet.getBigDecimal("Rate"));
    }
}
