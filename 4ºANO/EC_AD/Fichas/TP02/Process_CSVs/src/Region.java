import java.util.Objects;

public class Region {
    private String province;
    private String city;

    public Region() {
    }

    public Region(String province, String city) {
        this.province = province;
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(province, region.province) &&
                Objects.equals(city, region.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(province, city);
    }
}
