SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rusers
-- ----------------------------
DROP TABLE IF EXISTS `rusers`;
CREATE TABLE `rusers` (
  `uuid` text,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
