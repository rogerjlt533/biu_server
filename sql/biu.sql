/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80026
Source Host           : localhost:3306
Source Database       : biu

Target Server Type    : MYSQL
Target Server Version : 80026
File Encoding         : 65001

Date: 2021-10-27 16:10:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for biu_areas
-- ----------------------------
DROP TABLE IF EXISTS `biu_areas`;
CREATE TABLE `biu_areas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `area_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '地区名称',
  `code` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '地区code',
  `area_type` tinyint NOT NULL DEFAULT '0' COMMENT '类型 1-省 2-市 3-区 4-街道',
  `parent_code` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '父级地区编号',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of biu_areas
-- ----------------------------

-- ----------------------------
-- Table structure for biu_users
-- ----------------------------
DROP TABLE IF EXISTS `biu_users`;
CREATE TABLE `biu_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `nick` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `pen_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '笔名',
  `openid` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `unionid` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `sex` tinyint NOT NULL DEFAULT '0' COMMENT '性别 1-男 2-女',
  `age` tinyint NOT NULL DEFAULT '0' COMMENT '年龄(周岁)',
  `match_start_age` tinyint NOT NULL DEFAULT '0' COMMENT '匹配起始年龄(周岁)',
  `match_end_age` tinyint NOT NULL DEFAULT '0' COMMENT '匹配终止年龄(周岁)',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(30) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '联系电话',
  `email` varchar(50) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '联系邮箱',
  `province` bigint NOT NULL DEFAULT '0' COMMENT '所在省份',
  `city` bigint NOT NULL DEFAULT '0' COMMENT '所在城市',
  `country` bigint NOT NULL DEFAULT '0' COMMENT '所在区县',
  `street` bigint NOT NULL DEFAULT '0' COMMENT '所在街道',
  `address` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '具体地址',
  `zipcode` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '邮编',
  `introduce` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户简介',
  `last_ip` varchar(30) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后登录IP',
  `remark` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `use_status` tinyint NOT NULL DEFAULT '0' COMMENT '使用状态 1-正常 0-禁用',
  `comment_status` tinyint NOT NULL DEFAULT '0' COMMENT '树洞评论开关状态 1-开启 0-关闭',
  `search_status` tinyint NOT NULL DEFAULT '0' COMMENT '寻友开关状态 1-开启 0-关闭',
  `anonymous` tinyint NOT NULL DEFAULT '0' COMMENT '匿名状态 1-开启 0-关闭',
  `last_login` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of biu_users
-- ----------------------------
