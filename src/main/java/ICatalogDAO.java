import java.sql.SQLException;
import java.util.List;

public interface ICatalogDAO {
    public void insertCatalog(Catalog catalog) throws  SQLException;

    public Catalog selectCatalog(int id);

    public List<Catalog> selectAllCatalog();



    public boolean deleteCatalog(int id) throws SQLException;

    public boolean updateCatalog(Catalog catalog) throws SQLException;


}
