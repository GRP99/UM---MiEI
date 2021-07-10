-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema appb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema appb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `appb` DEFAULT CHARACTER SET utf8 ;
USE `appb` ;

-- -----------------------------------------------------
-- Table `appb`.`episodio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `appb`.`episodio` (
  `idepisodio` INT NOT NULL AUTO_INCREMENT,
  `caracterizacao` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`idepisodio`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `appb`.`tipoexame`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `appb`.`tipoexame` (
  `idtipoexame` INT NOT NULL AUTO_INCREMENT,
  `sigla` VARCHAR(50) NOT NULL,
  `descricao` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`idtipoexame`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `appb`.`paciente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `appb`.`paciente` (
  `idpaciente` INT NOT NULL AUTO_INCREMENT,
  `sexo` VARCHAR(5) NOT NULL,
  `telemovel` VARCHAR(10) NOT NULL,
  `num_utente` VARCHAR(10) NOT NULL,
  `data_nascimento` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `nome` VARCHAR(250) NOT NULL,
  `morada` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`idpaciente`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `appb`.`pedido`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `appb`.`pedido` (
  `idpedido` INT NOT NULL AUTO_INCREMENT,
  `data_hora` VARCHAR(100) NOT NULL,
  `estado` VARCHAR(100) NOT NULL,
  `explicacao` VARCHAR(500) NOT NULL,
  `relatorio` TEXT NOT NULL,
  `fk_episodio` INT NOT NULL,
  `fk_tipoexame` INT NOT NULL,
  `fk_paciente` INT NOT NULL,
  PRIMARY KEY (`idpedido`),
  INDEX `fk_episodio_idx` (`fk_episodio` ASC) VISIBLE,
  INDEX `fk_tipoexame_idx` (`fk_tipoexame` ASC) VISIBLE,
  INDEX `fk_paciente_idx` (`fk_paciente` ASC) VISIBLE,
  CONSTRAINT `fk_episodio`
    FOREIGN KEY (`fk_episodio`)
    REFERENCES `appb`.`episodio` (`idepisodio`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_tipoexame`
    FOREIGN KEY (`fk_tipoexame`)
    REFERENCES `appb`.`tipoexame` (`idtipoexame`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_paciente`
    FOREIGN KEY (`fk_paciente`)
    REFERENCES `appb`.`paciente` (`idpaciente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
