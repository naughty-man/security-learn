/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : security

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-11-19 17:11:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `enable` tinyint(4) NOT NULL DEFAULT '1' COMMENT '用户是否可用',
  `roles` text COMMENT '用户角色，多个角色之间用逗号隔开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', '123', '1', 'ROLE_ADMIN,ROLE_USER');
INSERT INTO `users` VALUES ('2', 'user', '123', '1', 'ROLE_USER');
