/*
Navicat MySQL Data Transfer

Source Server         : VMySQL
Source Server Version : 50617
Source Host           : 127.0.0.1:3306
Source Database       : sectraauction

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2016-03-30 00:17:32
*/

CREATE DATABASE SecTraAuction;
USE SecTraAuction;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `admin` varchar(30) NOT NULL,
  `pasword` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`admin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('admin', 'admin');
INSERT INTO `t_admin` VALUES ('adminlogin', 'adminlogin');
INSERT INTO `t_admin` VALUES ('zlpaicheadmin', 'zhaoyan2008');

-- ----------------------------
-- Table structure for t_bid
-- ----------------------------
DROP TABLE IF EXISTS `t_bid`;
CREATE TABLE `t_bid` (
  `bid_id` int(11) NOT NULL AUTO_INCREMENT,
  `bidTime` datetime DEFAULT NULL,
  `bidSpri` int(8) DEFAULT NULL,
  `plusPri` int(6) DEFAULT NULL,
  `bidEndTime` datetime DEFAULT NULL,
  `v_id` int(11) DEFAULT NULL,
  `beginAuction` tinyint(4) DEFAULT '0',
  `stopAuction` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`bid_id`),
  KEY `fk_up11` (`v_id`),
  CONSTRAINT `fk_up11` FOREIGN KEY (`v_id`) REFERENCES `t_vehicle` (`v_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_bid
-- ----------------------------
INSERT INTO `t_bid` VALUES ('43', '2016-03-18 00:13:00', '40000', '100', '2020-03-30 00:13:00', '2', '0', '0');

