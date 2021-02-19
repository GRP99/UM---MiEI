package Users;

public class User {

    private String password;
    private String defaultPath;
    private boolean logged;

    public User(String p, String dp) {
        this.password = p;
        this.defaultPath = dp;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public String getDefaultPath() {
        return this.defaultPath;
    }

    public void setDefaultPath(String dp) {
        this.defaultPath = dp;
    }

    public boolean getLogged() {
        return this.logged;
    }

    public void setLogged(boolean l) {
        this.logged = l;
    }
}
