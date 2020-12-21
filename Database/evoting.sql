-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 07, 2018 at 03:25 PM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `evoting`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `email` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `contact` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`email`, `name`, `password`, `contact`) VALUES
('taha.asif7@gmail.com', 'Taha Asif', 'taha', '03323349909');

-- --------------------------------------------------------

--
-- Table structure for table `candidates`
--

CREATE TABLE `candidates` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `event_id` int(11) NOT NULL,
  `profile_pic` varchar(20) NOT NULL,
  `votesCount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `candidates`
--

INSERT INTO `candidates` (`id`, `name`, `event_id`, `profile_pic`, `votesCount`) VALUES
(1, 'taha', 1, 'aa.png', 4),
(2, 'shahiq', 2, 'bb.png', 2),
(3, 'ijc', 1, 'cc.png', 3),
(4, 'taha', 2, 'aa.png', 1),
(5, 'ijc', 4, 'cc.png', 19);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `CNIC` varchar(100) NOT NULL,
  `FullName` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `contact` varchar(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `code` varchar(400) NOT NULL,
  `lastcode` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`CNIC`, `FullName`, `password`, `contact`, `email`, `code`, `lastcode`) VALUES
('30bb4b2d6f82161f48d3f57fb32a0ed5c30da270', 'ijc', '30bb4b2d6f82161f48d3f57fb32a0ed5c30da270', '03323349909', 'k152131@nu.edu.pk', 'cYmw517u8uU:APA91bFrNroqFAh4KjXF0wxk9BVp2rpM1ftXsqFutGMSRWAbXlzUy6UJvukyb1Z0MHEAoWUQtnqYtiAFx03vhEvEt_dvaOTy-a8ekkh3uA1jRY4MwBRhayCA8PQlrnrZB__mRuqSXd5A', ''),
('5af3d069254f15a248cb8c551a6959cc995e47b4', 'owais', '5af3d069254f15a248cb8c551a6959cc995e47b4', '03323349909', 'taha.asif7@gmail.com', 'cYmw517u8uU:APA91bFrNroqFAh4KjXF0wxk9BVp2rpM1ftXsqFutGMSRWAbXlzUy6UJvukyb1Z0MHEAoWUQtnqYtiAFx03vhEvEt_dvaOTy-a8ekkh3uA1jRY4MwBRhayCA8PQlrnrZB__mRuqSXd5A', ''),
('8cb2237d0679ca88db6464eac60da96345513964', 'Taha Asif', '7c222fb2927d828af22f592134e8932480637c0d', '03323349909', 'k152855@nu.edu.pk', 'cYmw517u8uU:APA91bFrNroqFAh4KjXF0wxk9BVp2rpM1ftXsqFutGMSRWAbXlzUy6UJvukyb1Z0MHEAoWUQtnqYtiAFx03vhEvEt_dvaOTy-a8ekkh3uA1jRY4MwBRhayCA8PQlrnrZB__mRuqSXd5A', '587271'),
('a67f8e400bb1dcee0dfba5f2185e4b7c5b211af6', 'Shahiq', 'a67f8e400bb1dcee0dfba5f2185e4b7c5b211af6', '03352155194', 'k152822@nu.edu.pk', 'cYmw517u8uU:APA91bFrNroqFAh4KjXF0wxk9BVp2rpM1ftXsqFutGMSRWAbXlzUy6UJvukyb1Z0MHEAoWUQtnqYtiAFx03vhEvEt_dvaOTy-a8ekkh3uA1jRY4MwBRhayCA8PQlrnrZB__mRuqSXd5A', '');

-- --------------------------------------------------------

--
-- Table structure for table `votes`
--

CREATE TABLE `votes` (
  `id` int(11) NOT NULL,
  `candidate` int(11) NOT NULL,
  `event_id` int(11) NOT NULL,
  `CNIC` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `votes`
--

INSERT INTO `votes` (`id`, `candidate`, `event_id`, `CNIC`) VALUES
(88, 5, 4, '12345');

-- --------------------------------------------------------

--
-- Table structure for table `votingevents`
--

CREATE TABLE `votingevents` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(50) NOT NULL,
  `institute` varchar(50) NOT NULL,
  `poster` varchar(20) NOT NULL,
  `event_date` date NOT NULL,
  `event_time` time NOT NULL,
  `status` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `votingevents`
--

INSERT INTO `votingevents` (`id`, `name`, `description`, `institute`, `poster`, `event_date`, `event_time`, `status`) VALUES
(1, 'abcasdas', 'abcasda', 'abcsssd', 'a.jpg', '0000-00-11', '00:00:11', 2),
(2, 'def', 'defff', 'def', 'coders_cup.jpg', '0000-00-00', '00:00:00', 1),
(4, 'TahaAsif', 'developer', 'asas', 'event_bg.jpg', '0000-00-00', '00:00:00', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`email`),
  ADD UNIQUE KEY `contact` (`contact`);

--
-- Indexes for table `candidates`
--
ALTER TABLE `candidates`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`CNIC`);

--
-- Indexes for table `votes`
--
ALTER TABLE `votes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `event_id` (`event_id`,`CNIC`);

--
-- Indexes for table `votingevents`
--
ALTER TABLE `votingevents`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `candidates`
--
ALTER TABLE `candidates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `votes`
--
ALTER TABLE `votes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;
--
-- AUTO_INCREMENT for table `votingevents`
--
ALTER TABLE `votingevents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
