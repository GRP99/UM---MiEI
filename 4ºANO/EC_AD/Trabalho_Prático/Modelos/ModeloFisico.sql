-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema airbnb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema airbnb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `airbnb` DEFAULT CHARACTER SET utf8 ;
USE `airbnb` ;

-- -----------------------------------------------------
-- Table `airbnb`.`dim_experience`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_experience` (
  `id_experience` INT NOT NULL AUTO_INCREMENT,
  `experiences_offered` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id_experience`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_date`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_date` (
  `date` DATE NOT NULL,
  `year` INT NOT NULL,
  `month` INT NOT NULL,
  `day` INT NOT NULL,
  PRIMARY KEY (`date`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_locationhost`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_locationhost` (
  `id_locationhost` INT NOT NULL AUTO_INCREMENT,
  `location` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`id_locationhost`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_responsetime`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_responsetime` (
  `id_responsetime` INT NOT NULL AUTO_INCREMENT,
  `response_time` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id_responsetime`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_host`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_host` (
  `id_host` INT NOT NULL AUTO_INCREMENT,
  `id` INT NOT NULL,
  `since` DATE NOT NULL,
  `fk_locationhost` INT NOT NULL,
  `fk_responsetime` INT NOT NULL,
  `response_rate` INT NOT NULL,
  `total_listings_count` INT NOT NULL,
  PRIMARY KEY (`id_host`),
  INDEX `since_idx` (`since` ASC) VISIBLE,
  INDEX `fk_locationhost_idx` (`fk_locationhost` ASC) VISIBLE,
  INDEX `fk_responsetime_idx` (`fk_responsetime` ASC) VISIBLE,
  CONSTRAINT `since`
    FOREIGN KEY (`since`)
    REFERENCES `airbnb`.`dim_date` (`date`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_locationhost`
    FOREIGN KEY (`fk_locationhost`)
    REFERENCES `airbnb`.`dim_locationhost` (`id_locationhost`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_responsetime`
    FOREIGN KEY (`fk_responsetime`)
    REFERENCES `airbnb`.`dim_responsetime` (`id_responsetime`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_neighbourhood`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_neighbourhood` (
  `id_neighbourhood` INT NOT NULL AUTO_INCREMENT,
  `neighbourhood` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`id_neighbourhood`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_city`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_city` (
  `id_city` INT NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(350) NOT NULL,
  PRIMARY KEY (`id_city`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_country` (
  `id_country` INT NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_country`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_location` (
  `id_location` INT NOT NULL AUTO_INCREMENT,
  `fk_neighbourhood` INT NOT NULL,
  `fk_city` INT NOT NULL,
  `fk_country` INT NOT NULL,
  `latitude` DECIMAL(20,17) NOT NULL,
  `longitude` DECIMAL(20,17) NOT NULL,
  PRIMARY KEY (`id_location`),
  INDEX `fk_neighbourhood_idx` (`fk_neighbourhood` ASC) VISIBLE,
  INDEX `fk_city_idx` (`fk_city` ASC) VISIBLE,
  INDEX `fk_country_idx` (`fk_country` ASC) VISIBLE,
  CONSTRAINT `fk_neighbourhood`
    FOREIGN KEY (`fk_neighbourhood`)
    REFERENCES `airbnb`.`dim_neighbourhood` (`id_neighbourhood`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_city`
    FOREIGN KEY (`fk_city`)
    REFERENCES `airbnb`.`dim_city` (`id_city`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_country`
    FOREIGN KEY (`fk_country`)
    REFERENCES `airbnb`.`dim_country` (`id_country`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_property`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_property` (
  `id_property` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_property`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_room`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_room` (
  `id_room` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_room`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_bed`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_bed` (
  `id_bed` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_bed`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_installation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_installation` (
  `id_installation` INT NOT NULL AUTO_INCREMENT,
  `fk_property` INT NOT NULL,
  `fk_room` INT NOT NULL,
  `accommodates` INT NOT NULL,
  `bathrooms` DECIMAL(3,1) NOT NULL,
  `bedrooms` INT NOT NULL,
  `beds` INT NOT NULL,
  `fk_bedtype` INT NOT NULL,
  PRIMARY KEY (`id_installation`),
  INDEX `fk_property_idx` (`fk_property` ASC) VISIBLE,
  INDEX `fk_room_idx` (`fk_room` ASC) VISIBLE,
  INDEX `fk_bedtype_idx` (`fk_bedtype` ASC) VISIBLE,
  CONSTRAINT `fk_property`
    FOREIGN KEY (`fk_property`)
    REFERENCES `airbnb`.`dim_property` (`id_property`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_room`
    FOREIGN KEY (`fk_room`)
    REFERENCES `airbnb`.`dim_room` (`id_room`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bedtype`
    FOREIGN KEY (`fk_bedtype`)
    REFERENCES `airbnb`.`dim_bed` (`id_bed`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_reviews`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_reviews` (
  `id_reviews` INT NOT NULL AUTO_INCREMENT,
  `number` INT NOT NULL,
  `first` DATE NOT NULL,
  `last` DATE NOT NULL,
  `scores_rating` INT NOT NULL DEFAULT 0,
  `scores_accuracy` INT NOT NULL DEFAULT 0,
  `scores_cleanliness` INT NOT NULL DEFAULT 0,
  `scores_checkin` INT NOT NULL DEFAULT 0,
  `scores_communication` INT NOT NULL DEFAULT 0,
  `scores_location` INT NOT NULL DEFAULT 0,
  `scores_value` INT NOT NULL DEFAULT 0,
  `per_Month` DECIMAL(5,2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`id_reviews`),
  INDEX `first_idx` (`first` ASC) VISIBLE,
  INDEX `last_idx` (`last` ASC) VISIBLE,
  CONSTRAINT `first`
    FOREIGN KEY (`first`)
    REFERENCES `airbnb`.`dim_date` (`date`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `last`
    FOREIGN KEY (`last`)
    REFERENCES `airbnb`.`dim_date` (`date`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_cancellationpolicy`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_cancellationpolicy` (
  `id_cancellationpolicy` INT NOT NULL AUTO_INCREMENT,
  `cancellation_policy` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_cancellationpolicy`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`fact_AIRBNB`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`fact_AIRBNB` (
  `id` INT NOT NULL,
  `fk_experience` INT NOT NULL,
  `fk_host` INT NOT NULL,
  `fk_location` INT NOT NULL,
  `fk_installion` INT NOT NULL,
  `price` INT NOT NULL,
  `weekly_price` INT NOT NULL DEFAULT 0,
  `monthly_price` INT NOT NULL DEFAULT 0,
  `security_deposit` INT NOT NULL DEFAULT 0,
  `cleaning_fee` INT NOT NULL,
  `guests_included` INT NOT NULL DEFAULT 1,
  `extra_people` INT NOT NULL DEFAULT 0,
  `minimum_nights` INT NOT NULL DEFAULT 0,
  `availability365` INT NOT NULL,
  `fk_reviews` INT NOT NULL,
  `fk_cancellationpolicy` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_experience_idx` (`fk_experience` ASC) VISIBLE,
  INDEX `fk_host_idx` (`fk_host` ASC) VISIBLE,
  INDEX `fk_location_idx` (`fk_location` ASC) VISIBLE,
  INDEX `fk_installion_idx` (`fk_installion` ASC) VISIBLE,
  INDEX `fk_reviews_idx` (`fk_reviews` ASC) VISIBLE,
  INDEX `fk_cancellationpolicy_idx` (`fk_cancellationpolicy` ASC) VISIBLE,
  CONSTRAINT `fk_experience`
    FOREIGN KEY (`fk_experience`)
    REFERENCES `airbnb`.`dim_experience` (`id_experience`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_host`
    FOREIGN KEY (`fk_host`)
    REFERENCES `airbnb`.`dim_host` (`id_host`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_location`
    FOREIGN KEY (`fk_location`)
    REFERENCES `airbnb`.`dim_location` (`id_location`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_installion`
    FOREIGN KEY (`fk_installion`)
    REFERENCES `airbnb`.`dim_installation` (`id_installation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_reviews`
    FOREIGN KEY (`fk_reviews`)
    REFERENCES `airbnb`.`dim_reviews` (`id_reviews`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cancellationpolicy`
    FOREIGN KEY (`fk_cancellationpolicy`)
    REFERENCES `airbnb`.`dim_cancellationpolicy` (`id_cancellationpolicy`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_verification`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_verification` (
  `id_verification` INT NOT NULL AUTO_INCREMENT,
  `verification` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_verification`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_verifications`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_verifications` (
  `fk_idhost` INT NOT NULL,
  `fk_verification` INT NOT NULL,
  PRIMARY KEY (`fk_idhost`, `fk_verification`),
  INDEX `fk_verification_idx` (`fk_verification` ASC) VISIBLE,
  CONSTRAINT `fk_idhost`
    FOREIGN KEY (`fk_idhost`)
    REFERENCES `airbnb`.`dim_host` (`id_host`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_verification`
    FOREIGN KEY (`fk_verification`)
    REFERENCES `airbnb`.`dim_verification` (`id_verification`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_amenity`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_amenity` (
  `id_amenity` INT NOT NULL AUTO_INCREMENT,
  `amenity` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id_amenity`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_amenities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_amenities` (
  `installation` INT NOT NULL,
  `amenity` INT NOT NULL,
  PRIMARY KEY (`installation`, `amenity`),
  INDEX `amenity_idx` (`amenity` ASC) VISIBLE,
  CONSTRAINT `installation`
    FOREIGN KEY (`installation`)
    REFERENCES `airbnb`.`dim_installation` (`id_installation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `amenity`
    FOREIGN KEY (`amenity`)
    REFERENCES `airbnb`.`dim_amenity` (`id_amenity`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_feature`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_feature` (
  `id_feature` INT NOT NULL AUTO_INCREMENT,
  `feature` VARCHAR(150) NOT NULL,
  PRIMARY KEY (`id_feature`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airbnb`.`dim_features`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airbnb`.`dim_features` (
  `idairbnb` INT NOT NULL,
  `feature` INT NOT NULL,
  PRIMARY KEY (`idairbnb`, `feature`),
  INDEX `feature_idx` (`feature` ASC) VISIBLE,
  CONSTRAINT `idairbnb`
    FOREIGN KEY (`idairbnb`)
    REFERENCES `airbnb`.`fact_AIRBNB` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `feature`
    FOREIGN KEY (`feature`)
    REFERENCES `airbnb`.`dim_feature` (`id_feature`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
