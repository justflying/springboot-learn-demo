/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.211.133（docker-mysql-8.0）
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : 192.168.211.133:3306
 Source Schema         : mybatis-plus-demo

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 03/03/2020 20:57:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `manager_id` bigint(20) NULL DEFAULT NULL COMMENT '直属上级id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `manager_fk`(`manager_id`) USING BTREE,
  CONSTRAINT `manager_fk` FOREIGN KEY (`manager_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1087982257332887553, '大boss', 26, 'wtf2@baomidou.com', NULL, '2019-01-11 14:20:20');
INSERT INTO `user` VALUES (1088248166370832385, ' 王天风', 25, 'wtf@baomidou.com', 1087982257332887553, '2019-02-05 11:12:22');
INSERT INTO `user` VALUES (1088250446457389058, '李艺伟', 29, 'lyw2019@baomidou.com', 1088248166370832385, '2019-02-14 08:31:16');
INSERT INTO `user` VALUES (1094590409767661570, '张雨琪', 31, 'zjq@baomidou.com', 1088248166370832385, '2019-01-14 09:15:15');
INSERT INTO `user` VALUES (1094592041087729666, '刘红雨', 32, 'lhm@baomidou.com', 1088248166370832385, '2019-01-14 09:48:16');
INSERT INTO `user` VALUES (1200317082731388930, '刘明强', 31, NULL, 1087982257332887553, '2019-11-29 15:34:28');
INSERT INTO `user` VALUES (1200328961730117634, '向前', 31, NULL, 1087982257332887553, '2019-11-29 16:21:40');

SET FOREIGN_KEY_CHECKS = 1;
