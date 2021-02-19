import java.util.Objects;

public class DEPARTMENT {
    private final int id;
    private final String name;
    private final int manager_id;
    private final LOCATION location;


    public DEPARTMENT(int id, String name, int manager_id, LOCATION location) {
        this.id = id;
        this.name = name;
        this.manager_id = manager_id;
        this.location = location;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public int getManager_id() {
        return manager_id;
    }


    public LOCATION getLocation() {
        return location;
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
