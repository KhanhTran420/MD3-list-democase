import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogDAO implements ICatalogDAO{

    private String jdbcURL = "jdbc:mysql://localhost:3306/PhuLuc";
    private String jdbcUsername = "root";
    private String jdbcPassword = "khanh123";

    private static final String INSERT_CATALOG_SQL = "INSERT INTO catalog (name) VALUES (?);";
    private static final String SELECT_CATALOG_BY_ID = "select id,name from catalog where id =?";
    private static final String SELECT_ALL_CATALOG = "select * from catalog";
    private static final String DELETE_CATALOG_SQL = "delete from catalog where id = ?;";
    private static final String UPDATE_CATALOG_SQL = "update catalog set name = ? where id = ?;";

    public CatalogDAO(){}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertCatalog(Catalog catalog) throws SQLException{
        System.out.println(INSERT_CATALOG_SQL);
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATALOG_SQL)) {
            preparedStatement.setString(1,catalog.getName());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            printSQLException(e);
        }
    }

    public Catalog selectCatalog(int id) {
        Catalog catalog = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATALOG_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");

                catalog = new Catalog(id, name);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return catalog;
    }

    public List<Catalog> selectAllCatalog() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Catalog> catalogs = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATALOG);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                catalogs.add(new Catalog(id, name));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return catalogs;
    }

    public boolean deleteCatalog(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_CATALOG_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateCatalog(Catalog catalog) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_CATALOG_SQL);) {
            statement.setString(1, catalog.getName());

            statement.setInt(2, catalog.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}
