package Users;

public class WrongPassword extends Exception {

    public WrongPassword() {
        super();
    }

    public WrongPassword(String s) {
        super(s);
    }
}
