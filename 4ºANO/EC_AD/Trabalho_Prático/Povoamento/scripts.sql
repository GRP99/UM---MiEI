	/* --------------------------------------------------------------------------------------
	Follow the following steps before executing the DELETE command: In MySQL Workbench
    	1 Go to Edit --> Preferences
    	2 Click "SQL Editor" tab and uncheck "Safe Updates" check box
    	3 Query --> Reconnect to Server // logout and then login
    	4 Now execute your SQL query

DELETE FROM dim_features ;
DELETE FROM fact_airbnb ;
DELETE FROM dim_verifications;
DELETE FROM dim_amenities ;
DELETE FROM dim_reviews;
DELETE FROM dim_location ;
DELETE FROM dim_installation ;
DELETE FROM dim_host;
DELETE FROM dim_verification ;
DELETE FROM dim_feature ;
DELETE FROM dim_amenity ;
DELETE FROM dim_room ;
DELETE FROM dim_responsetime ;
DELETE FROM dim_property ;
DELETE FROM dim_neighbourhood ;
DELETE FROM dim_locationhost ;
DELETE FROM dim_experience ;
DELETE FROM dim_country ;
DELETE FROM dim_city ;
DELETE FROM dim_cancellationpolicy ;
DELETE FROM dim_bed ;
DELETE FROM dim_date ;

--------------------------------------------------------------------------------------
	You can reset the counter with: 
ALTER TABLE dim_reviews AUTO_INCREMENT = 1 ;
ALTER TABLE dim_location AUTO_INCREMENT = 1 ;
ALTER TABLE dim_installation AUTO_INCREMENT = 1 ;
ALTER TABLE dim_host AUTO_INCREMENT = 1 ;
ALTER TABLE dim_verification AUTO_INCREMENT = 1 ;
ALTER TABLE dim_feature AUTO_INCREMENT = 1 ;
ALTER TABLE dim_amenity AUTO_INCREMENT = 1 ;
ALTER TABLE dim_room AUTO_INCREMENT = 1 ;
ALTER TABLE dim_responsetime AUTO_INCREMENT = 1 ;
ALTER TABLE dim_property AUTO_INCREMENT = 1 ;
ALTER TABLE dim_neighbourhood AUTO_INCREMENT = 1 ;
ALTER TABLE dim_locationhost AUTO_INCREMENT = 1 ;
ALTER TABLE dim_experience AUTO_INCREMENT = 1 ;
ALTER TABLE dim_country AUTO_INCREMENT = 1 ;
ALTER TABLE dim_city AUTO_INCREMENT = 1 ;
ALTER TABLE dim_cancellationpolicy AUTO_INCREMENT = 1 ;
ALTER TABLE dim_bed AUTO_INCREMENT = 1 ;
-------------------------------------------------------------------------------------- */
------------------------------------------------------------------------------------

/* Populate the DIM_DATE table */
DELIMITER |
CREATE PROCEDURE Generate_Dates(date_start DATE, date_end DATE)
BEGIN
	WHILE date_start <= date_end DO
    	INSERT INTO dim_date (date, year,month, day) VALUES (date_start, year(date_start), month(date_start), day(date_start));
    	SET date_start = date_add(date_start, INTERVAL 1 DAY);
  	END WHILE;
END; |
DELIMITER ;

------------------------------------------------------------------------------------

/* See the Host of certain id */
DELIMITER $$
CREATE PROCEDURE GetHost(IN id INT)
BEGIN
	SELECT f.id, h.id, h.since, lh.location, r.response_time, h.response_rate, h.total_listings_count
		FROM fact_airbnb f, dim_host h, dim_locationhost lh, dim_responsetime r
			WHERE f.fk_host = h.id_host AND h.fk_locationhost = lh.id_locationhost AND h.fk_responsetime = r.id_responsetime 
				AND f.id = id ;
END $$

