import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToWrite {
    private final String url;
    private final String user;
    private final String password;
    private Connection connection;

    public ConnectToWrite() {
        this.url = "jdbc:oracle:thin:@localhost:1521/dbmonitor.localdomain";
        this.user = "monitor";
        this.password = "database";
        this.connection = null;
    }

    public String getUrl() {
        return url;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public String getUser() {
        return user;
    }

    public void setConnection() throws SQLException {
        this.connection = DriverManager.getConnection(this.url, this.user, this.password);
    }
}