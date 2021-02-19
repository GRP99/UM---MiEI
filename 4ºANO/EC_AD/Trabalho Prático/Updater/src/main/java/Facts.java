import java.util.Objects;

public class Facts {
    private int id;
    private int experience;
    private int host;
    private int location;
    private int installation;
    private String price;
    private String weekly_price;
    private String monthly_price;
    private String security_deposit;
    private String cleaning_fee;
    private String guests;
    private String extras;
    private String minimum_nights;
    private String availability;
    private int reviews;
    private int cancellationpolicy;

    public Facts(int id, int experience, int host, int location, int installation, String price, String weekly_price, String monthly_price, String security_deposit, String cleaning_fee, String guests, String extras, String minimum_nights, String availability, int reviews, int cancellationpolicy) {
        this.id = id;
        this.experience = experience;
        this.host = host;
        this.location = location;
        this.installation = installation;
        this.price = price;
        this.weekly_price = weekly_price;
        this.monthly_price = monthly_price;
        this.security_deposit = security_deposit;
        this.cleaning_fee = cleaning_fee;
        this.guests = guests;
        this.extras = extras;
        this.minimum_nights = minimum_nights;
        this.availability = availability;
        this.reviews = reviews;
        this.cancellationpolicy = cancellationpolicy;
    }

    public Facts() {
        id = 0;
    }

    public int getId() {
        return id;
    }


    public int getExperience() {
        return experience;
    }

    public int getHost() {
        return host;
    }


    public int getLocation() {
        return location;
    }


    public int getInstallation() {
        return installation;
    }


    public String getPrice() {
        return price;
    }


    public String getWeekly_price() {
        return weekly_price;
    }


    public String getMonthly_price() {
        return monthly_price;
    }

    public String getSecurity_deposit() {
        return security_deposit;
    }


    public String getCleaning_fee() {
        return cleaning_fee;
    }


    public String getGuests() {
        return guests;
    }


    public String getExtras() {
        return extras;
    }


    public String getMinimum_nights() {
        return minimum_nights;
    }

    public String getAvailability() {
        return availability;
    }

    public int getReviews() {
        return reviews;
    }

    public int getCancellationpolicy() {
        return cancellationpolicy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facts facts = (Facts) o;
        return id == facts.id && experience == facts.experience && host == facts.host && location == facts.location && installation == facts.installation && reviews == facts.reviews && cancellationpolicy == facts.cancellationpolicy && Objects.equals(price, facts.price) && Objects.equals(weekly_price, facts.weekly_price) && Objects.equals(monthly_price, facts.monthly_price) && Objects.equals(security_deposit, facts.security_deposit) && Objects.equals(cleaning_fee, facts.cleaning_fee) && Objects.equals(guests, facts.guests) && Objects.equals(extras, facts.extras) && Objects.equals(minimum_nights, facts.minimum_nights) && Objects.equals(availability, facts.availability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, experience, host, location, installation, price, weekly_price, monthly_price, security_deposit, cleaning_fee, guests, extras, minimum_nights, availability, reviews, cancellationpolicy);
    }
}
