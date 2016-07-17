CREATE TABLE `ARTICLE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pzn` varchar(8) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `provider` varchar(100) DEFAULT NULL,
  `dosage` varchar(45) DEFAULT NULL,
  `packaging` varchar(45) DEFAULT NULL,
  `selling_price` decimal(4,2) DEFAULT NULL,
  `purchase_price` decimal(4,2) DEFAULT NULL,
  `narcotic` tinyint(4) DEFAULT NULL,
  `stock` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pzn_UNIQUE` (`pzn`)
) ENGINE=InnoDB AUTO_INCREMENT=232661 DEFAULT CHARSET=latin1;
