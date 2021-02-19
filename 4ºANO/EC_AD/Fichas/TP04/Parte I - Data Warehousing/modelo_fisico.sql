-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema netflix
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema netflix
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `netflix` DEFAULT CHARACTER SET utf8 ;
USE `netflix` ;

-- -----------------------------------------------------
-- Table `netflix`.`dim_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_type` (
  `id_type` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_type`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`dim_titledescription`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_titledescription` (
  `id_titledescription` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(250) NOT NULL,
  `description` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id_titledescription`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`dim_date`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_date` (
  `id_date` DATE NOT NULL,
  `year` INT NOT NULL,
  `month` INT NOT NULL,
  `day` INT NOT NULL,
  PRIMARY KEY (`id_date`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`dim_rating`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_rating` (
  `id_rating` INT NOT NULL AUTO_INCREMENT,
  `rating` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_rating`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`dim_duration`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_duration` (
  `id_duration` INT NOT NULL AUTO_INCREMENT,
  `duration` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_duration`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`dim_IMDB`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_IMDB` (
  `id_imdb` INT NOT NULL AUTO_INCREMENT,
  `imdb_titleID` VARCHAR(100) NOT NULL,
  `rating` DECIMAL(2,1) NOT NULL,
  `rating_votes` INT NOT NULL,
  `title_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_imdb`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`fact_netflix`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`fact_netflix` (
  `show_id` INT NOT NULL,
  `fk_type` INT NOT NULL,
  `fk_titledescription` INT NOT NULL,
  `date_added` DATE NOT NULL,
  `release_year` INT NOT NULL,
  `fk_rating` INT NOT NULL,
  `fk_duration` INT NOT NULL,
  `fk_imdb` INT NOT NULL,
  PRIMARY KEY (`show_id`),
  INDEX `fk_type_idx` (`fk_type` ASC) VISIBLE,
  INDEX `fk_title&description_idx` (`fk_titledescription` ASC) VISIBLE,
  INDEX `date_added_idx` (`date_added` ASC) VISIBLE,
  INDEX `fk_rating_idx` (`fk_rating` ASC) VISIBLE,
  INDEX `fk_duration_idx` (`fk_duration` ASC) VISIBLE,
  INDEX `fk_imdb_idx` (`fk_imdb` ASC) VISIBLE,
  CONSTRAINT `fk_type`
    FOREIGN KEY (`fk_type`)
    REFERENCES `netflix`.`dim_type` (`id_type`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_titledescription`
    FOREIGN KEY (`fk_titledescription`)
    REFERENCES `netflix`.`dim_titledescription` (`id_titledescription`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `date_added`
    FOREIGN KEY (`date_added`)
    REFERENCES `netflix`.`dim_date` (`id_date`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_rating`
    FOREIGN KEY (`fk_rating`)
    REFERENCES `netflix`.`dim_rating` (`id_rating`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_duration`
    FOREIGN KEY (`fk_duration`)
    REFERENCES `netflix`.`dim_duration` (`id_duration`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_imdb`
    FOREIGN KEY (`fk_imdb`)
    REFERENCES `netflix`.`dim_IMDB` (`id_imdb`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`dim_country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_country` (
  `id_country` INT NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_country`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`dim_listed_in`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_listed_in` (
  `id_listedin` INT NOT NULL AUTO_INCREMENT,
  `listed_in` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_listedin`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`dim_actor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_actor` (
  `id_actor` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_actor`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`dim_cast`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_cast` (
  `fk_show` INT NOT NULL,
  `fk_actor` INT NOT NULL,
  PRIMARY KEY (`fk_show`, `fk_actor`),
  INDEX `fk_person_idx` (`fk_actor` ASC) VISIBLE,
  CONSTRAINT `fk_show`
    FOREIGN KEY (`fk_show`)
    REFERENCES `netflix`.`fact_netflix` (`show_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_actor`
    FOREIGN KEY (`fk_actor`)
    REFERENCES `netflix`.`dim_actor` (`id_actor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`dim_countries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_countries` (
  `fk_showid` INT NOT NULL,
  `fk_country` INT NOT NULL,
  PRIMARY KEY (`fk_showid`, `fk_country`),
  INDEX `fk_country_idx` (`fk_country` ASC) VISIBLE,
  CONSTRAINT `fk_showid`
    FOREIGN KEY (`fk_showid`)
    REFERENCES `netflix`.`fact_netflix` (`show_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_country`
    FOREIGN KEY (`fk_country`)
    REFERENCES `netflix`.`dim_country` (`id_country`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`dim_director`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_director` (
  `id_director` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_director`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`dim_directors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_directors` (
  `fkshowid` INT NOT NULL,
  `fk_director` INT NOT NULL,
  PRIMARY KEY (`fkshowid`, `fk_director`),
  INDEX `fk_director_idx` (`fk_director` ASC) VISIBLE,
  CONSTRAINT `fkshowid`
    FOREIGN KEY (`fkshowid`)
    REFERENCES `netflix`.`fact_netflix` (`show_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_director`
    FOREIGN KEY (`fk_director`)
    REFERENCES `netflix`.`dim_director` (`id_director`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `netflix`.`dim_listeds_in`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `netflix`.`dim_listeds_in` (
  `fk_id` INT NOT NULL,
  `fk_listedin` INT NOT NULL,
  PRIMARY KEY (`fk_id`, `fk_listedin`),
  INDEX `fk_listedin_idx` (`fk_listedin` ASC) VISIBLE,
  CONSTRAINT `fk_id`
    FOREIGN KEY (`fk_id`)
    REFERENCES `netflix`.`fact_netflix` (`show_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_listedin`
    FOREIGN KEY (`fk_listedin`)
    REFERENCES `netflix`.`dim_listed_in` (`id_listedin`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
