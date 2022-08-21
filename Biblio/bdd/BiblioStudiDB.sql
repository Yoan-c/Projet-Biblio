-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 29, 2022 at 10:42 PM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `BibliostudiDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `auteur`
--

CREATE TABLE `auteur` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(50) DEFAULT NULL,
  `prenom` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `auteur`
--

INSERT INTO `auteur` (`id`, `nom`, `prenom`) VALUES
(1, 'Bennett', 'Richard'),
(2, 'Gonzalez', 'Willie'),
(3, 'Cole', 'Glenda'),
(4, 'Anton', 'Clément'),
(5, 'Paquette', 'Gauthier'),
(6, 'Cole', 'Patrice');

-- --------------------------------------------------------

--
-- Table structure for table `editeur`
--

CREATE TABLE `editeur` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `editeur`
--

INSERT INTO `editeur` (`id`, `nom`) VALUES
(1, 'Flammarion'),
(2, 'Milan'),
(3, 'Baudelaire'),
(4, 'Hachette');

-- --------------------------------------------------------

--
-- Table structure for table `exemplaire`
--

CREATE TABLE `exemplaire` (
  `id` bigint(20) NOT NULL,
  `stock` int(11) NOT NULL,
  `isbn_isbn` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `exemplaire`
--

INSERT INTO `exemplaire` (`id`, `stock`, `isbn_isbn`) VALUES
(1, 20, '9781234567892'),
(2, 2, '9781234567893'),
(3, 10, '9781234567894'),
(4, 5, '9781234567895'),
(5, 10, '9781234567896'),
(9, 3, '9781234567897'),
(10, 2, '9781234567898'),
(11, 1, '9781234567899');

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE `genre` (
  `nom` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `genre`
--

INSERT INTO `genre` (`nom`) VALUES
('Fantastique'),
('Manga'),
('Policier'),
('Science-fiction');

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(1);

-- --------------------------------------------------------

--
-- Table structure for table `langue`
--

CREATE TABLE `langue` (
  `nom` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `langue`
--

INSERT INTO `langue` (`nom`) VALUES
('Anglais'),
('Français');

-- --------------------------------------------------------

--
-- Table structure for table `livre`
--

CREATE TABLE `livre` (
  `isbn` varchar(50) NOT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `date_publication` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `titre` varchar(50) DEFAULT NULL,
  `id_editeur` bigint(20) DEFAULT NULL,
  `langue_nom` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `livre`
--

INSERT INTO `livre` (`isbn`, `cover`, `date_publication`, `description`, `titre`, `id_editeur`, `langue_nom`) VALUES
('9781234567892', 'images/4450593.jpg', '2022-04-20 13:31:54.000000', 'Vivamus mollis tellus erat, non efficitur ipsum rutrum id. Nam sit amet ultrices felis. Quisque id ligula facilisis, lobortis neque nec, porttitor nibh. Praesent justo nunc, semper fringilla mi quis, lacinia efficitur neque. Integer vel ligula at ipsum ma', 'Last dance', 3, 'Français'),
('9781234567893', 'images/4439243.jpg', '2022-04-09 15:34:13.000000', 'Vivamus mollis tellus erat, non efficitur ipsum rutrum id. Nam sit amet ultrices felis. Quisque id ligula facilisis, lobortis neque nec, porttitor nibh. Praesent justo nunc, semper fringilla mi quis, lacinia efficitur neque. Integer vel ligula at ipsum ma', 'The next thing you know', 1, 'Anglais'),
('9781234567894', 'images/4407347.jpg', '2022-04-28 15:35:09.000000', 'Ut mattis lorem sed dui blandit commodo. Aenean turpis odio, aliquam in euismod a, tincidunt a nulla. Duis sit amet augue odio. Donec velit elit, tristique et odio quis, ullamcorper pulvinar ex. Maecenas porttitor sapien a massa tempus sollicitudin. Nam a', 'Rise of the mages', 4, 'Anglais'),
('9781234567895', 'images/90280.jpg', '2022-04-28 15:36:12.000000', 'Donec non mauris ut sem tempor fermentum nec vitae velit. Duis pharetra lectus sit amet metus imperdiet, id viverra nisl volutpat. Etiam quis porta leo, non luctus libero. Nam eget congue nunc. Pellentesque bibendum sodales urna sit amet sodales. Vestibul', 'Prélude', 2, 'Anglais'),
('9781234567896', 'images/60580.jpg', '2022-04-28 15:37:03.000000', 'Ut interdum justo ut viverra ornare. Sed a tristique ipsum. Morbi hendrerit venenatis diam, eu iaculis nibh malesuada id. Aenean porttitor in dui quis molestie. Sed ut orci quis purus viverra ultricies. Nullam turpis dolor, tincidunt fermentum elementum s', 'Cortome biotech', 4, 'Français'),
('9781234567897', 'images/452080.jpg', '2022-05-01 15:38:06.000000', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur tellus tellus, convallis vitae viverra eget, dapibus sit amet diam. Nulla rhoncus ultricies ullamcorper. Interdum et malesuada fames ac ante ipsum primis in faucibus. Fusce lacus lacus, pr', 'Cléopédia', 1, 'Français'),
('9781234567898', 'images/36058.jpg', '2022-05-02 15:38:43.000000', 'Phasellus diam urna, rhoncus finibus nibh id, congue tempus tortor. Aenean tempor elementum libero quis vehicula. Proin lacinia volutpat justo vel aliquam. Aenean eget pharetra massa. Donec id velit et diam luctus luctus.', 'The promise nerverland', 3, 'Anglais'),
('9781234567899', 'images/45080.jpg', '2022-05-02 15:39:41.000000', 'Ut cursus condimentum tellus, in accumsan diam faucibus nec. Pellentesque in dapibus ante. Praesent tortor sapien, aliquam in feugiat nec, dictum rhoncus enim. Mauris non tellus quis risus pretium feugiat id at enim. Nunc vitae neque vitae leo placerat co', 'Les pierres du cauchemard', 4, 'Français');

-- --------------------------------------------------------

--
-- Table structure for table `livre_auteurs`
--

CREATE TABLE `livre_auteurs` (
  `livre_isbn` varchar(50) NOT NULL,
  `auteurs_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `livre_auteurs`
