import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        String line;
        String[] campos;
        BufferedReader in = new BufferedReader(new FileReader("csv\\Information_metrics_for_listings.csv"));
        PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream("sql\\settlement.sql", true)));

        int exp_counter = 1, cancellation_policy_counter = 1, responsetime_counter = 1, host_location_counter = 1;
        int verification_counter = 1, features_counter = 1, rooms_counter = 1, cities_counter = 1;
        int bed_counter = 1, property_counter = 1, amenities_counter = 1, countries_counter = 1, neighborhood_counter = 1;
        int location_counter = 1, installation_counter = 1, reviews_counter = 1, hosts_counter = 1;
        HashMap<String, Integer> experiences = new HashMap<>();
        HashMap<String, Integer> cancellations_policy = new HashMap<>();
        HashMap<String, Integer> host_responsetime = new HashMap<>();
        HashMap<String, Integer> host_location = new HashMap<>();
        HashMap<String, Integer> verifications = new HashMap<>();
        HashMap<String, Integer> features = new HashMap<>();
        HashMap<String, Integer> rooms = new HashMap<>();
        HashMap<String, Integer> beds = new HashMap<>();
        HashMap<String, Integer> properties = new HashMap<>();
        HashMap<String, Integer> amenities = new HashMap<>();
        HashMap<String, Integer> cities = new HashMap<>();
        HashMap<String, Integer> countries = new HashMap<>();
        HashMap<String, Integer> neighborhoods = new HashMap<>();
        HashMap<String, Location> locations = new HashMap<>();
        HashMap<String, Installation> installations = new HashMap<>();
        HashSet<Amenities> installation_amenity = new HashSet<>();
        HashMap<String, Review> reviews = new HashMap<>();
        HashMap<String, Host> hosts = new HashMap<>();
        ArrayList<Verifications> verificationsArrayList = new ArrayList<>();
        ArrayList<Facts> factsArrayList = new ArrayList<>();
        ArrayList<Features> featuresArrayList = new ArrayList<>();

        int i = 1;

        in.readLine();
        while ((line = in.readLine()) != null) {
            campos = line.split(";");

            // ALL UNIFORM
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


            //bedType
            if (!beds.containsKey(bedType)) {
                beds.put(bedType, bed_counter++);
            }

            //cancellationPolicy
            if (!cancellations_policy.containsKey(cancellationPolicy)) {
                cancellations_policy.put(cancellationPolicy, cancellation_policy_counter++);
            }

            //city
            if (!cities.containsKey(city)) {
                cities.put(city, cities_counter++);
            }

            //country
            if (!countries.containsKey(country)) {
                countries.put(country, countries_counter++);
            }

            //experiencesOffered
            if (!experiences.containsKey(experiencesOffered)) {
                experiences.put(experiencesOffered, exp_counter++);
            }

            //hostLocation
            if (!host_location.containsKey(hostLocation)) {
                host_location.put(hostLocation, host_location_counter++);
            }

            //neighbourhood
            if (!neighborhoods.containsKey(neighbourhood)) {
                neighborhoods.put(neighbourhood, neighborhood_counter++);
            }

            //propertyType
            if (!properties.containsKey(propertyType)) {
                properties.put(propertyType, property_counter++);
            }

            //hostResponseTime
            if (!host_responsetime.containsKey(hostResponseTime)) {
                host_responsetime.put(hostResponseTime, responsetime_counter++);
            }

            //roomType
            if (!rooms.containsKey(roomType)) {
                rooms.put(roomType, rooms_counter++);
            }

            //amenity
            String[] amenities_fields = string_Amenities.split(",");
            for (String ver : amenities_fields) {
                if (!amenities.containsKey(ver)) {
                    amenities.put(ver, amenities_counter++);
                }
            }

            //feature
            String[] features_fields = string_Features.split(",");
            for (String ver : features_fields) {
                if (!features.containsKey(ver)) {
                    features.put(ver, features_counter++);
                }
            }

            //verification
            String[] verification_fields = hostVerifications.split(",");
            for (String ver : verification_fields) {
                if (!verifications.containsKey(ver)) {
                    verifications.put(ver, verification_counter++);
                }
            }

            //hosts
            String key_host = "" + hostID + ";" + hostSince + ";" + hostLocation + ";" + hostResponseTime + ";" + hostResponseRate + ";" + hostTotalListingsCount + ";" + id;
            if (!hosts.containsKey(key_host)) {
                Host l = new Host(hosts_counter++, hostID, hostSince, host_location.get(hostLocation), host_responsetime.get(hostResponseTime), hostResponseRate, hostTotalListingsCount);
                hosts.put(key_host, l);
            }

            //installations
            String key_installation = "" + propertyType + ";" + roomType + ";" + accommodates + ";" + bathrooms + ";" + bedrooms + ";" + string_Beds + ";" + bedType + ";" + id;
            if (!installations.containsKey(key_installation)) {
                Installation l = new Installation(installation_counter++, properties.get(propertyType), rooms.get(roomType), beds.get(bedType), accommodates, bedrooms, string_Beds, bathrooms);
                installations.put(key_installation, l);
            }

            //locations
            String key_location = "" + neighbourhood + ";" + city + ";" + country + ";" + latitude + ";" + longitude;
            if (!locations.containsKey(key_location)) {
                Location l = new Location(location_counter++, neighborhoods.get(neighbourhood), cities.get(city), countries.get(country), latitude, longitude);
                locations.put(key_location, l);
            }

            //reviews
            String key_review = "" + numberReviews + ";" + firstReview + ";" + lastReview + ";" + reviewScoresRating + ";" + reviewScoresAccuracy + ";" + reviewScoresCleanliness + ";" + reviewScoresChecking + ";" + reviewScoresCommunication + ";" + reviewScoresLocation + ";" + reviewScoresValue + ";" + reviewsMonth;
            if (!reviews.containsKey(key_review)) {
                Review l = new Review(reviews_counter++, numberReviews, firstReview, lastReview, reviewScoresRating, reviewScoresAccuracy, reviewScoresCleanliness, reviewScoresChecking, reviewScoresCommunication, reviewScoresLocation, reviewScoresValue, reviewsMonth);
                reviews.put(key_review, l);
            }

            //amenities
            for (String s : amenities_fields) {
                int id_amenity = amenities.get(s);
                int id_installation = installations.get(key_installation).getId();
                installation_amenity.add(new Amenities(id_installation, id_amenity));
            }

            //verifications
            for (String s : verification_fields) {
                int id_verification = verifications.get(s);
                int id_host = hosts.get(key_host).getId();
                verificationsArrayList.add(new Verifications(id_host, id_verification));
            }

            //facts
            factsArrayList.add(new Facts(
                    Integer.parseInt(id)
                    , experiences.get(experiencesOffered)
                    , hosts.get(key_host).getId()
                    , locations.get(key_location).getId()
                    , installations.get(key_installation).getId()
                    , price, weeklyPrice, monthlyPrice, securityDeposit, cleaningFee, guestsIncluded, extraPeople, minimumNights, availability
                    , reviews.get(key_review).getId()
                    , cancellations_policy.get(cancellationPolicy)
            ));

            //features
            for (String s : features_fields) {
                int id_feature = features.get(s);
                featuresArrayList.add(new Features(id_feature, Integer.parseInt(id)));
            }
            i++;
        }
        System.out.println("Read " + i + " lines...");

        System.out.println("Start writing ... ");
        long init_time = System.currentTimeMillis();

        //dates
        out.println("CALL Generate_Dates('2008-01-01','2021-01-01');");
        out.println("INSERT INTO dim_date VALUES ('1998-01-01',1998,1,1);");
        //bed
        for (Map.Entry<String, Integer> m : beds.entrySet()) {
            String insert = "insert into dim_bed values (" + m.getValue() + ",'" + m.getKey() + "');";
            out.println(insert);
            out.flush();
        }
        //cancellation Policy
        for (Map.Entry<String, Integer> m : cancellations_policy.entrySet()) {
            String insert = "insert into dim_cancellationpolicy values (" + m.getValue() + ",'" + m.getKey() + "');";
            out.println(insert);
            out.flush();
        }
        //city
        for (Map.Entry<String, Integer> m : cities.entrySet()) {
            String insert = "insert into dim_city values (" + m.getValue() + ",'" + m.getKey().replace("'", "") + "');";
            out.println(insert);
            out.flush();
        }
        //country
        for (Map.Entry<String, Integer> m : countries.entrySet()) {
            String insert = "insert into dim_country values (" + m.getValue() + ",'" + m.getKey().replace("'", "") + "');";
            out.println(insert);
            out.flush();
        }
        //experience
        for (Map.Entry<String, Integer> m : experiences.entrySet()) {
            String insert = "insert into dim_experience values (" + m.getValue() + ",'" + m.getKey() + "');";
            out.println(insert);
            out.flush();
        }
        //host location
        for (Map.Entry<String, Integer> m : host_location.entrySet()) {
            String insert = "insert into dim_locationhost values (" + m.getValue() + ",'" + m.getKey().replace("'", "") + "');";
            out.println(insert);
            out.flush();
        }
        //neighborhood
        for (Map.Entry<String, Integer> m : neighborhoods.entrySet()) {
            String insert = "insert into dim_neighbourhood values (" + m.getValue() + ",'" + m.getKey().replace("'", "") + "');";
            out.println(insert);
            out.flush();
        }
        //properties
        for (Map.Entry<String, Integer> m : properties.entrySet()) {
            String insert = "insert into dim_property values (" + m.getValue() + ",'" + m.getKey() + "');";
            out.println(insert);
            out.flush();
        }
        //host response time
        for (Map.Entry<String, Integer> m : host_responsetime.entrySet()) {
            String insert = "insert into dim_responsetime values (" + m.getValue() + ",'" + m.getKey() + "');";
            out.println(insert);
            out.flush();
        }
        //room
        for (Map.Entry<String, Integer> m : rooms.entrySet()) {
            String insert = "insert into dim_room values (" + m.getValue() + ",'" + m.getKey() + "');";
            out.println(insert);
            out.flush();
        }
        //amenities
        for (Map.Entry<String, Integer> m : amenities.entrySet()) {
            String insert = "insert into dim_amenity values (" + m.getValue() + ",'" + m.getKey() + "');";
            out.println(insert);
            out.flush();
        }
        //features
        for (Map.Entry<String, Integer> m : features.entrySet()) {
            String insert = "insert into dim_feature values (" + m.getValue() + ",'" + m.getKey() + "');";
            out.println(insert);
            out.flush();
        }
        //verification
        for (Map.Entry<String, Integer> m : verifications.entrySet()) {
            String insert = "insert into dim_verification values (" + m.getValue() + ",'" + m.getKey() + "');";
            out.println(insert);
            out.flush();
        }
        //hosts
        for (Map.Entry<String, Host> m : hosts.entrySet()) {
            String[] date = m.getValue().getSince().split("/");
            String first_date = "'" + date[2] + "-" + date[1] + "-" + date[0] + "'";
            String insert = "insert into dim_host values (" + m.getValue().getId() + "," + m.getValue().getId_host() + "," + first_date + "," + m.getValue().getFk_locationhost() + "," + m.getValue().getFk_responsetime() + "," + m.getValue().getResponse_rate() + "," + m.getValue().getTotal_listings_count() + ");";
            out.println(insert);
            out.flush();
        }
        //installations
        for (Map.Entry<String, Installation> m : installations.entrySet()) {
            String insert = "insert into dim_installation values (" + m.getValue().getId() + "," + m.getValue().getProperty() + "," + m.getValue().getRoom() + "," + m.getValue().getAccommodates() + "," + m.getValue().getBathrooms() + "," + m.getValue().getBedrooms() + "," + m.getValue().getBeds() + "," + m.getValue().getBed_type() + ");";
            out.println(insert);
            out.flush();
        }
        //locations
        for (Map.Entry<String, Location> m : locations.entrySet()) {
            String insert = "insert into dim_location values (" + m.getValue().getId() + "," + m.getValue().getNeighborhood() + "," + m.getValue().getCity() + "," + m.getValue().getCountry() + "," + m.getValue().getLatitude() + "," + m.getValue().getLongitude() + ");";
            out.println(insert);
            out.flush();
        }
        //reviews
        for (Map.Entry<String, Review> m : reviews.entrySet()) {
            String[] date = m.getValue().getDate_first().split("/");
            String first_date = "'" + date[2] + "-" + date[1] + "-" + date[0] + "'";
            date = m.getValue().getDate_last().split("/");
            String second_date = "'" + date[2] + "-" + date[1] + "-" + date[0] + "'";
            String insert = "insert into dim_reviews values (" + m.getValue().getId() + "," + m.getValue().getNumber() + "," + first_date + "," + second_date + "," + m.getValue().getScores_rating() + "," + m.getValue().getScores_accuracy() + "," + m.getValue().getScores_cleanliness() + "," + m.getValue().getScores_checking() + "," + m.getValue().getScores_communication() + "," + m.getValue().getScores_location() + "," + m.getValue().getScores_value() + "," + m.getValue().getReviews_per_month() + ");";
            out.println(insert);
            out.flush();
        }

        //installations plus amenity
        for (Amenities a : installation_amenity) {
            String insert = "insert into dim_amenities values (" + a.getInstallation() + "," + a.getAmenity() + ");";
            out.println(insert);
            out.flush();
        }
        //host plus verification
        for (Verifications a : verificationsArrayList) {
            String insert = "insert into dim_verifications values (" + a.getId_host() + "," + a.getId_verification() + ");";
            out.println(insert);
            out.flush();
        }
        int shit = 1;
        //facts
        for (Facts a : factsArrayList) {
            String insert = "insert into fact_AIRBNB values (" + a.getId() + "," + a.getExperience() + "," + a.getHost() + "," + a.getLocation() + "," + a.getInstallation() + "," + a.getPrice() + "," + a.getWeekly_price() + "," + a.getMonthly_price() + "," + a.getSecurity_deposit() + "," + a.getCleaning_fee() + "," + a.getGuests() + "," + a.getExtras() + "," + a.getMinimum_nights() + "," + a.getAvailability() + "," + a.getReviews() + "," + a.getCancellationpolicy() + ");";
            out.println(insert);
            out.flush();
            shit++;
        }
        System.out.println("Write " + shit + " facts");
        //fact plus feature
        for (Features a : featuresArrayList) {
            String insert = "insert into dim_features values (" + a.getFacto() + "," + a.getFeature() + ");";
            out.println(insert);
            out.flush();
        }

        System.out.println("Final time = " + (System.currentTimeMillis() - init_time) + "ms");

        out.close();
        in.close();
    }
}