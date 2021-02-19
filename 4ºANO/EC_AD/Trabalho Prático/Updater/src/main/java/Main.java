import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class Main {
    static ArrayList<String> dates = new ArrayList<>();
    static HashMap<String, Integer> beds = new HashMap<>();
    static HashMap<String, Integer> cancellations_policy = new HashMap<>();
    static HashMap<String, Integer> cities = new HashMap<>();
    static HashMap<String, Integer> countries = new HashMap<>();
    static HashMap<String, Integer> experiences = new HashMap<>();
    static HashMap<String, Integer> host_location = new HashMap<>();
    static HashMap<String, Integer> neighborhoods = new HashMap<>();
    static HashMap<String, Integer> properties = new HashMap<>();
    static HashMap<String, Integer> host_responsetime = new HashMap<>();
    static HashMap<String, Integer> rooms = new HashMap<>();
    static HashMap<String, Integer> amenities = new HashMap<>();
    static HashMap<String, Integer> features = new HashMap<>();
    static HashMap<String, Integer> verifications = new HashMap<>();
    static HashMap<String, Host> hosts = new HashMap<>();
    static HashMap<String, Installation> installations = new HashMap<>();
    static HashMap<String, Location> locations = new HashMap<>();
    static HashMap<String, Review> reviews = new HashMap<>();
    static HashSet<Amenities> installation_amenity = new HashSet<>();
    static ArrayList<Verifications> verificationsArrayList = new ArrayList<>();
    static ArrayList<Facts> factsArrayList = new ArrayList<>();
    static ArrayList<Features> featuresArrayList = new ArrayList<>();


    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/airbnb?useTimezone=true&serverTimezone=UTC", "root", "12345");
            Statement stmt = con.createStatement();

            populate_structures(stmt);

            int exp_counter = experiences.size();
            int cancellation_policy_counter = cancellations_policy.size();
            int responsetime_counter = host_responsetime.size();
            int host_location_counter = host_location.size();
            int verification_counter = verifications.size();
            int features_counter = features.size();
            int rooms_counter = rooms.size();
            int cities_counter = cities.size();
            int bed_counter = beds.size();
            int property_counter = properties.size();
            int amenities_counter = amenities.size();
            int countries_counter = countries.size();
            int neighborhood_counter = neighborhoods.size();
            int location_counter = locations.size();
            int installation_counter = installations.size();
            int reviews_counter = reviews.size();
            int hosts_counter = hosts.size();

            con.close();

            String line;
            String[] campos;

            PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("sql\\settlement.sql", true)));
            BufferedReader in = new BufferedReader(new FileReader("csv\\update_listings.csv"));

            in.readLine();

            while ((line = in.readLine()) != null) {
                campos = line.split(";");

                String id = campos[0];
                if (id.equals("")) {
                    id = "-1";
                }
                String experiencesOffered = campos[1];
                if (experiencesOffered.equals("")) {
                    experiencesOffered = "unknown";
                }
                String hostID = campos[2];
                if (hostID.equals("")) {
                    hostID = "-1";
                }
                String hostSince = campos[3];
                if (hostSince.equals("")) {
                    hostSince = "01/01/1998";
                }
                String hostLocation = campos[4];
                if (hostLocation.equals("")) {
                    hostLocation = "unknown";
                }
                String hostResponseTime = campos[5];
                if (hostResponseTime.equals("")) {
                    hostResponseTime = "unknown";
                }
                String hostResponseRate = campos[6];
                if (hostResponseRate.equals("")) {
                    hostResponseRate = "-1";
                }
                String hostTotalListingsCount = campos[7];
                if (hostTotalListingsCount.equals("")) {
                    hostTotalListingsCount = "-1";
                }
                String hostVerifications = campos[8];
                if (hostVerifications.equals("")) {
                    hostVerifications = "unknown";
                }
                String neighbourhood = campos[9];
                if (neighbourhood.equals("")) {
                    neighbourhood = "unknown";
                }
                String city = campos[10];
                if (city.equals("")) {
                    city = "unknown";
                }
                String country = campos[11];
                if (country.equals("")) {
                    country = "unknown";
                }
                String latitude = campos[12];
                if (latitude.equals("")) {
                    latitude = "-1";
                }
                String longitude = campos[13];
                if (longitude.equals("")) {
                    longitude = "-1";
                }
                String propertyType = campos[14];
                if (propertyType.equals("")) {
                    propertyType = "unknown";
                }
                String roomType = campos[15];
                if (roomType.equals("")) {
                    roomType = "unknown";
                }
                String accommodates = campos[16];
                if (accommodates.equals("")) {
                    accommodates = "-1";
                }
                String bathrooms = campos[17];
                if (bathrooms.equals("")) {
                    bathrooms = "-1";
                }
                String bedrooms = campos[18];
                if (bedrooms.equals("")) {
                    bedrooms = "-1";
                }
                String string_Beds = campos[19];
                if (string_Beds.equals("")) {
                    string_Beds = "-1";
                }
                String bedType = campos[20];
                if (bedType.equals("")) {
                    bedType = "unknown";
                }
                String string_Amenities = campos[21];
                if (string_Amenities.equals("")) {
                    string_Amenities = "unknown";
                }
                String price = campos[22];
                if (price.equals("")) {
                    price = "-1";
                }
                String weeklyPrice = campos[23];
                if (weeklyPrice.equals("")) {
                    weeklyPrice = "-1";
                }
                String monthlyPrice = campos[24];
                if (monthlyPrice.equals("")) {
                    monthlyPrice = "-1";
                }
                String securityDeposit = campos[25];
                if (securityDeposit.equals("")) {
                    securityDeposit = "-1";
                }
                String cleaningFee = campos[26];
                if (cleaningFee.equals("")) {
                    cleaningFee = "-1";
                }
                String guestsIncluded = campos[27];
                if (guestsIncluded.equals("")) {
                    guestsIncluded = "-1";
                }
                String extraPeople = campos[28];
                if (extraPeople.equals("")) {
                    extraPeople = "-1";
                }
                String minimumNights = campos[29];
                if (minimumNights.equals("")) {
                    minimumNights = "-1";
                }
                String availability = campos[30];
                if (availability.equals("")) {
                    availability = "-1";
                }
                String numberReviews = campos[31];
                if (numberReviews.equals("")) {
                    numberReviews = "-1";
                }
                String firstReview = campos[32];
                if (firstReview.equals("")) {
                    firstReview = "01/01/1998";
                }
                String lastReview = campos[33];
                if (lastReview.equals("")) {
                    lastReview = "01/01/1998";
                }
                String reviewScoresRating = campos[34];
                if (reviewScoresRating.equals("")) {
                    reviewScoresRating = "-1";
                }
                String reviewScoresAccuracy = campos[35];
                if (reviewScoresAccuracy.equals("")) {
                    reviewScoresAccuracy = "-1";
                }
                String reviewScoresCleanliness = campos[36];
                if (reviewScoresCleanliness.equals("")) {
                    reviewScoresCleanliness = "-1";
                }
                String reviewScoresChecking = campos[37];
                if (reviewScoresChecking.equals("")) {
                    reviewScoresChecking = "-1";
                }
                String reviewScoresCommunication = campos[38];
                if (reviewScoresCommunication.equals("")) {
                    reviewScoresCommunication = "-1";
                }
                String reviewScoresLocation = campos[39];
                if (reviewScoresLocation.equals("")) {
                    reviewScoresLocation = "-1";
                }
                String reviewScoresValue = campos[40];
                if (reviewScoresValue.equals("")) {
                    reviewScoresValue = "-1";
                }
                String cancellationPolicy = campos[41];
                if (cancellationPolicy.equals("")) {
                    cancellationPolicy = "unknown";
                }
                String reviewsMonth = campos[42];
                if (reviewsMonth.equals("")) {
                    reviewsMonth = "-1.0";
                }
                String string_Features = campos[43];
                if (string_Features.equals("")) {
                    string_Features = "unknown";
                }

                Facts facts = new Facts();

                for (Facts f : factsArrayList) {
                    if (f.getId() == Integer.parseInt(id)) {
                        facts = f;
                    }
                }
                if (facts.getId() != 0) {

                    if (!experiences.containsKey(experiencesOffered)) {
                        exp_counter += 1;
                        experiences.put(experiencesOffered, exp_counter);
                        String insert = "insert into dim_experience values (" + exp_counter + ",'" + experiencesOffered + "');";
                        out.println(insert);
                        out.flush();
                        int id_experience = experiences.get(experiencesOffered);
                        String s1 = "update fact_AIRBNB set fk_experience = " + id_experience + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    } else {
                        int experience = experiences.get(experiencesOffered);
                        if (experience != facts.getExperience()) {
                            int id_experience = experiences.get(experiencesOffered);
                            String s1 = "update fact_AIRBNB set fk_experience = " + id_experience + " where id=" + id + " ;";
                            out.println(s1);
                            out.flush();
                        }
                    }


                    String[] date = hostSince.split("/");
                    String first_date = "'" + date[2] + "-" + date[1] + "-" + date[0] + "'";
                    if (!(dates.contains(first_date))) {
                        String insert = "insert into dim_date values (" + first_date + "," + date[2] + "," + date[1] + "," + date[0] + ");";
                        out.println(insert);
                        out.flush();
                    }
                    String key_host = "" + hostID + ";" + first_date + ";" + hostLocation + ";" + hostResponseTime + ";" + hostResponseRate + ";" + hostTotalListingsCount + ";" + id;
                    if (!hosts.containsKey(key_host)) {
                        if (!host_location.containsKey(hostLocation)) {
                            host_location_counter += 1;
                            host_location.put(hostLocation, host_location_counter);
                            String insert = "insert into dim_locationhost values (" + host_location_counter + ",'" + hostLocation.replace("'", "") + "');";
                            out.println(insert);
                            out.flush();
                        }

                        if (!host_responsetime.containsKey(hostResponseTime)) {
                            responsetime_counter += 1;
                            host_responsetime.put(hostResponseTime, responsetime_counter);
                            String insert = "insert into dim_responsetime values (" + responsetime_counter + ",'" + hostResponseTime + "');";
                            out.println(insert);
                            out.flush();
                        }

                        hosts_counter += 1;
                        Host l = new Host(hosts_counter, hostID, first_date, host_location.get(hostLocation), host_responsetime.get(hostResponseTime), hostResponseRate, hostTotalListingsCount);
                        hosts.put(key_host, l);
                        String insert = "insert into dim_host values (" + l.getId() + "," + l.getId_host() + "," + first_date + "," + l.getFk_locationhost() + "," + l.getFk_responsetime() + "," + l.getResponse_rate() + "," + l.getTotal_listings_count() + ");";
                        out.println(insert);
                        out.flush();

                        int id_host = hosts.get(key_host).getId();
                        String s1 = "update fact_AIRBNB set fk_host = " + id_host + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    } else {
                        Host host = hosts.get(key_host);
                        if (host.getId() != facts.getHost()) {
                            int id_host = hosts.get(key_host).getId();
                            String s1 = "update fact_AIRBNB set fk_host = " + id_host + " where id=" + id + " ;";
                            out.println(s1);
                            out.flush();
                        }
                    }

                    int id_host = hosts.get(key_host).getId();

                    ArrayList<String> stringArrayList = new ArrayList<>();
                    for (Verifications v : verificationsArrayList) {
                        if (v.getId_host() == facts.getHost()) {
                            int id_verification = v.getId_verification();
                            for (Map.Entry<String, Integer> m : verifications.entrySet()) {
                                if (m.getValue() == id_verification) {
                                    stringArrayList.add(m.getKey());
                                }
                            }
                        }
                    }
                    String[] verification_fields = hostVerifications.split(",");
                    for (String ver : verification_fields) {
                        if (!(stringArrayList.contains(ver))) {
                            if (!verifications.containsKey(ver)) {
                                verification_counter += 1;
                                verifications.put(ver, verification_counter);
                                String insert = "insert into dim_verification values (" + verification_counter + ",'" + ver + "');";
                                out.println(insert);
                                out.flush();
                            }
                            int id_verification = verifications.get(ver);
                            Verifications verifications1 = new Verifications(id_host, id_verification);
                            verificationsArrayList.add(verifications1);
                            String insert = "insert into dim_verifications values (" + verifications1.getId_host() + "," + verifications1.getId_verification() + ");";
                            out.println(insert);
                            out.flush();
                        } else {
                            stringArrayList.remove(ver);
                        }
                    }
                    if (stringArrayList.size() != 0) {
                        for (String s : stringArrayList) {
                            int id_verification = verifications.get(s);
                            String ss = "delete from dim_verifications where fk_idhost=" + id_host + ",fk_verification=" + id_verification + " ;";
                            out.println(ss);
                            out.flush();
                        }
                    }

                    String key_location = "" + neighbourhood + ";" + city + ";" + country + ";" + latitude + ";" + longitude;
                    if (!locations.containsKey(key_host)) {
                        if (!neighborhoods.containsKey(neighbourhood)) {
                            neighborhood_counter += 1;
                            neighborhoods.put(neighbourhood, neighborhood_counter);
                            String insert = "insert into dim_neighbourhood values (" + neighborhood_counter + ",'" + neighbourhood.replace("'", "") + "');";
                            out.println(insert);
                            out.flush();
                        }
                        if (!cities.containsKey(city)) {
                            cities_counter += 1;
                            cities.put(city, cities_counter);
                            String insert = "insert into dim_city values (" + cities_counter + ",'" + city.replace("'", "") + "');";
                            out.println(insert);
                            out.flush();
                        }
                        if (!countries.containsKey(country)) {
                            cities_counter += 1;
                            countries.put(country, countries_counter);
                            String insert = "insert into dim_country values (" + countries_counter + ",'" + country.replace("'", "") + "');";
                            out.println(insert);
                            out.flush();
                        }

                        location_counter += 1;
                        Location l = new Location(location_counter, neighborhoods.get(neighbourhood), cities.get(city), countries.get(country), latitude, longitude);
                        locations.put(key_location, l);
                        String insert = "insert into dim_location values (" + l.getId() + "," + l.getNeighborhood() + "," + l.getCity() + "," + l.getCountry() + "," + l.getLatitude() + "," + l.getLongitude() + ");";
                        out.println(insert);
                        out.flush();

                        int id_location = locations.get(key_location).getId();
                        String s1 = "update fact_AIRBNB set fk_location = " + id_location + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    } else {
                        Location location = locations.get(key_location);
                        if (facts.getLocation() != location.getId()) {
                            int id_location = locations.get(key_location).getId();
                            String s1 = "update fact_AIRBNB set fk_location = " + id_location + " where id=" + id + " ;";
                            out.println(s1);
                            out.flush();
                        }
                    }


                    String key_installation = "" + propertyType + ";" + roomType + ";" + accommodates + ";" + bathrooms + ";" + bedrooms + ";" + string_Beds + ";" + bedType;


                    if (!installations.containsKey(key_installation)) {
                        if (!properties.containsKey(propertyType)) {
                            property_counter += 1;
                            properties.put(propertyType, property_counter);
                            String insert = "insert into dim_property values (" + property_counter + ",'" + propertyType + "');";
                            out.println(insert);
                            out.flush();
                        }
                        if (!rooms.containsKey(roomType)) {
                            rooms_counter += 1;
                            rooms.put(roomType, rooms_counter);
                            String insert = "insert into dim_room values (" + rooms_counter + ",'" + roomType + "');";
                            out.println(insert);
                            out.flush();
                        }
                        if (!beds.containsKey(bedType)) {
                            bed_counter += 1;
                            beds.put(bedType, bed_counter);
                            String insert = "insert into dim_bed values (" + bed_counter + ",'" + bedType + "');";
                            out.println(insert);
                            out.flush();
                        }

                        installation_counter += 1;
                        Installation l = new Installation(installation_counter, properties.get(propertyType), rooms.get(roomType), beds.get(bedType), accommodates, bedrooms, string_Beds, bathrooms);
                        installations.put(key_installation, l);
                        String insert = "insert into dim_installation values (" + l.getId() + "," + l.getProperty() + "," + l.getRoom() + "," + l.getAccommodates() + "," + l.getBathrooms() + "," + l.getBedrooms() + "," + l.getBeds() + "," + l.getBed_type() + ");";
                        out.println(insert);
                        out.flush();

                        int id_installation = installations.get(key_installation).getId();
                        String s1 = "update fact_AIRBNB set fk_installion = " + id_installation + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    } else {
                        Installation installation = installations.get(key_installation);
                        if (facts.getInstallation() != installation.getId()) {
                            int id_installation = installations.get(key_installation).getId();
                            String s1 = "update fact_AIRBNB set fk_installion = " + id_installation + " where id=" + id + " ;";
                            out.println(s1);
                            out.flush();
                        }
                    }

                    int id_installation = installations.get(key_installation).getId();

                    ArrayList<String> stringArrayListAmenities = new ArrayList<>();
                    for (Amenities v : installation_amenity) {
                        if (v.getInstallation() == facts.getInstallation()) {
                            int id_amenity = v.getAmenity();
                            for (Map.Entry<String, Integer> m : amenities.entrySet()) {
                                if (m.getValue() == id_amenity) {
                                    stringArrayListAmenities.add(m.getKey());
                                }
                            }
                        }
                    }
                    String[] amenities_fields = string_Amenities.split(",");
                    for (String ver : amenities_fields) {
                        if (!(stringArrayListAmenities.contains(ver))) {
                            if (!amenities.containsKey(ver)) {
                                amenities_counter += 1;
                                amenities.put(ver, amenities_counter);
                                String ins = "insert into dim_amenity values (" + amenities_counter + ",'" + ver + "');";
                                out.println(ins);
                                out.flush();
                            }
                            int id_amenity = amenities.get(ver);
                            Amenities a = new Amenities(id_installation, id_amenity);
                            installation_amenity.add(a);
                            String insert = "insert into dim_amenities values (" + a.getInstallation() + "," + a.getAmenity() + ");";
                            out.println(insert);
                            out.flush();
                        } else {
                            stringArrayListAmenities.remove(ver);
                        }
                    }
                    if (stringArrayListAmenities.size() != 0) {
                        for (String s : stringArrayListAmenities) {
                            int id_amenity = amenities.get(s);
                            String ss = "delete from dim_amenities where installation=" + id_installation + ",amenity=" + id_amenity + " ;";
                            out.println(ss);
                            out.flush();
                        }
                    }


                    String[] date2 = firstReview.split("/");
                    String second_date = "'" + date2[2] + "-" + date2[1] + "-" + date2[0] + "'";
                    if (!dates.contains(second_date)) {
                        String insert = "insert into dim_date values (" + second_date + "," + date2[2] + "," + date2[1] + "," + date2[0] + ");";
                        out.println(insert);
                        out.flush();
                    }
                    String[] date3 = lastReview.split("/");
                    String third_date = "'" + date3[2] + "-" + date3[1] + "-" + date3[0] + "'";
                    if (!(dates.contains(third_date))) {
                        String insert = "insert into dim_date values (" + third_date + "," + date3[2] + "," + date3[1] + "," + date3[0] + ");";
                        out.println(insert);
                        out.flush();
                    }
                    String key_review = "" + numberReviews + ";" + second_date + ";" + third_date + ";" + reviewScoresRating + ";" + reviewScoresAccuracy + ";" + reviewScoresCleanliness + ";" + reviewScoresChecking + ";" + reviewScoresCommunication + ";" + reviewScoresLocation + ";" + reviewScoresValue + ";" + reviewsMonth;

                    if (!reviews.containsKey(key_review)) {
                        reviews_counter += 1;
                        Review l = new Review(reviews_counter, numberReviews, second_date, third_date, reviewScoresRating, reviewScoresAccuracy, reviewScoresCleanliness, reviewScoresChecking, reviewScoresCommunication, reviewScoresLocation, reviewScoresValue, reviewsMonth);
                        reviews.put(key_review, l);
                        String insert = "insert into dim_reviews values (" + l.getId() + "," + l.getNumber() + "," + second_date + "," + third_date + "," + l.getScores_rating() + "," + l.getScores_accuracy() + "," + l.getScores_cleanliness() + "," + l.getScores_checking() + "," + l.getScores_communication() + "," + l.getScores_location() + "," + l.getScores_value() + "," + l.getReviews_per_month() + ");";
                        out.println(insert);
                        out.flush();

                        int id_review = reviews.get(key_review).getId();
                        String s1 = "update fact_AIRBNB set fk_reviews = " + id_review + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    } else {
                        Review review = reviews.get(key_review);
                        if (facts.getReviews() != review.getId()) {
                            int id_review = reviews.get(key_review).getId();
                            String s1 = "update fact_AIRBNB set fk_reviews = " + id_review + " where id=" + id + " ;";
                            out.println(s1);
                            out.flush();
                        }
                    }


                    if (!cancellations_policy.containsKey(cancellationPolicy)) {
                        cancellation_policy_counter += 1;
                        cancellations_policy.put(cancellationPolicy, cancellation_policy_counter);
                        String insert = "insert into dim_cancellationpolicy values (" + cancellation_policy_counter + ",'" + cancellationPolicy + "');";
                        out.println(insert);
                        out.flush();

                        int id_cancellation = cancellations_policy.get(cancellationPolicy);
                        String s1 = "update fact_AIRBNB set fk_cancellationpolicy = " + id_cancellation + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    } else {
                        int cancellation = cancellations_policy.get(cancellationPolicy);
                        if (cancellation != facts.getCancellationpolicy()) {
                            int id_cancellation = cancellations_policy.get(cancellationPolicy);
                            String s1 = "update fact_AIRBNB set fk_cancellationpolicy = " + id_cancellation + " where id=" + id + " ;";
                            out.println(s1);
                            out.flush();
                        }
                    }


                    if (!(facts.getPrice().equals(price))) {
                        String s1 = "update fact_AIRBNB set price = " + price + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    }
                    if (!(facts.getWeekly_price().equals(weeklyPrice))) {
                        String s1 = "update fact_AIRBNB set weekly_price = " + weeklyPrice + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    }
                    if (!(facts.getMonthly_price().equals(monthlyPrice))) {
                        String s1 = "update fact_AIRBNB set monthly_price = " + monthlyPrice + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    }
                    if (!(facts.getSecurity_deposit().equals(securityDeposit))) {
                        String s1 = "update fact_AIRBNB set security_deposit = " + securityDeposit + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    }
                    if (!(facts.getCleaning_fee().equals(cleaningFee))) {
                        String s1 = "update fact_AIRBNB set cleaning_fee = " + cleaningFee + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    }
                    if (!(facts.getGuests().equals(guestsIncluded))) {
                        String s1 = "update fact_AIRBNB set guests_included = " + guestsIncluded + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    }
                    if (!(facts.getExtras().equals(extraPeople))) {
                        String s1 = "update fact_AIRBNB set extra_people = " + extraPeople + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    }
                    if (!(facts.getMinimum_nights().equals(minimumNights))) {
                        String s1 = "update fact_AIRBNB set minimum_nights = " + minimumNights + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    }
                    if (!(facts.getAvailability().equals(availability))) {
                        String s1 = "update fact_AIRBNB set availability365 = " + availability + " where id=" + id + " ;";
                        out.println(s1);
                        out.flush();
                    }

                    ArrayList<String> stringArrayListFeatures = new ArrayList<>();
                    for (Features f : featuresArrayList) {
                        if (f.getFacto() == facts.getId()) {
                            int id_feature = f.getFeature();
                            for (Map.Entry<String, Integer> m : features.entrySet()) {
                                if (m.getValue() == id_feature) {
                                    stringArrayListFeatures.add(m.getKey());
                                }
                            }
                        }
                    }
                    String[] features_fields = string_Features.split(",");
                    for (String ver : features_fields) {
                        if (!(stringArrayListFeatures.contains(ver))) {
                            if (!features.containsKey(ver)) {
                                features_counter += 1;
                                features.put(ver, features_counter);
                                String insert = "insert into dim_feature values (" + features_counter + ",'" + ver + "');";
                                out.println(insert);
                                out.flush();
                            }
                            int id_feature = features.get(ver);
                            Features features1 = new Features(id_feature, Integer.parseInt(id));
                            featuresArrayList.add(features1);
                            String s1 = "insert into dim_features values (" + features1.getFacto() + "," + features1.getFeature() + ");";
                            out.println(s1);
                            out.flush();
                        } else {
                            stringArrayListFeatures.remove(ver);
                        }
                    }
                    if (stringArrayListFeatures.size() != 0) {
                        for (String s : stringArrayListFeatures) {
                            int id_feature = features.get(s);
                            String s1 = "delete from dim_features where idairbnb=" + id + ",feature=" + id_feature + " ;";
                            out.println(s1);
                            out.flush();
                        }
                    }
                }
            }
        } catch (
                Exception e) {
            System.out.println(e);
        }

    }

    private static void populate_structures(Statement stmt) throws SQLException {
        ResultSet rsd = stmt.executeQuery("select * from dim_date");
        while (rsd.next()) {
            dates.add("'" + rsd.getString("date") + "'");
        }

        ResultSet rsb = stmt.executeQuery("select * from dim_bed");
        while (rsb.next()) {
            beds.put(rsb.getString("type"), rsb.getInt("id_bed"));
        }

        ResultSet rs_cp = stmt.executeQuery("select * from dim_cancellationpolicy");
        while (rs_cp.next()) {
            cancellations_policy.put(rs_cp.getString("cancellation_policy"), rs_cp.getInt("id_cancellationpolicy"));
        }

        ResultSet rs_city = stmt.executeQuery("select * from dim_city");
        while (rs_city.next()) {
            cities.put(rs_city.getString("city"), rs_city.getInt("id_city"));
        }

        ResultSet rs_country = stmt.executeQuery("select * from dim_country");
        while (rs_country.next()) {
            countries.put(rs_country.getString("country"), rs_country.getInt("id_country"));
        }

        ResultSet rs_eo = stmt.executeQuery("select * from dim_experience");
        while (rs_eo.next()) {
            experiences.put(rs_eo.getString("experiences_offered"), rs_eo.getInt("id_experience"));
        }

        ResultSet rs_hl = stmt.executeQuery("select * from dim_locationhost");
        while (rs_hl.next()) {
            host_location.put(rs_hl.getString("location"), rs_hl.getInt("id_locationhost"));
        }

        ResultSet rs_nei = stmt.executeQuery("select * from dim_neighbourhood");
        while (rs_nei.next()) {
            neighborhoods.put(rs_nei.getString("neighbourhood"), rs_nei.getInt("id_neighbourhood"));
        }

        ResultSet rs_pro = stmt.executeQuery("select * from dim_property");
        while (rs_pro.next()) {
            properties.put(rs_pro.getString("type"), rs_pro.getInt("id_property"));
        }

        ResultSet rs_rt = stmt.executeQuery("select * from dim_responsetime");
        while (rs_rt.next()) {
            host_responsetime.put(rs_rt.getString("response_time"), rs_rt.getInt("id_responsetime"));
        }

        ResultSet rs_ro = stmt.executeQuery("select * from dim_room");
        while (rs_ro.next()) {
            rooms.put(rs_ro.getString("type"), rs_ro.getInt("id_room"));
        }

        ResultSet rs_a = stmt.executeQuery("select * from dim_amenity");
        while (rs_a.next()) {
            amenities.put(rs_a.getString("amenity"), rs_a.getInt("id_amenity"));
        }

        ResultSet rs_f = stmt.executeQuery("select * from dim_feature");
        while (rs_f.next()) {
            features.put(rs_f.getString("feature"), rs_f.getInt("id_feature"));
        }

        ResultSet rs_v = stmt.executeQuery("select * from dim_verification");
        while (rs_v.next()) {
            verifications.put(rs_v.getString("verification"), rs_v.getInt("id_verification"));
        }

        ResultSet rs_h = stmt.executeQuery("select h.*, lh.*, rt.*, f.id as id_airbnb from dim_host h,dim_locationhost lh,dim_responsetime rt, fact_airbnb f where h.fk_locationhost=lh.id_locationhost and h.fk_responsetime=rt.id_responsetime and f.fk_host=h.id_host");
        while (rs_h.next()) {
            Host host = new Host(rs_h.getInt("id_host"), rs_h.getString("id"), rs_h.getString("since"), rs_h.getInt("fk_locationhost"), rs_h.getInt("fk_responsetime"), rs_h.getString("response_rate"), rs_h.getString("total_listings_count"));
            String key_host = "" + rs_h.getString("id") + ";" + rs_h.getString("since") + ";" + rs_h.getString("location") + ";" + rs_h.getString("response_time") + ";" + rs_h.getString("response_rate") + ";" + rs_h.getString("total_listings_count") + ";" + rs_h.getString("id_airbnb");
            hosts.put(key_host, host);
        }

        ResultSet rs_i = stmt.executeQuery("select id_installation,accommodates,bathrooms,bedrooms,beds,p.type as property,r.type as room,b.type as bed, fk_room, fk_property, fk_bedtype, f.id as id_airbnb \n" +
                " from dim_installation,dim_property p ,dim_room r,dim_bed b, fact_airbnb f where fk_property=id_property and fk_room=id_room and id_bed=fk_bedtype and f.fk_installion=id_installation");
        while (rs_i.next()) {
            Installation installation = new Installation(rs_i.getInt("id_installation"), rs_i.getInt("fk_property"), rs_i.getInt("fk_room"), rs_i.getInt("fk_bedtype"), rs_i.getString("accommodates"), rs_i.getString("bedrooms"), rs_i.getString("beds"), rs_i.getString("bathrooms"));
            String key_installation = "" + rs_i.getString("property") + ";" + rs_i.getString("room") + ";" + rs_i.getString("accommodates") + ";" + rs_i.getString("bathrooms") + ";" + rs_i.getString("bedrooms") + ";" + rs_i.getString("beds") + ";" + rs_i.getString("bed") + ";" + rs_i.getString("id_airbnb");
            installations.put(key_installation, installation);
        }

        ResultSet rs_l = stmt.executeQuery("select * from dim_location, dim_neighbourhood, dim_city, dim_country where fk_neighbourhood= id_neighbourhood and fk_city=id_city and id_country=fk_country");
        while (rs_l.next()) {
            Location location = new Location(rs_l.getInt("id_location"), rs_l.getInt("fk_neighbourhood"), rs_l.getInt("fk_city"), rs_l.getInt("fk_country"), rs_l.getString("latitude"), rs_l.getString("longitude"));
            String key_location = "" + rs_l.getString("neighbourhood") + ";" + rs_l.getString("city") + ";" + rs_l.getString("country") + ";" + rs_l.getString("latitude") + ";" + rs_l.getString("longitude");
            locations.put(key_location, location);
        }

        ResultSet rs_r = stmt.executeQuery("select * from dim_reviews");
        while (rs_r.next()) {
            Review review = new Review(rs_r.getInt("id_reviews"), rs_r.getString("number"), rs_r.getString("first"), rs_r.getString("last"), rs_r.getString("scores_rating"), rs_r.getString("scores_accuracy"), rs_r.getString("scores_cleanliness"), rs_r.getString("scores_checkin"), rs_r.getString("scores_communication"), rs_r.getString("scores_location"), rs_r.getString("scores_value"), rs_r.getString("per_Month"));
            String key_review = "" + rs_r.getString("number") + ";" + rs_r.getString("first") + ";" + rs_r.getString("last") + ";" + rs_r.getString("scores_rating") + ";" + rs_r.getString("scores_accuracy") + ";" + rs_r.getString("scores_cleanliness") + ";" + rs_r.getString("scores_checkin") + ";" + rs_r.getString("scores_communication") + ";" + rs_r.getString("scores_location") + ";" + rs_r.getString("scores_value") + ";" + rs_r.getString("per_Month");
            reviews.put(key_review, review);
        }

        ResultSet rs_af = stmt.executeQuery("select * from dim_amenities");
        while (rs_af.next()) {
            int id_amenity = rs_af.getInt("installation");
            int id_installation = rs_af.getInt("amenity");
            installation_amenity.add(new Amenities(id_installation, id_amenity));
        }

        //verifications
        ResultSet rs_vf = stmt.executeQuery("select * from dim_verifications");
        while (rs_vf.next()) {
            int id_verification = rs_vf.getInt("fk_verification");
            int id_host = rs_vf.getInt("fk_idhost");
            verificationsArrayList.add(new Verifications(id_host, id_verification));
        }

        //facts
        ResultSet rs_fact = stmt.executeQuery("select * from fact_airbnb");
        while (rs_fact.next()) {
            factsArrayList.add(new Facts(
                    rs_fact.getInt("id")
                    , rs_fact.getInt("fk_experience")
                    , rs_fact.getInt("fk_host")
                    , rs_fact.getInt("fk_location")
                    , rs_fact.getInt("fk_installion")
                    , rs_fact.getString("price"), rs_fact.getString("weekly_price"), rs_fact.getString("monthly_price"), rs_fact.getString("security_deposit"), rs_fact.getString("cleaning_fee"), rs_fact.getString("guests_included"), rs_fact.getString("extra_people"), rs_fact.getString("minimum_nights"), rs_fact.getString("availability365")
                    , rs_fact.getInt("fk_reviews")
                    , rs_fact.getInt("fk_cancellationpolicy")
            ));
        }

        //features
        ResultSet rs_ft = stmt.executeQuery("select * from dim_features");
        while (rs_ft.next()) {
            int id_feature = rs_ft.getInt("feature");
            featuresArrayList.add(new Features(id_feature, rs_ft.getInt("idairbnb")));
        }

    }
}