/* See the Installation of certain id */
DELIMITER $$
CREATE PROCEDURE GetInstallation(IN id INT)
BEGIN
	SELECT f.id, p.type, r.type, i.accommodates, i.bathrooms, i.bedrooms, i.beds, b.type
		FROM fact_airbnb f, dim_installation i, dim_property p, dim_room r, dim_bed b
			WHERE f.fk_installion = i.id_installation AND i.fk_property = p.id_property AND i.fk_room = r.id_room AND i.fk_bedtype = b.id_bed
				AND f.id = id ;
END $$

/* See the Location of certain id */
DELIMITER $$
CREATE PROCEDURE GetLocation(IN id INT)
BEGIN
	SELECT f.id, n.neighbourhood, ci.city, co.country, l.latitude, l.longitude
		FROM fact_airbnb f, dim_location l, dim_city ci, dim_country co, dim_neighbourhood n
			WHERE f.fk_location = l.id_location AND l.fk_neighbourhood = n.id_neighbourhood AND l.fk_city = ci.id_city AND l.fk_country = co.id_country
				AND f.id = id ;
END $$

/* See the Review of certain id */
DELIMITER $$
CREATE PROCEDURE GetReview(IN id INT)
BEGIN
	SELECT f.id, r.number, r.first, r.last, r.scores_rating, r.scores_accuracy, r.scores_cleanliness, r.scores_checkin, r.scores_communication, r.scores_location, r.scores_value, r.per_Month
		FROM fact_airbnb f, dim_reviews r
			WHERE f.fk_reviews = r.id_reviews AND f.id = id ;
END $$

/* See the Amenities of certain id */
DELIMITER $$
CREATE PROCEDURE GetAmenities(IN id INT)
BEGIN
	SELECT f.id, a.amenity
		FROM fact_airbnb f, dim_installation i, dim_amenities aa, dim_amenity a
			WHERE f.fk_installion = i.id_installation AND i.id_installation = aa.installation AND aa.amenity = a.id_amenity
				AND f.id = id ;
END $$

/* See the Verifications of certain id */
DELIMITER $$
CREATE PROCEDURE GetVerifications(IN id INT)
BEGIN
	SELECT f.id, v.verification
		FROM fact_airbnb f, dim_host h, dim_verifications vv, dim_verification v
			WHERE f.fk_host = h.id_host AND h.id_host = vv.fk_idhost AND vv.fk_verification = v.id_verification 
				AND f.id = id ;
END $$

/* See the Fact of certain id */
DELIMITER $$
CREATE PROCEDURE GetFact(IN id INT)
BEGIN
	SELECT f.id, e.experiences_offered, f.price, f.weekly_price, f.monthly_price, f.security_deposit, f.cleaning_fee, 
		f.guests_included, f.extra_people, f.minimum_nights, f.availability365, cp.cancellation_policy
			FROM fact_airbnb f, dim_experience e, dim_cancellationpolicy cp
				WHERE f.fk_experience = e.id_experience AND f.fk_cancellationpolicy = cp.id_cancellationpolicy
				AND f.id = id ;
END $$

/* See the Features of certain id */
DELIMITER $$
CREATE PROCEDURE GetFeatures(IN id INT)
BEGIN
	SELECT fa.id, f.feature
		FROM fact_airbnb fa, dim_features ff, dim_feature f
			WHERE fa.id = ff.idairbnb AND ff.feature = f.id_feature
				AND fa.id = id ;
END $$

