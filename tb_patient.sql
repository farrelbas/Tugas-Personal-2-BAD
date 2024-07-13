-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 13, 2024 at 08:40 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tp2_bad`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_patient`
--

CREATE TABLE `tb_patient` (
  `id_patient` tinyint(5) NOT NULL,
  `patient_name` varchar(50) NOT NULL,
  `patient_dob` date NOT NULL,
  `patient_address` text NOT NULL,
  `patient_nik` char(16) NOT NULL,
  `patient_gender` tinyint(1) NOT NULL,
  `patient_inserted_at` datetime NOT NULL,
  `patient_last_updated_at` datetime NOT NULL,
  `patient_softdel` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_patient`
--
ALTER TABLE `tb_patient`
  ADD PRIMARY KEY (`id_patient`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_patient`
--
ALTER TABLE `tb_patient`
  MODIFY `id_patient` tinyint(5) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
