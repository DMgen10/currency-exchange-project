package dao;

public class ExchangeRateDao {

    private static final String SELECT = "";

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