/* ALL like CSV except Amenities, Verifications, Features */
DELIMITER $$
CREATE PROCEDURE GetALL(IN id INT)
BEGIN
	SELECT f.id, e.experiences_offered, 
		h.id, h.since, lh.location, rt.response_time, h.response_rate, h.total_listings_count,
        n.neighbourhood, ci.city, co.country, l.latitude, l.longitude,
        p.type, ro.type, i.accommodates, i.bathrooms, i.bedrooms, i.beds, b.type,
		f.price, f.weekly_price, f.monthly_price, f.security_deposit, f.cleaning_fee, f.guests_included, f.extra_people, f.minimum_nights, f.availability365,
         r.number, r.first, r.last, r.scores_rating, r.scores_accuracy, r.scores_cleanliness, r.scores_checkin, r.scores_communication, r.scores_location, r.scores_value, cp.cancellation_policy, r.per_Month
			FROM fact_airbnb f, dim_experience e, 
				dim_host h, dim_locationhost lh, dim_responsetime rt,
                dim_location l, dim_city ci, dim_country co, dim_neighbourhood n,
                dim_installation i, dim_property p, dim_room ro, dim_bed b,
                dim_reviews r,
				dim_cancellationpolicy cp
				WHERE f.fk_experience = e.id_experience 
					AND f.fk_host = h.id_host AND h.fk_locationhost = lh.id_locationhost AND h.fk_responsetime = rt.id_responsetime
                    AND f.fk_location = l.id_location AND l.fk_neighbourhood = n.id_neighbourhood AND l.fk_city = ci.id_city AND l.fk_country = co.id_country
                    AND f.fk_installion = i.id_installation AND i.fk_property = p.id_property AND i.fk_room = ro.id_room AND i.fk_bedtype = b.id_bed
                    AND f.fk_reviews = r.id_reviews
					AND f.fk_cancellationpolicy = cp.id_cancellationpolicy
					AND f.id = id ;
END $$

------------------------------------------------------------------------------------

/* CSV except Amenities, Verifications, Features */
CREATE VIEW CSV AS
	SELECT f.id AS ID, e.experiences_offered AS ExperiencesOffered, 
		h.id AS HostID, h.since AS HostSince, lh.location AS HostLocation, rt.response_time AS HostResponseTime, h.response_rate AS HostResponseRate, h.total_listings_count AS HostTotalListingsCount,
        n.neighbourhood AS Neighbourhood, ci.city AS City, co.country AS Country, l.latitude AS Latitude, l.longitude AS Longitude,
        p.type AS PropertyType, ro.type AS RoomType, i.accommodates AS Accommodates, i.bathrooms AS Bathrooms, i.bedrooms AS Bedrooms, i.beds AS Beds, b.type AS BedType,
		f.price AS Price, f.weekly_price AS WeeklyPrice, f.monthly_price AS MonthlyPrice, f.security_deposit AS SecurityDeposit, f.cleaning_fee AS CleaningFee, f.guests_included AS GuestsIncluded, f.extra_people AS ExtraPeople, f.minimum_nights AS MinimumNights, f.availability365 AS Availability,
         r.number AS NumberReviews, r.first AS FirstReview, r.last AS LastReview, r.scores_rating AS ReviewScoresRating, r.scores_accuracy AS ReviewScoresAccuracy, r.scores_cleanliness AS ReviewScoresCleanliness, r.scores_checkin AS ReviewScoresCheckin, r.scores_communication AS ReviewScoresCommunication, r.scores_location AS ReviewScoresLocation, r.scores_value AS ReviewScoresValue, 
         cp.cancellation_policy AS CancellationPolicy, r.per_Month AS ReviewsMonth
			FROM fact_airbnb f, dim_experience e, 
				dim_host h, dim_locationhost lh, dim_responsetime rt,
                dim_location l, dim_city ci, dim_country co, dim_neighbourhood n,
                dim_installation i, dim_property p, dim_room ro, dim_bed b,
                dim_reviews r,
				dim_cancellationpolicy cp
				WHERE f.fk_experience = e.id_experience 
					AND f.fk_host = h.id_host AND h.fk_locationhost = lh.id_locationhost AND h.fk_responsetime = rt.id_responsetime
                    AND f.fk_location = l.id_location AND l.fk_neighbourhood = n.id_neighbourhood AND l.fk_city = ci.id_city AND l.fk_country = co.id_country
                    AND f.fk_installion = i.id_installation AND i.fk_property = p.id_property AND i.fk_room = ro.id_room AND i.fk_bedtype = b.id_bed
                    AND f.fk_reviews = r.id_reviews
					AND f.fk_cancellationpolicy = cp.id_cancellationpolicy;

------------------------------------------------------------------------------------					