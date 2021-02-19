-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema South_Korea_Covid19
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema South_Korea_Covid19
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `South_Korea_Covid19` DEFAULT CHARACTER SET utf8 ;
USE `South_Korea_Covid19` ;

-- -----------------------------------------------------
-- Table `South_Korea_Covid19`.`Dim_Region`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `South_Korea_Covid19`.`Dim_Region` (
  `idRegion` INT NOT NULL AUTO_INCREMENT,
  `province` VARCHAR(50) NULL,
  `city` VARCHAR(50) NULL,
  PRIMARY KEY (`idRegion`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `South_Korea_Covid19`.`Dim_Date`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `South_Korea_Covid19`.`Dim_Date` (
  `idDate` INT NOT NULL AUTO_INCREMENT,
  `symptom_start_date` VARCHAR(50) NULL,
  `confirmed_case_date` VARCHAR(50) NULL,
  `released_date` VARCHAR(50) NULL,
  `deceased_date` VARCHAR(50) NULL,
  PRIMARY KEY (`idDate`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `South_Korea_Covid19`.`Dim_Sex`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `South_Korea_Covid19`.`Dim_Sex` (
  `idSex` INT NOT NULL AUTO_INCREMENT,
  `sex` VARCHAR(50) NULL,
  PRIMARY KEY (`idSex`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `South_Korea_Covid19`.`Dim_Age`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `South_Korea_Covid19`.`Dim_Age` (
  `idAge` INT NOT NULL AUTO_INCREMENT,
  `age` VARCHAR(50) NULL,
  PRIMARY KEY (`idAge`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `South_Korea_Covid19`.`Dim_Infection`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `South_Korea_Covid19`.`Dim_Infection` (
  `idInfection` INT NOT NULL AUTO_INCREMENT,
  `infection_case` VARCHAR(50) NULL,
  `contact_number` VARCHAR(50) NULL,
  PRIMARY KEY (`idInfection`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `South_Korea_Covid19`.`Dim_Origin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `South_Korea_Covid19`.`Dim_Origin` (
  `idOrigin` INT NOT NULL AUTO_INCREMENT,
  `origin` VARCHAR(50) NULL,
  PRIMARY KEY (`idOrigin`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `South_Korea_Covid19`.`Dim_State`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `South_Korea_Covid19`.`Dim_State` (
  `idState` INT NOT NULL AUTO_INCREMENT,
  `state` VARCHAR(50) NULL,
  PRIMARY KEY (`idState`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `South_Korea_Covid19`.`Fact_Patient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `South_Korea_Covid19`.`Fact_Patient` (
  `patient_id` BIGINT NOT NULL,
  `infected_by` VARCHAR(50) NULL,
  `id_age` INT NULL,
  `id_region` INT NULL,
  `id_date` INT NULL,
  `id_infection` INT NULL,
  `id_origin` INT NULL,
  `id_sex` INT NULL,
  `id_state` INT NULL,
  PRIMARY KEY (`patient_id`),
  INDEX `id_sex_idx` (`id_sex` ASC) VISIBLE,
  INDEX `id_age_idx` (`id_age` ASC) VISIBLE,
  INDEX `id_Infection_idx` (`id_infection` ASC) VISIBLE,
  INDEX `id_region_idx` (`id_region` ASC) VISIBLE,
  INDEX `id_date_idx` (`id_date` ASC) VISIBLE,
  INDEX `id_origin_idx` (`id_origin` ASC) VISIBLE,
  INDEX `id_state_idx` (`id_state` ASC) VISIBLE,
  CONSTRAINT `id_sex`
    FOREIGN KEY (`id_sex`)
    REFERENCES `South_Korea_Covid19`.`Dim_Sex` (`idSex`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_age`
    FOREIGN KEY (`id_age`)
    REFERENCES `South_Korea_Covid19`.`Dim_Age` (`idAge`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_infection`
    FOREIGN KEY (`id_infection`)
    REFERENCES `South_Korea_Covid19`.`Dim_Infection` (`idInfection`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_region`
    FOREIGN KEY (`id_region`)
    REFERENCES `South_Korea_Covid19`.`Dim_Region` (`idRegion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_date`
    FOREIGN KEY (`id_date`)
    REFERENCES `South_Korea_Covid19`.`Dim_Date` (`idDate`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_origin`
    FOREIGN KEY (`id_origin`)
    REFERENCES `South_Korea_Covid19`.`Dim_Origin` (`idOrigin`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `id_state`
    FOREIGN KEY (`id_state`)
    REFERENCES `South_Korea_Covid19`.`Dim_State` (`idState`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