--

INSERT INTO `livre_auteurs` (`livre_isbn`, `auteurs_id`) VALUES
('9781234567892', 3),
('9781234567892', 1),
('9781234567893', 2),
('9781234567894', 3),
('9781234567895', 4),
('9781234567896', 5),
('9781234567897', 1),
('9781234567898', 2),
('9781234567899', 3),
('9781234567897', 6);

-- --------------------------------------------------------

--
-- Table structure for table `livre_genres`
--

CREATE TABLE `livre_genres` (
  `livre_isbn` varchar(50) NOT NULL,
  `genres_nom` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `livre_genres`
--

INSERT INTO `livre_genres` (`livre_isbn`, `genres_nom`) VALUES
('9781234567892', 'Fantastique'),
('9781234567892', 'Manga'),
('9781234567892', 'Policier'),
('9781234567892', 'Science-fiction'),
('9781234567893', 'Manga'),
('9781234567894', 'Policier'),
('9781234567895', 'Science-fiction'),
('9781234567896', 'Fantastique'),
('9781234567897', 'Manga'),
('9781234567898', 'Policier'),
('9781234567899', 'Science-fiction');

-- --------------------------------------------------------

--
-- Table structure for table `pret`
--

CREATE TABLE `pret` (
  `id` bigint(20) NOT NULL,
  `date_debut` datetime(6) DEFAULT NULL,
  `date_fin` datetime(6) DEFAULT NULL,
  `renouvele` bit(1) DEFAULT NULL,
  `exemplaire_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pret`
--

INSERT INTO `pret` (`id`, `date_debut`, `date_fin`, `renouvele`, `exemplaire_id`, `user_id`) VALUES
(4, '2022-07-19 22:10:36.000000', '2022-08-02 22:10:36.000000', b'1', 1, 158),
(5, '2022-07-29 22:10:36.000000', '2022-08-11 22:10:36.000000', b'0', 2, 158),
(6, '2022-07-01 22:11:36.000000', '2022-07-08 22:11:36.000000', b'0', 9, 158),
(7, '2022-07-24 22:11:36.000000', '2022-07-30 22:11:36.000000', b'1', 1, 160);

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mdp` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `hash` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `validity` datetime(6) DEFAULT NULL,
  `is_connect` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `email`, `mdp`, `nom`, `prenom`, `hash`, `salt`, `validity`, `is_connect`) VALUES
(158, 'test@gmail.com', NULL, 'test', 'test', '888325663ddd931f1b9e1a8e8bae488c45a15d679ad12252290e02146f8825bc', '93xpT03byGKbfEbAXWGiGuYjhaPCjRFJWwWNt9rgey89iMQgZJtRn1zNswQ5Gj1i', '2022-07-29 22:35:23.000000', b'1'),
(159, 'admin@gmail.com', NULL, 'admin', 'admin', '602329d890637985a235f5f9dd084aa921b12cfb619ab8d195afb34f9fc7c09e', '4MU8LknHc3qGP0w8TLoB0MnlCyFKWzyQdPWethnhuwDoRYMIPDr0DD1PVFntvtuV', '2022-07-29 22:24:24.000000', b'0'),
(160, 'test2@gmail.com', NULL, 'test2', 'test2', 'ff774f4602c9729d3c4be97b5835438420c5e497e1d696b22d941dcf7c6ff547', 'AMGzvIKnM6UChlHaLlKyVP6tfM1NO42dS7X4hoQoufuduSNLIguOO0RxdkLHvK4i', '2022-07-29 22:24:55.000000', b'0');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `auteur`
--
ALTER TABLE `auteur`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `editeur`
--
ALTER TABLE `editeur`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `exemplaire`
--
ALTER TABLE `exemplaire`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKj924ahmrv9rtvfu8yymqt3pyu` (`isbn_isbn`);

--
-- Indexes for table `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`nom`);

--
-- Indexes for table `langue`
--
ALTER TABLE `langue`
  ADD PRIMARY KEY (`nom`);

--
-- Indexes for table `livre`
--
ALTER TABLE `livre`
  ADD PRIMARY KEY (`isbn`),
  ADD KEY `FK2mc0ke265ktqwejavevt4ceri` (`id_editeur`),
  ADD KEY `FKqf9etnhkvxdd5u428pr1b6nun` (`langue_nom`);

--
-- Indexes for table `livre_auteurs`
--
ALTER TABLE `livre_auteurs`
  ADD KEY `FK47j6nd0wl5wd6kvn2knx7iahv` (`auteurs_id`),
  ADD KEY `FK4nc5qdtkq00ht7tioepux176h` (`livre_isbn`);

--
-- Indexes for table `livre_genres`
--
ALTER TABLE `livre_genres`
  ADD KEY `FKt6nslgnw10xg7unkw9bsm7yvt` (`genres_nom`),
  ADD KEY `FKr1j4wb07036lm48fpag1xwb8x` (`livre_isbn`);

--
-- Indexes for table `pret`
--
ALTER TABLE `pret`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjtomu4b9nf3r5tlk8uo9y2exl` (`exemplaire_id`),
  ADD KEY `FK64sut2lvpx80t9we8mcui2tu6` (`user_id`);

--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `auteur`
--
ALTER TABLE `auteur`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `editeur`
--
ALTER TABLE `editeur`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `exemplaire`
--
ALTER TABLE `exemplaire`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `pret`
--
ALTER TABLE `pret`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=161;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `exemplaire`
--
ALTER TABLE `exemplaire`
  ADD CONSTRAINT `FKj924ahmrv9rtvfu8yymqt3pyu` FOREIGN KEY (`isbn_isbn`) REFERENCES `livre` (`isbn`);

--
-- Constraints for table `livre`
--
ALTER TABLE `livre`
  ADD CONSTRAINT `FK2mc0ke265ktqwejavevt4ceri` FOREIGN KEY (`id_editeur`) REFERENCES `editeur` (`id`),
  ADD CONSTRAINT `FKqf9etnhkvxdd5u428pr1b6nun` FOREIGN KEY (`langue_nom`) REFERENCES `langue` (`nom`);

--
-- Constraints for table `livre_auteurs`
--
ALTER TABLE `livre_auteurs`
  ADD CONSTRAINT `FK47j6nd0wl5wd6kvn2knx7iahv` FOREIGN KEY (`auteurs_id`) REFERENCES `auteur` (`id`),
  ADD CONSTRAINT `FK4nc5qdtkq00ht7tioepux176h` FOREIGN KEY (`livre_isbn`) REFERENCES `livre` (`isbn`);

--
-- Constraints for table `livre_genres`
--
ALTER TABLE `livre_genres`
  ADD CONSTRAINT `FKr1j4wb07036lm48fpag1xwb8x` FOREIGN KEY (`livre_isbn`) REFERENCES `livre` (`isbn`),
  ADD CONSTRAINT `FKt6nslgnw10xg7unkw9bsm7yvt` FOREIGN KEY (`genres_nom`) REFERENCES `genre` (`nom`);

--
-- Constraints for table `pret`
--
ALTER TABLE `pret`
  ADD CONSTRAINT `FK64sut2lvpx80t9we8mcui2tu6` FOREIGN KEY (`user_id`) REFERENCES `utilisateur` (`id`),
  ADD CONSTRAINT `FKjtomu4b9nf3r5tlk8uo9y2exl` FOREIGN KEY (`exemplaire_id`) REFERENCES `exemplaire` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
