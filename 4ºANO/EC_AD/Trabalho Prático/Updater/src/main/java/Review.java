import java.util.Objects;

public class Review {
    private int id;
    private String number;
    private String date_first;
    private String date_last;
    private String scores_rating;
    private String scores_accuracy;
    private String scores_cleanliness;
    private String scores_checking;
    private String scores_communication;
    private String scores_location;
    private String scores_value;
    private String reviews_per_month;

    public Review() {
    }

    public Review(int id, String number, String date_first, String date_last, String scores_rating, String scores_accuracy, String scores_cleanliness, String scores_checking, String scores_communication, String scores_location, String scores_value, String reviews_per_month) {
        this.id = id;
        this.number = number;
        this.date_first = date_first;
        this.date_last = date_last;
        this.scores_rating = scores_rating;
        this.scores_accuracy = scores_accuracy;
        this.scores_cleanliness = scores_cleanliness;
        this.scores_checking = scores_checking;
        this.scores_communication = scores_communication;
        this.scores_location = scores_location;
        this.scores_value = scores_value;
        this.reviews_per_month = reviews_per_month;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getDate_first() {
        return date_first;
    }

    public String getDate_last() {
        return date_last;
    }

    public String getScores_rating() {
        return scores_rating;
    }

    public String getScores_accuracy() {
        return scores_accuracy;
    }

    public String getScores_cleanliness() {
        return scores_cleanliness;
    }

    public String getScores_checking() {
        return scores_checking;
    }

    public String getScores_communication() {
        return scores_communication;
    }

    public String getScores_location() {
        return scores_location;
    }

    public String getScores_value() {
        return scores_value;
    }

    public String getReviews_per_month() {
        return reviews_per_month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return id == review.id && Objects.equals(number, review.number) && Objects.equals(date_first, review.date_first) && Objects.equals(date_last, review.date_last) && Objects.equals(scores_rating, review.scores_rating) && Objects.equals(scores_accuracy, review.scores_accuracy) && Objects.equals(scores_cleanliness, review.scores_cleanliness) && Objects.equals(scores_checking, review.scores_checking) && Objects.equals(scores_communication, review.scores_communication) && Objects.equals(scores_location, review.scores_location) && Objects.equals(scores_value, review.scores_value) && Objects.equals(reviews_per_month, review.reviews_per_month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, date_first, date_last, scores_rating, scores_accuracy, scores_cleanliness, scores_checking, scores_communication, scores_location, scores_value, reviews_per_month);
    }
}