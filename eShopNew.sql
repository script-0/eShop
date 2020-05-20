-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema eshop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema eshop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `eshop` DEFAULT CHARACTER SET utf8 ;
USE `eshop` ;

-- -----------------------------------------------------
-- Table `eshop`.`categorie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`categorie` (
  `idCat` INT(3) UNSIGNED NOT NULL,
  `nomCat` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`idCat`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eshop`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`client` (
  `idClient` INT(7) NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(100) NOT NULL DEFAULT 'ND',
  `tel` VARCHAR(13) NULL DEFAULT NULL,
  `adresse` VARCHAR(45) NULL DEFAULT NULL,
  `bonus` INT(10) NULL,
  `actif` TINYINT(1) NOT NULL,
  PRIMARY KEY (`idClient`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eshop`.`gestionnaire`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`gestionnaire` (
  `idGest` INT(4) UNSIGNED NOT NULL,
  `nomGest` VARCHAR(45) NOT NULL,
  `typeGest` TINYINT(1) NOT NULL,
  `login` VARCHAR(20) NOT NULL,
  `pwd` VARCHAR(20) NOT NULL,
  `actif` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idGest`),
  UNIQUE INDEX `login` (`login` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eshop`.`facture`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`facture` (
  `idFac` INT(10) UNSIGNED NOT NULL,
  `dateFac` DATETIME NOT NULL,
  `codePaiement` VARCHAR(60) NOT NULL,
  `remise` DECIMAL(4,2) NOT NULL,
  `montant` DECIMAL(10,2) NOT NULL,
  `modePaiement` TINYINT(1) NOT NULL,
  `idCaissiere` INT(10) UNSIGNED NOT NULL,
  `idClient` INT(7) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idFac`),
  INDEX `fk_idCaiss_idx` (`idCaissiere` ASC) VISIBLE,
  INDEX `fk_facture_client1_idx` (`idClient` ASC) VISIBLE,
  CONSTRAINT `fk_idCaiss`
    FOREIGN KEY (`idCaissiere`)
    REFERENCES `eshop`.`gestionnaire` (`idGest`)
    ON UPDATE CASCADE,
  CONSTRAINT `fk_facture_client1`
    FOREIGN KEY (`idClient`)
    REFERENCES `eshop`.`client` (`idClient`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eshop`.`fournisseur`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`fournisseur` (
  `idFournisseur` INT(3) NOT NULL,
  `nom` VARCHAR(45) NOT NULL,
  `adresse` VARCHAR(45) NULL DEFAULT NULL,
  `tel` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idFournisseur`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eshop`.`produit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`produit` (
  `codePro` INT(6) UNSIGNED NOT NULL,
  `nomPro` VARCHAR(100) NOT NULL DEFAULT 'ND',
  `prixVente` DECIMAL(8,2) NOT NULL,
  `prixAchat` DECIMAL(8,2) NOT NULL,
  `qte` DECIMAL(8,2) UNSIGNED NOT NULL,
  `description` VARCHAR(1000) NOT NULL DEFAULT 'RAS',
  `codeFour` VARCHAR(40) NOT NULL,
  `dateInsertion` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `actif` TINYINT(1) NOT NULL DEFAULT '1',
  `categorie_idCat` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`codePro`),
  INDEX `fk_produit_categorie1_idx` (`categorie_idCat` ASC) VISIBLE,
  CONSTRAINT `fk_produit_categorie1`
    FOREIGN KEY (`categorie_idCat`)
    REFERENCES `eshop`.`categorie` (`idCat`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eshop`.`gestionstock`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`gestionstock` (
  `idStock` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `qte` DECIMAL(8,2) UNSIGNED NOT NULL,
  `dateStock` DATETIME NOT NULL,
  `operation` TINYINT(1) NOT NULL DEFAULT '0',
  `idGest` INT(4) UNSIGNED NOT NULL,
  `codePro` INT(6) UNSIGNED NOT NULL,
  PRIMARY KEY (`idStock`),
  INDEX `fk_idGest_stock_idx` (`idGest` ASC) VISIBLE,
  INDEX `fk_codePro_stock_idx` (`codePro` ASC) VISIBLE,
  CONSTRAINT `fk_codePro_stock`
    FOREIGN KEY (`codePro`)
    REFERENCES `eshop`.`produit` (`codePro`),
  CONSTRAINT `fk_idGest_stock`
    FOREIGN KEY (`idGest`)
    REFERENCES `eshop`.`gestionnaire` (`idGest`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `eshop`.`lignefacture`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `eshop`.`lignefacture` (
  `idLFac` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `codePro` INT(6) UNSIGNED NOT NULL,
  `idFac` INT(10) UNSIGNED NOT NULL,
  `prixVente` DECIMAL(8,2) NOT NULL,
  `qte` DECIMAL(8,2) UNSIGNED NOT NULL,
  `prixAchat` DECIMAL(8,2) NOT NULL,
  PRIMARY KEY (`idLFac`),
  UNIQUE INDEX `idx_fac_pro` (`codePro` ASC, `idFac` ASC) VISIBLE,
  INDEX `fk_idFac` (`idFac` ASC) VISIBLE,
  CONSTRAINT `fk_codePro`
    FOREIGN KEY (`codePro`)
    REFERENCES `eshop`.`produit` (`codePro`),
  CONSTRAINT `fk_idFac`
    FOREIGN KEY (`idFac`)
    REFERENCES `eshop`.`facture` (`idFac`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
