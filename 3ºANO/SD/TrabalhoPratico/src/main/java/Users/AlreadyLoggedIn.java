package Users;

public class AlreadyLoggedIn extends Exception {

    public AlreadyLoggedIn() {
        super();
    }

    public AlreadyLoggedIn(String s) {
        super(s);
    }
}
