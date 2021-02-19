import java.util.Objects;

public class Host {
    private final int id;
    private final String id_host;
    private final String since;
    private final int fk_locationhost;
    private final int fk_responsetime;
    private final String response_rate;
    private final String total_listings_count;

    public Host(int id, String id_host, String since, int fk_locationhost, int fk_responsetime, String response_rate, String total_listings_count) {
        this.id = id;
        this.id_host = id_host;
        this.since = since;
        this.fk_locationhost = fk_locationhost;
        this.fk_responsetime = fk_responsetime;
        this.response_rate = response_rate;
        this.total_listings_count = total_listings_count;
    }

    public int getId() {
        return id;
    }

    public String getId_host() {
        return id_host;
    }

    public String getSince() {
        return since;
    }

    public int getFk_locationhost() {
        return fk_locationhost;
    }

    public int getFk_responsetime() {
        return fk_responsetime;
    }

    public String getResponse_rate() {
        return response_rate;
    }

    public String getTotal_listings_count() {
        return total_listings_count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return id == host.id && fk_locationhost == host.fk_locationhost && fk_responsetime == host.fk_responsetime && Objects.equals(id_host, host.id_host) && Objects.equals(since, host.since) && Objects.equals(response_rate, host.response_rate) && Objects.equals(total_listings_count, host.total_listings_count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, id_host, since, fk_locationhost, fk_responsetime, response_rate, total_listings_count);
    }
}
