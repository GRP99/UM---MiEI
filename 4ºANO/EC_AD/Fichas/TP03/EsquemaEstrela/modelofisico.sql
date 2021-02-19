-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema esquemaestrela
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema esquemaestrela
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `esquemaestrela` DEFAULT CHARACTER SET utf8 ;
USE `esquemaestrela` ;

-- -----------------------------------------------------
-- Table `esquemaestrela`.`dim_gender`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `esquemaestrela`.`dim_gender` (
  `id_gender` INT NOT NULL AUTO_INCREMENT,
  `gender` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`id_gender`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `esquemaestrela`.`dim_place`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `esquemaestrela`.`dim_place` (
  `id_place` INT NOT NULL AUTO_INCREMENT,
  `state` VARCHAR(25) NOT NULL,
  `city` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_place`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `esquemaestrela`.`dim_name`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `esquemaestrela`.`dim_name` (
  `id_name` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id_name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `esquemaestrela`.`dim_date`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `esquemaestrela`.`dim_date` (
  `id_date` DATE NOT NULL,
  `year` INT NOT NULL,
  `month` INT NOT NULL,
  `day` INT NOT NULL,
  PRIMARY KEY (`id_date`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `esquemaestrela`.`dim_mannerofdeath`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `esquemaestrela`.`dim_mannerofdeath` (
  `id_mannerofdeath` INT NOT NULL AUTO_INCREMENT,
  `mannerofdeath` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_mannerofdeath`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `esquemaestrela`.`dim_armed`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `esquemaestrela`.`dim_armed` (
  `id_armed` INT NOT NULL AUTO_INCREMENT,
  `armed` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_armed`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `esquemaestrela`.`dim_race`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `esquemaestrela`.`dim_race` (
  `id_race` INT NOT NULL AUTO_INCREMENT,
  `race` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`id_race`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `esquemaestrela`.`dim_threatlevel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `esquemaestrela`.`dim_threatlevel` (
  `id_threatlevel` INT NOT NULL AUTO_INCREMENT,
  `threatlevel` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_threatlevel`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `esquemaestrela`.`dim_flee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `esquemaestrela`.`dim_flee` (
  `id_flee` INT NOT NULL AUTO_INCREMENT,
  `flee` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id_flee`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `esquemaestrela`.`Fatal_Police_Shootings`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `esquemaestrela`.`Fatal_Police_Shootings` (
  `id` INT NOT NULL,
  `fk_id_name` INT NOT NULL,
  `fk_id_date` DATE NOT NULL,
  `fk_id_mannerofdeath` INT NOT NULL,
  `fk_id_armed` INT NOT NULL,
  `age` INT NOT NULL,
  `fk_id_gender` INT NOT NULL,
  `fk_id_race` INT NOT NULL,
  `fk_id_place` INT NOT NULL,
  `signs_of_mental_illness` TINYINT NOT NULL,
  `fk_id_threatlevel` INT NOT NULL,
  `fk_id_flee` INT NOT NULL,
  `bodycamera` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_id_name_idx` (`fk_id_name` ASC) VISIBLE,
  INDEX `fk_id_date_idx` (`fk_id_date` ASC) VISIBLE,
  INDEX `fk_id_mannerofdeath_idx` (`fk_id_mannerofdeath` ASC) VISIBLE,
  INDEX `fk_id_armed_idx` (`fk_id_armed` ASC) VISIBLE,
  INDEX `fk_id_gender_idx` (`fk_id_gender` ASC) VISIBLE,
  INDEX `fk_id_race_idx` (`fk_id_race` ASC) VISIBLE,
  INDEX `fk_id_place_idx` (`fk_id_place` ASC) VISIBLE,
  INDEX `fk_id_threatlevel_idx` (`fk_id_threatlevel` ASC) VISIBLE,
  INDEX `fk_id_flee_idx` (`fk_id_flee` ASC) VISIBLE,
  CONSTRAINT `fk_id_name`
    FOREIGN KEY (`fk_id_name`)
    REFERENCES `esquemaestrela`.`dim_name` (`id_name`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_date`
    FOREIGN KEY (`fk_id_date`)
    REFERENCES `esquemaestrela`.`dim_date` (`id_date`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_mannerofdeath`
    FOREIGN KEY (`fk_id_mannerofdeath`)
    REFERENCES `esquemaestrela`.`dim_mannerofdeath` (`id_mannerofdeath`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_armed`
    FOREIGN KEY (`fk_id_armed`)
    REFERENCES `esquemaestrela`.`dim_armed` (`id_armed`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_gender`
    FOREIGN KEY (`fk_id_gender`)
    REFERENCES `esquemaestrela`.`dim_gender` (`id_gender`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_race`
    FOREIGN KEY (`fk_id_race`)
    REFERENCES `esquemaestrela`.`dim_race` (`id_race`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_place`
    FOREIGN KEY (`fk_id_place`)
    REFERENCES `esquemaestrela`.`dim_place` (`id_place`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_threatlevel`
    FOREIGN KEY (`fk_id_threatlevel`)
    REFERENCES `esquemaestrela`.`dim_threatlevel` (`id_threatlevel`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_id_flee`
    FOREIGN KEY (`fk_id_flee`)
    REFERENCES `esquemaestrela`.`dim_flee` (`id_flee`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
