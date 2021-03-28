/*
Navicat MySQL Data Transfer

Source Server         : 192.168.241.129
Source Server Version : 50733
Source Host           : 192.168.241.129:3306
Source Database       : customize_mybatis

Target Server Type    : MYSQL
Target Server Version : 50733
File Encoding         : 65001

Date: 2021-03-28 16:59:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` int(11) NOT NULL,
                        `username` varchar(64) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'jamie');
INSERT INTO `user` VALUES ('2', 'dog');
INSERT INTO `user` VALUES ('3', 'cat');
