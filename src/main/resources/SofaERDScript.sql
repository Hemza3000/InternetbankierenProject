-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Particulier`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Particulier` (
  `voornaam` VARCHAR(45) NOT NULL,
  `tussenvoegsels` VARCHAR(45) NULL,
  `achternaam` VARCHAR(45) NOT NULL,
  `BSN` INT NOT NULL,
  `geboortedatum` DATE NOT NULL,
  `idRekeninghouder` INT NOT NULL,
  `straat` VARCHAR(45) NOT NULL,
  `huisnummer` VARCHAR(45) NOT NULL,
  `postcode` VARCHAR(45) NOT NULL,
  `woonplaats` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idRekeninghouder`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Medewerker`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Medewerker` (
  `personeelsnummer` INT NOT NULL AUTO_INCREMENT,
  `rol` VARCHAR(45) NOT NULL,
  `voornaam` VARCHAR(45) NOT NULL,
  `tussenvoegsels` VARCHAR(45) NULL,
  `achternaam` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`personeelsnummer`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Bedrijf`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Bedrijf` (
  `bedrijfsnaam` VARCHAR(45) NOT NULL,
  `KVKnummer` INT NOT NULL,
  `sector` VARCHAR(45) NOT NULL,
  `BTWnummer` VARCHAR(45) NOT NULL,
  `idBedrijf` INT NOT NULL,
  `idAccountmanager` INT NOT NULL,
  `straat` VARCHAR(45) NOT NULL,
  `huisnummer` VARCHAR(45) NOT NULL,
  `postcode` VARCHAR(45) NOT NULL,
  `woonplaats` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idBedrijf`),
  INDEX `verzinzelf9_idx` (`idAccountmanager` ASC) VISIBLE,
  CONSTRAINT `verzinzelf9`
    FOREIGN KEY (`idAccountmanager`)
    REFERENCES `mydb`.`Medewerker` (`personeelsnummer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Bedrijfsrekening`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Bedrijfsrekening` (
  `idBedrijf` INT NOT NULL,
  `idBedrijfsrekening` VARCHAR(45) NULL,
  `idContactpersoon` INT NOT NULL,
  `saldo` INT NOT NULL,
  `IBAN` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idBedrijfsrekening`),
  INDEX `verzinzelf7_idx` (`idBedrijf` ASC) VISIBLE,
  INDEX `verzinzelf10_idx` (`idContactpersoon` ASC) VISIBLE,
  CONSTRAINT `verzinzelf7`
    FOREIGN KEY (`idBedrijf`)
    REFERENCES `mydb`.`Bedrijf` (`idBedrijf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `verzinzelf10`
    FOREIGN KEY (`idContactpersoon`)
    REFERENCES `mydb`.`Particulier` (`idRekeninghouder`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Priverekening`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Priverekening` (
  `idRekeninghouder` INT NOT NULL,
  `idPriverekening` VARCHAR(45) NULL,
  `IBAN` VARCHAR(45) NOT NULL,
  `saldo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idPriverekening`),
  INDEX `verzinzelf6_idx` (`idRekeninghouder` ASC) VISIBLE,
  CONSTRAINT `verzinzelf6`
    FOREIGN KEY (`idRekeninghouder`)
    REFERENCES `mydb`.`Particulier` (`idRekeninghouder`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Rekeninghouder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Rekeninghouder` (
  `idKlant` INT NOT NULL,
  `idPriverekening` INT NOT NULL,
  PRIMARY KEY (`idKlant`, `idPriverekening`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Transactie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Transactie` (
  `idTransactie` INT NOT NULL AUTO_INCREMENT,
  `bedrag` VARCHAR(45) NOT NULL,
  `transactiebeschrijving` VARCHAR(45) NOT NULL,
  `datum` DATETIME NOT NULL,
  `bijschrijving` TINYINT NOT NULL,
  `idBedrijfsrekening` VARCHAR(45) NULL,
  `idPriverekening` VARCHAR(45) NULL,
  PRIMARY KEY (`idTransactie`),
  INDEX `verzinzelf1_idx` (`idBedrijfsrekening` ASC) VISIBLE,
  INDEX `verzinzelf2_idx` (`idPriverekening` ASC) VISIBLE,
  CONSTRAINT `verzinzelf1`
    FOREIGN KEY (`idBedrijfsrekening`)
    REFERENCES `mydb`.`Bedrijfsrekening` (`idBedrijfsrekening`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `verzinzelf2`
    FOREIGN KEY (`idPriverekening`)
    REFERENCES `mydb`.`Priverekening` (`idPriverekening`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
