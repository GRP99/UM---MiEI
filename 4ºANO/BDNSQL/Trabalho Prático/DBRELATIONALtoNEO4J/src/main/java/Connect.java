import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private String url;
    private String user;
    private String password;
    private Connection connection;

    public Connect() {
        this.url = "jdbc:oracle:thin:@localhost:1521/orclpdb1.localdomain";
        this.user = "hr";
        this.password = "hr";
        this.connection = null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public Connection setConnection() throws SQLException {
        this.connection = DriverManager.getConnection(this.url, this.user, this.password);
        return this.connection;
    }


}
