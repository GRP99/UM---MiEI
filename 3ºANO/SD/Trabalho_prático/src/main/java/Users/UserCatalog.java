package Users;

import RWLocks.InterfRWLock;
import RWLocks.RWLock;
import java.util.HashMap;
import java.util.Map;

public class UserCatalog implements InterfUserCatalog {

    private Map<String, User> catalog;
    private InterfRWLock rwlock;

    public UserCatalog() {
        this.catalog = new HashMap<>();
        this.rwlock = new RWLock();
    }

    public boolean login(String email, String password) throws WrongPassword, WrongEmail, AlreadyLoggedIn {
        this.rwlock.readLock();
        if (!(this.catalog.containsKey(email))) {
            this.rwlock.readUnlock();
            throw new WrongEmail();
        }
        User u = catalog.get(email);
        if (!(u.getPassword().equals(password))) {
            this.rwlock.readUnlock();
            throw new WrongPassword();
        }
        if (!(u.getLogged())) {
            this.rwlock.readUnlock();
            throw new AlreadyLoggedIn();
        }
        u.setLogged(true);
        this.rwlock.readUnlock();
        return true;
    }

    public void changeEmail(String email, String newemail) throws WrongEmail, ExistingEmail {
        this.rwlock.writeLock();
        if (!(catalog.containsKey(email))) {
            this.rwlock.writeUnlock();
            throw new WrongEmail();
        }
        if (catalog.containsKey(newemail)) {
            this.rwlock.writeUnlock();
            throw new ExistingEmail();
        }
        User u = this.catalog.get(email);
        this.catalog.remove(email, u);
        this.catalog.put(newemail, u);
        this.rwlock.writeUnlock();
    }

    public void changePassword(String email, String password, String newpassword) throws WrongEmail, WrongPassword {
        this.rwlock.writeLock();
        if (!(this.catalog.containsKey(email))) {
            this.rwlock.writeUnlock();
            throw new WrongEmail();
        }
        if (!(this.catalog.get(email).getPassword().equals(password))) {
            this.rwlock.writeUnlock();
            throw new WrongPassword();
        }
        User u = this.catalog.get(email);
        u.setPassword(newpassword);
        this.catalog.put(email, u);
        rwlock.writeUnlock();
    }

    public void newUser(String email, String password, String path) throws ExistingEmail {
        this.rwlock.writeLock();
        if (this.catalog.containsKey(email)) {
            this.rwlock.writeUnlock();
            throw new ExistingEmail();
        }
        User u = new User(password, path);
        this.catalog.put(email, u);
        this.rwlock.writeUnlock();
    }

    public String getDefaultPath(String email) throws WrongEmail {
        this.rwlock.readLock();
        if (!(this.catalog.containsKey(email))) {
            this.rwlock.readUnlock();
            throw new WrongEmail();
        }
        User u = this.catalog.get(email);
        String str = u.getDefaultPath();
        this.rwlock.readUnlock();
        return str;
    }

    public boolean getUserStatus(String email) throws WrongEmail {
        this.rwlock.readLock();
        if (!(this.catalog.containsKey(email))) {
            this.rwlock.readUnlock();
            throw new WrongEmail();
        }
        User u = this.catalog.get(email);
        boolean status = u.getLogged();
        this.rwlock.readUnlock();
        return status;
    }

    public void changeUserStatus(boolean status, String email) {
        rwlock.writeLock();
        catalog.get(email).setLogged(status);
        rwlock.writeUnlock();
    }

    public void logout(String email) throws WrongEmail {
        this.rwlock.writeLock();
        if (!(this.catalog.containsKey(email))) {
            this.rwlock.writeUnlock();
            throw new WrongEmail();
        }
        this.catalog.get(email).setLogged(false);
        this.rwlock.writeUnlock();
    }
}
