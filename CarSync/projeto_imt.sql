-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 25-Ago-2023 às 01:55
-- Versão do servidor: 10.4.28-MariaDB
-- versão do PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `projeto_imt`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `customer`
--

CREATE TABLE `customer` (
                            `driver_license_number` int(8) NOT NULL,
                            `license_type` char(1) NOT NULL,
                            `start_date` date NOT NULL,
                            `expiration_date` date NOT NULL,
                            `nif` int(9) NOT NULL,
                            `deactivated` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `customer`
--

INSERT INTO `customer` (`driver_license_number`, `license_type`, `start_date`, `expiration_date`, `nif`, `deactivated`) VALUES
                                                                                                                            (10000000, 'B', '2023-03-05', '2026-03-05', 200000000, 1),
                                                                                                                            (10000001, 'B', '2023-04-04', '2028-04-04', 200000001, 1),
                                                                                                                            (10000002, 'A', '2023-03-11', '2028-03-11', 200000002, 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `employee`
--

CREATE TABLE `employee` (
                            `nif` int(9) NOT NULL,
                            `access_level` int(1) NOT NULL,
                            `deactivated` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `insurance`
--

CREATE TABLE `insurance` (
                             `policy` int(9) NOT NULL,
                             `expiry_date` date NOT NULL,
                             `company` varchar(45) NOT NULL,
                             `start_date` date NOT NULL,
                             `extra_category` varchar(35) NOT NULL,
                             `plate` char(8) NOT NULL,
                             `deactivated` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `person`
--

CREATE TABLE `person` (
                          `nif` int(9) NOT NULL,
                          `name` varchar(45) NOT NULL,
                          `address` varchar(60) NOT NULL,
                          `b_date` date NOT NULL,
                          `password` varchar(60) NOT NULL,
                          `deactivated` tinyint(1) NOT NULL DEFAULT 1,
                          `email` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `person`
--

INSERT INTO `person` (`nif`, `name`, `address`, `b_date`, `password`, `deactivated`, `email`) VALUES
                                                                                                  (200000000, 'Pedro Ribeiro', 'Rua das Vendas', '2000-03-26', '$2a$10$9hzfxiT0ttKCh9/8b3zEyeRaXwsD8TY/PqI51ktb.LesXlFmnIEmm', 1, 'sarafim@gmail.com'),
                                                                                                  (200000001, 'Diogo Amazonia', 'Rua das Compras', '2001-03-30', '$2a$10$bZhLDLdA61oUIf4ulgDSx.5INn.9VtFoWwu3OJ4tH7yDfXE7yVRJ2', 1, 'josefim20@gmail.com'),
                                                                                                  (200000002, 'Diogo Sem Fim', 'Morada das moradas', '2002-03-11', '$2a$10$qVWsKhRYCzQVLMwozOQ3c.Id/jA36d7uH0Fbl3dVYz3M4W1I2b6AC', 1, '123mail@gmail.com');

-- --------------------------------------------------------

--
-- Estrutura da tabela `ticket`
--

CREATE TABLE `ticket` (
                          `plate` char(8) NOT NULL,
                          `date` date NOT NULL,
                          `expiry_date` date NOT NULL,
                          `value` double NOT NULL,
                          `reason` varchar(30) NOT NULL,
                          `paid` tinyint(1) NOT NULL DEFAULT 1,
                          `deactivated` tinyint(1) NOT NULL DEFAULT 1,
                          `ticketID` int(11) NOT NULL,
                          `nif` int(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `vehicle`
--

CREATE TABLE `vehicle` (
                           `brand` varchar(45) NOT NULL,
                           `model` varchar(45) NOT NULL,
                           `color` varchar(45) NOT NULL,
                           `registration_date` date NOT NULL,
                           `plate` char(8) NOT NULL,
                           `vin` char(17) NOT NULL,
                           `category` varchar(35) NOT NULL,
                           `nif` int(9) NOT NULL,
                           `deactivated` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `customer`
--
ALTER TABLE `customer`
    ADD PRIMARY KEY (`driver_license_number`),
    ADD UNIQUE KEY `nif` (`nif`),
    ADD UNIQUE KEY `nif_2` (`nif`);

--
-- Índices para tabela `employee`
--
ALTER TABLE `employee`
    ADD UNIQUE KEY `nif` (`nif`);

--
-- Índices para tabela `insurance`
--
ALTER TABLE `insurance`
    ADD PRIMARY KEY (`policy`),
    ADD KEY `Plate_Insurance_FK` (`plate`);

--
-- Índices para tabela `person`
--
ALTER TABLE `person`
    ADD PRIMARY KEY (`nif`);

--
-- Índices para tabela `ticket`
--
ALTER TABLE `ticket`
    ADD PRIMARY KEY (`ticketID`),
    ADD KEY `Plate_Number_Ticket_FK` (`plate`),
    ADD KEY `NIF_Offender` (`nif`);

--
-- Índices para tabela `vehicle`
--
ALTER TABLE `vehicle`
    ADD PRIMARY KEY (`plate`),
    ADD UNIQUE KEY `vin` (`vin`),
    ADD KEY `Owner_NIF` (`nif`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `ticket`
--
ALTER TABLE `ticket`
    MODIFY `ticketID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `customer`
--
ALTER TABLE `customer`
    ADD CONSTRAINT `nif_user` FOREIGN KEY (`nif`) REFERENCES `person` (`nif`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `employee`
--
ALTER TABLE `employee`
    ADD CONSTRAINT `nif_employee` FOREIGN KEY (`nif`) REFERENCES `person` (`nif`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `insurance`
--
ALTER TABLE `insurance`
    ADD CONSTRAINT `Plate_Insurance_FK` FOREIGN KEY (`plate`) REFERENCES `vehicle` (`plate`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limitadores para a tabela `ticket`
--
ALTER TABLE `ticket`
    ADD CONSTRAINT `NIF_Offender` FOREIGN KEY (`nif`) REFERENCES `vehicle` (`nif`),
    ADD CONSTRAINT `Plate_Number_Ticket_FK` FOREIGN KEY (`plate`) REFERENCES `vehicle` (`plate`);

--
-- Limitadores para a tabela `vehicle`
--
ALTER TABLE `vehicle`
    ADD CONSTRAINT `Owner_NIF` FOREIGN KEY (`nif`) REFERENCES `customer` (`nif`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
