DELIMITER |
CREATE PROCEDURE Generate_Dates(date_start DATE, date_end DATE)
BEGIN
  WHILE date_start <= date_end DO
    INSERT INTO dim_date (id_date,year,month,day) VALUES (date_start,year(date_start),month(date_start),day(date_start));
    SET date_start = date_add(date_start, INTERVAL 1 DAY);
  END WHILE;
END;
|
DELIMITER ;