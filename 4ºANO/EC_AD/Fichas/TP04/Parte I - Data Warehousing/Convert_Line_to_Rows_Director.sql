DELIMITER $$

DROP PROCEDURE IF EXISTS Convert_Line_to_Rows_Director $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `Convert_Line_to_Rows_Director`(bound VARCHAR(255))
BEGIN

DECLARE id INT DEFAULT 0;
DECLARE value TEXT;
DECLARE occurance INT DEFAULT 0;
DECLARE i INT DEFAULT 0;
DECLARE splitted_value VARCHAR(255);
DECLARE done INT DEFAULT 0;
DECLARE cur1 CURSOR FOR SELECT t1.show_id, t1.director
                                     FROM netflix_dataset t1
                                     WHERE t1.director != ''
                                      LIMIT 2000;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

DROP TEMPORARY TABLE IF EXISTS Directors;
CREATE TEMPORARY TABLE Directors( -- OR NOT TEMPORARY
`id` INT NOT NULL,
`value` VARCHAR(255) NOT NULL
) ENGINE=Memory;

OPEN cur1;
  read_loop: LOOP
    FETCH cur1 INTO id, value;
    IF done THEN
      LEAVE read_loop;
    END IF;

    SET occurance = (SELECT LENGTH(value)
                             - LENGTH(REPLACE(value, bound, ''))
                             +1);
    SET i=1;
    WHILE i <= occurance DO
      SET splitted_value =
      (SELECT REPLACE(SUBSTRING(SUBSTRING_INDEX(value, bound, i),
      LENGTH(SUBSTRING_INDEX(value, bound, i - 1)) + 1), ',', ''));

	  if(splitted_value = ' ') then INSERT INTO Directors (id,value) VALUES (id, 'unknown'); end if;
      if(splitted_value != ' ') then INSERT INTO Directors (id,value) VALUES (id, trim(splitted_value)); end if;
      SET i = i + 1;

    END WHILE;
  END LOOP;

SELECT 
    *
FROM
    Directors;
 CLOSE cur1;
 END; $$