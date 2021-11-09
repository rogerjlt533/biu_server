/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80026
Source Host           : localhost:3306
Source Database       : biu

Target Server Type    : MYSQL
Target Server Version : 80026
File Encoding         : 65001

Date: 2021-11-09 15:05:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for biu_areas
-- ----------------------------
DROP TABLE IF EXISTS `biu_areas`;
CREATE TABLE `biu_areas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `area_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '地区名称',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '地区code',
  `area_type` tinyint NOT NULL DEFAULT '0' COMMENT '类型 1-省 2-市 3-区 4-街道',
  `parent_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '父级地区编号',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of biu_areas
-- ----------------------------

-- ----------------------------
-- Table structure for biu_hole_note_comments
-- ----------------------------
DROP TABLE IF EXISTS `biu_hole_note_comments`;
CREATE TABLE `biu_hole_note_comments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `note_id` bigint NOT NULL DEFAULT '0' COMMENT '信ID',
  `comment_userid` bigint NOT NULL DEFAULT '0' COMMENT '被评论的用户ID',
  `comment_id` bigint NOT NULL DEFAULT '0' COMMENT '被评论的评论ID',
  `content` varchar(255) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户评论',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='树洞评论';

-- ----------------------------
-- Records of biu_hole_note_comments
-- ----------------------------
INSERT INTO `biu_hole_note_comments` VALUES ('1', '2', '1', '1', '0', '2222', '2021-11-08 10:51:20', '2021-11-08 10:51:22', null);
INSERT INTO `biu_hole_note_comments` VALUES ('2', '1', '1', '2', '1', '333', '2021-11-08 10:51:43', '2021-11-08 10:51:46', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='树洞用户标签';

-- ----------------------------
-- Records of biu_hole_note_labels
-- ----------------------------
INSERT INTO `biu_hole_note_labels` VALUES ('1', '1', '1', '1', '0', '2021-11-08 11:03:01', '2021-11-08 11:03:03', null);
INSERT INTO `biu_hole_note_labels` VALUES ('2', '1', '1', '2', '0', '2021-11-08 11:03:11', '2021-11-08 11:04:31', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='树洞信心情';

-- ----------------------------
-- Records of biu_hole_note_moods
-- ----------------------------
INSERT INTO `biu_hole_note_moods` VALUES ('1', '1', '1', '1', '2021-11-08 11:05:13', '2021-11-08 11:05:15', null);
INSERT INTO `biu_hole_note_moods` VALUES ('2', '1', '1', '2', '2021-11-08 11:05:23', '2021-11-08 11:05:26', null);

-- ----------------------------
-- Table structure for biu_hole_notes
-- ----------------------------
DROP TABLE IF EXISTS `biu_hole_notes`;
CREATE TABLE `biu_hole_notes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '内容',
  `is_private` tinyint NOT NULL DEFAULT '0' COMMENT '是否私有 0-否 1-是',
  `nick_show` tinyint NOT NULL DEFAULT '0' COMMENT '是否匿名显示 0-否 1-是',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='树洞信';

-- ----------------------------
-- Records of biu_hole_notes
-- ----------------------------
INSERT INTO `biu_hole_notes` VALUES ('1', '1', '111', '0', '0', '2021-11-08 10:49:55', '2021-11-08 10:49:57', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='兴趣爱好';

-- ----------------------------
-- Records of biu_interests
-- ----------------------------
INSERT INTO `biu_interests` VALUES ('1', '看书', '2021-11-05 15:30:43', '2021-11-05 15:42:18', '2021-11-05 15:42:16');
INSERT INTO `biu_interests` VALUES ('2', '睡觉', '2021-11-05 15:30:56', '2021-11-05 15:42:23', '2021-11-05 15:42:21');

-- ----------------------------
-- Table structure for biu_labels
-- ----------------------------
DROP TABLE IF EXISTS `biu_labels`;
CREATE TABLE `biu_labels` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `tag` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标签',
  `is_recommend` tinyint NOT NULL DEFAULT '0' COMMENT '是否推荐 0-否 1-是',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='标签';

-- ----------------------------
-- Records of biu_labels
-- ----------------------------
INSERT INTO `biu_labels` VALUES ('1', '1', '111', '0', '2021-11-08 11:02:30', '2021-11-08 11:02:33', null);
INSERT INTO `biu_labels` VALUES ('2', '1', '222', '0', '2021-11-08 11:02:43', '2021-11-08 11:02:45', null);

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
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '内容',
  `read_status` tinyint NOT NULL DEFAULT '0' COMMENT '阅读状态 0-未读 1-已读',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='消息通知';

-- ----------------------------
-- Records of biu_messages
-- ----------------------------

-- ----------------------------
-- Table structure for biu_moods
-- ----------------------------
DROP TABLE IF EXISTS `biu_moods`;
CREATE TABLE `biu_moods` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tag` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '心情文字',
  `emoj` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '表情',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='树洞心情';

-- ----------------------------
-- Records of biu_moods
-- ----------------------------
INSERT INTO `biu_moods` VALUES ('1', '111', '111', '2021-11-08 11:04:48', '2021-11-08 11:04:51', null);
INSERT INTO `biu_moods` VALUES ('2', '222', '222', '2021-11-08 11:04:57', '2021-11-08 11:05:00', null);

-- ----------------------------
-- Table structure for biu_user_collects
-- ----------------------------
DROP TABLE IF EXISTS `biu_user_collects`;
CREATE TABLE `biu_user_collects` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `relate_id` bigint NOT NULL DEFAULT '0' COMMENT '被收藏用户ID',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户收藏';

-- ----------------------------
-- Records of biu_user_collects
-- ----------------------------
INSERT INTO `biu_user_collects` VALUES ('8', '1', '2', '2021-11-05 13:11:03', '2021-11-05 13:11:14', null);

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
-- Records of biu_user_communicates
-- ----------------------------

-- ----------------------------
-- Table structure for biu_user_favors
-- ----------------------------
DROP TABLE IF EXISTS `biu_user_favors`;
CREATE TABLE `biu_user_favors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `relate_id` bigint NOT NULL DEFAULT '0' COMMENT '被点赞的用户ID',
  `note_id` bigint NOT NULL DEFAULT '0' COMMENT '信ID',
  `comment_id` bigint NOT NULL DEFAULT '0' COMMENT '评论ID',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户点赞';

-- ----------------------------
-- Records of biu_user_favors
-- ----------------------------
INSERT INTO `biu_user_favors` VALUES ('1', '1', '1', '1', '0', '2021-11-08 10:50:47', '2021-11-08 10:50:49', null);
INSERT INTO `biu_user_favors` VALUES ('2', '2', '1', '1', '0', '2021-11-08 10:52:52', '2021-11-08 10:53:56', null);

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
-- Records of biu_user_images
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户兴趣爱好';

-- ----------------------------
-- Records of biu_user_interests
-- ----------------------------
INSERT INTO `biu_user_interests` VALUES ('1', '1', '1', '1', '2021-11-05 15:31:15', '2021-11-05 16:01:56', null);
INSERT INTO `biu_user_interests` VALUES ('2', '1', '1', '2', '2021-11-05 15:31:36', '2021-11-05 16:01:59', null);

-- ----------------------------
-- Table structure for biu_user_reports
-- ----------------------------
DROP TABLE IF EXISTS `biu_user_reports`;
CREATE TABLE `biu_user_reports` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `relate_id` bigint NOT NULL DEFAULT '0' COMMENT '被举报用户ID',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户举报';

-- ----------------------------
-- Records of biu_user_reports
-- ----------------------------

-- ----------------------------
-- Table structure for biu_user_sexes
-- ----------------------------
DROP TABLE IF EXISTS `biu_user_sexes`;
CREATE TABLE `biu_user_sexes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户ID',
  `sex` int NOT NULL DEFAULT '0' COMMENT '性别 1-男 2-女',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户搜索性别';

-- ----------------------------
-- Records of biu_user_sexes
-- ----------------------------

-- ----------------------------
-- Table structure for biu_users
-- ----------------------------
DROP TABLE IF EXISTS `biu_users`;
CREATE TABLE `biu_users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `nick` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `pen_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '笔名',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `unionid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `sex` tinyint NOT NULL DEFAULT '0' COMMENT '性别 1-男 2-女',
  `match_start_age` tinyint NOT NULL DEFAULT '0' COMMENT '匹配起始年龄(周岁)',
  `match_end_age` tinyint NOT NULL DEFAULT '0' COMMENT '匹配终止年龄(周岁)',
  `birthday_year` int NOT NULL DEFAULT '0' COMMENT '出生年月',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '联系邮箱',
  `province` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '所在省份',
  `city` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '所在城市',
  `country` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '所在区县',
  `street` varchar(20) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '所在街道',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '具体地址',
  `zipcode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '邮编',
  `introduce` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户简介',
  `last_ip` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '最后登录IP',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `use_status` tinyint NOT NULL DEFAULT '0' COMMENT '使用状态 1-正常 0-禁用',
  `comment_status` tinyint NOT NULL DEFAULT '0' COMMENT '树洞评论开关状态 1-开启 0-关闭',
  `search_status` tinyint NOT NULL DEFAULT '0' COMMENT '寻友开关状态 1-开启 0-关闭',
  `anonymous` tinyint NOT NULL DEFAULT '0' COMMENT '匿名状态 1-开启 0-关闭',
  `last_login` timestamp NULL DEFAULT NULL,
  `sort_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of biu_users
-- ----------------------------
INSERT INTO `biu_users` VALUES ('1', '163540553661168', '123nick', 'image1234', '', '123', '123uuu', '0', '0', '0', '1995', null, '', '', '', '', '', '', '', '', '', '192.168.0.139', '', '0', '0', '0', '0', '2021-10-28 15:18:56', '2021-11-09 13:34:56', '2021-10-28 15:18:57', '2021-11-09 13:34:56', null);
INSERT INTO `biu_users` VALUES ('2', '163540553661169', '1234nick', 'image12345', '', '1234', '1234uuu', '0', '0', '0', '0', null, '', '', '', '', '', '', '', '', '', '192.168.0.139', '', '0', '0', '0', '0', '2021-10-28 15:18:56', '2021-11-09 13:34:56', '2021-10-28 15:18:57', '2021-11-09 13:34:56', null);

-- ----------------------------
-- View structure for biu_hole_note_views
-- ----------------------------
DROP VIEW IF EXISTS `biu_hole_note_views`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `biu_hole_note_views` AS select `hn`.`id` AS `id`,`hn`.`user_id` AS `user_id`,`hn`.`content` AS `content`,`hn`.`is_private` AS `is_private`,`hn`.`nick_show` AS `nick_show`,`hn`.`created_at` AS `created_at`,`hn`.`updated_at` AS `updated_at`,`hn`.`deleted_at` AS `deleted_at`,(select group_concat(distinct '\'',`hnl`.`label_id`,'\'' separator ',') from `biu_hole_note_labels` `hnl` where ((`hnl`.`note_id` = `hn`.`id`) and (`hnl`.`deleted_at` is null))) AS `labels`,(select group_concat(distinct '\'',`hnm`.`mood_id`,'\'' separator ',') from `biu_hole_note_moods` `hnm` where ((`hnm`.`note_id` = `hn`.`id`) and (`hnm`.`deleted_at` is null))) AS `moods`,(select count(0) from `biu_hole_note_comments` `hnc` where ((`hnc`.`note_id` = `hn`.`id`) and (`hnc`.`deleted_at` is null))) AS `comment_num`,(select count(0) from `biu_user_favors` `uf` where ((`uf`.`note_id` = `hn`.`id`) and (`uf`.`deleted_at` is null))) AS `favor_num` from `biu_hole_notes` `hn` where (`hn`.`deleted_at` is null) ;

-- ----------------------------
-- View structure for biu_user_views
-- ----------------------------
DROP VIEW IF EXISTS `biu_user_views`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `biu_user_views` AS select `u`.`id` AS `id`,`u`.`username` AS `username`,`u`.`nick` AS `nick`,`u`.`image` AS `image`,`u`.`pen_name` AS `pen_name`,`u`.`openid` AS `openid`,`u`.`unionid` AS `unionid`,`u`.`sex` AS `sex`,`u`.`match_start_age` AS `match_start_age`,`u`.`match_end_age` AS `match_end_age`,`u`.`birthday_year` AS `birthday_year`,if(`u`.`birthday_year`,(date_format(now(),'%Y') - `u`.`birthday_year`),0) AS `age`,`u`.`birthday` AS `birthday`,`u`.`phone` AS `phone`,`u`.`email` AS `email`,`u`.`province` AS `province`,`u`.`city` AS `city`,`u`.`country` AS `country`,`u`.`street` AS `street`,`u`.`address` AS `address`,`u`.`zipcode` AS `zipcode`,`u`.`introduce` AS `introduce`,`u`.`last_ip` AS `last_ip`,`u`.`remark` AS `remark`,`u`.`use_status` AS `use_status`,`u`.`comment_status` AS `comment_status`,`u`.`search_status` AS `search_status`,`u`.`anonymous` AS `anonymous`,(select count(distinct `ucl`.`user_id`) from `biu_user_collects` `ucl` where ((`u`.`id` = `ucl`.`relate_id`) and (`ucl`.`deleted_at` is null))) AS `collect_num`,`u`.`last_login` AS `last_login`,`u`.`sort_time` AS `sort_time`,`u`.`created_at` AS `created_at`,`u`.`updated_at` AS `updated_at`,`u`.`deleted_at` AS `deleted_at`,(select group_concat(distinct '\'',`ui`.`interest_id`,'\'' separator ',') from `biu_user_interests` `ui` where ((`u`.`id` = `ui`.`user_id`) and (`ui`.`use_type` = 1) and (`ui`.`deleted_at` is null))) AS `self_interest`,(select group_concat(distinct '\'',`ui`.`interest_id`,'\'' separator ',') from `biu_user_interests` `ui` where ((`u`.`id` = `ui`.`user_id`) and (`ui`.`use_type` = 2) and (`ui`.`deleted_at` is null))) AS `search_interest`,(select group_concat(distinct '\'',`uc`.`com_method`,'\'' separator ',') from `biu_user_communicates` `uc` where ((`u`.`id` = `uc`.`user_id`) and (`uc`.`use_type` = 1) and (`uc`.`deleted_at` is null))) AS `self_communicate`,(select group_concat(distinct '\'',`uc`.`com_method`,'\'' separator ',') from `biu_user_communicates` `uc` where ((`u`.`id` = `uc`.`user_id`) and (`uc`.`use_type` = 2) and (`uc`.`deleted_at` is null))) AS `search_communicate`,(select group_concat(distinct '\'',`us`.`sex`,'\'' separator ',') from `biu_user_sexes` `us` where ((`u`.`id` = `us`.`user_id`) and (`us`.`deleted_at` is null))) AS `search_sex` from `biu_users` `u` where (`u`.`deleted_at` is null) ;
