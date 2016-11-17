/*
Navicat MySQL Data Transfer

Source Server         : 开发库
Source Server Version : 50536
Source Host           : 172.16.17.117:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50536
File Encoding         : 65001

Date: 2015-09-09 14:02:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `parentId` bigint(20) NOT NULL,
  `productCount` int(11) NOT NULL DEFAULT '0',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userId` bigint(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('8', '家用电器', '0', '0', now(), '1', 'appliances', '0');
INSERT INTO `category` VALUES ('9', '手机通讯', '0', '0', now(), '1', 'sjtx', '0');
INSERT INTO `category` VALUES ('12', '办公用品', '0', '0', now(), '1', 'bgyp', '0');
INSERT INTO `category` VALUES ('13', '家具用品', '0', '0', now(), '1', 'jiajuyongpin', '0');
INSERT INTO `category` VALUES ('14', '衣服', '0', '0', now(), '1', 'yifu', '0');
INSERT INTO `category` VALUES ('15', '个人化妆', '0', '0', now(), '1', 'grhz', '0');
INSERT INTO `category` VALUES ('16', '鞋箱表', '0', '0', now(), '1', 'xxz', '0');
INSERT INTO `category` VALUES ('26', '家用电器', '8', '0', now(), '0', 'jiayongdianqi', '0');
INSERT INTO `category` VALUES ('27', '手机', '9', '0', now(), '0', 'shouji', '0');
INSERT INTO `category` VALUES ('28', '数码', '9', '0', now(), '0', 'shuma', '0');
INSERT INTO `category` VALUES ('29', '通讯', '9', '0', now(), '0', 'tongxun', '0');
INSERT INTO `category` VALUES ('30', '电脑', '12', '0', now(), '0', 'diannao', '0');
INSERT INTO `category` VALUES ('31', '办公', '12', '0', now(), '0', 'bangong', '0');
INSERT INTO `category` VALUES ('32', '家居', '13', '0', now(), '0', 'jiaju', '0');
INSERT INTO `category` VALUES ('33', '家具', '13', '0', now(), '0', 'jiaju2', '0');
INSERT INTO `category` VALUES ('34', '家装', '13', '0', now(), '0', 'jiazhuang', '0');
INSERT INTO `category` VALUES ('35', '厨具', '13', '0', now(), '0', 'chuju', '0');
INSERT INTO `category` VALUES ('36', '男装', '14', '0', now(), '0', 'nanzhuang', '0');
INSERT INTO `category` VALUES ('37', '女装', '14', '0', now(), '0', 'nvzhuang', '0');
INSERT INTO `category` VALUES ('38', '内衣', '14', '0', now(), '0', 'neiyi', '0');
INSERT INTO `category` VALUES ('39', '珠宝', '14', '0', now(), '0', 'zhubao', '0');
INSERT INTO `category` VALUES ('40', '个人化妆', '15', '0', now(), '0', 'gerenhuazhuang', '0');
INSERT INTO `category` VALUES ('41', '鞋靴', '16', '0', now(), '0', 'xiexue', '0');
INSERT INTO `category` VALUES ('42', '箱包', '16', '0', now(), '0', 'xiangbao', '0');
INSERT INTO `category` VALUES ('43', '钟表', '16', '0', now(), '0', 'zhongbiao', '0');
INSERT INTO `category` VALUES ('44', '奢侈品', '16', '0', now(), '0', 'shechipin', '0');
INSERT INTO `category` VALUES ('45', '大家电', '26', '0', now(), '0', 'dajiadian', '0');
INSERT INTO `category` VALUES ('46', '平板电视', '45', '0', now(), '0', 'pingbandianshi', '0');
INSERT INTO `category` VALUES ('47', '空调', '45', '0', now(), '0', 'kongtiao', '0');
INSERT INTO `category` VALUES ('48', '冰箱', '45', '0', now(), '0', 'bingxiang', '0');
INSERT INTO `category` VALUES ('49', '洗衣机', '45', '0', now(), '0', 'xiyiji', '0');
INSERT INTO `category` VALUES ('50', '家庭影院', '45', '0', now(), '0', 'jiatingyingyuan', '0');
INSERT INTO `category` VALUES ('51', '生活电器', '26', '0', now(), '0', 'shenghuodianqi', '0');
INSERT INTO `category` VALUES ('52', '电风扇', '51', '0', now(), '0', 'dianfengshan', '0');
INSERT INTO `category` VALUES ('53', '冷风扇', '51', '0', now(), '0', 'lengfengshan', '0');
INSERT INTO `category` VALUES ('54', '厨房电器', '26', '0', now(), '0', 'chufangdianqi', '0');
INSERT INTO `category` VALUES ('55', '电压力锅', '54', '0', now(), '0', 'dianyaliguo', '0');
INSERT INTO `category` VALUES ('56', '电饭煲', '54', '0', now(), '0', 'dianfanbao', '0');
INSERT INTO `category` VALUES ('57', '手机通讯', '27', '0', now(), '0', 'shoujitongxun', '0');
INSERT INTO `category` VALUES ('58', '摄影摄像', '28', '0', now(), '0', 'sheyingshexiang', '0');
INSERT INTO `category` VALUES ('61', '手机', '57', '0', now(), '0', 'shouji1', '0');
INSERT INTO `category` VALUES ('62', '对讲机', '57', '0', now(), '0', 'duijiangji', '0');
INSERT INTO `category` VALUES ('63', '数码相机', '58', '0', now(), '0', 'shumaxiangji', '0');
INSERT INTO `category` VALUES ('64', '单电/微单相机', '58', '0', now(), '0', 'dandianweidanxiangji', '0');
INSERT INTO `category` VALUES ('65', '手机配件', '27', '0', now(), '0', 'shoujipeijian', '0');
INSERT INTO `category` VALUES ('66', '电池/移动电源', '65', '0', now(), '0', 'dcyddy', '0');
INSERT INTO `category` VALUES ('67', '蓝牙耳机', '65', '0', now(), '0', 'lanyaerji', '0');
INSERT INTO `category` VALUES ('68', '运动户外组', '0', '0', now(), '0', 'ydhwz', '0');
INSERT INTO `category` VALUES ('69', '运动户外', '68', '0', now(), '0', 'yundonghuwai', '0');
INSERT INTO `category` VALUES ('70', '汽车用品组', '0', '0', now(), '0', 'qcypz', '0');
INSERT INTO `category` VALUES ('72', '汽车用品', '70', '0', now(), '0', 'qicheyongpin', '0');
INSERT INTO `category` VALUES ('73', '母婴玩具组', '0', '0', now(), '0', 'mywjz', '0');
INSERT INTO `category` VALUES ('74', '母婴', '73', '0', now(), '0', 'muying', '0');
INSERT INTO `category` VALUES ('75', '玩具乐器', '73', '0', now(), '0', 'wanjuyueqi', '0');
INSERT INTO `category` VALUES ('76', '食品饮料组', '0', '0', now(), '0', 'spylz', '0');
INSERT INTO `category` VALUES ('77', '食品饮料', '76', '0', now(), '0', 'shipinyinliao', '0');
INSERT INTO `category` VALUES ('78', '酒类', '76', '0', now(), '0', 'jiulei', '0');
INSERT INTO `category` VALUES ('79', '生鲜', '76', '0', now(), '0', 'shengxian', '0');
INSERT INTO `category` VALUES ('80', '营养保健', '0', '0', now(), '0', 'yybjz', '0');
INSERT INTO `category` VALUES ('81', '营养保健', '80', '0', now(), '0', 'yingyangbaojian', '0');
INSERT INTO `category` VALUES ('82', '图书音像组', '0', '0', now(), '0', 'tsyxz', '0');
INSERT INTO `category` VALUES ('83', '图书', '82', '0', now(), '0', 'tushu', '0');
INSERT INTO `category` VALUES ('84', '音响', '82', '0', now(), '0', 'yinxiang', '0');
INSERT INTO `category` VALUES ('85', '数字商品', '82', '0', now(), '0', 'shuzishangpin', '0');
INSERT INTO `category` VALUES ('86', '彩票旅行组', '0', '0', now(), '0', 'cplxz', '0');
INSERT INTO `category` VALUES ('87', '彩票', '86', '0', now(), '0', 'caipiao', '0');
INSERT INTO `category` VALUES ('89', '旅行', '86', '0', now(), '0', 'lvxing', '0');
INSERT INTO `category` VALUES ('90', '充值', '86', '0', now(), '0', 'chongzhi', '0');
INSERT INTO `category` VALUES ('91', '票务', '86', '0', now(), '0', 'piaowu', '0');
INSERT INTO `category` VALUES ('93', '理财众筹组', '0', '0', now(), '0', 'lczcz', '0');
INSERT INTO `category` VALUES ('94', '理财', '93', '0', now(), '0', 'licai', '0');
INSERT INTO `category` VALUES ('95', '众筹', '93', '0', now(), '0', 'zhongchou', '0');
INSERT INTO `category` VALUES ('96', '白条', '93', '0', now(), '0', 'baitiao', '0');
INSERT INTO `category` VALUES ('97', '保险', '93', '0', now(), '0', 'baoxian', '0');
INSERT INTO `category` VALUES ('98', '电脑整机', '30', '0', now(), '0', 'diannaozhengji', '0');
INSERT INTO `category` VALUES ('102', '厨具', '32', '0', now(), '0', 'chuju1', '0');
INSERT INTO `category` VALUES ('104', '精品男装', '36', '0', now(), '0', 'jingpinnanz', '0');
INSERT INTO `category` VALUES ('118', '时尚鞋子', '41', '0', now(), '0', 'xiezi', '0');
INSERT INTO `category` VALUES ('119', '户外旅行', '69', '0', now(), '0', 'lvxing1', '0');
INSERT INTO `category` VALUES ('120', '化妆', '40', '0', now(), '0', 'huazhuang1', '0');
