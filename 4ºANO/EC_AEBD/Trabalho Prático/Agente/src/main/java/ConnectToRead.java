import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToRead {
    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    public ConnectToRead() {
        this.url = "jdbc:oracle:thin:@localhost:1521/orclpdb1.localdomain";
        this.user = "system";
        this.password = "Oradoc_db1";
        this.connection = null;
    }

    public String getUrl() {
        return url;
    }


    public Connection getConnection() {
        return this.connection;
    }

    public void setConnection() throws SQLException {
        this.connection = DriverManager.getConnection(this.url, this.user, this.password);
    }
}