/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80026
Source Host           : localhost:3306
Source Database       : biu

Target Server Type    : MYSQL
Target Server Version : 80026
File Encoding         : 65001

Date: 2021-11-04 14:48:14
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
-- Table structure for biu_hole_note_labels
-- ----------------------------
DROP TABLE IF EXISTS `biu_hole_note_labels`;
CREATE TABLE `biu_hole_note_labels` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `note_id` bigint NOT NULL DEFAULT '0' COMMENT '信ID',
  `label_id` bigint NOT NULL DEFAULT '0' COMMENT '标签ID',
  `is_recommend` tinyint NOT NULL DEFAULT '0' COMMENT '是否推荐 0-否 1-是',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='树洞用户标签';

-- ----------------------------
-- Table structure for biu_hole_note_moods
-- ----------------------------
DROP TABLE IF EXISTS `biu_hole_note_moods`;
CREATE TABLE `biu_hole_note_moods` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `note_id` bigint NOT NULL DEFAULT '0' COMMENT '信ID',
  `mood_id` bigint NOT NULL DEFAULT '0' COMMENT '心情ID',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='树洞信心情';

-- ----------------------------
-- Table structure for biu_hole_notes
-- ----------------------------
DROP TABLE IF EXISTS `biu_hole_notes`;
CREATE TABLE `biu_hole_notes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `content` text COLLATE utf8mb4_general_ci COMMENT '内容',
  `is_private` tinyint NOT NULL DEFAULT '0' COMMENT '是否私有 0-否 1-是',
  `nick_show` tinyint NOT NULL DEFAULT '0' COMMENT '是否匿名显示 0-否 1-是',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='树洞信';

-- ----------------------------
-- Table structure for biu_interests
-- ----------------------------
DROP TABLE IF EXISTS `biu_interests`;
CREATE TABLE `biu_interests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '兴趣爱好标签',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='兴趣爱好';

-- ----------------------------
-- Table structure for biu_labels
-- ----------------------------
DROP TABLE IF EXISTS `biu_labels`;
CREATE TABLE `biu_labels` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `tag` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标签',
  `is_recommend` tinyint NOT NULL DEFAULT '0' COMMENT '是否推荐 0-否 1-是',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='标签';

-- ----------------------------
-- Table structure for biu_messages
-- ----------------------------
DROP TABLE IF EXISTS `biu_messages`;
CREATE TABLE `biu_messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dest_id` bigint NOT NULL DEFAULT '0' COMMENT '目标接收用户ID',
  `source_id` bigint NOT NULL DEFAULT '0' COMMENT '来源用户ID',
  `message_type` int NOT NULL DEFAULT '0' COMMENT '消息类型 1-...',
  `relate_id` bigint NOT NULL DEFAULT '0' COMMENT '关联记录',
  `title` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `content` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '内容',
  `read_status` tinyint NOT NULL DEFAULT '0' COMMENT '阅读状态 0-未读 1-已读',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='消息通知';

-- ----------------------------
-- Table structure for biu_moods
-- ----------------------------
DROP TABLE IF EXISTS `biu_moods`;
CREATE TABLE `biu_moods` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tag` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '心情文字',
  `emoj` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '表情',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='树洞心情';

-- ----------------------------
-- Table structure for biu_user_collects
-- ----------------------------
DROP TABLE IF EXISTS `biu_user_collects`;
CREATE TABLE `biu_user_collects` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `relate_id` bigint NOT NULL DEFAULT '0' COMMENT '关注用户ID',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户关注';

-- ----------------------------
-- Table structure for biu_user_communicates
-- ----------------------------
DROP TABLE IF EXISTS `biu_user_communicates`;
CREATE TABLE `biu_user_communicates` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `use_type` int NOT NULL DEFAULT '0' COMMENT '使用类别 1-自己 2-匹配笔友',
  `com_method` tinyint NOT NULL DEFAULT '0' COMMENT '通信方式 1-信件 2-E-mail',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户通讯方式';

-- ----------------------------
-- Table structure for biu_user_images
-- ----------------------------
DROP TABLE IF EXISTS `biu_user_images`;
CREATE TABLE `biu_user_images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `use_type` int NOT NULL DEFAULT '0' COMMENT '使用类别 1-用户简介 2-树洞信 3-树洞信评论',
  `file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件地址',
  `sort_index` int NOT NULL DEFAULT '0' COMMENT '资源排序',
  `relate_id` bigint NOT NULL DEFAULT '0' COMMENT '资源关联记录',
  `hash_code` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文件hash',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户文件资源';

-- ----------------------------
-- Table structure for biu_user_interests
-- ----------------------------
DROP TABLE IF EXISTS `biu_user_interests`;
CREATE TABLE `biu_user_interests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `use_type` int NOT NULL DEFAULT '0' COMMENT '使用类别 1-自己 2-匹配笔友',
  `interest_id` bigint NOT NULL DEFAULT '0' COMMENT '兴趣爱好ID',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户兴趣爱好';

-- ----------------------------
-- Table structure for biu_users
-- ----------------------------
DROP TABLE IF EXISTS `biu_users`;
CREATE TABLE `biu_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `nick` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `image` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `pen_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '笔名',
  `openid` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `unionid` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `sex` tinyint NOT NULL DEFAULT '0' COMMENT '性别 1-男 2-女',
  `birthday_year` int NOT NULL DEFAULT '0' COMMENT '出生年月',
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
