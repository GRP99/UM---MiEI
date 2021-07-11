package Users;

public interface InterfUserCatalog {

    public boolean login(String email, String password) throws WrongPassword, WrongEmail, AlreadyLoggedIn;

    public void changeEmail(String email, String newemail) throws WrongEmail, ExistingEmail;

    public void changePassword(String email, String password, String newpassword) throws WrongEmail, WrongPassword;

    public void newUser(String email, String password, String path) throws ExistingEmail;

    public String getDefaultPath(String email) throws WrongEmail;

    public void logout(String email) throws WrongEmail;

    public boolean getUserStatus(String email) throws WrongEmail;

    public void changeUserStatus(boolean status, String email);
}
