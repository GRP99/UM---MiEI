package Users;

public class ExistingEmail extends Exception {

    public ExistingEmail() {
        super();
    }

    public ExistingEmail(String s) {
        super(s);
    }
}
