import java.util.Objects;

public class DEPARTMENT {
    private int id;
    private String name;
    private int manager_id;
    private LOCATION location;

    public DEPARTMENT() {
    }

    public DEPARTMENT(int id, String name, int manager_id, LOCATION location) {
        this.id = id;
        this.name = name;
        this.manager_id = manager_id;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public LOCATION getLocation() {
        return location;
    }

    public void setLocation(LOCATION location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DEPARTMENT that = (DEPARTMENT) o;
        return id == that.id && name == that.name && manager_id == that.manager_id && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, manager_id, location);
    }
}
