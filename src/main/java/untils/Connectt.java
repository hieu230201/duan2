package untils;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

public class Connectt {
    public Connection con() throws SQLServerException {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser("sa");
        ds.setPassword("Hieu230201.");
        ds.setDatabaseName("duan");
        ds.setServerName("192.168.16.121");
        ds.setPortNumber(1433);
        Connection con = ds.getConnection();
        return con;
    }
}
