-- mysql workbench forward engineering

set @old_unique_checks=@@unique_checks, unique_checks=0;
set @old_foreign_key_checks=@@foreign_key_checks, foreign_key_checks=0;
set @old_sql_mode=@@sql_mode, sql_mode='only_full_group_by,strict_trans_tables,no_zero_in_date,no_zero_date,error_for_division_by_zero,no_engine_substitution';

-- -----------------------------------------------------
-- schema internet_bankieren
-- -----------------------------------------------------

-- -----------------------------------------------------
-- schema internet_bankieren
-- -----------------------------------------------------
create schema if not exists `internet_bankieren` default character set utf8 ;
use `internet_bankieren` ;

-- -----------------------------------------------------
-- table `internet_bankieren`.`particulier`
-- -----------------------------------------------------
create table if not exists `internet_bankieren`.`particulier` (
  `idparticulier` int not null,
  `gebruikersnaam` varchar(45) not null,
  `wachtwoord` varchar(45) not null,
  `voornaam` varchar(45) not null,
  `tussenvoegsels` varchar(45) null,
  `achternaam` varchar(45) not null,
  `bsn` int not null,
  `geboortedatum` date not null,
  `straat` varchar(45) not null,
  `huisnummer` int not null,
  `postcode` varchar(45) not null,
  `woonplaats` varchar(45) not null,
  primary key (`idparticulier`))
engine = innodb default charset=utf8mb4 collate=utf8mb4_general_ci;

-- -----------------------------------------------------
-- table `internet_bankieren`.`medewerker`
-- -----------------------------------------------------
create table if not exists `internet_bankieren`.`medewerker` (
  `personeelsnummer` int not null auto_increment,
  `rol` varchar(45) not null,
  `voornaam` varchar(45) not null,
  `tussenvoegsels` varchar(45) null,
  `achternaam` varchar(45) not null,
  primary key (`personeelsnummer`))
engine = innodb default charset=utf8mb4 collate=utf8mb4_general_ci;


-- -----------------------------------------------------
-- table `internet_bankieren`.`bedrijf`
-- -----------------------------------------------------
create table if not exists `internet_bankieren`.`bedrijf` (
  `idbedrijf` int not null,
  `idaccountmanager` int not null,
  `gebruikersnaam` varchar(45) not null,
  `wachtwoord` varchar(45) not null,
  `bedrijfsnaam` varchar(45) not null,
  `kvknummer` int not null,
  `sector` varchar(45) not null,
  `btwnummer` varchar(45) not null,
  `straat` varchar(45) not null,
  `huisnummer` int not null,
  `postcode` varchar(45) not null,
  `woonplaats` varchar(45) not null,
  primary key (`idbedrijf`),
  index `verzinzelf9_idx` (`idaccountmanager` asc) visible,
  constraint `verzinzelf9`
    foreign key (`idaccountmanager`)
    references `internet_bankieren`.`medewerker` (`personeelsnummer`)
    on delete no action
    on update no action)
engine = innodb default charset=utf8mb4 collate=utf8mb4_general_ci;

-- -----------------------------------------------------
-- table `internet_bankieren`.`bedrijfsrekening`
-- -----------------------------------------------------
create table if not exists `internet_bankieren`.`bedrijfsrekening` (
  `idbedrijf` int not null,
  `idbedrijfsrekening` varchar(45) not null,
  `idcontactpersoon` int not null,
  `saldo` int not null,
  `iban` varchar(45) not null,
  primary key (`idbedrijfsrekening`),
  index `verzinzelf7_idx` (`idbedrijf` asc) visible,
  index `verzinzelf10_idx` (`idcontactpersoon` asc) visible,
  constraint `verzinzelf7`
    foreign key (`idbedrijf`)
    references `internet_bankieren`.`bedrijf` (`idbedrijf`)
    on delete no action
    on update no action,
  constraint `verzinzelf10`
    foreign key (`idcontactpersoon`)
    references `internet_bankieren`.`particulier` (`idparticulier`)
    on delete no action
    on update no action)
engine = innodb default charset=utf8mb4 collate=utf8mb4_general_ci;


-- -----------------------------------------------------
-- table `internet_bankieren`.`priverekening`
-- -----------------------------------------------------
create table if not exists `internet_bankieren`.`priverekening` (
  `idrekeninghouder` int not null,
  `idpriverekening` varchar(45) not null,
  `iban` varchar(45) not null,
  `saldo` varchar(45) not null,
  primary key (`idpriverekening`),
  index `verzinzelf6_idx` (`idrekeninghouder` asc) visible,
  constraint `verzinzelf6`
    foreign key (`idrekeninghouder`)
    references `internet_bankieren`.`particulier` (`idparticulier`)
    on delete no action
    on update no action)
engine = innodb default charset=utf8mb4 collate=utf8mb4_general_ci;


-- -----------------------------------------------------
-- table `internet_bankieren`.`transactie`
-- -----------------------------------------------------
create table if not exists `internet_bankieren`.`transactie` (
  `idtransactie` int not null auto_increment,
  `bedrag` varchar(45) not null,
  `transactiebeschrijving` varchar(45) not null,
  `datum` datetime not null,
  `bijschrijving` tinyint not null,
  `idbedrijfsrekening` varchar(45) null,
  `idpriverekening` varchar(45) null,
  primary key (`idtransactie`),
  index `verzinzelf1_idx` (`idbedrijfsrekening` asc) visible,
  index `verzinzelf2_idx` (`idpriverekening` asc) visible,
  constraint `verzinzelf1`
    foreign key (`idbedrijfsrekening`)
    references `internet_bankieren`.`bedrijfsrekening` (`idbedrijfsrekening`)
    on delete no action
    on update no action,
  constraint `verzinzelf2`
    foreign key (`idpriverekening`)
    references `internet_bankieren`.`priverekening` (`idpriverekening`)
    on delete no action
    on update no action)
engine = innodb default charset=utf8mb4 collate=utf8mb4_general_ci;


set sql_mode=@old_sql_mode;
set foreign_key_checks=@old_foreign_key_checks;
set unique_checks=@old_unique_checks;
