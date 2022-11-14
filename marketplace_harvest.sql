-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema marketplace_harvest
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema marketplace_harvest
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `marketplace_harvest` DEFAULT CHARACTER SET utf8mb3 ;
USE `marketplace_harvest` ;

-- -----------------------------------------------------
-- Table `marketplace_harvest`.`analysts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `marketplace_harvest`.`analysts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `email` VARCHAR(100) NULL DEFAULT NULL,
  `password` VARCHAR(100) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `marketplace_harvest`.`marketplace_detections`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `marketplace_harvest`.`marketplace_detections` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `capture_date` VARCHAR(100) NULL DEFAULT NULL,
  `marketplace` VARCHAR(500) NULL DEFAULT NULL,
  `order_on_page` INT NULL DEFAULT NULL,
  `title` VARCHAR(500) NULL DEFAULT NULL,
  `description` VARCHAR(5000) NULL DEFAULT NULL,
  `url` VARCHAR(1000) NULL DEFAULT NULL,
  `image_url` VARCHAR(1500) NULL DEFAULT NULL,
  `price` VARCHAR(500) NULL DEFAULT NULL,
  `seller` VARCHAR(100) NULL DEFAULT NULL,
  `paid_search` VARCHAR(45) NULL DEFAULT NULL,
  `status` VARCHAR(500) NULL DEFAULT 'open',
  `state` VARCHAR(500) NULL DEFAULT 'new',
  `reason_code` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `url_UNIQUE` (`url` ASC) VISIBLE,
  INDEX `fk_marketplace_detections_status1_idx` (`status` ASC) VISIBLE,
  INDEX `fk_marketplace_detections_state1_idx` (`state` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `marketplace_harvest`.`audit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `marketplace_harvest`.`audit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date_time` VARCHAR(100) NULL DEFAULT NULL,
  `parameter` ENUM('state', 'status', 'reason_code') NOT NULL,
  `marketplace_detections_id` INT UNSIGNED NOT NULL,
  `analysts_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_audit_changes_marketplace_detections1_idx` (`marketplace_detections_id` ASC) VISIBLE,
  INDEX `fk_audit_changes_analysts1_idx` (`analysts_id` ASC) VISIBLE,
  CONSTRAINT `fk_audit_changes_analysts1`
    FOREIGN KEY (`analysts_id`)
    REFERENCES `marketplace_harvest`.`analysts` (`id`),
  CONSTRAINT `fk_audit_changes_marketplace_detections1`
    FOREIGN KEY (`marketplace_detections_id`)
    REFERENCES `marketplace_harvest`.`marketplace_detections` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
