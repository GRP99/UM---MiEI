-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema COVID19_EU_EEA_UK
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema COVID19_EU_EEA_UK
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `COVID19_EU_EEA_UK` DEFAULT CHARACTER SET utf8 ;
USE `COVID19_EU_EEA_UK` ;

-- -----------------------------------------------------
-- Table `COVID19_EU_EEA_UK`.`Week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `COVID19_EU_EEA_UK`.`Week` (
  `idWeek` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`idWeek`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `COVID19_EU_EEA_UK`.`Country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `COVID19_EU_EEA_UK`.`Country` (
  `country_code` VARCHAR(10) NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `population` INT NOT NULL,
  `countryterritoryCode` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`country_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `COVID19_EU_EEA_UK`.`Testing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `COVID19_EU_EEA_UK`.`Testing` (
  `country_code` VARCHAR(10) NOT NULL,
  `refweek` VARCHAR(20) NOT NULL,
  `new_cases` INT NOT NULL,
  `tests_done` INT NOT NULL,
  `testing_rate` DECIMAL(20,15) NOT NULL,
  `positivity_rate` DECIMAL(20,15) NOT NULL,
  `testing_data_source` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`country_code`, `refweek`),
  INDEX `week_idx` (`refweek` ASC) VISIBLE,
  CONSTRAINT `country_code`
    FOREIGN KEY (`country_code`)
    REFERENCES `COVID19_EU_EEA_UK`.`Country` (`country_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `refweek`
    FOREIGN KEY (`refweek`)
    REFERENCES `COVID19_EU_EEA_UK`.`Week` (`idWeek`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `COVID19_EU_EEA_UK`.`Data`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `COVID19_EU_EEA_UK`.`Data` (
  `geoId` VARCHAR(10) NOT NULL,
  `iweek` VARCHAR(20) NOT NULL,
  `date` DATETIME NOT NULL,
  `cases` INT NULL,
  `deaths` INT NULL,
  `Cumulative_number` DECIMAL(20,15) NOT NULL DEFAULT 0,
  PRIMARY KEY (`geoId`, `iweek`, `date`),
  INDEX `Pais_idx` (`geoId` ASC) VISIBLE,
  INDEX `week_idx` (`iweek` ASC) VISIBLE,
  CONSTRAINT `geoId`
    FOREIGN KEY (`geoId`)
    REFERENCES `COVID19_EU_EEA_UK`.`Country` (`country_code`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `iweek`
    FOREIGN KEY (`iweek`)
    REFERENCES `COVID19_EU_EEA_UK`.`Week` (`idWeek`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
