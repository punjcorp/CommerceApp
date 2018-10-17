# This file will have the initial sqls needed for PI-POS-DB user setup
# Also, the schema creation for flyway to connect using new user

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- User Creation
-- -----------------------------------------------------
ALTER USER 'root'@'localhost' IDENTIFIED BY 'PI_POS_T_DB_ROOT_PASSWORD';

-- -----------------------------------------------------
-- User Creation
-- -----------------------------------------------------
CREATE USER 'PI_POS_T_APP_DB_USER'@'localhost' IDENTIFIED BY 'PI_POS_T_APP_DB_PASSWORD';
GRANT ALL PRIVILEGES ON * . * TO 'PI_POS_T_APP_DB_USER'@'localhost';
FLUSH PRIVILEGES;

-- -----------------------------------------------------
-- Schema commerce
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `commercedb` DEFAULT CHARACTER SET utf8 ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