-- ----------------------------
-- Table structure for t_card
-- ----------------------------
DROP TABLE IF EXISTS `t_card`;
CREATE TABLE `t_card` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `card_id` varchar(30) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `c_owner` varchar(30) DEFAULT NULL,
  `c_type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_card
-- ----------------------------

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `n_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `title` varchar(30) DEFAULT NULL,
  `n_time` datetime DEFAULT NULL,
  PRIMARY KEY (`n_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '公告的内容', '发布的公告', '2016-03-16 21:19:37');
INSERT INTO `t_notice` VALUES ('2', '公告的内容', '最新公告', '2016-03-26 21:27:53');

-- ----------------------------
-- Table structure for t_rank
-- ----------------------------
DROP TABLE IF EXISTS `t_rank`;
CREATE TABLE `t_rank` (
  `r_rank` tinyint(4) NOT NULL,
  `r_range` varchar(8) DEFAULT NULL,
  `r_cmsion` int(11) DEFAULT NULL,
  PRIMARY KEY (`r_rank`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_rank
-- ----------------------------
INSERT INTO `t_rank` VALUES ('0', null, null);
INSERT INTO `t_rank` VALUES ('1', null, null);

-- ----------------------------
-- Table structure for t_sourceimg
-- ----------------------------
DROP TABLE IF EXISTS `t_sourceimg`;
CREATE TABLE `t_sourceimg` (
  `simg_id` int(11) NOT NULL AUTO_INCREMENT,
  `simg_name` varchar(30) DEFAULT NULL,
  `simg_path` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`simg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sourceimg
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `u_id` varchar(15) NOT NULL,
  `u_name` varchar(15) DEFAULT NULL,
  `psword` varchar(25) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `tel` varchar(13) DEFAULT NULL,
  `r_rank` tinyint(4) DEFAULT NULL,
  `vipTime` int(11) DEFAULT NULL,
  `paytime` datetime DEFAULT NULL,
  `reIntro` varchar(50) DEFAULT NULL,
  `cardID` char(18) DEFAULT NULL,
  `tname` varchar(30) DEFAULT NULL,
  `u_address` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`u_id`),
  KEY `pk_rank` (`r_rank`),
  CONSTRAINT `pk_rank` FOREIGN KEY (`r_rank`) REFERENCES `t_rank` (`r_rank`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('admin', 'admin', 'ISMvKXpXpadDiUoOSoAfww==', 'qwer@qq.com', '13245678911', null, null, null, 'kS7IA7LOSeSlQQaNSVq1cA==', 'asdf', 'asdf', 'asdf');
INSERT INTO `t_user` VALUES ('admins', 'admins', 'Ku/DQgCilKPMfbgbQ6gYcw==', 'Q@qq.com', '13622060782', null, null, null, 'kS7IA7LOSeSlQQaNSVq1cA==', 'admin', 'admin', 'asf');
INSERT INTO `t_user` VALUES ('cheng1483', '111', '4QrcOUm6Wau+VuBX8g+IPg==', 'chemh1483@163.com', '11111111111', null, null, null, 'AsQlFX7NMvJZVIszQC/20w==', '111111111111111111', '111', 'zzzzzz');
INSERT INTO `t_user` VALUES ('huyangvista', 'asdf', 'CHtnvTfGsmtw/Bz7H/Kmdw==', 'hu@qq.com', '13622060782', null, null, null, 'IsJ2oFqnyQVmriF1vMKpsA==', 'asdf', 'adf', 'asdf');
INSERT INTO `t_user` VALUES ('q', 'q', 'dpT0pmMW5TyM3Z2ZVL1hHQ==', 'q@q.com', 'qqqqqqqqqqq', null, null, null, 'dpT0pmMW5TyM3Z2ZVL1hHQ==', 'q', 'q', 'q');

-- ----------------------------
-- Table structure for t_userpart
-- ----------------------------
DROP TABLE IF EXISTS `t_userpart`;
CREATE TABLE `t_userpart` (
  `u_id` varchar(15) NOT NULL,
  `v_id` int(11) NOT NULL,
  `price` varchar(6) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `attention` tinyint(4) DEFAULT NULL,
  `pledge` varchar(6) DEFAULT NULL,
  `currentTime` datetime DEFAULT NULL,
  PRIMARY KEY (`u_id`,`v_id`),
  KEY `fk_up2` (`v_id`),
  CONSTRAINT `fk_up112` FOREIGN KEY (`u_id`) REFERENCES `t_user` (`u_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_up2` FOREIGN KEY (`v_id`) REFERENCES `t_vehicle` (`v_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userpart
-- ----------------------------
INSERT INTO `t_userpart` VALUES ('admin', '4', null, '1', '1', null, null);
INSERT INTO `t_userpart` VALUES ('admins', '4', null, '1', '1', null, null);
INSERT INTO `t_userpart` VALUES ('huyangvista', '1', null, '1', '1', null, null);

-- ----------------------------
-- Table structure for t_userpartfinish
-- ----------------------------
DROP TABLE IF EXISTS `t_userpartfinish`;
CREATE TABLE `t_userpartfinish` (
  `u_id` varchar(15) NOT NULL,
  `v_id` int(11) NOT NULL,
  `price` varchar(6) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `attention` tinyint(4) DEFAULT NULL,
  `pledge` varchar(6) DEFAULT NULL,
  `currentTime` datetime DEFAULT NULL,
  PRIMARY KEY (`u_id`,`v_id`),
  KEY `fk_up2` (`v_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_userpartfinish
-- ----------------------------

-- ----------------------------
-- Table structure for t_vehicle
-- ----------------------------
DROP TABLE IF EXISTS `t_vehicle`;
CREATE TABLE `t_vehicle` (
  `v_id` int(11) NOT NULL AUTO_INCREMENT,
  `plateNo` varchar(20) DEFAULT NULL,
  `note` varchar(100) DEFAULT NULL,
  `loc` varchar(50) DEFAULT NULL,
  `vname` varchar(20) DEFAULT NULL,
  `regTime` datetime DEFAULT NULL,
  `v_version` varchar(20) DEFAULT NULL,
  `gear` tinyint(4) DEFAULT NULL,
  `vframe` varchar(30) DEFAULT NULL,
  `pledge` varchar(6) DEFAULT NULL,
  `motor` varchar(15) DEFAULT NULL,
  `gearBox` varchar(15) DEFAULT NULL,
  `output` varchar(15) DEFAULT NULL,
  `mairSac` varchar(15) DEFAULT NULL,
  `sairSac` varchar(15) DEFAULT NULL,
  `fbroke` tinyint(4) DEFAULT NULL,
  `ftransfer` tinyint(4) DEFAULT NULL,
  `fdisass` tinyint(4) DEFAULT NULL,
  `fagain` tinyint(4) DEFAULT NULL,
  `fbrule` tinyint(4) DEFAULT NULL,
  `fmort` tinyint(4) DEFAULT NULL,
  `tel` varchar(13) DEFAULT NULL,
  `source` varchar(20) DEFAULT NULL,
  `v_source` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`v_id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_vehicle
-- ----------------------------
INSERT INTO `t_vehicle` VALUES ('1', '津1001', null, null, '奥迪A6', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '13622060782', null, null);
INSERT INTO `t_vehicle` VALUES ('2', '津1002', null, null, '丰田1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '13622060782', null, null);
INSERT INTO `t_vehicle` VALUES ('3', '津1003', null, null, '奔驰6', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '13622060782', null, null);
INSERT INTO `t_vehicle` VALUES ('4', '津1004', null, null, '宝马1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '13622060782', '9', null);

-- ----------------------------
-- Table structure for v_caruser
-- ----------------------------
DROP TABLE IF EXISTS `v_caruser`;
CREATE TABLE `v_caruser` (
  `vid` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` varchar(15) DEFAULT NULL,
  `v_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of v_caruser
-- ----------------------------
INSERT INTO `v_caruser` VALUES ('3', 'admin', '1');
INSERT INTO `v_caruser` VALUES ('4', 'q', '2');
INSERT INTO `v_caruser` VALUES ('5', 'q', '3');
INSERT INTO `v_caruser` VALUES ('6', 'admins', '4');
