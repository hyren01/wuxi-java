/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : metadata_management

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-08-16 16:19:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_meta_model
-- ----------------------------
DROP TABLE IF EXISTS `tb_meta_model`;
CREATE TABLE `tb_meta_model` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '模型ID,采用uuid',
  `code` varchar(128) NOT NULL DEFAULT '' COMMENT '模型编码',
  `en_name` varchar(128) DEFAULT '' COMMENT '英文名称',
  `name` varchar(128) DEFAULT '' COMMENT '模型中文名称',
  `type` int(4) DEFAULT '0' COMMENT '模型类型，分为普通模型和组合模型,0表示普通模型，1表示组合模型',
  `create_time` datetime DEFAULT NULL,
  `create_person` varchar(256) DEFAULT '' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_person` varchar(32) DEFAULT '' COMMENT '更新人',
  `description` varchar(2048) DEFAULT '' COMMENT '模型描述',
  `version` varchar(32) DEFAULT '' COMMENT '版本号，g格式20180501_001',
  `register_status` varchar(32) DEFAULT '未注册' COMMENT '注册状态',
  `category_id` varchar(32) NOT NULL DEFAULT '' COMMENT '挂载的目录ID',
  `bmId` varchar(128) DEFAULT NULL,
  `fullbmId` varchar(2048) DEFAULT NULL,
  `borned` varchar(32) DEFAULT '未落地' COMMENT '是否落地',
  `born_count` int(11) DEFAULT '0' COMMENT '落地次数',
  `extention` varchar(2048) DEFAULT NULL,
  `std_source` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_schema_code` (`code`),
  KEY `idx_model_category` (`category_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_meta_model
-- ----------------------------
INSERT INTO `tb_meta_model` VALUES ('02423be887af4079ac8123b97042b9ef', 'tra', 'train', '火车', '0', '2019-08-10 08:22:19', 'metaAdmin', '2019-08-10 09:50:56', 'metaAdmin', '', '', '未注册', '', 'train', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('0df4ca19598fc459777f0d016611c205', 'test18052105', 'test18052105', 'test18052105', '0', '2018-05-21 20:12:51', '1', '2018-11-12 10:52:54', '', 'test18052105', '20171215_001', '审批中', '', null, null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('0df4ca19598fc459777f0d016611c206', 'test18052106', 'test18052106', 'test18052106', '0', '2018-05-21 20:13:21', '1', '2019-05-22 11:18:21', 'admin', 'test18052106', '20190423_003', '审批中', '749aa4671b474790a3aea5c13388b077', 'tdfxt', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('0ebf09434bdc43dfb111d276158de88c', 'b20190515', '', '', '21', '2019-05-15 09:13:47', 'admin', null, '', '', '20190515_001', '已注册', '471300', 'b20190515', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('1df4ca19598fc459777f0d016611c208', '1a680298188c42bea83997b9cd9d02', 'tb180428022', 'ceshi 3333', '0', '2018-04-28 08:54:12', 'admin', '2019-05-14 09:32:26', 'admin', 'tb18042802', '20180608_002', '审批中', '', '1234561', null, '已落地', '4', null, null);
INSERT INTO `tb_meta_model` VALUES ('1f421922adc44098ad727993fd81813c', 'test_20190319', 'test_20190319', 'test_20190319', '0', '2019-03-19 20:27:14', 'admin', '2019-04-09 16:04:10', '', '', '', '审批中', '', 'test_20190319', null, '未落地', '0', '', null);
INSERT INTO `tb_meta_model` VALUES ('20cf58ad93be43eeab634c26690561b9', 'test06151512', 'test06151512', 'test06151512', '0', '2018-06-15 15:12:34', 'admin', '2018-11-12 10:52:54', 'admin', 'test06151512', '', '审批中', '', '346tr', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('25909753b79f4c5eb9ebf98577dc9baf', 'b20190515_002', '', '', '21', '2019-05-15 14:37:19', 'admin', '2019-05-15 17:00:45', '', '', '20190515_001', '已注册', '783fb9a431ed49d89eb5fb226202ecfe', 'b20190515_002', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('2839fb432b2f4a73b2541d492d669de6', 'a20190516_001', '', '', '0', '2019-05-16 15:07:50', 'admin', null, '', '', '20190516_001', '已注册', '749aa4671b474790a3aea5c13388b077', '20190516_001', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('2b6735ee810a408d86d6de7fa8f7421c', 'c22', 'c22', 'c22', '21', '2019-05-17 10:05:29', 'admin', '2019-06-20 10:33:29', '', '', '', '未注册', '', 'c22', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('2d3236eeb4324cbea4d278db2e72b114', 'test06151432', 'test06151428', 'test06151428', '0', '2018-06-15 14:32:41', 'admin', '2019-05-05 14:56:52', '', 'test06151428', '20190428_001', '已注册', '783fb9a431ed49d89eb5fb226202ecfe', 'test06151432', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('3104473dae2641368fed0fe96595a7ea', 'mysql_test0614_01', 'mysql_test0614', 'mysql_test0614', '0', '2018-06-23 09:55:02', 'admin', '2019-05-05 14:56:52', '', 'mysql_test0614', '20190420_001', '已注册', '783fb9a431ed49d89eb5fb226202ecfe', 'mysql_test0614', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('43dd8b6641cc4151a747240a9109446c', 'svdfga', 'asdfwcasdf', 'sdfawefxz', '0', '2018-06-15 11:31:05', 'admin', '2019-04-28 11:11:07', '', 'asdvascvasfe', '20190505_001', '已注册', '891673', 'asdfwacasdf', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('45056b9aa2444778a7b5a4bbb32a4082', 'log123', 'log', 'log', '0', '2018-06-28 16:35:40', 'admin', '2018-11-12 10:52:54', '', '', '', '审批中', '465136', '123', null, '未落地', '0', '', null);
INSERT INTO `tb_meta_model` VALUES ('49daf37a51984f519fde3c4cca5e5579', 'test20190505', '', '', '0', '2019-05-05 10:50:12', 'admin', null, '', '', '20190505_001', '已注册', '783fb9a431ed49d89eb5fb226202ecfe', 'test20190505', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('556cf69f50ed4d7ba1886a2d7600ad6a', 'test123', 'test123', 'test123', '0', '2018-06-20 10:06:45', 'admin', '2018-11-12 10:52:54', '', '', '', '审批中', '', 'test', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('56247872fde64bcf88b0a389a36a5dd1', 'qb_jsbry_whl_copy1', 'qb_jsbry_whl_copy1', '精神病', '0', '2018-06-24 16:45:13', 'admin', '2019-03-26 16:00:37', 'admin', '精神病', '20180624_001', '审批中', '08dd50b1bcd742249a60b242a07f8bb2', 'qb_jsbry_whl_copy1', null, '已落地', '2', null, null);
INSERT INTO `tb_meta_model` VALUES ('56c997f2a0ac4dd0a6f2b0675f5c6ad3', 'Gundam', 'Gundam', '高达驾驶者', '0', '2019-05-16 15:41:31', 'admin', null, '', '', '20190516_001', '已注册', '471300', '1345', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('595dcaaa139d482fa1d81418be592672', 'test123456', 'test', 'test', '0', '2018-06-20 15:16:27', 'admin', '2018-11-12 10:52:54', 'admin', 'test', '20190420_001', '已注册', 'e581fb4621994771a70e3796b2154bbe', 'test', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('684e6655a8654774afe19256b40b0631', 'test06201515', 'test06201515', 'test06201515', '0', '2018-06-20 15:16:00', 'admin', '2018-11-12 10:52:54', 'admin', 'test06201515', '20180620_001', '审批中', '749aa4671b474790a3aea5c13388b077', 'test06201515', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('69307b09eddb48a78dd85832b56757e8', 'test_20190429_1', 'test_20190429_1', 'test_20190429_1', '0', '2019-04-29 08:51:01', 'admin', '2019-05-05 08:53:41', '', 'test_20190429_1', '20190505_001', '已注册', '6b4f6613e11c41e5820fe8ef7748a3c2', 'test_20190429_1', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('6dc84248d06f4885aef30064527d3ebb', 'log06290910', 'log', 'log', '0', '2018-06-29 09:11:30', 'admin', null, '', '', '20190420_001', '已注册', '749aa4671b474790a3aea5c13388b077', 'log06290910', '', '未落地', '0', '{\'tableId\':\'WX521832\',\'location\':\'WX\'}', '');
INSERT INTO `tb_meta_model` VALUES ('72f671bf41e14a259fb1a9c813d5fe3c', 'bbb', '', '', '31', '2019-05-02 15:03:29', 'admin', null, '', '', '20190502_001', '已注册', 'e581fb4621994771a70e3796b2154bbe', 'bbb', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('75d423e64f4f42b68b80cd6ab59b05be', 'aa', '', '', '21', '2019-05-02 11:53:20', 'admin', null, '', '', '', '未注册', '', 'aa', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('8b3bef3f1b0f4bc0a771aaa4d7965ab9', 'test05301953', 'test05301953', 'test05301953', '0', '2018-05-30 19:53:33', 'admin', '2019-04-20 10:41:56', 'admin', 'test05301953', '20180609_001', '已注册', '891673', 'test05301953', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('90873f243e8e4130818d64863eed1193', 'north', 'north', '北境', '0', '2019-05-16 15:01:32', 'admin', '2019-05-16 15:08:26', '', '', '20190516_001', '已注册', '471300', '123', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('9313ab520476491eb0bf24580dd0a8ba', 'test06112032', 'test06112032', 'test06112032', '0', '2018-06-11 20:32:30', 'admin', '2019-04-26 09:44:31', 'admin', 'test06112032', '20180615_001', '已注册', '2ff068d31f1b4319a68ad8babb5aaf3e', 'test06112032', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('995b758f91c0490aa7f677f54e6ec020', 'mysql_test0614', 'mysql_test0614', 'mysql_test0614', '0', '2018-06-23 09:44:04', 'admin', '2019-04-20 10:42:46', 'admin', 'mysql_test0614', '20180623_001', '已注册', '749aa4671b474790a3aea5c13388b077', 'mysql_test0614', null, '已落地', '1', null, null);
INSERT INTO `tb_meta_model` VALUES ('9a24725b80ee4ae399a7a564f33bceea', 'test1946', 'test1946', 'test1946', '0', '2018-06-07 19:46:40', 'admin', '2019-04-26 09:44:31', 'admin', 'test1946', '20180609_011', '已注册', '8830123', 'test1946', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('9bb9a038beee4d00aa14694ea5ee335b', 'mysql_test0614_04', 'mysql_test0614', 'mysql_test0614', '0', '2018-06-23 10:49:52', 'admin', '2018-11-12 10:52:54', '', 'mysql_test0614_04', '20190415_001', '已注册', 'e581fb4621994771a70e3796b2154bbe', 'mysql_test0614_04', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('9cb3fbe824264cc5a9cd3964235e2cf6', 'test12', 'test12', 'test12', '0', '2018-06-19 17:07:10', 'admin', '2018-11-12 10:52:54', '', 'test1', '', '审批中', '', 'test12', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('a12ee410708c436abb1fa5f5521d4d0e', 'test20190403_002', 'test20190403_002', '', '0', '2019-04-03 09:24:11', 'admin', null, '', '', '20190415_001', '已注册', '783fb9a431ed49d89eb5fb226202ecfe', '', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('a3928cbb8bb04b6789f5437aa05764ff', 'test06151450', 'test06151428', 'test06151428', '0', '2018-06-15 14:53:04', 'admin', '2019-04-15 17:35:59', 'admin', 'test06151428', '20190415_001', '已注册', '749aa4671b474790a3aea5c13388b077', 'test06151450', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('a6b8ae393a1b4d1386227d048198c1bc', 'mysql_test0614_2', 'mysql_test0614', 'mysql_test0614', '0', '2018-06-23 10:11:24', 'admin', '2018-11-12 10:52:54', '', 'mysql_test0614_2', '20190415_001', '已注册', '783fb9a431ed49d89eb5fb226202ecfe', 'mysql_test0614_2', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('ab544e8b09e74a46a66b32925e2712b9', 'mysql_test0614_03', 'mysql_test0614', 'mysql_test0614', '0', '2018-06-23 10:17:57', 'admin', '2018-11-12 10:52:54', 'admin', '', '20180623_001', '已注册', '749aa4671b474790a3aea5c13388b077', 'mysql_test0614_03', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('abc07b2fd0784574be4268ddc9441210', 'log06290907', 'log', 'log', '0', '2018-06-29 09:09:23', 'admin', '2018-11-12 10:52:54', '', '', '', '审批中', '749aa4671b474790a3aea5c13388b077', 'log06290907', null, '未落地', '0', '{\'tableId\':\'WX521832\',\'location\':\'WX\'}', null);
INSERT INTO `tb_meta_model` VALUES ('b0ece484662e49719d5eec5ab066b193', 'test1', 'test1', 'test1', '0', '2018-06-19 16:58:18', 'admin', '2018-11-12 10:52:54', '', 'test1', '', '审批中', '', 'test1', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('b5c7a75c46a54ef982f9ac584d675375', 'test06151151', 'test06151151', 'test06151151', '0', '2018-06-15 11:51:46', 'admin', '2019-04-15 17:40:08', 'admin', 'test06151151', '20190415_001', '已注册', '749aa4671b474790a3aea5c13388b077', 'test06151151', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('b8b0713f9f264e8cbbc546834464ce74', 'aaa', '', '', '21', '2019-05-15 09:09:54', 'admin', null, '', '', '', '未注册', '', 'aaaa', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('BJ1452653275', 'BLTEST121502', 'bltest121502', 'bltest121502', '0', '2019-05-15 10:43:35', 'admin', null, '', 'test', '', '审批中', '895240', '', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('c674197d79cf4df9b9b46c6fd7f12316', 'test1234567', 'test1234567', '', '0', '2019-04-25 20:43:14', 'admin', '2019-04-29 08:46:01', '', '', '20190429_001', '已注册', '891673', 'test1234567', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('c7bcf0825d8f4beba468deaa0424eabe', 'test06201107', 'test06201107', 'test06201107', '0', '2018-06-20 11:07:39', 'admin', '2018-11-12 10:52:54', '', '', '', '审批中', '', 'test', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('d2b47987d14741918d38a763620e0afa', 'test123456112233', 'test123456112233', 'test123456112233', '0', '2018-06-27 11:26:09', 'admin', '2018-11-12 10:52:54', 'admin', '', '20180627_002', '审批中', '6b4f6613e11c41e5820fe8ef7748a3c2', 'test123456', '', '未落地', '0', '', null);
INSERT INTO `tb_meta_model` VALUES ('d3bbc82d95924aeab8938350f036394b', ' QGERGSXDFGQERG', 'T Q', 'QERTQWRE', '0', '2018-06-15 11:42:37', 'admin', '2018-11-12 10:52:54', 'admin', '', '20180615_001', '已注册', '0', 'WEV RTVR HSG DF', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('de418e3ee56448c8bad8ed67808f565b', 'test20190403_008', 'test20190403_008', '', '0', '2019-04-03 09:46:52', 'admin', '2019-04-29 08:58:59', '', '', '', '未注册', '', 'test20190403_008', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('df27f8b5a3b848aaad4e051cc2c397e2', 'test06221533', 'test06221533', 'test06221533', '0', '2018-06-22 15:34:49', 'admin', '2018-11-12 10:52:54', 'admin', 'test06221533', '', '未注册', '', 'test06221533', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('e07c334819d947dcac06dcd58ef188ac', 'test0531', 'test', 'test', '0', '2018-05-31 19:52:45', 'admin', '2019-04-26 09:44:00', 'admin', 'test', '20180609_003', '已注册', 'a946dd9158004139a5191261be11778c', 'test', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('f0dd6b1d18c541e59f0e26d2c84928bf', 'test06111519', 'test06111519', 'test06111519', '0', '2018-06-11 15:20:17', 'admin', '2018-11-12 10:52:54', 'admin', 'test06111519', '20180615_001', '已注册', '6b4f6613e11c41e5820fe8ef7748a3c2', 'test06111519', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('f36ac59c9e564107aef51423bf07e6b8', 'test06271040', 'test06271040', 'test06271040', '0', '2018-06-27 10:40:28', 'admin', '2019-04-23 09:08:31', 'admin', '', '', '未注册', '', 'test06271040', null, '未落地', '0', '', null);
INSERT INTO `tb_meta_model` VALUES ('f4d3eaffbb1341a18e9175a14223663e', 'test06111139', 'test06111139', 'test06111139', '0', '2018-06-11 11:39:23', 'admin', '2018-11-12 10:52:54', 'admin', 'test06111139', '20180611_001', '已注册', '891673', 'test06111139', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('f5b25f4807e74f9e995f40588a3ba39a', 'test06211953', 'test06211953', 'test06211953', '0', '2018-06-21 19:54:35', 'admin', '2018-11-12 10:52:54', '', 'test06211953', '', '未注册', '', 'test06211953', null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('fb65adf0aa104cc194352e116b59f435', 'registerStatus', 'registerStatus', '', '0', '2019-04-03 09:33:47', 'admin', '2019-04-29 08:55:00', '', '', '', '未注册', '', 'registerStatus', '', '未落地', '0', '', '');
INSERT INTO `tb_meta_model` VALUES ('WX482587', 'gbase_0621', 'gbase_0621', 'gbase_0621', '0', '2018-06-20 16:58:00', 'wx_test', '2018-11-12 10:52:54', '', null, '', '已注册', '', null, null, '未落地', '0', null, null);
INSERT INTO `tb_meta_model` VALUES ('WX483665', 'emptyModel2edf', 'emptyModel2edf', 'emptyModel2edf', '0', '2018-06-21 19:48:00', 'wx_test', '2018-11-12 10:52:54', '', null, '', '已注册', '', null, null, '已落地', '1', null, null);
INSERT INTO `tb_meta_model` VALUES ('WX494119', 'mysql_0625', 'mysql_0625', 'mysql_0625', '0', '2018-06-24 15:21:00', 'wx_test', '2019-05-15 14:41:34', '', '哇哦', '', '已注册', '891673', null, null, '已落地', '1', null, null);

-- ----------------------------
-- Table structure for tb_meta_model_wld
-- ----------------------------
DROP TABLE IF EXISTS `tb_meta_model_wld`;
CREATE TABLE `tb_meta_model_wld` (
  `id` varchar(32) NOT NULL,
  `bmid` varchar(128) DEFAULT NULL,
  `born_count` int(11) NOT NULL,
  `borned` varchar(32) NOT NULL,
  `category_id` varchar(32) DEFAULT NULL,
  `code` varchar(128) NOT NULL,
  `create_person` varchar(128) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `description` varchar(2048) DEFAULT NULL,
  `en_name` varchar(128) DEFAULT NULL,
  `extention` varchar(11) DEFAULT NULL,
  `fullbmid` varchar(512) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `register_status` varchar(32) DEFAULT NULL,
  `std_source` varchar(256) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `update_person` varchar(128) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_meta_model_wld
-- ----------------------------

-- ----------------------------
-- Table structure for tb_meta_modelfield
-- ----------------------------
DROP TABLE IF EXISTS `tb_meta_modelfield`;
CREATE TABLE `tb_meta_modelfield` (
  `id` varchar(32) NOT NULL COMMENT '字段ID号（UUID）',
  `model_id` varchar(32) NOT NULL,
  `code` varchar(128) NOT NULL COMMENT '字段编码',
  `name` varchar(128) DEFAULT '' COMMENT '字段中文名称',
  `en_name` varchar(128) DEFAULT '' COMMENT '字段英文名称',
  `required` tinyint(1) DEFAULT '0' COMMENT '约束，是否必须',
  `is_unique` tinyint(1) DEFAULT '0' COMMENT '是否唯一',
  `default_value` varchar(1024) DEFAULT '' COMMENT '缺省值',
  `max_size` int(4) DEFAULT '256' COMMENT '最大长度',
  `precition` int(4) DEFAULT '0' COMMENT '字段精度，字段类型为单精度和双精度时有效',
  `scale` int(4) DEFAULT NULL,
  `type` varchar(128) DEFAULT 'STRING' COMMENT '数据类型',
  `is_primary` tinyint(1) DEFAULT '0' COMMENT '是否主键',
  `pxh` int(4) DEFAULT '128',
  `field_range` text COMMENT '域/值域',
  `defination` varchar(2048) DEFAULT NULL COMMENT '定义',
  `max_contains` int(4) DEFAULT '1' COMMENT '最大出现次数',
  `comments` text COMMENT '备注',
  `security` int(1) DEFAULT '1' COMMENT '密级',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_meta_modelfield
-- ----------------------------
INSERT INTO `tb_meta_modelfield` VALUES ('05128bccd2ef42219902f283be618126', 'WX482587', 'column_1', 'column_1', null, '0', '0', '', '10', '0', null, '整数', '0', '1', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('078db1cd08824e0e83018022da9646cb', 'de418e3ee56448c8bad8ed67808f565b', 'null', 'test20190403_008', 'test20190403_008', '0', '0', '', '0', '6', '5', '字符串', '0', '1', '0', 'null', '1', 'null', '0', '2019-04-03 09:46:52');
INSERT INTO `tb_meta_modelfield` VALUES ('07dfb09db16e415b96e32c409dd87f80', '56247872fde64bcf88b0a389a36a5dd1', 'gmsfhm', '公民身份号码', 'gmsfhm', '0', '0', '', '18', '0', null, '字符串', '0', '6', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('081367937dfc41fabd3b2300460ff6f9', '43dd8b6641cc4151a747240a9109446c', 'null1', 'null1', 'null1', '0', '0', '', '0', '6', '5', '字符串', '0', '2', '0', 'null1', '1', 'null1', '0', '2019-05-05 09:43:12');
INSERT INTO `tb_meta_modelfield` VALUES ('0bbaa8b808b74e5387ec65b671e699fc', 'WX483665', 'column_6', '测试字段下面', null, '0', '0', '', '10', '0', null, '双精度', '0', '10', null, null, '1', null, '1', '2018-11-12 15:25:14');
INSERT INTO `tb_meta_modelfield` VALUES ('0c3218c763b147788c730999a7898947', '995b758f91c0490aa7f677f54e6ec020', 'column_2', 'column_2', 'column_2', '0', '0', '', '12', '0', null, '字符串', '0', '2', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('0cd61176e5094232b66bcbb55b924b63', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_1', 'column_1', 'column_1', '0', '0', '', '11', '0', '0', '字符串', '0', '1', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('0cf250ed5f4c476d958d7eed8d3c0ca5', '02423be887af4079ac8123b97042b9ef', 'field1', 'field1', 'field1', '0', '0', '', '1', '0', '5', '字符串', '0', '1', '0', 'field1', '1', 'field1', '1', '2019-08-10 17:50:56');
INSERT INTO `tb_meta_modelfield` VALUES ('0de10daf18704d59adf965c7ed390018', 'd2b47987d14741918d38a763620e0afa', 'cccccc', 'cccccc', 'cccccc', '0', '0', '', '0', '0', null, '字符串', '0', '2', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('1016151a6c70446595c07e881ae91003', 'd2b47987d14741918d38a763620e0afa', 'test112233', 'test112233', 'test112233疼痛', '0', '0', '', '234', '0', null, '字符串', '0', '4', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('10734ef1461c441d8e5237efbe119ca0', '684e6655a8654774afe19256b40b0631', 'test0620151502', 'test0620151502', 'test0620151502', '0', '1', '', '12', '0', null, '字符串', '0', '2', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('10fd273d065b48078a3bce1126b45971', '02423be887af4079ac8123b97042b9ef', 'num', '生产数量', 'number', '0', '0', '', '4', '3', '5', '整数', '0', '2', '0', '该火车生产数量', '1', null, '1', '2019-08-10 17:50:56');
INSERT INTO `tb_meta_modelfield` VALUES ('10ffcdde950f4e86a3e571f8d50f071f', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_12', 'column_12', 'column_12', '0', '0', '', '0', '0', '0', '日期', '0', '14', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('1149dd0581c542a397e2c4ae7e948f10', '9313ab520476491eb0bf24580dd0a8ba', 'test061120323', 'test061120323', 'test061120323', '1', '1', '', '45', '0', null, '整数', '0', '3', '', '', '1', '', '2', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('11d77cd3eb204ad68235a449f6209547', '8b3bef3f1b0f4bc0a771aaa4d7965ab9', '放大放大时', '放大放大时', '', '1', '0', '', '12', '0', null, '浮点型', '0', '1', '', '', '1', '爱手工坊', '3', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('11e23604f4da46eb9b06f83bf9a8d9f7', '90873f243e8e4130818d64863eed1193', 'Brandon', '布兰登', 'Brandon', '0', '0', '', '8', '6', '5', '单精度', '0', '3', '', '三眼乌鸦', '1', '', '1', '2019-05-16 15:08:52');
INSERT INTO `tb_meta_modelfield` VALUES ('12fce05be0e144d4b400de29b095c411', 'ab544e8b09e74a46a66b32925e2712b9', 'column_3', 'column_3', 'column_3', '0', '0', '', '255', '0', null, '字符串', '0', '3', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('133f52cb55894c80996e856181a4157b', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_10', 'column_10', 'column_10', '0', '0', '', '11', '0', '0', '双精度', '0', '10', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('14161eec0c1349d8ab6cd27fe7f9c61d', 'b8b0713f9f264e8cbbc546834464ce74', 'sssjsx2', '实时数据属性2', null, '0', '0', '', '256', '6', '5', '日期', '0', '2', null, 'definition', '1', '333', '0', '2019-05-15 09:09:54');
INSERT INTO `tb_meta_modelfield` VALUES ('14b78c3e622042a89228db971b043b19', 'ab544e8b09e74a46a66b32925e2712b9', 'column_9', 'column_9', 'column_9', '0', '0', '', '12', '0', null, '双精度', '0', '9', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('15b7e67eae13416fb0977a84a666d701', '995b758f91c0490aa7f677f54e6ec020', 'column_5', 'column_5', 'column_5', '0', '0', '', '16777215', '0', null, '字符串', '0', '5', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('15bd4275d0954e24bf951b6af4f302f8', '2d3236eeb4324cbea4d278db2e72b114', 'test1', 'test1', 'test1', '1', '0', '', '12', '6', '5', '整数', '0', '1', '', '', '1', '', '1', '2019-04-28 19:37:28');
INSERT INTO `tb_meta_modelfield` VALUES ('1607a6d1d15d4ba398f686d0dac8040b', 'f4d3eaffbb1341a18e9175a14223663e', 'test2', 'test2', '', '0', '1', '', '12', '0', null, '整数', '0', '2', '', '', '1', '', '2', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('17f05573845041329197094e3a8e90c8', '9bb9a038beee4d00aa14694ea5ee335b', 'column_10', 'column_10', 'column_10', '0', '0', '', '11', '0', '0', '双精度', '0', '10', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('18f128f6053f44b887e157e1909f9f43', 'f4d3eaffbb1341a18e9175a14223663e', 'test1', 'test1', '', '1', '0', '', '12', '0', null, '浮点型', '0', '1', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('1a6eb683a0fc4caf9ec4a0874f7601eb', '69307b09eddb48a78dd85832b56757e8', 'fdsaf', 'fdsaf', 'fdsaf', '0', '0', '', '32', '6', '5', '整数', '0', '7', '1', '', '1', '', '1', '2019-05-05 08:53:41');
INSERT INTO `tb_meta_modelfield` VALUES ('1aea69648f5e4229b1b5d3391009a79a', 'ab544e8b09e74a46a66b32925e2712b9', 'column_14', 'column_14', 'column_14', '0', '0', '', '0', '0', null, '日期', '0', '14', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('1b2f3f33fd77423bb93c4bc1d5eb242e', '56247872fde64bcf88b0a389a36a5dd1', 'xm', '姓名', 'xm', '0', '0', '', '50', '0', null, '字符串', '0', '2', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('1cddf73bcec64985a499178d9c480cc7', '9bb9a038beee4d00aa14694ea5ee335b', 'column_8', 'column_8', 'column_8', '0', '0', '', '10', '0', '0', '整数', '0', '7', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('20327298de3b4af3be789a3e09d25c4b', '2b6735ee810a408d86d6de7fa8f7421c', 'sssjsx3', '实时数据属性3', 'sssjsx3', '0', '0', '', '256', '6', '5', '整数', '0', '3', '', 'definition', '1', '23123213', '1', '2019-06-20 10:33:29');
INSERT INTO `tb_meta_modelfield` VALUES ('20b92057ff0e4275a061b45810bd11ff', 'BJ1452653275', 'testfield', '测试字段', 'testfield', '0', '0', '123', '128', '6', '5', '字符串', '0', '1', '范围', '测试字段', '1', null, '1', '2019-05-15 10:43:35');
INSERT INTO `tb_meta_modelfield` VALUES ('241207e3a21342b49f4035561f4eda99', 'abc07b2fd0784574be4268ddc9441210', 'LOGIN_ID', 'LOGIN_ID', 'LOGIN_ID', '0', '0', '', '20', '0', null, '字符串', '0', '2', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('24b1215689bf49c3bb8458ddd27f690c', '75d423e64f4f42b68b80cd6ab59b05be', 'sssjmc', '实时数据名称', null, '0', '0', '', '256', '6', '5', '字符串', '0', '1', null, 'definition', '1', '111111', '1', '2019-05-02 11:53:20');
INSERT INTO `tb_meta_modelfield` VALUES ('24f5e7016c624c9d989e32dd24767b68', '3104473dae2641368fed0fe96595a7ea', 'column_8', 'column_8', 'column_8', '0', '0', '', '10', '0', '0', '整数', '0', '7', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('261676b5654e4429849e817e26f3868d', '3104473dae2641368fed0fe96595a7ea', 'column_4', 'column_4', 'column_4', '0', '0', '', '65535', '0', '0', '字符串', '0', '4', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('2632a66d260e4870990f169dc0221fa8', '3104473dae2641368fed0fe96595a7ea', 'column_14', 'column_14', 'column_14', '0', '0', '', '0', '0', '0', '日期', '0', '12', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('266aa3937c7349be96f42bd63b569b7f', '9bb9a038beee4d00aa14694ea5ee335b', 'column_2', 'column_2', 'column_2', '0', '0', '', '12', '0', '0', '字符串', '0', '1', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('26ab78869d0c4174985e85508b62af9d', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_7', 'column_7', 'column_7', '0', '0', '', '10', '0', '0', '整数', '0', '8', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('273f6305338b4c158e95de05117909dd', '3104473dae2641368fed0fe96595a7ea', 'column_10', 'column_10', 'column_10', '0', '0', '', '11', '0', '0', '双精度', '0', '10', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('27728f377c8a424f97ebeb8f8d53375e', '25909753b79f4c5eb9ebf98577dc9baf', 'sssjmc', '实时数据名称', '', '0', '0', '', '256', '6', '5', '字符串', '0', '1', '', 'definition', '1', 'ere', '1', '2019-05-15 14:37:30');
INSERT INTO `tb_meta_modelfield` VALUES ('28dc0dcb489f4920850989eb678f37a3', 'WX483665', 'column_10', 'column_10', null, '0', '0', '', '10', '0', null, '整数', '0', '6', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('2aa14ed20f4f4d4aa3963c542a5509fd', 'WX483665', 'column_4', 'column_4', null, '0', '0', '', '10', '0', null, '单精度', '0', '4', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('2c43e69915a34a4681eeed38bc09e88a', '72f671bf41e14a259fb1a9c813d5fe3c', 'yssjsx3', '原始数据属性3', '', '0', '0', '', '256', '6', '5', '整数', '0', '3', '', 'definition', '1', '33', '1', '2019-05-02 15:03:41');
INSERT INTO `tb_meta_modelfield` VALUES ('2daa47ba20da45c287750903554a0972', '995b758f91c0490aa7f677f54e6ec020', 'column_6', 'column_6', 'column_6', '0', '0', '', '0', '0', null, '字符串', '0', '6', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('30770d9a338840cd99b88f462b147aa7', 'df27f8b5a3b848aaad4e051cc2c397e2', 'test06221533', 'test06221533', 'test06221533', '0', '0', '', '0', '0', null, '字符串', '0', '1', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('30fc93195ca2404c948f050f07008b2a', 'e07c334819d947dcac06dcd58ef188ac', 'tesat', 'tesat', 'tesat', '0', '0', '', '12', '0', null, '字符串', '0', '4', '', '', '1', '', '5', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('310b2b250528491ebfa95b0e459813d4', '9a24725b80ee4ae399a7a564f33bceea', 'test1', 'test1', '', '1', '0', '', '10086', '0', null, '整数', '0', '1', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('33b2396561214882abf719b89de3ecc5', 'd2b47987d14741918d38a763620e0afa', 'stdtest', 'stdtest', 'stdtest', '0', '0', '', '34', '0', null, '单精度', '0', '6', '', '', '1', '', '2', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('3462e332fa0f4561ac67d0f8ac971ea6', '2b6735ee810a408d86d6de7fa8f7421c', 'sssjmc', '实时数据名称', 'sssjmc', '0', '0', '', '256', '6', '5', '字符串', '0', '1', '', 'definition', '1', '212312', '1', '2019-06-20 10:33:29');
INSERT INTO `tb_meta_modelfield` VALUES ('355542807b1b4182b4f5e3f7e9415cb0', '9bb9a038beee4d00aa14694ea5ee335b', 'column_13', 'column_13', 'column_13', '0', '0', '', '0', '0', '0', '日期', '0', '13', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('3614f84f234847dcb3fc47faaf4a9a28', 'c674197d79cf4df9b9b46c6fd7f12316', 'fdasf', 'null1f', 'null1f', '0', '0', '', '0', '6', '5', '字符串', '0', '2', '0', 'null1', '1', 'null1', '0', '2019-04-29 08:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('370081f8f9c94e4dbed71fbb0526cdb5', 'f5b25f4807e74f9e995f40588a3ba39a', 'test1', 'test1', 'test1', '0', '0', '', '12', '0', null, '单精度', '0', '1', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('373afcdd717f4309b1a316962f8dc101', '02423be887af4079ac8123b97042b9ef', 'dat', '定型时间', 'date', '0', '0', '', '0', '6', '5', '日期', '0', '3', '0', '该火车定型时间', '1', null, '1', '2019-08-10 17:50:56');
INSERT INTO `tb_meta_modelfield` VALUES ('37d8cae3c0f74e9d928427c85fad7a8f', '092f27cf5d4da3fa17eb1189bc6defa8', 'null', 'null', 'null', '0', '0', '', '0', '6', '5', '日期', '0', '1', '0', 'null', '1', 'null', '2', '2019-04-03 16:48:57');
INSERT INTO `tb_meta_modelfield` VALUES ('38c7470933af48d88b389080def5e743', '9bb9a038beee4d00aa14694ea5ee335b', 'column_9', 'column_9', 'column_9', '0', '0', '', '12', '0', '0', '双精度', '0', '11', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('397975d0313347939d2db25df7dde09d', 'f36ac59c9e564107aef51423bf07e6b8', 'test1', 'test1', 'test1', '0', '0', '', '12', '0', null, '整数', '0', '1', '', '', '2', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('3a826e1475254d9eb2e6f994a8c25c4b', '684e6655a8654774afe19256b40b0631', 'test0620151503', 'test0620151503', 'test0620151503', '0', '0', '', '13', '0', null, '字符串', '0', '3', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('3bcc8afe16bb433aa856d31ac1f09d22', '9bb9a038beee4d00aa14694ea5ee335b', 'column_6', 'column_6', 'column_6', '0', '0', '', '-1', '0', '0', '字符串', '0', '6', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('3c518a9c1f0f4ac08dea0d7c146d0d67', '56247872fde64bcf88b0a389a36a5dd1', 'jsbmc', '精神病名称', 'jsbmc', '0', '0', '', '100', '0', null, '字符串', '0', '8', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('3ccec39e1d1e430b8f3adbf2ca5e0e8c', '3104473dae2641368fed0fe96595a7ea', 'column_6', 'column_6', 'column_6', '0', '0', '', '0', '0', '0', '字符串', '0', '6', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('3d7f20bf56a04727b1160bec97cffed9', '9bb9a038beee4d00aa14694ea5ee335b', 'column_12', 'column_12', 'column_12', '0', '0', '', '0', '0', '0', '日期', '0', '14', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('3f1310962bf14d79b0520644d45cea18', 'abc07b2fd0784574be4268ddc9441210', 'TENANT_ID', 'TENANT_ID', 'TENANT_ID', '0', '0', '', '20', '0', null, '字符串', '0', '3', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('3f2674f7743d40a4841dc32ad72acf0e', 'WX494119', 'column_5', 'column_5', null, '0', '0', '', '12', '0', null, '字符串', '0', '5', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('40eb50cdddb34fe6a7447e8d4d82e57b', '6dc84248d06f4885aef30064527d3ebb', 'TENANT_ID', 'TENANT_ID', 'TENANT_ID', '0', '0', '', '20', '6', '5', '字符串', '0', '4', '', '', '1', '', '1', '2019-04-20 08:44:46');
INSERT INTO `tb_meta_modelfield` VALUES ('446d724cf3d1403499e52b773c20c9e7', '57e87c148a654f80bcee9bf9def673e1', 'testfield', '测试字段', 'testfield', '0', '0', '123', '128', '0', null, '字符串', '0', '1', '范围', '测试字段', '1', '测试字段', '0', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('44842afbb56d44748058c6fb139f723c', 'ab544e8b09e74a46a66b32925e2712b9', 'column_4', 'column_4', 'column_4', '0', '0', '', '65535', '0', null, '字符串', '0', '4', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('4696c5aa8a264eef982aad56a3d7f430', '0ebf09434bdc43dfb111d276158de88c', 'sssjsx3', '实时数据属性3', '', '0', '0', '', '256', '6', '5', '整数', '0', '3', '', 'definition', '1', 'sdsd', '0', '2019-05-15 09:20:47');
INSERT INTO `tb_meta_modelfield` VALUES ('485e1ea6ac704574a80a0f3ef522af02', 'ab544e8b09e74a46a66b32925e2712b9', 'column_10', 'column_10', 'column_10', '0', '0', '', '11', '0', null, '双精度', '0', '10', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('4b80a2fa49ff4ae59fe9a67f7b2941ee', 'f5b25f4807e74f9e995f40588a3ba39a', 'test2', 'test2', 'test2', '0', '0', '', '21', '0', null, '整数', '0', '2', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('4d6b9e10f08e4b8995228b6e221173a8', 'WX483665', 'column_1', 'column_1', null, '0', '0', '', '13', '0', null, '整数', '0', '1', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('4de628aeb7594fcf83d9ffc17f42578b', '3104473dae2641368fed0fe96595a7ea', 'column_13', 'column_13', 'column_13', '0', '0', '', '0', '0', '0', '日期', '0', '13', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('505398b0af10439c8f62bd5ea1ff897a', '995b758f91c0490aa7f677f54e6ec020', 'column_4', 'column_4', 'column_4', '0', '0', '', '65535', '0', null, '字符串', '0', '4', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('52a6ed4d6a8e4ec4b1fe9fa8ae39b92f', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_2', 'column_2', 'column_2', '0', '0', '', '12', '0', '0', '字符串', '0', '2', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('537b03dd625e49e199aeb03d2fedc7f0', '9a24725b80ee4ae399a7a564f33bceea', 'test3', 'test3', '', '1', '1', '', '12306', '0', null, '浮点型', '0', '3', '', '', '1', '', '3', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('5472b3a01931424f841a7708e77bd238', '9313ab520476491eb0bf24580dd0a8ba', 'test061120322', 'test061120322', 'test061120322', '0', '1', '', '23', '0', null, '日期', '0', '2', '', '', '1', '', '3', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('54818b687c5742bab85377b94dca3f58', 'ab544e8b09e74a46a66b32925e2712b9', 'column_8', 'column_8', 'column_8', '0', '0', '', '10', '0', null, '整数', '0', '8', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('556c58acb3864fdc8db3f42028629c69', '595dcaaa139d482fa1d81418be592672', 'test1', 'test1', 'test1', '1', '0', '', '11', '0', '0', '字符串', '0', '1', '', '', '1', '', '1', '2019-04-20 10:45:49');
INSERT INTO `tb_meta_modelfield` VALUES ('559b1764d3cd4a5387050283df3ae8d1', 'c674197d79cf4df9b9b46c6fd7f12316', 'null1', 'null1', 'null1', '0', '0', '', '0', '6', '5', '字符串', '0', '1', '0', 'null1', '1', 'null1', '0', '2019-04-29 08:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('57585286a0144a0888b8b40cc9c1ce5e', 'WX494119', 'column_2', 'column_2', null, '0', '0', '', '7', '0', null, '整数', '0', '2', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('59a064a3a46a46779db2c316f1ce218c', '56c997f2a0ac4dd0a6f2b0675f5c6ad3', 'jiladahe', '基拉大和', 'jiladahe', '0', '0', '', '8', '6', '5', '字符串', '0', '2', '', '', '1', '', '1', '2019-05-16 15:41:51');
INSERT INTO `tb_meta_modelfield` VALUES ('5a49712c121e4b69854eb8ce8008cfb7', '9bb9a038beee4d00aa14694ea5ee335b', 'column_3', 'column_3', 'column_3', '0', '0', '', '255', '0', '0', '字符串', '0', '3', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('5afa54f50cc9424394b2361a0af9bf83', 'WX483665', 'column_5', 'column_5', null, '0', '0', '', '10', '0', null, '双精度', '0', '5', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('5b87edb53728437eac327cc2711cec59', '6dc84248d06f4885aef30064527d3ebb', 'LOGIN_ID', 'LOGIN_ID', 'LOGIN_ID', '0', '0', '', '20', '6', '5', '字符串', '0', '2', '', '', '1', '', '1', '2019-04-20 08:44:46');
INSERT INTO `tb_meta_modelfield` VALUES ('5d20dd37dea64b57b2d2af3c158d88d5', '69307b09eddb48a78dd85832b56757e8', 'test4', 'test4', 'test4', '0', '0', '', '320', '6', '5', '双精度', '0', '4', '', '', '1', '', '1', '2019-05-05 08:53:41');
INSERT INTO `tb_meta_modelfield` VALUES ('626b493a37124505934a9145c46f8722', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_11', 'column_11', 'column_11', '0', '0', '', '10', '0', '0', '单精度', '0', '9', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('62dda9bbba824a6f87ea380ea77976f7', 'b8b0713f9f264e8cbbc546834464ce74', 'sssjmc', '实时数据名称', null, '0', '0', '', '256', '6', '5', '字符串', '0', '1', null, 'definition', '1', '111', '1', '2019-05-15 09:09:54');
INSERT INTO `tb_meta_modelfield` VALUES ('666b27f64b874b4093b84d8118098f30', 'abc07b2fd0784574be4268ddc9441210', 'ID', 'ID', 'ID', '0', '0', '', '0', '0', null, '日期', '0', '1', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('677d9facca0747bca402a7bcd6970268', 'e07c334819d947dcac06dcd58ef188ac', 'fgd ', 'fgd ', 'fgd ', '0', '0', '', '12', '0', null, '字符串', '0', '1', '', '', '1', '', '2', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('67b1133e750d415680a14282e028fbcf', '9bb9a038beee4d00aa14694ea5ee335b', 'column_11', 'column_11', 'column_11', '0', '0', '', '10', '0', '0', '单精度', '0', '9', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('68e9736354e14bc58a01e6813ad87c85', '8b3bef3f1b0f4bc0a771aaa4d7965ab9', 'test', 'test', 'test', '0', '1', '', '32', '0', null, '整数', '0', '1', '', '', '1', '发放', '3', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('690427050660473cb18510e5851108cb', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_4', 'column_4', 'column_4', '0', '0', '', '65535', '0', '0', '字符串', '0', '4', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('6b2dcdc37fc14dd89f14510ae9f50822', '49daf37a51984f519fde3c4cca5e5579', 'field3', 'field', 'field', '0', '0', '', '0', '6', '5', '字符串', '0', '3', '0', 'field', '1', 'field', '0', '2019-05-05 10:50:52');
INSERT INTO `tb_meta_modelfield` VALUES ('6d4f1ef6c9a44e879c10c3b65e505e8d', '1f421922adc44098ad727993fd81813c', 'null', 'null', 'null', '1', '1', '', '0', '6', '5', '字符串', '0', '1', '0', 'null', '1', 'null', '0', '2019-04-02 18:44:49');
INSERT INTO `tb_meta_modelfield` VALUES ('6e02419152a8440da87c0cb2d9c8d096', '9bb9a038beee4d00aa14694ea5ee335b', 'column_14', 'column_14', 'column_14', '0', '0', '', '0', '0', '0', '日期', '0', '12', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('6e885bc5497b40709159d5eea6d46988', '9a24725b80ee4ae399a7a564f33bceea', 'test4', 'test4', '', '0', '0', '', '12580', '0', null, '字符串', '0', '4', '', '', '1', '', '5', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('6ffb04e0548242b8a265bec44f89912e', '25909753b79f4c5eb9ebf98577dc9baf', 'sssjsx2', '实时数据属性2', '', '0', '0', '', '256', '6', '5', '日期', '0', '2', '', 'definition', '1', 'rere', '0', '2019-05-15 14:37:30');
INSERT INTO `tb_meta_modelfield` VALUES ('71f4d9ab6ed94f5e92805184f4e61bf6', 'WX494119', 'column_1', 'column_1', null, '0', '0', '', '10', '0', null, '整数', '0', '1', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('73e000e83b4f4f479830e1e6071da98e', '995b758f91c0490aa7f677f54e6ec020', 'column_10', 'column_10', 'column_10', '0', '0', '', '11', '0', '0', '双精度', '0', '10', '', '', '1', '', '1', '2019-04-20 10:41:37');
INSERT INTO `tb_meta_modelfield` VALUES ('757e56b9093043c2b1485d2ad3afecc7', 'b8b0713f9f264e8cbbc546834464ce74', 'sssjsx3', '实时数据属性3', null, '0', '0', '', '256', '6', '5', '整数', '0', '3', null, 'definition', '1', '4444', '0', '2019-05-15 09:09:54');
INSERT INTO `tb_meta_modelfield` VALUES ('76134e78e9b04cd3bc4575e319c72de1', '8b3bef3f1b0f4bc0a771aaa4d7965ab9', 'test05301953', 'test05301953', '', '0', '0', '', '21', '0', null, '字符串', '0', '1', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('76b6c520160f4bd39b554b2def46ff3a', '0ebf09434bdc43dfb111d276158de88c', 'sssjsx2', '实时数据属性2', '', '0', '0', '', '256', '6', '5', '日期', '0', '2', '', 'definition', '1', 'sdd', '0', '2019-05-15 09:20:47');
INSERT INTO `tb_meta_modelfield` VALUES ('78a581ea9f2745cbaf7f45b0713b4617', '3104473dae2641368fed0fe96595a7ea', 'column_11', 'column_11', 'column_11', '0', '0', '', '10', '0', '0', '单精度', '0', '9', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('793903eadc924b56b4bf78ab72239fdb', 'd2b47987d14741918d38a763620e0afa', 'test', 'test', 'test', '0', '0', '', '23', '0', null, '整数', '0', '5', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('799d6ecdaa8047208e29db0b17bfc739', '595dcaaa139d482fa1d81418be592672', 'test2', 'test2', 'test2', '0', '1', '', '12', '0', '0', '字符串', '0', '2', '', '', '1', '', '1', '2019-04-20 10:45:49');
INSERT INTO `tb_meta_modelfield` VALUES ('7b6fbc5ecdff4b85925fd3a27e0a8ddd', '49daf37a51984f519fde3c4cca5e5579', 'field1', 'field', 'field', '0', '0', '', '0', '6', '5', '字符串', '0', '1', '0', 'field', '1', 'field', '0', '2019-05-05 10:50:52');
INSERT INTO `tb_meta_modelfield` VALUES ('7bd346e6a902488da0c171532973dbf3', '90873f243e8e4130818d64863eed1193', 'Eddard', '艾德', 'Eddard', '0', '0', '', '24', '6', '5', '字符串', '0', '2', '', '狼爸', '1', '', '1', '2019-05-16 15:08:52');
INSERT INTO `tb_meta_modelfield` VALUES ('7cd7e9512cca4fd4a974efc506a71328', '69307b09eddb48a78dd85832b56757e8', 'test5', 'test5', 'test5', '0', '0', '', '0', '6', '5', '日期', '0', '5', '0', '', '1', '', '0', '2019-05-05 08:53:41');
INSERT INTO `tb_meta_modelfield` VALUES ('7cdcab8711b34634a290882f183dc49d', '684e6655a8654774afe19256b40b0631', 'test0620151501', 'test0620151501', 'test0620151501', '1', '0', '', '11', '0', null, '字符串', '0', '1', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('7e570a891296475db53e96c548b72c9c', '9bb9a038beee4d00aa14694ea5ee335b', 'column_5', 'column_5', 'column_5', '0', '0', '', '16777215', '0', '0', '字符串', '0', '5', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('7e6260a3e2064c0e95d11afb1db41fed', 'd3bbc82d95924aeab8938350f036394b', '3253245', '3253245', '3253245', '0', '0', '', '25625', '0', null, '字符串', '0', '2', '', '', '1', '', '3', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('7ff3d615e10d49678eb5a8b9982edb16', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_5', 'column_5', 'column_5', '0', '0', '', '16777215', '0', '0', '字符串', '0', '5', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('8037f66eb7da400586259918dfc8bf49', 'a3928cbb8bb04b6789f5437aa05764ff', 'test1', 'test1', 'test1', '1', '0', '', '12', '6', '5', '整数', '0', '1', '', '', '1', '', '1', '2019-04-15 17:36:38');
INSERT INTO `tb_meta_modelfield` VALUES ('8086c69c87a244678b0f922bf96a6c54', '9a24725b80ee4ae399a7a564f33bceea', 'test2', 'test2', '', '0', '1', '', '10010', '0', null, '日期', '0', '2', '', '', '1', '', '2', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('8183a865ef624bad92b0c9090eb3a25d', '3104473dae2641368fed0fe96595a7ea', 'column_5', 'column_5', 'column_5', '0', '0', '', '16777215', '0', '0', '字符串', '0', '5', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('8373dc058294447386a54c1eab24d566', '75d423e64f4f42b68b80cd6ab59b05be', 'sssjsx3', '实时数据属性3', null, '0', '0', '', '256', '6', '5', '整数', '0', '3', null, 'definition', '1', '33333', '0', '2019-05-02 11:53:20');
INSERT INTO `tb_meta_modelfield` VALUES ('858c44da074f415b80822d3a281dc0de', 'ab544e8b09e74a46a66b32925e2712b9', 'column_7', 'column_7', 'column_7', '0', '0', '', '10', '0', null, '整数', '0', '7', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('85b7f44f7c4c482ea11ca4d050ebeb95', '56247872fde64bcf88b0a389a36a5dd1', 'csrq', '出生日期', 'csrq', '0', '0', '', '50', '0', null, '字符串', '0', '5', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('85e78b74674c4e0db14fd8eea6db1399', '0ebf09434bdc43dfb111d276158de88c', 'sssjmc', '实时数据名称', '', '0', '0', '', '256', '6', '5', '字符串', '0', '1', '', 'definition', '1', 'as', '1', '2019-05-15 09:20:47');
INSERT INTO `tb_meta_modelfield` VALUES ('86ca276cb3824cc48fe15f8368be1cd7', '69307b09eddb48a78dd85832b56757e8', 'test1', 'test1', 'test1', '1', '0', '', '32', '6', '5', '字符串', '0', '1', '', '', '1', '', '1', '2019-05-05 08:53:41');
INSERT INTO `tb_meta_modelfield` VALUES ('87888832875e43a58ab8c011989c1f67', '2839fb432b2f4a73b2541d492d669de6', 'field', 'field', 'field', '1', '1', '', '0', '6', '5', '字符串', '0', '1', '0', 'field', '1', 'field', '0', '2019-05-16 15:08:15');
INSERT INTO `tb_meta_modelfield` VALUES ('87c9e322f7bf49c3afc909b14bc5dbc2', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_6', 'column_6', 'column_6', '0', '0', '', '-1', '0', '0', '字符串', '0', '6', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('8abfaea84b684b778cb1ba20c31f69b5', '69307b09eddb48a78dd85832b56757e8', 'a11', 'a11', 'a11', '0', '0', '', '0', '6', '5', '字符串', '0', '6', '0', '', '1', '', '1', '2019-05-05 08:53:41');
INSERT INTO `tb_meta_modelfield` VALUES ('8b02f80f48bd42ac8c79594ffb7d86a0', '995b758f91c0490aa7f677f54e6ec020', 'column_1', 'column_1', 'column_1', '0', '0', '', '11', '0', null, '字符串', '0', '1', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('8b270a7215ce4a13aeb9a944ac008b87', 'WX483665', 'column_3', 'column_3', null, '0', '0', '', '10', '0', null, '日期', '0', '3', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('8cc24410615f4c9ea4ffc47bbf5f1cab', '02423be887af4079ac8123b97042b9ef', 'mod', '型号', 'model', '0', '0', '', '32', '6', '5', '字符串', '0', '5', '0', '定义火车的型号', '1', null, '1', '2019-08-10 17:50:56');
INSERT INTO `tb_meta_modelfield` VALUES ('8d7620f87f9c42828012b4a293bc01e0', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_14', 'column_14', 'column_14', '0', '0', '', '0', '0', '0', '日期', '0', '12', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('8ea7da548a914e009aeddf9a35445a5d', '995b758f91c0490aa7f677f54e6ec020', 'column_13', 'column_13', 'column_13', '0', '0', '', '0', '0', null, '日期', '0', '13', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('8f0cf0210c264f82aa01ee019beec983', 'ab544e8b09e74a46a66b32925e2712b9', 'column_13', 'column_13', 'column_13', '0', '0', '', '0', '0', null, '日期', '0', '13', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('91e55fb9b2f14e91ad85d3e519d470fe', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_3', 'column_3', 'column_3', '0', '0', '', '255', '0', '0', '字符串', '0', '3', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('925083e067da43eeac8cba92cf1825f4', '995b758f91c0490aa7f677f54e6ec020', 'column_7', 'column_7', 'column_7', '0', '0', '', '10', '0', null, '整数', '0', '8', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('9309f2aea3a145959ee50d4274f8648b', '49daf37a51984f519fde3c4cca5e5579', 'field2', 'field', 'field', '0', '0', '', '0', '6', '5', '字符串', '0', '2', '0', 'field', '1', 'field', '0', '2019-05-05 10:50:52');
INSERT INTO `tb_meta_modelfield` VALUES ('975a729181ec42e7a292bd474501901f', '56247872fde64bcf88b0a389a36a5dd1', 'lxdh', '联系电话', 'lxdh', '0', '0', '', '40', '0', null, '字符串', '0', '9', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('975de1433c02455c9213edf2845df46d', '72f671bf41e14a259fb1a9c813d5fe3c', 'yssjmc', '原始数据名称', '', '0', '0', '', '256', '6', '5', '字符串', '0', '1', '', 'definition', '1', '11', '0', '2019-05-02 15:03:41');
INSERT INTO `tb_meta_modelfield` VALUES ('9920dd6eac1044f39d65fc9f78f59ea5', 'f0dd6b1d18c541e59f0e26d2c84928bf', 'test4', 'test4', 'test4', '0', '0', '', '14', '0', null, '字符串', '0', '4', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('9a6337543be1405ca3b64ff91a205371', '2b6735ee810a408d86d6de7fa8f7421c', 'sssjsx2', '实时数据属性2', 'sssjsx2', '0', '0', '', '0', '6', '5', '日期', '0', '2', '', 'definition', '1', '23123', '1', '2019-06-20 10:33:29');
INSERT INTO `tb_meta_modelfield` VALUES ('9e07bedfcc154da1a1944605105d3ff6', '995b758f91c0490aa7f677f54e6ec020', 'column_12', 'column_12', 'column_12', '0', '0', '', '0', '0', null, '日期', '0', '14', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('9ef65510957d43b590efcac27fba2dac', '6dc84248d06f4885aef30064527d3ebb', 'ID', 'ID', 'ID', '0', '0', '', '0', '6', '5', '日期', '0', '1', '', '', '1', '', '1', '2019-04-20 08:44:46');
INSERT INTO `tb_meta_modelfield` VALUES ('9f6972e56b2d45c381e1f0c5242afcf1', 'f36ac59c9e564107aef51423bf07e6b8', 'aaaaaa', 'aaaa', 'aaaaaa', '0', '0', '', '0', '0', null, '整数', '0', '2', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('a0fa916dcf5e4772b59618c533896ff8', '9bb9a038beee4d00aa14694ea5ee335b', 'column_4', 'column_4', 'column_4', '0', '0', '', '65535', '0', '0', '字符串', '0', '4', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('a2061be0e66140168c7171af91c7ca5d', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_8', 'column_8', 'column_8', '0', '0', '', '10', '0', '0', '整数', '0', '7', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('a4bc7eaf66774f9bb5920d05578666ba', '90873f243e8e4130818d64863eed1193', 'Robb', '罗伯', 'Robb', '0', '0', '', '4', '3', '5', '整数', '0', '1', '', '狼长子 ', '1', '', '1', '2019-05-16 15:08:52');
INSERT INTO `tb_meta_modelfield` VALUES ('a6ceadc9ac604eccb9338c0ce1637251', 'ab544e8b09e74a46a66b32925e2712b9', 'column_11', 'column_11', 'column_11', '0', '0', '', '10', '0', null, '单精度', '0', '11', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('a7f0789cf9d94fa8804582674a1b9890', 'd3bbc82d95924aeab8938350f036394b', 'WQRTWE4RT', 'WQRTWE4RT', 'WQRTWE4RT', '0', '0', '', '234523456', '0', null, '整数', '0', '1', '', '', '1', '', '3', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('a87f1658e5f541999a0d0f164014fa97', '0df4ca19598fc459777f0d016611c206', 'test123', 'test', 'test', '0', '0', '', '12', '6', '5', '字符串', '0', '1', '', '', '1', '', '1', '2019-04-23 11:05:50');
INSERT INTO `tb_meta_modelfield` VALUES ('a988e01b47124e289185bc881a1a1ac1', '2839fb432b2f4a73b2541d492d669de6', 'field1', 'field', 'field', '0', '0', '', '0', '6', '5', '字符串', '0', '2', '0', 'field', '1', 'field', '1', '2019-05-16 15:08:15');
INSERT INTO `tb_meta_modelfield` VALUES ('aa655ce7615e4e228ec217df8154cc20', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_13', 'column_13', 'column_13', '0', '0', '', '0', '0', '0', '日期', '0', '13', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('ab3d2d314bda445f9e6cf60223b4159b', 'WX494119', 'column_4', 'column_4', null, '0', '0', '', '10', '0', null, '整数', '0', '4', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('ad2d6b13f7604e089a9c80c89e8f2e5d', '3104473dae2641368fed0fe96595a7ea', 'column_7', 'column_7', 'column_7', '0', '0', '', '10', '0', '0', '整数', '0', '8', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('aeda1d9279b049c988039d232b48ab54', '69307b09eddb48a78dd85832b56757e8', 'test2', 'test2', 'test2', '0', '1', '', '10', '6', '5', '整数', '0', '2', '', '', '1', '', '2', '2019-05-05 08:53:41');
INSERT INTO `tb_meta_modelfield` VALUES ('af16e01630d64aaca18d7463f68bcf3f', 'f4d3eaffbb1341a18e9175a14223663e', 'test4', 'test4', 'test4', '1', '1', '', '34', '0', null, '字符串', '0', '4', '', '', '1', '', '5', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('afb9c675dbdf4af08fb0169aa5802ab8', '995b758f91c0490aa7f677f54e6ec020', 'column_14', 'column_14', 'column_14', '0', '0', '', '0', '0', null, '日期', '0', '12', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('b18889626bbc49ff801457c4c55f50d1', '3104473dae2641368fed0fe96595a7ea', 'column_9', 'column_9', 'column_9', '0', '0', '', '12', '0', '0', '双精度', '0', '11', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('b1ac556d4dad4aaab61de6688602220f', '56247872fde64bcf88b0a389a36a5dd1', 'xbdm', '性别代码', 'xbdm', '0', '0', '', '1', '0', null, '字符串', '0', '4', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('b1aea2a19e7b4d5e8446c3f92416ddfa', 'WX494119', 'column_3', 'column_3', null, '0', '0', '', '12', '0', null, '整数', '0', '3', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('b2ca531095be4e1684527c80953ddb98', 'WX482587', 'column_3', 'column_3', null, '0', '0', '', '10', '0', null, '整数', '0', '3', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('b3007d0f5ccf4df9996af81f89df6c09', 'd2b47987d14741918d38a763620e0afa', 'test1', 'test1', 'test1', '0', '0', '', '12', '0', null, '字符串', '0', '1', '', '', '2', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('b5aa5ca385b5495abd471640b8155ef0', '9313ab520476491eb0bf24580dd0a8ba', 'test061120321', 'test061120321', 'test061120321', '1', '0', '', '12', '0', null, '字符串', '0', '1', '', '', '1', '', '5', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('baa67498b8694e939beb54c204f3585a', 'WX482587', 'column_4', 'column_4', null, '0', '0', '', '10', '0', null, '整数', '0', '4', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('bfcbef8d7ccb4ee4a8fc86d1f8eb2838', 'a6b8ae393a1b4d1386227d048198c1bc', 'column_9', 'column_9', 'column_9', '0', '0', '', '12', '0', '0', '双精度', '0', '11', '', '', '1', '', '1', '2019-04-15 11:27:42');
INSERT INTO `tb_meta_modelfield` VALUES ('c1775c87d11b487db109c40746cfe256', '56247872fde64bcf88b0a389a36a5dd1', 'zdrylb', '重点人员类别', 'zdrylb', '0', '0', '', '47', '0', null, '字符串', '0', '1', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('c194518070a14bf9b17a87a44fea636a', 'f0dd6b1d18c541e59f0e26d2c84928bf', 'test1', 'test1', 'test1', '0', '0', '', '12', '0', null, '字符串', '0', '1', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('c2920d54eb544c4bafb1a97320dc11d8', '995b758f91c0490aa7f677f54e6ec020', 'column_9', 'column_9', 'column_9', '0', '0', '', '12', '0', '0', '双精度', '0', '11', '', '', '1', '', '1', '2019-04-20 10:41:37');
INSERT INTO `tb_meta_modelfield` VALUES ('c3e9bfd860d4487ba090d36253146f79', '56247872fde64bcf88b0a389a36a5dd1', 'xmhypy', '姓名汉语拼音', 'xmhypy', '0', '0', '', '150', '0', null, '字符串', '0', '3', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('c555b2fce22b42ed8ca14c4b3ce045e2', 'WX483665', 'column_9', 'column_9', null, '0', '0', '', '10', '0', null, '字符串', '0', '7', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('c5fdcbc4b4b746a78daba2e4b1a68d22', 'WX483665', 'column_7', 'column_7', null, '0', '0', '', '10', '0', null, '单精度', '0', '9', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('c9e7e5f39d8940e3a0696d9fa6c888a0', '3104473dae2641368fed0fe96595a7ea', 'column_12', 'column_12', 'column_12', '0', '0', '', '0', '0', '0', '日期', '0', '14', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('cbbbfca06011481589c2a76aaf0d7d52', 'b5c7a75c46a54ef982f9ac584d675375', 'aaa', null, null, '1', '0', '', '0', '6', '5', '整数', '0', '1', '0', null, '1', null, '1', '2019-04-15 17:40:27');
INSERT INTO `tb_meta_modelfield` VALUES ('cd5706a646d24ffbb5245762ddf5f9ab', 'ab544e8b09e74a46a66b32925e2712b9', 'column_5', 'column_5', 'column_5', '0', '0', '', '16777215', '0', null, '字符串', '0', '5', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('ce59884d4f844392b92d3ddcdf5bca18', '9a24725b80ee4ae399a7a564f33bceea', 'test5', 'test5', '', '0', '0', '', '119', '0', null, '日期', '0', '5', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('cffb411e2d344642acc3c434e5eea38e', '43dd8b6641cc4151a747240a9109446c', 'field', '', '', '0', '0', '', '0', '6', '5', '字符串', '0', '1', '0', 'field', '1', 'field', '0', '2019-05-05 09:43:12');
INSERT INTO `tb_meta_modelfield` VALUES ('d0279e4e58cb41268851ab33bf859088', 'e07c334819d947dcac06dcd58ef188ac', '24', '24', '24', '0', '0', '', '412', '0', null, '浮点型', '0', '2', '', '', '1', '', '3', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('d0c008f168044bcaaa23b56e6085bf1e', '75d423e64f4f42b68b80cd6ab59b05be', 'sssjsx2', '实时数据属性2', null, '0', '0', '', '256', '6', '5', '日期', '0', '2', null, 'definition', '1', '2222222', '0', '2019-05-02 11:53:20');
INSERT INTO `tb_meta_modelfield` VALUES ('d1a110c8cdbd4f2997cf9a9a984e892a', '25909753b79f4c5eb9ebf98577dc9baf', 'sssjsx3', '实时数据属性3', '', '0', '0', '', '256', '6', '5', '整数', '0', '3', '', 'definition', '1', '333', '0', '2019-05-15 14:37:30');
INSERT INTO `tb_meta_modelfield` VALUES ('d2c52ef5630c448dbc3d4e6810aa12e1', 'ab544e8b09e74a46a66b32925e2712b9', 'column_2', 'column_2', 'column_2', '0', '0', '', '12', '0', null, '字符串', '0', '2', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('d7a5efbdb2784200ade6603891a905cf', 'ab544e8b09e74a46a66b32925e2712b9', 'column_1', 'column_1', 'column_1', '0', '0', '', '11', '0', null, '字符串', '0', '1', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('d92dd7d588a049b79620de5babe60957', 'f0dd6b1d18c541e59f0e26d2c84928bf', 'test3', 'test3', 'test3', '0', '0', '', '13', '0', null, '字符串', '0', '3', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('d9a627f7e93f45ff8487bca37f7b1a4c', '72f671bf41e14a259fb1a9c813d5fe3c', 'yssjcclj', '原始数据存储路径', '', '0', '0', '', '256', '6', '5', '字符串', '0', '2', '', 'definition', '1', '22', '1', '2019-05-02 15:03:41');
INSERT INTO `tb_meta_modelfield` VALUES ('dbc02ed75cad4617ae3e68ebaf881698', 'WX482587', 'column_2', 'column_2', null, '0', '0', '', '10', '0', null, '整数', '0', '2', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('dcbc8c08372542338bf86974288c647f', '3104473dae2641368fed0fe96595a7ea', 'column_2', 'column_2', 'column_2', '0', '0', '', '12', '0', '0', '字符串', '0', '2', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('de2723d104d44090a92a99e8895a2c85', 'a12ee410708c436abb1fa5f5521d4d0e', 'test20190403_002', null, null, '0', '0', '', '0', '6', '5', '字符串', '0', '1', '0', null, '1', null, '0', '2019-04-15 11:32:46');
INSERT INTO `tb_meta_modelfield` VALUES ('dfb2f9ba98754edd95a24b2398c69a98', '1f421922adc44098ad727993fd81813c', 'test', 'test', 'test', '0', '0', '', '32', '6', '5', '字符串', '0', '2', '', '', '1', '', '1', '2019-04-02 18:44:49');
INSERT INTO `tb_meta_modelfield` VALUES ('dfe90420a3f14ef1a5020baa05add6f7', 'WX483665', 'column_8', 'column_8', null, '0', '0', '', '10', '0', null, '日期', '0', '8', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('e0bc0a2f7d4e4f3cad46f1d6cac453b2', '995b758f91c0490aa7f677f54e6ec020', 'column_11', 'column_11', 'column_11', '0', '0', '', '10', '0', '0', '单精度', '0', '9', '', '', '1', '', '1', '2019-04-20 10:41:37');
INSERT INTO `tb_meta_modelfield` VALUES ('e12e34857ec7438b9dc89a7caf3abf22', '69307b09eddb48a78dd85832b56757e8', 'test3', 'test3', 'test3', '1', '1', '', '20', '6', '5', '单精度', '0', '3', '', '', '1', '', '3', '2019-05-05 08:53:41');
INSERT INTO `tb_meta_modelfield` VALUES ('e32c062e97574af0ba72df691ca16c92', '995b758f91c0490aa7f677f54e6ec020', 'column_8', 'column_8', 'column_8', '0', '0', '', '10', '0', null, '整数', '0', '7', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('e39a3efda44949e393c68faef22f814a', 'a3928cbb8bb04b6789f5437aa05764ff', 'test2', 'test2', 'test2', '0', '1', '', '23', '6', '5', '日期', '0', '2', '', '', '1', '', '2', '2019-04-15 17:36:38');
INSERT INTO `tb_meta_modelfield` VALUES ('e59bde6276c34219be082d8ec32ee28e', '9bb9a038beee4d00aa14694ea5ee335b', 'column_7', 'column_7', 'column_7', '0', '0', '', '10', '0', '0', '整数', '0', '8', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('e80b8776fc9c4727afd695487a180032', 'fb65adf0aa104cc194352e116b59f435', 'null', 'registerStatus', 'registerStatus', '0', '0', '', '0', '6', '5', '字符串', '0', '1', '0', 'null', '1', 'null', '0', '2019-04-03 09:33:47');
INSERT INTO `tb_meta_modelfield` VALUES ('e81055f7e8144c3aabbd03e9f2b9f96d', '995b758f91c0490aa7f677f54e6ec020', 'column_3', 'column_3', 'column_3', '0', '0', '', '255', '0', null, '字符串', '0', '3', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('e94db382f3f84a2cb641bf96c9c135ce', 'WX483665', 'column_2', 'column_2', null, '0', '0', '', '10', '0', null, '字符串', '0', '2', null, null, '1', null, '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('e98fe7ddfeaf4b26b0fb9d27c45fa899', '56c997f2a0ac4dd0a6f2b0675f5c6ad3', 'amuluo', '阿姆罗', 'amuluo', '0', '0', '', '4', '3', '5', '整数', '0', '1', '', '', '1', '', '1', '2019-05-16 15:41:51');
INSERT INTO `tb_meta_modelfield` VALUES ('e9ab8f81abc24addbe607342d6340fe9', '02423be887af4079ac8123b97042b9ef', 'com', '公司', 'company', '0', '0', '', '32', '6', '5', '字符串', '0', '4', '0', '火车生产公司', '1', null, '1', '2019-08-10 17:50:56');
INSERT INTO `tb_meta_modelfield` VALUES ('ef5b9f7bbb54422dab528c5fc6cf411b', 'd2b47987d14741918d38a763620e0afa', 'eeeeeee', 'eeeeeee', 'eeeeeee', '0', '0', '', '0', '0', null, '字符串', '0', '3', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('f030253fc3564fd699d77d69f8abeef2', '3104473dae2641368fed0fe96595a7ea', 'column_3', 'column_3', 'column_3', '0', '0', '', '255', '0', '0', '字符串', '0', '3', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('f1943d731e4a40f2b3fa7c0f2d5e4bd8', 'ab544e8b09e74a46a66b32925e2712b9', 'column_6', 'column_6', 'column_6', '0', '0', '', '-1', '0', null, '字符串', '0', '6', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('f26f60bf99ba40b18ce64436587c7fae', '9bb9a038beee4d00aa14694ea5ee335b', 'column_1', 'column_1', 'column_1', '0', '0', '', '11', '0', '0', '字符串', '0', '2', '', '', '1', '', '1', '2019-04-15 14:49:36');
INSERT INTO `tb_meta_modelfield` VALUES ('f2787c1c2ce74806ac5d4917b37f7737', 'f4d3eaffbb1341a18e9175a14223663e', '托尔斯泰', '托尔斯泰', '', '0', '0', '', '12', '0', null, '日期', '0', '3', '', '', '1', '', '3', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('f35e346a39514b43a8cf9846f0a59ca5', 'f0dd6b1d18c541e59f0e26d2c84928bf', 'test2', 'test2', 'test2', '0', '0', '', '11', '0', null, '字符串', '0', '2', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('f5c2420628014b539d97e01aa833ecf9', 'c674197d79cf4df9b9b46c6fd7f12316', 'null2', 'null1fd', 'null1fd', '0', '0', '', '0', '6', '5', '字符串', '0', '3', '0', 'null1', '1', 'null1', '0', '2019-04-29 08:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('f5f4c24fccc64275bf1c55b9296bad25', 'ab544e8b09e74a46a66b32925e2712b9', 'column_12', 'column_12', 'column_12', '0', '0', '', '0', '0', null, '日期', '0', '12', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('f7bc8dd0c1054073a8ece8a9cc920ff7', '56247872fde64bcf88b0a389a36a5dd1', 'hz_zjhm', '护照_证件号码', 'hz_zjhm', '0', '0', '', '50', '0', null, '字符串', '0', '7', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('f7d3b28058374aa68515d1af43d9d83d', '3104473dae2641368fed0fe96595a7ea', 'column_1', 'column_1', 'column_1', '0', '0', '', '11', '0', '0', '字符串', '0', '1', '', '', '1', '', '1', '2019-04-20 10:46:01');
INSERT INTO `tb_meta_modelfield` VALUES ('f9fc04d36a7d48b993b2c89431edc715', '6dc84248d06f4885aef30064527d3ebb', 'MESSAGE', 'MESSAGE', 'MESSAGE', '0', '0', '', '5000', '6', '5', '字符串', '0', '3', '', '', '1', '', '1', '2019-04-20 08:44:46');
INSERT INTO `tb_meta_modelfield` VALUES ('fa1c8b7e0b4844748cc81fe8603b2ba2', '45056b9aa2444778a7b5a4bbb32a4082', 'LOG_RESOURCE_NAME', 'LOG_RESOURCE_NAME', 'LOG_RESOURCE_NAME', '0', '0', '', '20', '0', null, '字符串', '0', '2', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('fc974d94023d4991acfe348bbd131d87', 'e07c334819d947dcac06dcd58ef188ac', '干啥蛋糕', '干啥蛋糕', '', '0', '0', '', '234', '0', null, '日期', '0', '3', '', '', '1', '', '4', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('fd7b5d95f6194b3d9508e757f46bb9f2', '8b3bef3f1b0f4bc0a771aaa4d7965ab9', '国防生的', '国防生的', '国防生的', '1', '1', '', '23', '0', null, '浮点型', '0', '1', '', '', '1', '发售表', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('fec289bc5f9d4d95adb939cb47ca1da6', '45056b9aa2444778a7b5a4bbb32a4082', 'ID', 'ID', 'ID', '0', '0', '', '0', '0', null, '日期', '0', '1', '', '', '1', '', '1', '2018-11-12 10:52:27');
INSERT INTO `tb_meta_modelfield` VALUES ('ff85b9ba431e4f43b7ab2f6f399f0781', '2d3236eeb4324cbea4d278db2e72b114', 'test2', 'test2', 'test2', '0', '1', '', '23', '6', '5', '字符串', '0', '2', '', '', '1', '', '2', '2019-04-28 19:37:28');

-- ----------------------------
-- Table structure for tb_meta_modelfield_checkresult
-- ----------------------------
DROP TABLE IF EXISTS `tb_meta_modelfield_checkresult`;
CREATE TABLE `tb_meta_modelfield_checkresult` (
  `id` varchar(32) NOT NULL COMMENT '字段ID号（UUID）',
  `stdname` varchar(512) DEFAULT '1' COMMENT '最大出现次数',
  `stdproperty` varchar(512) DEFAULT NULL COMMENT '备注',
  `checkresult` varchar(128) DEFAULT '1' COMMENT '密级',
  `detail` text COMMENT '更新时间',
  `field_id` varchar(128) DEFAULT NULL,
  `field_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8ckrtofnts6y54d0pjl4vchi2` (`field_id`),
  CONSTRAINT `FKnxwy2fxl647j6jrutyhgtciy6` FOREIGN KEY (`field_id`) REFERENCES `tb_meta_modelfield` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_meta_modelfield_checkresult
-- ----------------------------
INSERT INTO `tb_meta_modelfield_checkresult` VALUES ('21d941e737104cd981de57167ef50b1a', null, 'a4e7be8f72bf42688c49c01309e97b7f', '相似', '英文名称不吻合', '8cc24410615f4c9ea4ffc47bbf5f1cab', null);
INSERT INTO `tb_meta_modelfield_checkresult` VALUES ('2d27e649b1cc4d6fa2f001a800a7ca02', null, 'c7b2f500115046759c6a9ba638f8c0df', '相似', '长度不吻合', '10fd273d065b48078a3bce1126b45971', null);
INSERT INTO `tb_meta_modelfield_checkresult` VALUES ('347', 'north', '0197937c5df248328d0381af78c083b3', '符合', '符合', 'a87f1658e5f541999a0d0f164014fa97', null);
INSERT INTO `tb_meta_modelfield_checkresult` VALUES ('348', 'north', '1234568', '符合', '符合', '7bd346e6a902488da0c171532973dbf3', null);
INSERT INTO `tb_meta_modelfield_checkresult` VALUES ('349', 'north', '1234567', '符合', '符合', 'a4bc7eaf66774f9bb5920d05578666ba', null);
INSERT INTO `tb_meta_modelfield_checkresult` VALUES ('350', 'Gundam', '2345678', '符合', '符合', 'e98fe7ddfeaf4b26b0fb9d27c45fa899', null);
INSERT INTO `tb_meta_modelfield_checkresult` VALUES ('351', 'Gundam', '2345679', '符合', '符合', '59a064a3a46a46779db2c316f1ce218c', null);
INSERT INTO `tb_meta_modelfield_checkresult` VALUES ('37fe292d63824e168a8eb4c60c08563e', null, null, null, null, null, null);
INSERT INTO `tb_meta_modelfield_checkresult` VALUES ('684a33dde9544c7693d673e3b316a603', '火车', 'c1b7d74e205a422ab45c1ef6fb315a62', '符合', '完全符合', '373afcdd717f4309b1a316962f8dc101', null);
INSERT INTO `tb_meta_modelfield_checkresult` VALUES ('baef55f5013c4b3ca5049eab7f1451e0', null, null, '不符合', '完全不符合', '20b92057ff0e4275a061b45810bd11ff', 'testfield');
INSERT INTO `tb_meta_modelfield_checkresult` VALUES ('c124da45b4af4aa79205e4179791a374', null, null, '不符合', '完全不符合', '0cf250ed5f4c476d958d7eed8d3c0ca5', null);
INSERT INTO `tb_meta_modelfield_checkresult` VALUES ('f1d84841cec54d93b45fa945cf8d06c9', null, '961720b4547d4f78ba35e96e8beecdb8', '相似', '中文名称不吻合', 'e9ab8f81abc24addbe607342d6340fe9', null);
INSERT INTO `tb_meta_modelfield_checkresult` VALUES ('fd6c2fae32be4f949dfebb67bdee3a29', null, null, '不符合', '完全不符合', '11e23604f4da46eb9b06f83bf9a8d9f7', 'Brandon');

-- ----------------------------
-- Table structure for tb_meta_modelfield_wld
-- ----------------------------
DROP TABLE IF EXISTS `tb_meta_modelfield_wld`;
CREATE TABLE `tb_meta_modelfield_wld` (
  `id` varchar(32) NOT NULL,
  `code` varchar(128) NOT NULL,
  `comments` varchar(2048) DEFAULT NULL,
  `default_value` varchar(1024) DEFAULT NULL,
  `defination` varchar(2048) DEFAULT NULL,
  `en_name` varchar(128) DEFAULT NULL,
  `max_contains` int(11) DEFAULT NULL,
  `max_size` int(11) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `precition` int(11) DEFAULT NULL,
  `is_primary` bit(1) DEFAULT NULL,
  `pxh` int(11) DEFAULT NULL,
  `field_range` varchar(2048) DEFAULT NULL,
  `required` bit(1) DEFAULT NULL,
  `scale` int(11) DEFAULT NULL,
  `security` int(11) DEFAULT NULL,
  `type` varchar(128) NOT NULL,
  `is_unique` bit(1) DEFAULT NULL,
  `model_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_meta_modelfield_wld
-- ----------------------------


-- ----------------------------
-- Table structure for tb_stdgl_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_stdgl_category`;
CREATE TABLE `tb_stdgl_category` (
  `id` varchar(50) NOT NULL COMMENT '目录ID,采用uuid',
  `name` varchar(50) NOT NULL COMMENT '目录节点中文名称',
  `parent_id` varchar(50) DEFAULT '' COMMENT '父目录节点ID',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间。',
  `bmID` varchar(128) DEFAULT '' COMMENT '编目ID',
  `fullbmID` varchar(512) DEFAULT '' COMMENT '全编目ID',
  `pxh` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_id_unique` (`id`),
  UNIQUE KEY `category_name_unique` (`name`,`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='目录信息表。';

-- ----------------------------
-- Records of tb_stdgl_category
-- ----------------------------
INSERT INTO `tb_stdgl_category` VALUES ('0', '我的目录', '-1', 'admin', '2017-12-14 21:05:35', '0', '', null);
INSERT INTO `tb_stdgl_category` VALUES ('019a1df7879341d89ff27b23f87b8a20', '缺省目录', '447283', 'admin', '2019-04-26 09:38:41', '', '', '1');
INSERT INTO `tb_stdgl_category` VALUES ('061241ebf8854fb2990150f921d262d2', '标准目录', '0', 'admin', '2019-05-20 15:46:29', 'bzml', '', '100');
INSERT INTO `tb_stdgl_category` VALUES ('08dd50b1bcd742249a60b242a07f8bb2', '无锡', '469824', 'admin', '2018-06-13 15:27:52', 'WX', '465136465137469824WXWX', '1');
INSERT INTO `tb_stdgl_category` VALUES ('2ff068d31f1b4319a68ad8babb5aaf3e', '海淀', '8830123', 'admin', '2018-06-11 20:06:38', 'HD', 'new_node7', null);
INSERT INTO `tb_stdgl_category` VALUES ('447283', '测试目录', '0', '100019', '2018-06-25 14:54:16', '447283', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('457199', 'ftp资源', '447283', '100019', '2018-06-25 14:54:18', '457199', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465136', '测试目录_whl', '0', '100019', '2018-06-25 14:54:23', '465136', '465136465136', '1');
INSERT INTO `tb_stdgl_category` VALUES ('465137', '数据库资源', '465136', '100019', '2018-06-25 14:54:23', '465137', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465139', 'mysql资源', '465137', '100019', '2018-06-25 14:54:23', '465139', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465141', 'gbase资源', '465137', '100019', '2018-06-25 14:54:24', '465141', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465143', 'hive资源', '465137', '100019', '2018-06-25 14:54:24', '465143', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465158', '表资源', '465136', '100019', '2018-06-25 14:54:25', '465158', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465159', 'gbase表资源', '465158', '100019', '2018-06-25 14:54:25', '465159', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465162', 'mysql表资源', '465158', '100019', '2018-06-25 14:54:26', '465162', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465191', '数据库资源', '447283', '100019', '2018-06-25 14:54:19', '465191', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465192', 'mysql数据库资源', '465191', '100019', '2018-06-25 14:54:19', '465192', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465197', 'gbase数据库资源', '465191', '100019', '2018-06-25 14:54:19', '465197', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465202', 'odps数据库资源', '465191', '100019', '2018-06-25 14:54:19', '465202', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465206', 'hive数据库资源', '465191', '100019', '2018-06-25 14:54:20', '465206', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465208', '表资源', '447283', '100019', '2018-06-25 14:54:20', '465208', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465209', 'mysql表资源', '465208', '100019', '2018-06-25 14:54:21', '465209', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465211', 'gbase表资源', '465208', '100019', '2018-06-25 14:54:21', '465211', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465213', 'odps表资源', '465208', '100019', '2018-06-25 14:54:21', '465213', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465215', 'hive表资源', '0', '100019', '2018-06-25 14:54:22', '465215', '447283465208465215465215', null);
INSERT INTO `tb_stdgl_category` VALUES ('465217', 'odps资源', '465137', '100019', '2018-06-25 14:54:24', '465217', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465219', 'hive表资源', '465158', '100019', '2018-06-25 14:54:27', '465219', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('465223', 'odps表资源', '465158', '100019', '2018-06-25 14:54:27', '465223', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('469824', 'es资源', '465137', '100019', '2018-06-25 14:54:25', '469824', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('470231', 'hbase表资源', '465158', '100019', '2018-06-25 14:54:27', '470231', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('470233', 'hbase资源', '465137', '100019', '2018-06-25 14:54:25', '470233', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('470303', 'ES数据库资源', '465191', '100019', '2018-06-25 14:54:20', '470303', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('470305', 'hbase数据库资源', '465191', '100019', '2018-06-25 14:54:20', '470305', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('470307', 'hbase表资源', '465208', '100019', '2018-06-25 14:54:22', '470307', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('470309', 'ES表资源', '465208', '100019', '2018-06-25 14:54:22', '470309', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('470311', 'ES表资源', '465158', '100019', '2018-06-25 14:54:28', '470311', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('471300', '测试目录_gyf3', '0', '100019', '2018-06-25 14:54:28', '471300', '471300471300', '1');
INSERT INTO `tb_stdgl_category` VALUES ('6b4f6613e11c41e5820fe8ef7748a3c2', '嘉兴', '471300', 'admin', '2018-06-15 10:58:56', 'JX', '471300JXJX', '1');
INSERT INTO `tb_stdgl_category` VALUES ('749aa4671b474790a3aea5c13388b077', '调试3', '0', 'admin', '2018-06-13 20:02:03', 'ne', 'new_node2', null);
INSERT INTO `tb_stdgl_category` VALUES ('783fb9a431ed49d89eb5fb226202ecfe', '大连', '0', 'admin', '2018-06-19 08:35:19', 'DL', 'new_node7', null);
INSERT INTO `tb_stdgl_category` VALUES ('8830123', '长春', '0', 'admin', '2018-06-15 09:43:26', 'CC', 'CCCC', null);
INSERT INTO `tb_stdgl_category` VALUES ('a946dd9158004139a5191261be11778c', '大兴', '8830123', 'admin', '2018-06-05 09:48:24', 'DX', 'BJZZX', null);
INSERT INTO `tb_stdgl_category` VALUES ('e581fb4621994771a70e3796b2154bbe', '哈尔滨', '0', 'admin', '2018-06-15 10:58:57', 'HEB', 'new_node4', null);
INSERT INTO `tb_stdgl_category` VALUES ('WX478837', '华北地区', '0', '100019', '2018-06-25 14:54:30', 'WX478837', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('WX478838', '数据库资源', 'WX478837', '100019', '2018-06-25 14:54:30', 'WX478838', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('WX478857', '表资源', 'WX478837', '100019', '2018-06-25 14:54:31', 'WX478857', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('WX479398', '表资源迁移目录', 'WX478837', '100019', '2018-06-25 14:54:35', 'WX479398', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('WX479562', '系统定时流程', '447283', '100019', '2018-06-25 14:54:17', 'WX479562', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('WX480303', '映射测试_whl', '447283', '100019', '2018-06-25 14:54:17', 'WX480303', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('WX480315', '工作流资源_whl', '447283', '100019', '2018-06-25 14:54:16', 'WX480315', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('WX483213', 'mysql视图资源', '465158', '100019', '2018-06-25 14:54:28', 'WX483213', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('WX484695', '数据库资源2', 'WX478837', '100019', '2018-06-25 14:54:31', 'WX484695', null, null);
INSERT INTO `tb_stdgl_category` VALUES ('WX486708', '视图资源', 'WX478837', '100019', '2018-06-25 14:54:36', 'WX486708', null, null);

-- ----------------------------
-- Table structure for tb_stdgl_code_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_stdgl_code_info`;
CREATE TABLE `tb_stdgl_code_info` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '编号',
  `database_name` varchar(160) DEFAULT NULL COMMENT '数据库名称',
  `table_cname` varchar(160) DEFAULT NULL COMMENT '表中文名称',
  `table_ename` varchar(160) DEFAULT NULL COMMENT '表英文名称',
  `code_cname` varchar(80) DEFAULT NULL COMMENT '代码中文名称',
  `code_ename` varchar(80) NOT NULL COMMENT '代码英文名称',
  `code_value` varchar(16) NOT NULL COMMENT '代码值',
  `value_type` char(1) NOT NULL COMMENT '值类型',
  `remark` varchar(2048) DEFAULT NULL COMMENT '备注',
  `auth_type` varchar(16) NOT NULL COMMENT '审核类型',
  `is_auth` varchar(16) NOT NULL COMMENT '审核状态',
  `auth_remark` varchar(1024) DEFAULT NULL COMMENT '审核意见',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` char(8) DEFAULT NULL COMMENT '创建日期',
  `create_time` char(6) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_stdgl_request
-- ----------------------------
DROP TABLE IF EXISTS `tb_stdgl_request`;
CREATE TABLE `tb_stdgl_request` (
  `id` varchar(32) NOT NULL,
  `type` varchar(32) DEFAULT NULL,
  `resource_id` varchar(32) DEFAULT NULL,
  `resource_content` text,
  `request_time` datetime DEFAULT NULL,
  `request_person` varchar(128) DEFAULT NULL,
  `audit_time` datetime DEFAULT NULL,
  `audit_person` varchar(128) DEFAULT NULL,
  `audit_result` varchar(128) DEFAULT NULL,
  `audit_message` text,
  `resource_code` varchar(256) DEFAULT NULL,
  `fun_type` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_stdgl_request
-- ----------------------------
INSERT INTO `tb_stdgl_request` VALUES ('0cb9f98e33a042268af750e1fb61335f', '修改', '56ef51ae0dd045a6ae32f14e36dab1af', '{\"code\":\"tra1\",\"createPerson\":\"\",\"description\":\"火车标准\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":null,\"enName\":\"train1\",\"name\":\"火车1\",\"id\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"num1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"4字节\",\"maxContains\":1,\"type\":\"整数\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义该型号火车头目前的生产数量\",\"enName\":\"number1\",\"name\":\"生产数量1\",\"id\":\"70b4774f4cfc446a8fe395e42c552117\",\"maxsize\":4,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"dat1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"\",\"maxContains\":1,\"type\":\"日期\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义火车定型时间\",\"enName\":\"date1\",\"name\":\"定型时间1\",\"id\":\"794f7a2f015743acb884eab7382694f2\",\"maxsize\":0,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"typ1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"最长32字节\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"o\",\"security\":\"\",\"defination\":\"定义火车头类型\",\"enName\":\"type1\",\"name\":\"型号1\",\"id\":\"b5e9376fd63147469ac33dedf9bf152d\",\"maxsize\":32,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"com1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"最长32字节\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义火车头生产的公司\",\"enName\":\"company1\",\"name\":\"生产公司1\",\"id\":\"bfe64508add1455785610f3e43898ab2\",\"maxsize\":32,\"state\":\"\",\"primary\":false}],\"dataSource\":\"\",\"updatePerson\":\"\",\"categoryId\":\"596958\"}', '2019-08-13 01:23:10', 'admin', '2019-08-15 01:16:25', '', '通过审核', '\"111\"', 'tra1', '');
INSERT INTO `tb_stdgl_request` VALUES ('34f790b4f3cc459aaeffee88f19c49f9', '新增', '8e29bd3a8a57465083f3fcec1b90558f', '{\"code\":\"22222\",\"createPerson\":\"\",\"description\":\"222\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":15,\"hours\":10,\"seconds\":15,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":10,\"time\":1565835015473,\"day\":4},\"enName\":\"2\",\"name\":\"222\",\"id\":\"8e29bd3a8a57465083f3fcec1b90558f\",\"fields\":[],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-15 02:10:16', 'admin', '2019-08-15 02:10:33', '', '通过审核', '\"2222\"', '22222', '');
INSERT INTO `tb_stdgl_request` VALUES ('374aa08c80ce4a0e8ebf863f8d10b30e', '修改', '56ef51ae0dd045a6ae32f14e36dab1af', '{\"code\":\"tra1\",\"createPerson\":\"\",\"description\":\"火车标准\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":null,\"enName\":\"train1\",\"name\":\"火车1改\",\"id\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"num1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"4字节\",\"maxContains\":1,\"type\":\"整数\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义该型号火车头目前的生产数量\",\"enName\":\"number1\",\"name\":\"生产数量1\",\"id\":\"70b4774f4cfc446a8fe395e42c552117\",\"maxsize\":4,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"dat1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"\",\"maxContains\":1,\"type\":\"日期\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义火车定型时间\",\"enName\":\"date1\",\"name\":\"定型时间1\",\"id\":\"794f7a2f015743acb884eab7382694f2\",\"maxsize\":0,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"typ1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"最长32字节\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"o\",\"security\":\"\",\"defination\":\"定义火车头类型\",\"enName\":\"type1\",\"name\":\"型号1\",\"id\":\"b5e9376fd63147469ac33dedf9bf152d\",\"maxsize\":32,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"com1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"最长32字节\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义火车头生产的公司\",\"enName\":\"company1\",\"name\":\"生产公司1\",\"id\":\"bfe64508add1455785610f3e43898ab2\",\"maxsize\":32,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-16 01:20:04', 'admin', '2019-08-16 01:21:00', '', '通过审核', '\"213\"', 'tra1', null);
INSERT INTO `tb_stdgl_request` VALUES ('3e0d70d15b8b4dc38b823d2960b1117f', '新增', 'c87a7493e0cc438b8f9962894c27c95c', '{\"code\":\"11\",\"createPerson\":\"\",\"description\":\"11232\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":47,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":18,\"time\":1565659127401,\"day\":2},\"enName\":\"2\",\"name\":\"dd\",\"id\":\"c87a7493e0cc438b8f9962894c27c95c\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"c87a7493e0cc438b8f9962894c27c95c\",\"code\":\"3\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"1\",\"maxContains\":1,\"type\":\"单精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"6\",\"enName\":\"4\",\"name\":\"5\",\"id\":\"2fa407628e5545e4958043561b8190f5\",\"maxsize\":7,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 01:18:47', 'admin', '2019-08-13 01:45:12', '', '通过审核', '\"111\"', '11', '');
INSERT INTO `tb_stdgl_request` VALUES ('3f47b9531f8d42a996eba55bf07d60e6', '修改', 'c92c59bee49e420cb6b2b179a7de3e03', '{\"code\":\"33\",\"createPerson\":\"\",\"description\":\"111\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":46,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":54,\"time\":1565661286000,\"day\":2},\"enName\":\"1333\",\"name\":\"111\",\"id\":\"c92c59bee49e420cb6b2b179a7de3e03\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"\",\"code\":\"1\",\"comments\":\"5\",\"defaultValue\":\"\",\"range\":\"5\",\"maxContains\":1,\"type\":\"双精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"field\",\"enName\":\"2\",\"name\":\"4\",\"id\":\"f219e4797e594cccb89be1921f3a03b3\",\"maxsize\":5,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 01:59:40', 'admin', '2019-08-13 02:19:18', '', '拒绝审核', '\"\"', '33', '');
INSERT INTO `tb_stdgl_request` VALUES ('3fcc12d09afa4eeaac4b8b75550a7fc5', '修改', '56ef51ae0dd045a6ae32f14e36dab1af', '{\"code\":\"tra1\",\"createPerson\":\"\",\"description\":\"火车标准\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":null,\"enName\":\"train1\",\"name\":\"火车1\",\"id\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"num1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"4字节\",\"maxContains\":1,\"type\":\"整数\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义该型号火车头目前的生产数量\",\"enName\":\"number1\",\"name\":\"生产数量1\",\"id\":\"70b4774f4cfc446a8fe395e42c552117\",\"maxsize\":4,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"dat1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"\",\"maxContains\":1,\"type\":\"日期\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义火车定型时间\",\"enName\":\"date1\",\"name\":\"定型时间1\",\"id\":\"794f7a2f015743acb884eab7382694f2\",\"maxsize\":0,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"typ1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"最长32字节\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"o\",\"security\":\"\",\"defination\":\"定义火车头类型\",\"enName\":\"type1\",\"name\":\"型号1\",\"id\":\"b5e9376fd63147469ac33dedf9bf152d\",\"maxsize\":32,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"com1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"最长32字节\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义火车头生产的公司\",\"enName\":\"company1\",\"name\":\"生产公司1\",\"id\":\"bfe64508add1455785610f3e43898ab2\",\"maxsize\":32,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-16 01:19:20', 'admin', '2019-08-16 01:20:53', '', '通过审核', '\"231\"', 'tra1', null);
INSERT INTO `tb_stdgl_request` VALUES ('460e990581a34e57a90b8a672765fe27', '新增', 'c92c59bee49e420cb6b2b179a7de3e03', '{\"code\":\"11\",\"createPerson\":\"\",\"description\":\"111\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":45,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":54,\"time\":1565661285802,\"day\":2},\"enName\":\"11\",\"name\":\"111\",\"id\":\"c92c59bee49e420cb6b2b179a7de3e03\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"c92c59bee49e420cb6b2b179a7de3e03\",\"code\":\"1\",\"comments\":\"5\",\"defaultValue\":\"\",\"range\":\"5\",\"maxContains\":1,\"type\":\"双精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"field\",\"enName\":\"2\",\"name\":\"4\",\"id\":\"f219e4797e594cccb89be1921f3a03b3\",\"maxsize\":5,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 01:54:46', 'admin', '2019-08-13 01:55:00', '', '通过审核', '\"111\"', '11', '');
INSERT INTO `tb_stdgl_request` VALUES ('46c2f407161f4857a8054130362a3024', '修改', 'c92c59bee49e420cb6b2b179a7de3e03', '{\"code\":\"333333\",\"createPerson\":\"\",\"description\":\"111\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":46,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":54,\"time\":1565661286000,\"day\":2},\"enName\":\"33333\",\"name\":\"3333\",\"id\":\"c92c59bee49e420cb6b2b179a7de3e03\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"\",\"code\":\"1\",\"comments\":\"5\",\"defaultValue\":\"\",\"range\":\"5\",\"maxContains\":1,\"type\":\"双精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"field\",\"enName\":\"2\",\"name\":\"4\",\"id\":\"f219e4797e594cccb89be1921f3a03b3\",\"maxsize\":5,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 01:58:37', 'admin', '2019-08-14 13:38:52', '', '拒绝审核', '\"112dsadas\"', '333333', '');
INSERT INTO `tb_stdgl_request` VALUES ('4f201f0c80164abb82ffc1a39d5d73d5', '新增', '265c96ff4937458c8ba7c8e2c5e65560', '{\"code\":\"测试1\",\"createPerson\":\"\",\"description\":\"1111\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":15,\"hours\":10,\"seconds\":43,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":26,\"time\":1565836003395,\"day\":4},\"enName\":\"test1\",\"name\":\"测试1名称\",\"id\":\"265c96ff4937458c8ba7c8e2c5e65560\",\"fields\":[],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-15 02:26:43', 'admin', null, '', '等待审核', '', '测试1', '');
INSERT INTO `tb_stdgl_request` VALUES ('634ca59ea77c4ad99389abf88ce06b0e', '新增', '5250f3b7fc2c41889f9f391975138ec9', '{\"code\":\"1001\",\"createPerson\":\"\",\"description\":\"新标准，用于测试标准录入功能\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":16,\"hours\":14,\"seconds\":22,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":58,\"time\":1565938702309,\"day\":5},\"enName\":\"new_standard\",\"name\":\"新标准\",\"id\":\"5250f3b7fc2c41889f9f391975138ec9\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"5250f3b7fc2c41889f9f391975138ec9\",\"code\":\"100101\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"0\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"false\",\"security\":\"\",\"defination\":\"新定义\",\"enName\":\"new_field\",\"name\":\"新属性\",\"id\":\"c662c49618864d71bc767e08ac2ecc7d\",\"maxsize\":1024,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-16 06:58:22', 'admin', '2019-08-16 06:58:47', '', '通过审核', '\"新标准审核通过\"', '1001', null);
INSERT INTO `tb_stdgl_request` VALUES ('66eff63e58ec4cb3b5dd2887b6ec9ed9', '新增', '38a94f23406e4f6795d3ea373b3fcd40', '{\"code\":\"newstd\",\"createPerson\":\"\",\"description\":\"\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":16,\"hours\":9,\"seconds\":13,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":17,\"time\":1565918233886,\"day\":5},\"enName\":\"newstd\",\"name\":\"新标准\",\"id\":\"38a94f23406e4f6795d3ea373b3fcd40\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"38a94f23406e4f6795d3ea373b3fcd40\",\"code\":\"field1\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"0\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"false\",\"security\":\"\",\"defination\":\"field\",\"enName\":\"field1\",\"name\":\"field1\",\"id\":\"50e9db26d470478b89f48aa664e21e85\",\"maxsize\":0,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"38a94f23406e4f6795d3ea373b3fcd40\",\"code\":\"field2\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"0\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"false\",\"security\":\"\",\"defination\":\"field2\",\"enName\":\"field2\",\"name\":\"field2\",\"id\":\"d93899d44b3e4b29b5fdbba857d048fb\",\"maxsize\":0,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-16 01:17:14', 'admin', '2019-08-16 01:19:01', '', '通过审核', '\"222\"', 'newstd', null);
INSERT INTO `tb_stdgl_request` VALUES ('6866e4ff6db3440fab97f39a769de2c7', '修改', '56ef51ae0dd045a6ae32f14e36dab1af', '{\"code\":\"tra1\",\"createPerson\":\"\",\"description\":\"火车标准\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":null,\"enName\":\"train1\",\"name\":\"火车1\",\"id\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"num1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"4字节\",\"maxContains\":1,\"type\":\"整数\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义该型号火车头目前的生产数量\",\"enName\":\"number1\",\"name\":\"生产数量1\",\"id\":\"70b4774f4cfc446a8fe395e42c552117\",\"maxsize\":4,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"dat1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"\",\"maxContains\":1,\"type\":\"日期\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义火车定型时间\",\"enName\":\"date1\",\"name\":\"定型时间1\",\"id\":\"794f7a2f015743acb884eab7382694f2\",\"maxsize\":0,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"typ1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"最长32字节\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"o\",\"security\":\"\",\"defination\":\"定义火车头类型\",\"enName\":\"type1\",\"name\":\"型号1\",\"id\":\"b5e9376fd63147469ac33dedf9bf152d\",\"maxsize\":32,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"com1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"最长32字节\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义火车头生产的公司\",\"enName\":\"company1\",\"name\":\"生产公司1\",\"id\":\"bfe64508add1455785610f3e43898ab2\",\"maxsize\":32,\"state\":\"\",\"primary\":false}],\"dataSource\":\"\",\"updatePerson\":\"\",\"categoryId\":\"596958\"}', '2019-08-13 01:24:37', 'admin', '2019-08-15 01:10:58', '', '通过审核', '\"111\"', 'tra1', '');
INSERT INTO `tb_stdgl_request` VALUES ('772422fcb04f48ebaff33e142cce8584', '修改', 'c92c59bee49e420cb6b2b179a7de3e03', '{\"code\":\"44444\",\"createPerson\":\"\",\"description\":\"444\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":46,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":54,\"time\":1565661286000,\"day\":2},\"enName\":\"444\",\"name\":\"444\",\"id\":\"c92c59bee49e420cb6b2b179a7de3e03\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"c92c59bee49e420cb6b2b179a7de3e03\",\"code\":\"1\",\"comments\":\"5\",\"defaultValue\":\"\",\"range\":\"5\",\"maxContains\":1,\"type\":\"双精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"field\",\"enName\":\"2\",\"name\":\"4\",\"id\":\"f219e4797e594cccb89be1921f3a03b3\",\"maxsize\":5,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 01:59:01', 'admin', '2019-08-13 12:57:08', '', '通过审核', '\"11\"', '44444', '');
INSERT INTO `tb_stdgl_request` VALUES ('80c165e0c6f74020a145d5418c0f7aae', '新增', '50413309ad6342ceb1a8a4ac05c26d6e', '{\"code\":\"1\",\"createPerson\":\"\",\"description\":\"333\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":54,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":12,\"time\":1565658774240,\"day\":2},\"enName\":\"22\",\"name\":\"33\",\"id\":\"50413309ad6342ceb1a8a4ac05c26d6e\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"\",\"code\":\"3\",\"comments\":\"??\",\"defaultValue\":\"\",\"range\":\"7\",\"maxContains\":1,\"type\":\"单精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"6\",\"enName\":\"5\",\"name\":\"field\",\"id\":\"\",\"maxsize\":7,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 01:12:54', 'admin', '2019-08-15 02:08:04', '', '拒绝审核', '\"111\"', '1', '');
INSERT INTO `tb_stdgl_request` VALUES ('86f32405823743d48eefeb8a3766ce36', '修改', 'c92c59bee49e420cb6b2b179a7de3e03', '{\"code\":\"3333\",\"createPerson\":\"\",\"description\":\"111\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":46,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":54,\"time\":1565661286000,\"day\":2},\"enName\":\"3333\",\"name\":\"3333\",\"id\":\"c92c59bee49e420cb6b2b179a7de3e03\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"c92c59bee49e420cb6b2b179a7de3e03\",\"code\":\"1\",\"comments\":\"5\",\"defaultValue\":\"\",\"range\":\"5\",\"maxContains\":1,\"type\":\"双精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"field\",\"enName\":\"2\",\"name\":\"4\",\"id\":\"f219e4797e594cccb89be1921f3a03b3\",\"maxsize\":5,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 02:01:54', 'admin', '2019-08-13 07:30:47', '', '通过审核', '\"111\"', '3333', '');
INSERT INTO `tb_stdgl_request` VALUES ('97af73509b8746f1b3ee68780de69697', '修改', '56ef51ae0dd045a6ae32f14e36dab1af', '{\"code\":\"tra1\",\"createPerson\":\"\",\"description\":\"火车标准\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":null,\"enName\":\"train1\",\"name\":\"火车1\",\"id\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"num1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"4字节\",\"maxContains\":1,\"type\":\"整数\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义该型号火车头目前的生产数量\",\"enName\":\"number1\",\"name\":\"生产数量1\",\"id\":\"70b4774f4cfc446a8fe395e42c552117\",\"maxsize\":4,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"dat1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"\",\"maxContains\":1,\"type\":\"日期\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义火车定型时间\",\"enName\":\"date1\",\"name\":\"定型时间1\",\"id\":\"794f7a2f015743acb884eab7382694f2\",\"maxsize\":0,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"typ1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"最长32字节\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"o\",\"security\":\"\",\"defination\":\"定义火车头类型\",\"enName\":\"type1\",\"name\":\"型号1\",\"id\":\"b5e9376fd63147469ac33dedf9bf152d\",\"maxsize\":32,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"56ef51ae0dd045a6ae32f14e36dab1af\",\"code\":\"com1\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"最长32字节\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义火车头生产的公司\",\"enName\":\"company1\",\"name\":\"生产公司1\",\"id\":\"bfe64508add1455785610f3e43898ab2\",\"maxsize\":32,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-16 01:14:26', 'admin', '2019-08-16 01:14:34', '', '通过审核', '\"77\"', 'tra1', null);
INSERT INTO `tb_stdgl_request` VALUES ('a9dde6fcad2344d3a3c481a3b7a18dde', '新增', '2e457dd327fa475fb88411f844a710ba', '{\"code\":\"ceshiccc\",\"createPerson\":\"\",\"description\":\"\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":15,\"hours\":10,\"seconds\":38,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":30,\"time\":1565836238421,\"day\":4},\"enName\":\"s\",\"name\":\"dsad\",\"id\":\"2e457dd327fa475fb88411f844a710ba\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"\",\"code\":\"1\",\"comments\":\"asdasdas\",\"defaultValue\":\"\",\"range\":\"3\",\"maxContains\":1,\"type\":\"单精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"4\",\"enName\":\"2\",\"name\":\"3\",\"id\":\"\",\"maxsize\":5,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-15 02:30:38', 'admin', '2019-08-16 06:47:58', '', '拒绝审核', '\"\"', 'ceshiccc', '');
INSERT INTO `tb_stdgl_request` VALUES ('aec7ff1e9e4b421ba6491df5ca9332ff', '修改', 'aaec5cde497e43fabb08b40bf2ae8135', '{\"code\":\"tra\",\"createPerson\":\"\",\"description\":\"火车标准\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":null,\"enName\":\"train\",\"name\":\"火车2\",\"id\":\"aaec5cde497e43fabb08b40bf2ae8135\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"aaec5cde497e43fabb08b40bf2ae8135\",\"code\":\"com\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"最长32字节\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义火车头生产的公司\",\"enName\":\"company\",\"name\":\"生产公司\",\"id\":\"961720b4547d4f78ba35e96e8beecdb8\",\"maxsize\":32,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"aaec5cde497e43fabb08b40bf2ae8135\",\"code\":\"typ\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"最长32字节\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"o\",\"security\":\"\",\"defination\":\"定义火车头类型\",\"enName\":\"type\",\"name\":\"型号\",\"id\":\"a4e7be8f72bf42688c49c01309e97b7f\",\"maxsize\":32,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"aaec5cde497e43fabb08b40bf2ae8135\",\"code\":\"dat\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"\",\"maxContains\":1,\"type\":\"日期\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义火车定型时间\",\"enName\":\"date\",\"name\":\"定型时间\",\"id\":\"c1b7d74e205a422ab45c1ef6fb315a62\",\"maxsize\":0,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"aaec5cde497e43fabb08b40bf2ae8135\",\"code\":\"num\",\"comments\":\"无\",\"defaultValue\":\"\",\"range\":\"4字节\",\"maxContains\":1,\"type\":\"整数\",\"required\":\"O\",\"security\":\"\",\"defination\":\"定义该型号火车头目前的生产数量\",\"enName\":\"number\",\"name\":\"生产数量\",\"id\":\"c7b2f500115046759c6a9ba638f8c0df\",\"maxsize\":6,\"state\":\"\",\"primary\":false}],\"dataSource\":\"缺省目录\",\"updatePerson\":\"\",\"categoryId\":\"019a1df7879341d89ff27b23f87b8a20\"}', '2019-08-13 00:35:02', 'admin', '2019-08-15 01:18:42', '', '通过审核', '\"111\"', 'tra', '');
INSERT INTO `tb_stdgl_request` VALUES ('b2c4af43a63147b3b87d6fdbc350d3b8', '修改', '38a94f23406e4f6795d3ea373b3fcd40', '{\"code\":\"newstd_edit\",\"createPerson\":\"\",\"description\":\"\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":16,\"hours\":9,\"seconds\":14,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":17,\"time\":1565918234000,\"day\":5},\"enName\":\"newstd_edit\",\"name\":\"新标准_修改\",\"id\":\"38a94f23406e4f6795d3ea373b3fcd40\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"38a94f23406e4f6795d3ea373b3fcd40\",\"code\":\"field1_edit\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"0\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"false\",\"security\":\"\",\"defination\":\"field\",\"enName\":\"field1\",\"name\":\"field1\",\"id\":\"50e9db26d470478b89f48aa664e21e85\",\"maxsize\":0,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"38a94f23406e4f6795d3ea373b3fcd40\",\"code\":\"field2_edit\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"0\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"false\",\"security\":\"\",\"defination\":\"field2\",\"enName\":\"field2\",\"name\":\"field2\",\"id\":\"d93899d44b3e4b29b5fdbba857d048fb\",\"maxsize\":0,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-16 06:59:35', 'admin', '2019-08-16 07:00:04', '', '通过审核', '\"标准修改审核通过\"', 'newstd_edit', null);
INSERT INTO `tb_stdgl_request` VALUES ('b6eadf96c17342a7ba3b0978cd9aec86', '修改', 'c87a7493e0cc438b8f9962894c27c95c', '{\"code\":\"11\",\"createPerson\":\"\",\"description\":\"11232\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":47,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":18,\"time\":1565659127000,\"day\":2},\"enName\":\"2\",\"name\":\"dd\",\"id\":\"c87a7493e0cc438b8f9962894c27c95c\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"c87a7493e0cc438b8f9962894c27c95c\",\"code\":\"3\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"1\",\"maxContains\":1,\"type\":\"单精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"6\",\"enName\":\"4\",\"name\":\"5\",\"id\":\"2fa407628e5545e4958043561b8190f5\",\"maxsize\":7,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 01:47:15', 'admin', '2019-08-14 14:01:33', '', '通过审核', '\"111\"', '11', '');
INSERT INTO `tb_stdgl_request` VALUES ('c453e32f278547428b0a606f47608394', '新增', 'c6872ef47b6b44c7a45c6f371eab6424', '{\"code\":\"11\",\"createPerson\":\"\",\"description\":\"33232\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":21,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":14,\"time\":1565658861112,\"day\":2},\"enName\":\"22\",\"name\":\"343\",\"id\":\"c6872ef47b6b44c7a45c6f371eab6424\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"c6872ef47b6b44c7a45c6f371eab6424\",\"code\":\"2\",\"comments\":\"a\",\"defaultValue\":\"\",\"range\":\"6\",\"maxContains\":1,\"type\":\"单精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"5\",\"enName\":\"3\",\"name\":\"4\",\"id\":\"ac82e9324f11413ca54865db1014f27d\",\"maxsize\":6,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 01:14:21', 'admin', '2019-08-15 01:18:15', '', '通过审核', '\"通过\"', '11', '');
INSERT INTO `tb_stdgl_request` VALUES ('ce58be222c4f425d9dcf5df8f13a2108', '新增', '6d1fc69ed39f40059e5a3bbf56d10da2', '{\"code\":\"ceshiccc\",\"createPerson\":\"\",\"description\":\"ccc\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":15,\"hours\":10,\"seconds\":51,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":29,\"time\":1565836191285,\"day\":4},\"enName\":\"ccc\",\"name\":\"ccc\",\"id\":\"6d1fc69ed39f40059e5a3bbf56d10da2\",\"fields\":[],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-15 02:29:51', 'admin', null, '', '等待审核', '', 'ceshiccc', '');
INSERT INTO `tb_stdgl_request` VALUES ('d223d28fb8584d848a1152af79ca7ce9', '修改', 'c87a7493e0cc438b8f9962894c27c95c', '{\"code\":\"3333\",\"createPerson\":\"\",\"description\":\"11232\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":47,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":18,\"time\":1565659127000,\"day\":2},\"enName\":\"33\",\"name\":\"333\",\"id\":\"c87a7493e0cc438b8f9962894c27c95c\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"c87a7493e0cc438b8f9962894c27c95c\",\"code\":\"3\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"1\",\"maxContains\":1,\"type\":\"单精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"6\",\"enName\":\"4\",\"name\":\"5\",\"id\":\"2fa407628e5545e4958043561b8190f5\",\"maxsize\":7,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 01:45:49', 'admin', '2019-08-14 14:11:07', '', '通过审核', '\"111\"', '3333', '');
INSERT INTO `tb_stdgl_request` VALUES ('d2da775af29149328e516ff622038056', '删除', '6f49b3ea49b34c11958b19a7db0aa82e', '{\"code\":\"1\",\"createPerson\":\"\",\"description\":\"2\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":40,\"month\":7,\"nanos\":0,\"timezoneOffset\":-480,\"year\":119,\"minutes\":15,\"time\":1565658940000,\"day\":2},\"enName\":\"1\",\"name\":\"1\",\"id\":\"6f49b3ea49b34c11958b19a7db0aa82e\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"6f49b3ea49b34c11958b19a7db0aa82e\",\"code\":\"3\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"6\",\"maxContains\":1,\"type\":\"单精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"6\",\"enName\":\"4\",\"name\":\"5\",\"id\":\"c85ebeeb401d425d9931808838c913fd\",\"maxsize\":6,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-16 07:00:19', 'admin', '2019-08-16 07:00:37', '', '拒绝审核', '\"标准删除拒绝\"', '1', null);
INSERT INTO `tb_stdgl_request` VALUES ('d569a9b8e91d42059223d2818db63668', '新增', 'f46014c4e1d346329a3054ab9cc3c20c', '{\"code\":\"newstd1\",\"createPerson\":\"\",\"description\":\"\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":16,\"hours\":9,\"seconds\":32,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":18,\"time\":1565918312004,\"day\":5},\"enName\":\"newstd1\",\"name\":\"新标准1\",\"id\":\"f46014c4e1d346329a3054ab9cc3c20c\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"f46014c4e1d346329a3054ab9cc3c20c\",\"code\":\"field\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"0\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"false\",\"security\":\"\",\"defination\":\"field\",\"enName\":\"field\",\"name\":\"field\",\"id\":\"aa8e18472ff04930bae5e03c630b33fe\",\"maxsize\":0,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"f46014c4e1d346329a3054ab9cc3c20c\",\"code\":\"field2\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"0\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"false\",\"security\":\"\",\"defination\":\"field\",\"enName\":\"field2\",\"name\":\"field2\",\"id\":\"5f8f1580425b4f068dd93e863e104993\",\"maxsize\":0,\"state\":\"\",\"primary\":false},{\"pxh\":0,\"schemacodeId\":\"f46014c4e1d346329a3054ab9cc3c20c\",\"code\":\"field3\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"0\",\"maxContains\":1,\"type\":\"字符串\",\"required\":\"false\",\"security\":\"\",\"defination\":\"field\",\"enName\":\"field3\",\"name\":\"field3\",\"id\":\"7fdadbc8ddf74554b8f6c00287ec66a8\",\"maxsize\":0,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-16 01:18:32', 'admin', '2019-08-16 01:18:58', '', '通过审核', '\"111\"', 'newstd1', null);
INSERT INTO `tb_stdgl_request` VALUES ('d8546eae4d9445b894adec50e499857c', '新增', 'c349b28265d54a67a2c7be18378df840', '{\"code\":\"111\",\"createPerson\":\"\",\"description\":\"11111\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":50,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":11,\"time\":1565658710445,\"day\":2},\"enName\":\"2321\",\"name\":\"21321\",\"id\":\"c349b28265d54a67a2c7be18378df840\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"c349b28265d54a67a2c7be18378df840\",\"code\":\"2\",\"comments\":\"sss\",\"defaultValue\":\"\",\"range\":\"3\",\"maxContains\":1,\"type\":\"双精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"4\",\"enName\":\"2\",\"name\":\"3\",\"id\":\"f02964f3fa104366b2787ed80e4ff5b8\",\"maxsize\":222,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 01:11:50', 'admin', '2019-08-15 01:18:51', '', '通过审核', '\"111\"', '111', '');
INSERT INTO `tb_stdgl_request` VALUES ('e9b45a7c4ece4a58b4767a3bab76e50a', '新增', '6f49b3ea49b34c11958b19a7db0aa82e', '{\"code\":\"1\",\"createPerson\":\"\",\"description\":\"2\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":39,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":15,\"time\":1565658939893,\"day\":2},\"enName\":\"1\",\"name\":\"1\",\"id\":\"6f49b3ea49b34c11958b19a7db0aa82e\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"6f49b3ea49b34c11958b19a7db0aa82e\",\"code\":\"3\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"6\",\"maxContains\":1,\"type\":\"单精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"6\",\"enName\":\"4\",\"name\":\"5\",\"id\":\"c85ebeeb401d425d9931808838c913fd\",\"maxsize\":6,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 01:15:40', 'admin', '2019-08-15 01:17:52', '', '通过审核', '\"拒绝\"', '1', '');
INSERT INTO `tb_stdgl_request` VALUES ('ed04fd21dc144535bccca9c3c50f6614', '修改', 'c87a7493e0cc438b8f9962894c27c95c', '{\"code\":\"3334\",\"createPerson\":\"\",\"description\":\"11232\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":47,\"month\":7,\"timezoneOffset\":-480,\"year\":119,\"minutes\":18,\"time\":1565659127000,\"day\":2},\"enName\":\"333\",\"name\":\"333\",\"id\":\"c87a7493e0cc438b8f9962894c27c95c\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"c87a7493e0cc438b8f9962894c27c95c\",\"code\":\"3\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"1\",\"maxContains\":1,\"type\":\"单精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"6\",\"enName\":\"4\",\"name\":\"5\",\"id\":\"2fa407628e5545e4958043561b8190f5\",\"maxsize\":7,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 01:48:01', 'admin', '2019-08-13 01:50:12', '', '通过审核', '\"1111\"', '3334', '');
INSERT INTO `tb_stdgl_request` VALUES ('f93cb10a2c8441e0a42345263076deab', '删除', 'c87a7493e0cc438b8f9962894c27c95c', '{\"code\":\"3334\",\"createPerson\":\"\",\"description\":\"11232\",\"updateTime\":null,\"type\":-1,\"version\":\"\",\"createTime\":{\"date\":13,\"hours\":9,\"seconds\":47,\"month\":7,\"nanos\":0,\"timezoneOffset\":-480,\"year\":119,\"minutes\":18,\"time\":1565659127000,\"day\":2},\"enName\":\"333\",\"name\":\"333\",\"id\":\"c87a7493e0cc438b8f9962894c27c95c\",\"fields\":[{\"pxh\":0,\"schemacodeId\":\"c87a7493e0cc438b8f9962894c27c95c\",\"code\":\"3\",\"comments\":\"field\",\"defaultValue\":\"\",\"range\":\"1\",\"maxContains\":1,\"type\":\"单精度\",\"required\":\"true\",\"security\":\"\",\"defination\":\"6\",\"enName\":\"4\",\"name\":\"5\",\"id\":\"2fa407628e5545e4958043561b8190f5\",\"maxsize\":7,\"state\":\"\",\"primary\":false}],\"dataSource\":\"我的目录\",\"updatePerson\":\"\",\"categoryId\":\"0\"}', '2019-08-13 01:51:15', 'admin', '2019-08-13 01:51:26', '', '通过审核', '\"111\"', '3334', '');

-- ----------------------------
-- Table structure for tb_stdgl_schemafield
-- ----------------------------
DROP TABLE IF EXISTS `tb_stdgl_schemafield`;
CREATE TABLE `tb_stdgl_schemafield` (
  `id` varchar(32) NOT NULL,
  `fieldname` varchar(100) NOT NULL COMMENT '中文名称',
  `enname` varchar(100) DEFAULT NULL COMMENT '英文名称',
  `fieldcode` varchar(100) NOT NULL DEFAULT '' COMMENT '短名',
  `defination` text COMMENT '定义',
  `type` varchar(20) DEFAULT NULL COMMENT '元素数据类型',
  `maxsize` varchar(30) DEFAULT NULL COMMENT '长度',
  `range` text COMMENT '值域',
  `required` varchar(50) DEFAULT NULL COMMENT '是否必填',
  `comments` text COMMENT '备注',
  `maxcontaincount` int(5) DEFAULT '1' COMMENT '最大出现次数',
  `pxh` int(10) DEFAULT '1' COMMENT '排序号，正常排序从1开始，表示object类型的排序号用0标记',
  `schemacodeid` varchar(32) NOT NULL DEFAULT '',
  `code_id` varchar(32) DEFAULT NULL COMMENT '码表编号',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建者',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间，主要用于辅助排序',
  `fieldrange` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_stdgl_schemafield
-- ----------------------------
INSERT INTO `tb_stdgl_schemafield` VALUES ('13fa25b510fd4616bbde95277b92a6a9', '型号', 'model', 'mod', '定义汽车类型', '字符串', '32', null, 'o', '无', '1', '128', '947195be23c04b178062e840a67ddb3d', null, '', null, '最长32字节');
INSERT INTO `tb_stdgl_schemafield` VALUES ('2fa407628e5545e4958043561b8190f5', '5', '4', '3', '6', '单精度', '7', null, 'true', 'field', '1', '0', 'c87a7493e0cc438b8f9962894c27c95c', null, null, null, '1');
INSERT INTO `tb_stdgl_schemafield` VALUES ('30a20e23926b4b2aa179f0dc0adb2780', '生产公司', 'company', 'com', '定义汽车生产的公司', '字符串', '32', null, 'O', '无', '1', '128', '947195be23c04b178062e840a67ddb3d', null, '', null, '最长32字节');
INSERT INTO `tb_stdgl_schemafield` VALUES ('3ea715f6342144759f3db195cfc6e5f3', '类型', 'marking', 'marking', '定义轮船类型', '字符串', '32', null, 'O', '无', '1', '128', '34bd83211ff5456da05b0da7c2dd3137', null, '', null, '最长32字节');
INSERT INTO `tb_stdgl_schemafield` VALUES ('50e9db26d470478b89f48aa664e21e85', 'field1', 'field1', 'field1_edit', 'field', '字符串', '0', null, 'false', 'field', '1', '0', '38a94f23406e4f6795d3ea373b3fcd40', null, null, null, '0');
INSERT INTO `tb_stdgl_schemafield` VALUES ('570422d80d6c45cdb670219961b0bf81', '排水量', 'displacement', 'displace', '定义轮船排水量', '字符串', '32', null, 'o', '无', '1', '128', '34bd83211ff5456da05b0da7c2dd3137', null, '', null, '最长32字节');
INSERT INTO `tb_stdgl_schemafield` VALUES ('5f8f1580425b4f068dd93e863e104993', 'field2', 'field2', 'field2', 'field', '字符串', '0', null, 'false', 'field', '1', '0', 'f46014c4e1d346329a3054ab9cc3c20c', null, null, null, '0');
INSERT INTO `tb_stdgl_schemafield` VALUES ('70b4774f4cfc446a8fe395e42c552117', '生产数量1', 'number1', 'num1', '定义该型号火车头目前的生产数量', '整数', '4', null, 'O', '无', '1', '0', '56ef51ae0dd045a6ae32f14e36dab1af', null, null, null, '4字节');
INSERT INTO `tb_stdgl_schemafield` VALUES ('794f7a2f015743acb884eab7382694f2', '定型时间1', 'date1', 'dat1', '定义火车定型时间', '日期', '0', null, 'O', '无', '1', '0', '56ef51ae0dd045a6ae32f14e36dab1af', null, null, null, '');
INSERT INTO `tb_stdgl_schemafield` VALUES ('7fdadbc8ddf74554b8f6c00287ec66a8', 'field3', 'field3', 'field3', 'field', '字符串', '0', null, 'false', 'field', '1', '0', 'f46014c4e1d346329a3054ab9cc3c20c', null, null, null, '0');
INSERT INTO `tb_stdgl_schemafield` VALUES ('90c0ab248d9e4487b242f17251323e26', '生产公司', 'factory', 'fact', '定义轮船生产的公司', '字符串', '32', null, 'O', '无', '1', '128', '34bd83211ff5456da05b0da7c2dd3137', null, '', null, '最长32字节');
INSERT INTO `tb_stdgl_schemafield` VALUES ('961720b4547d4f78ba35e96e8beecdb8', '生产公司', 'company', 'com', '定义火车头生产的公司', '字符串', '32', null, 'O', '无', '1', '0', 'aaec5cde497e43fabb08b40bf2ae8135', null, null, null, '最长32字节');
INSERT INTO `tb_stdgl_schemafield` VALUES ('9c1cfc1f9320477bb83ec52b64d83da4', '定型时间', 'date', 'dat', '定义汽车定型时间', '日期', '', null, 'O', '无', '1', '128', '947195be23c04b178062e840a67ddb3d', null, '', null, '');
INSERT INTO `tb_stdgl_schemafield` VALUES ('a4e7be8f72bf42688c49c01309e97b7f', '型号', 'type', 'typ', '定义火车头类型', '字符串', '32', null, 'o', '无', '1', '0', 'aaec5cde497e43fabb08b40bf2ae8135', null, null, null, '最长32字节');
INSERT INTO `tb_stdgl_schemafield` VALUES ('aa8e18472ff04930bae5e03c630b33fe', 'field', 'field', 'field', 'field', '字符串', '0', null, 'false', 'field', '1', '0', 'f46014c4e1d346329a3054ab9cc3c20c', null, null, null, '0');
INSERT INTO `tb_stdgl_schemafield` VALUES ('ac82e9324f11413ca54865db1014f27d', '4', '3', '2', '5', '单精度', '6', null, 'true', 'a', '1', '0', 'c6872ef47b6b44c7a45c6f371eab6424', null, null, null, '6');
INSERT INTO `tb_stdgl_schemafield` VALUES ('b5e9376fd63147469ac33dedf9bf152d', '型号1', 'type1', 'typ1', '定义火车头类型', '字符串', '32', null, 'o', '无', '1', '0', '56ef51ae0dd045a6ae32f14e36dab1af', null, null, null, '最长32字节');
INSERT INTO `tb_stdgl_schemafield` VALUES ('ba7287f01494426c8222807b19e6a6e5', '生产数量', 'number', 'num', '定义该型号汽车目前的生产数量', '整数', '4', null, 'O', '无', '1', '128', '947195be23c04b178062e840a67ddb3d', null, '', null, '4字节');
INSERT INTO `tb_stdgl_schemafield` VALUES ('bfe64508add1455785610f3e43898ab2', '生产公司1', 'company1', 'com1', '定义火车头生产的公司', '字符串', '32', null, 'O', '无', '1', '0', '56ef51ae0dd045a6ae32f14e36dab1af', null, null, null, '最长32字节');
INSERT INTO `tb_stdgl_schemafield` VALUES ('c1b7d74e205a422ab45c1ef6fb315a62', '定型时间', 'date', 'dat', '定义火车定型时间', '日期', '0', null, 'O', '无', '1', '0', 'aaec5cde497e43fabb08b40bf2ae8135', null, null, null, '');
INSERT INTO `tb_stdgl_schemafield` VALUES ('c662c49618864d71bc767e08ac2ecc7d', '新属性', 'new_field', '100101', '新定义', '字符串', '1024', null, 'false', 'field', '1', '0', '5250f3b7fc2c41889f9f391975138ec9', null, null, null, '0');
INSERT INTO `tb_stdgl_schemafield` VALUES ('c7b2f500115046759c6a9ba638f8c0df', '生产数量', 'number', 'num', '定义该型号火车头目前的生产数量', '整数', '6', null, 'O', '无', '1', '0', 'aaec5cde497e43fabb08b40bf2ae8135', null, null, null, '4字节');
INSERT INTO `tb_stdgl_schemafield` VALUES ('c85ebeeb401d425d9931808838c913fd', '5', '4', '3', '6', '单精度', '6', null, 'true', 'field', '1', '0', '6f49b3ea49b34c11958b19a7db0aa82e', null, null, null, '6');
INSERT INTO `tb_stdgl_schemafield` VALUES ('d93899d44b3e4b29b5fdbba857d048fb', 'field2', 'field2', 'field2_edit', 'field2', '字符串', '0', null, 'false', 'field', '1', '0', '38a94f23406e4f6795d3ea373b3fcd40', null, null, null, '0');
INSERT INTO `tb_stdgl_schemafield` VALUES ('d97e30312ea649a0bcf5b3fea1377f07', '载员数', 'person_number', 'per_num', '定义该型号轮船载员数', '整数', '4', null, 'O', '无', '1', '128', '34bd83211ff5456da05b0da7c2dd3137', null, '', null, '4字节');
INSERT INTO `tb_stdgl_schemafield` VALUES ('f02964f3fa104366b2787ed80e4ff5b8', '3', '2', '2', '4', '双精度', '222', null, 'true', 'sss', '1', '0', 'c349b28265d54a67a2c7be18378df840', null, null, null, '3');
INSERT INTO `tb_stdgl_schemafield` VALUES ('f219e4797e594cccb89be1921f3a03b3', '4', '2', '1', 'field', '双精度', '5', null, 'true', '5', '1', '0', 'c92c59bee49e420cb6b2b179a7de3e03', null, null, null, '5');

-- ----------------------------
-- Table structure for tb_stdgl_schemamodule
-- ----------------------------
DROP TABLE IF EXISTS `tb_stdgl_schemamodule`;
CREATE TABLE `tb_stdgl_schemamodule` (
  `id` varchar(32) NOT NULL,
  `schemaname` varchar(100) DEFAULT NULL COMMENT '中文名称',
  `enname` varchar(100) DEFAULT NULL COMMENT '英文名称',
  `schemacode` varchar(50) NOT NULL DEFAULT '' COMMENT '短名',
  `description` text COMMENT '定义',
  `datasource` varchar(20) NOT NULL DEFAULT '' COMMENT '数据源',
  `version` varchar(32) DEFAULT NULL COMMENT '版本',
  `type` tinyint(1) DEFAULT '0' COMMENT '对象类型（1表示为子树的根节点，0表示为普通对象）',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建者',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间，主要用于排序',
  `categoryid` varchar(50) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_person` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_schema_code` (`schemacode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_stdgl_schemamodule
-- ----------------------------
INSERT INTO `tb_stdgl_schemamodule` VALUES ('34bd83211ff5456da05b0da7c2dd3137', '轮船', 'steamboat', 'boat', '轮船标准', '轮船', '', '0', '', null, '596958', null, null);
INSERT INTO `tb_stdgl_schemamodule` VALUES ('38a94f23406e4f6795d3ea373b3fcd40', '新标准_修改', 'newstd_edit', 'newstd_edit', '', '我的目录', '', '-1', '', '2019-08-16 01:17:14', '0', null, null);
INSERT INTO `tb_stdgl_schemamodule` VALUES ('5250f3b7fc2c41889f9f391975138ec9', '新标准', 'new_standard', '1001', '新标准，用于测试标准录入功能', '我的目录', '', '-1', '', '2019-08-16 06:58:22', '0', null, null);
INSERT INTO `tb_stdgl_schemamodule` VALUES ('56ef51ae0dd045a6ae32f14e36dab1af', '火车1改', 'train1', 'tra1', '火车标准', '我的目录', '', '-1', '', null, '0', null, null);
INSERT INTO `tb_stdgl_schemamodule` VALUES ('6f49b3ea49b34c11958b19a7db0aa82e', '1', '1', '1', '2', '我的目录', '', '-1', '', '2019-08-13 01:15:40', '0', null, null);
INSERT INTO `tb_stdgl_schemamodule` VALUES ('8e29bd3a8a57465083f3fcec1b90558f', '222', '2', '22222', '222', '我的目录', '', '-1', '', '2019-08-15 02:10:15', '0', null, null);
INSERT INTO `tb_stdgl_schemamodule` VALUES ('947195be23c04b178062e840a67ddb3d', '汽车', 'car', 'car', '汽车标准', '汽车', '', '0', '', null, '596958', null, null);
INSERT INTO `tb_stdgl_schemamodule` VALUES ('aaec5cde497e43fabb08b40bf2ae8135', '火车2', 'train', 'tra', '火车标准', '缺省目录', '', '-1', '', null, '019a1df7879341d89ff27b23f87b8a20', null, null);
INSERT INTO `tb_stdgl_schemamodule` VALUES ('c349b28265d54a67a2c7be18378df840', '21321', '2321', '111', '11111', '我的目录', '', '-1', '', '2019-08-13 01:11:50', '0', null, null);
INSERT INTO `tb_stdgl_schemamodule` VALUES ('c6872ef47b6b44c7a45c6f371eab6424', '343', '22', '11', '33232', '我的目录', '', '-1', '', '2019-08-13 01:14:21', '0', null, null);
INSERT INTO `tb_stdgl_schemamodule` VALUES ('c87a7493e0cc438b8f9962894c27c95c', '333', '33', '3333', '11232', '我的目录', '', '-1', '', '2019-08-13 01:18:47', '0', null, null);
INSERT INTO `tb_stdgl_schemamodule` VALUES ('c92c59bee49e420cb6b2b179a7de3e03', '444', '444', '44444', '444', '我的目录', '', '-1', '', '2019-08-13 01:54:46', '0', null, null);
INSERT INTO `tb_stdgl_schemamodule` VALUES ('f46014c4e1d346329a3054ab9cc3c20c', '新标准1', 'newstd1', 'newstd1', '', '我的目录', '', '-1', '', '2019-08-16 01:18:32', '0', null, null);


-- ----------------------------
-- Table structure for tb_stdmodel_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_stdmodel_category`;
CREATE TABLE `tb_stdmodel_category` (
  `id` varchar(50) NOT NULL COMMENT '目录ID,采用uuid',
  `name` varchar(50) NOT NULL COMMENT '目录节点中文名称',
  `parent_id` varchar(50) DEFAULT '' COMMENT '父目录节点ID',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间。',
  `modelid` varchar(128) DEFAULT '' COMMENT '为模型时，该模型的ID',
  `pxh` int(11) DEFAULT NULL,
  `ismodel` int(1) DEFAULT NULL COMMENT '是否是模型类型',
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_id_unique` (`id`),
  UNIQUE KEY `category_name_unique` (`name`,`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='目录信息表。';

-- ----------------------------
-- Records of tb_stdmodel_category
-- ----------------------------
INSERT INTO `tb_stdmodel_category` VALUES ('0', '我的目录', '-1', 'admin', '2017-12-14 21:05:35', '', null, '0');
INSERT INTO `tb_stdmodel_category` VALUES ('019a1df7879341d89ff27b23f87b8a20', '缺省目录', '447283', 'admin', '2019-04-26 09:38:41', '', '1', null);


DROP TABLE IF EXISTS tb_meta_quality;
CREATE TABLE `tb_meta_quality`(
`id`                                                VARCHAR(32) NOT NULL COMMENT '质量编号', 
`database_name`					    VARCHAR(128) NULL COMMENT '检查数据库名', 
`table_cname`					    VARCHAR(128) NULL COMMENT '检测模型表中文名', 
`table_ename`					    VARCHAR(128) NULL COMMENT '检测模型表英文名', 
`check_cname`                                       VARCHAR(128) NULL COMMENT '检测字段中文名', 
`check_ename`                                       VARCHAR(128) NULL COMMENT '检测字段英文名', 
`check_status`                                      CHAR(1) NOT NULL COMMENT '检测状态', 
`check_type`                                        CHAR(1) NOT NULL COMMENT '检测类型',
`minvalue`                                          DECIMAL(16,6) NULL COMMENT '检测范围最小值',
`maxvalue`                                          DECIMAL(16,6) NULL COMMENT '检测范围最大值',
`total_num`                                         DECIMAL(16) NULL COMMENT '检测总数',
`exception_num`                                     DECIMAL(16) NULL COMMENT '检测异常数',
`total_sql`                                         TEXT NULL COMMENT '检测总数SQL',
`exception_sql`                                     TEXT NULL COMMENT '检测异常数SQL',
`detail_sql`                                        TEXT NULL COMMENT '明细数据SQL',
`detail_path`                                       VARCHAR(1024) NULL COMMENT '明细文件地址',
`create_user`                                       VARCHAR(32) NULL COMMENT '创建人',
`create_date`                                       CHAR(8) NULL COMMENT '创建日期',
`create_time`                                       CHAR(6) NULL COMMENT '创建时间',
PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据标准元数据检测质量表';


-- ----------------------------
-- Table structure for tb_stdgl_dbtype_mapping
-- ----------------------------
DROP TABLE IF EXISTS `tb_stdgl_dbtype_mapping`;
CREATE TABLE `tb_stdgl_dbtype_mapping`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '映射编号',
  `db_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '检查数据库名',
  `driver_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '驱动名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据标准数据源驱动映射表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_stdgl_dbtype_mapping
-- ----------------------------
INSERT INTO `tb_stdgl_dbtype_mapping` VALUES ('1', 'MySQL', 'com.mysql.jdbc.Driver');
INSERT INTO `tb_stdgl_dbtype_mapping` VALUES ('2', 'GBase', 'com.gbase.jdbc.Driver');
INSERT INTO `tb_stdgl_dbtype_mapping` VALUES ('3', 'ODPS', 'com.aliyun.odps.jdbc.OdpsDriver');
INSERT INTO `tb_stdgl_dbtype_mapping` VALUES ('4', 'Hive', 'org.apache.hive.jdbc.HiveDriver');
