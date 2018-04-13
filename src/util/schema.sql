-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema financa
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema financa
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `financa` DEFAULT CHARACTER SET utf8 ;
USE `financa` ;

-- -----------------------------------------------------
-- Table `financa`.`bandeira`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `financa`.`bandeira` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(35) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `financa`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `financa`.`usuario` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(25) NOT NULL,
  `senha` VARCHAR(40) NOT NULL,
  `nome` VARCHAR(50) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `ativo` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `financa`.`cartao_credito`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `financa`.`cartao_credito` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(45) NOT NULL,
  `limite` DOUBLE NOT NULL,
  `dia_fecha` INT(11) NULL DEFAULT NULL,
  `dia_paga` INT(11) NULL DEFAULT NULL,
  `usuario_id` INT(10) UNSIGNED NOT NULL,
  `bandeira_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `usuario_id`, `bandeira_id`),
  INDEX `fk_cartao_credito_usuario1_idx` (`usuario_id` ASC),
  INDEX `fk_cartao_credito_bandeira1_idx` (`bandeira_id` ASC),
  CONSTRAINT `fk_cartao_credito_bandeira1`
    FOREIGN KEY (`bandeira_id`)
    REFERENCES `financa`.`bandeira` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_cartao_credito_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `financa`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `financa`.`categoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `financa`.`categoria` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(25) NOT NULL,
  `tipo_categoria` CHAR(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `financa`.`compras_cartao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `financa`.`compras_cartao` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `valor` DOUBLE NOT NULL,
  `data` DATE NOT NULL,
  `descricao` VARCHAR(45) NULL DEFAULT NULL,
  `parcelas` INT(10) UNSIGNED NULL DEFAULT NULL,
  `valor_parcela` DOUBLE UNSIGNED NULL DEFAULT NULL,
  `cartao_credito_id` INT(10) UNSIGNED NOT NULL,
  `categoria_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `cartao_credito_id`, `categoria_id`),
  INDEX `fk_compras_cartao_cartao_credito1_idx` (`cartao_credito_id` ASC),
  INDEX `fk_compras_cartao_categoria1_idx` (`categoria_id` ASC),
  CONSTRAINT `fk_compras_cartao_cartao_credito1`
    FOREIGN KEY (`cartao_credito_id`)
    REFERENCES `financa`.`cartao_credito` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_compras_cartao_categoria1`
    FOREIGN KEY (`categoria_id`)
    REFERENCES `financa`.`categoria` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `financa`.`tipo_conta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `financa`.`tipo_conta` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tipo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `financa`.`conta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `financa`.`conta` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `saldo_inicial` DOUBLE NOT NULL,
  `usuario_id` INT(10) UNSIGNED NOT NULL,
  `tipo_conta_id` INT(10) UNSIGNED NOT NULL,
  `inclui_soma` TINYINT(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `usuario_id`, `tipo_conta_id`),
  INDEX `fk_conta_usuario1_idx` (`usuario_id` ASC),
  INDEX `fk_conta_tipo_conta1_idx` (`tipo_conta_id` ASC),
  CONSTRAINT `fk_conta_tipo_conta1`
    FOREIGN KEY (`tipo_conta_id`)
    REFERENCES `financa`.`tipo_conta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_conta_usuario1`
    FOREIGN KEY (`usuario_id`)
    REFERENCES `financa`.`usuario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `financa`.`despesa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `financa`.`despesa` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `valor` DOUBLE NOT NULL,
  `data` DATE NOT NULL,
  `descricao` VARCHAR(45) NULL DEFAULT NULL,
  `pago` TINYINT(4) NOT NULL,
  `conta_id` INT(10) UNSIGNED NOT NULL,
  `categoria_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `conta_id`, `categoria_id`),
  INDEX `fk_despesa_conta1_idx` (`conta_id` ASC),
  INDEX `fk_despesa_categoria1_idx` (`categoria_id` ASC),
  CONSTRAINT `fk_despesa_categoria1`
    FOREIGN KEY (`categoria_id`)
    REFERENCES `financa`.`categoria` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_despesa_conta1`
    FOREIGN KEY (`conta_id`)
    REFERENCES `financa`.`conta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `financa`.`receita`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `financa`.`receita` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `valor` DOUBLE NOT NULL,
  `data` DATE NOT NULL,
  `descricao` VARCHAR(45) NULL DEFAULT NULL,
  `recebido` TINYINT(4) NOT NULL,
  `conta_id` INT(10) UNSIGNED NOT NULL,
  `categoria_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `conta_id`, `categoria_id`),
  INDEX `fk_receita_conta1_idx` (`conta_id` ASC),
  INDEX `fk_receita_categoria1_idx` (`categoria_id` ASC),
  CONSTRAINT `fk_receita_categoria1`
    FOREIGN KEY (`categoria_id`)
    REFERENCES `financa`.`categoria` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_receita_conta1`
    FOREIGN KEY (`conta_id`)
    REFERENCES `financa`.`conta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `financa`.`transferencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `financa`.`transferencia` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `observacao` VARCHAR(45) NOT NULL,
  `valor` DOUBLE NOT NULL,
  `data` DATE NOT NULL,
  `contaorigem_id` INT(10) UNSIGNED NOT NULL,
  `contadestino_id` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `contaorigem_id`, `contadestino_id`),
  INDEX `fk_transferencia_conta1_idx` (`contaorigem_id` ASC),
  INDEX `fk_transferencia_conta2_idx` (`contadestino_id` ASC),
  CONSTRAINT `fk_transferencia_conta1`
    FOREIGN KEY (`contaorigem_id`)
    REFERENCES `financa`.`conta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transferencia_conta2`
    FOREIGN KEY (`contadestino_id`)
    REFERENCES `financa`.`conta` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO `financa`.`categoria` (`nome`, `tipo_categoria`) VALUES ('Investimentos','R'), ('Outros','R'),('Presente','R'),('Prêmios','R'),('Salário','R');
INSERT INTO `financa`.`categoria` (`nome`, `tipo_categoria`) VALUES ('Alimentação','D'),('Educação','D'),('Lazer','D'),('Moradia','D'),('Pagamentos','D'),('Roupa','D'),('Saúde','D'),('Transporte','D');
INSERT INTO `financa`.`tipo_conta` (`tipo`) VALUES ('Conta Corrente'),('Dinheiro'),('Poupança'),('Investimentos'),('Outros');
INSERT INTO `financa`.`usuario` (`id`,`login`,`senha`,`nome`,`email`,`ativo`)  VALUES (1, 'admin', '123','Jeferson Menezes','je_lionjuda@hotmail.com',true);