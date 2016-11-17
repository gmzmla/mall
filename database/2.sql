-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.30-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2014-03-30 00:00:53
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;


-- Dumping structure for table mall.areaip
CREATE TABLE IF NOT EXISTS `areaip` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `countryId` bigint(20) DEFAULT NULL,
  `provinceId` bigint(20) DEFAULT NULL,
  `cityId` bigint(20) DEFAULT NULL,
  `areaId` bigint(20) DEFAULT NULL,
  `type` tinyint(4) NOT NULL,
  `start` bigint(20) NOT NULL,
  `end` bigint(20) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.category
CREATE TABLE IF NOT EXISTS `category` (
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.categoryproperty
CREATE TABLE IF NOT EXISTS `categoryproperty` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `property` varchar(60) NOT NULL,
  `categoryId` varchar(50) NOT NULL,
  `dataType` varchar(20) NOT NULL,
  `extend` tinyint(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

CREATE TABLE `categorypropertyvalue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `categoryPropertyId` bigint(20) NOT NULL,
  `name` varchar(200) NOT NULL,
  `code` varchar(140) NOT NULL,			
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



-- Dumping structure for table mall.city
CREATE TABLE IF NOT EXISTS `city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enName` varchar(40) DEFAULT NULL,
  `cnName` varchar(60) DEFAULT NULL,
  `sortId` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `provinceId` bigint(20) DEFAULT NULL,
  `beginLongitude` float DEFAULT NULL,
  `beginLatitude` float DEFAULT NULL,
  `endLongitude` float DEFAULT NULL,
  `endLatitude` float DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_city_sort` (`sortId`),
  KEY `id_city_type` (`type`),
  KEY `id_city_province` (`provinceId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.contact
CREATE TABLE IF NOT EXISTS `contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `name` varchar(60) NOT NULL,
  `mobile` varchar(40) DEFAULT NULL,
  `phone` varchar(40) DEFAULT NULL,
  `countryId` bigint(20) DEFAULT NULL,
  `provinceId` bigint(20) DEFAULT NULL,
  `cityId` bigint(20) DEFAULT NULL,
  `areaId` bigint(20) DEFAULT NULL,
  `address` varchar(200) NOT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `status` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.country
CREATE TABLE IF NOT EXISTS `country` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enName` varchar(20) DEFAULT NULL,
  `cnName` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `ecode` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_country_name` (`enName`),
  KEY `id_country_type` (`type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.menu
CREATE TABLE IF NOT EXISTS `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `url` varchar(200) DEFAULT NULL,
  `type` tinyint(4) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `level` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.order
CREATE TABLE IF NOT EXISTS `order` (
  `id` varchar(40) NOT NULL,
  `productId` bigint(20) DEFAULT NULL,
  `productCount` int(11) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `expressFee` int(11) DEFAULT NULL,
  `coupon` int(11) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `status` tinyint(11) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.orderaddress
CREATE TABLE IF NOT EXISTS `orderaddress` (
  `orderId` varchar(40) NOT NULL,
  `name` varchar(60) DEFAULT NULL,
  `type` tinyint(11) NOT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `address` varchar(140) DEFAULT NULL,
  `cityId` int(11) DEFAULT NULL,
  `areaId` bigint(20) DEFAULT NULL,
  `mail` varchar(100) DEFAULT NULL,
  `contactId` bigint(20) DEFAULT NULL,
  `countryId` bigint(20) DEFAULT NULL,
  `provinceId` bigint(20) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.orderpayment
CREATE TABLE IF NOT EXISTS `orderpayment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `orderId` varchar(40) NOT NULL,
  `paymentOrderId` varchar(40) NOT NULL,
  `status` tinyint(11) NOT NULL,
  `price` int(11) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.orderproduct
CREATE TABLE IF NOT EXISTS `orderproduct` (
  `productId` bigint(20) NOT NULL DEFAULT '0',
  `orderId` varchar(50) NOT NULL,
  `priceGroup` varchar(200) NOT NULL,
  `price` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `smallUrl` varchar(250) DEFAULT NULL,
  `expressFee` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`productId`,`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.product
CREATE TABLE IF NOT EXISTS `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `categoryId` bigint(20) NOT NULL,
  `name` varchar(200) NOT NULL,
  `content` text NOT NULL,
  `productNo` varchar(50) NOT NULL,
  `shopId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `saled` int(11) NOT NULL,
  `grade` int(11) NOT NULL,
  `originalPrice` int(10) DEFAULT NULL,
  `expiryDate` int(11) NOT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `recommend` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.productcomment
CREATE TABLE IF NOT EXISTS `productcomment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `productId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `score` smallint(11) NOT NULL,
  `type` tinyint(11) NOT NULL,
  `comment` varchar(400) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.productimage
CREATE TABLE IF NOT EXISTS `productimage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `productId` bigint(20) NOT NULL DEFAULT '0',
  `imageUrl` varchar(250) NOT NULL,
  `smallUrl` varchar(250) DEFAULT NULL,
  `type` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.productprice
CREATE TABLE IF NOT EXISTS `productprice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `productId` bigint(20) NOT NULL,
  `propertyGroup` varchar(100) NOT NULL,
  `price` int(11) NOT NULL,
  `originalPrice` int(11) NOT NULL DEFAULT '0',
  `inventory` int(11) NOT NULL,
  `saled` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.productproperty
CREATE TABLE IF NOT EXISTS `productproperty` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `productId` bigint(20) NOT NULL,
  `categoryPropertyId` bigint(20) NOT NULL,
  `property` varchar(200) NOT NULL,
  `value` varchar(140) NOT NULL,
  `extend` tinyint(4) NOT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.province
CREATE TABLE IF NOT EXISTS `province` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enName` varchar(40) DEFAULT NULL,
  `cnName` varchar(60) DEFAULT NULL,
  `sortId` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `countryId` bigint(20) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_province_name` (`enName`),
  KEY `id_province_sort` (`sortId`),
  KEY `id_province_type` (`type`),
  KEY `id_province_country` (`countryId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.sequence
CREATE TABLE IF NOT EXISTS `sequence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `start` bigint(20) NOT NULL,
  `end` bigint(20) NOT NULL,
  `step` bigint(20) NOT NULL,
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.shop
CREATE TABLE IF NOT EXISTS `shop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `phone` varchar(60) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userId` varchar(32),
  `grade` smallint(10) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `email` varchar(200) NOT NULL,
  `photoUrl` varchar(200) DEFAULT NULL,
  `smallUrl` varchar(200) DEFAULT NULL,
  `provinceId` bigint(20) DEFAULT NULL,
  `cityId` bigint(20) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `sversion` varchar(20) NOT NULL DEFAULT '0',
  `photoUrl` varchar(200) DEFAULT NULL,
  `smallUrl` varchar(200) DEFAULT NULL,
  `registerTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updateTime` timestamp NULL DEFAULT NULL,
  `lastTime` timestamp NULL DEFAULT NULL,
  `userType` tinyint(11) NOT NULL DEFAULT '0',
  `userStatus` tinyint(11) NOT NULL DEFAULT '0',
  `cityId` int(4) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `qq` varchar(20) DEFAULT NULL,
  `shopId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table mall.usercoupon
CREATE TABLE IF NOT EXISTS `usercoupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `name` varchar(60) NOT NULL,
  `price` int(11) NOT NULL,
  `type` tinyint(11) NOT NULL,
  `remark` varchar(140) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `expiryDate` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- 【购物车】:用户ID 。。
CREATE TABLE `cart`(
 `id` bigint(20) NOT NULL AUTO_INCREMENT,
`userId` bigint(20) NOT NULL,
PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
-- 【购物车商品表】:购物车ID ，商品ID，商品数量
create table `cartProduct`(
`id` bigint(20) NOT NULL AUTO_INCREMENT,
cartId bigint(20) NOT NULL,
productId bigint(20) NOT NULL,
productCount varchar(100) NOT NULL,
tableName varchar(100),
PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
-- 【购物车商品属性表】:属性ID，购物车商品表ID
create table `cartProductProperty`(
`id` bigint(20) NOT NULL AUTO_INCREMENT,
propertyId bigint(20) NOT NULL,
cartProductId bigint(20) NOT NULL,
PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
-- 【收货地址表】:收货人名字、所在地区、详细地址、手机号、固定电话、邮箱、是否默认地址:0否，1是、省ID、市ID、县ID
create table mailingAddress
(
   id                   bigint(20) not null auto_increment,
   userId               varchar(50),
   name                 varchar(50),
   area                 varchar(50),
   detailed             varchar(50),
   cellphone            varchar(50),
   phone                varchar(50),
   email                varchar(50),
   mark                 varchar(50),
   provinceId           varchar(50),
   cityId               varchar(50),
   countyId             varchar(50),
   primary key (id)
);
-- 【配送值表】:配送方式，配送时间
create table `dispatching`(
`id` bigint(20) NOT NULL AUTO_INCREMENT,
dispatchingMode bigint(20) NOT NULL,
dispatchingDate varchar(200) NOT NULL,
PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 【订单表】:订单编号、总计、订单状态、用户ID、收货人、收货人地址、手机号码、支付方式、运费、送货日期、配送时间、发票类型、发票抬头、发票内容、取消订单时间、快递单号、快递公司
create table orderTable
(
   id                   bigint(20) not null auto_increment,
   orderNumber          varchar(50) not null,
   total                varchar(50) not null,
   orderStatus          varchar(50) not null,
   userId               bigint(20) not null,
   submitTime           varchar(50),
   outTime              varchar(50),
   waitTime             varchar(50),
   completeTime         varchar(50),
   consignee            varchar(50),
   consigneeAddress     varchar(50),
   phoneNumber          varchar(50),
   modePayment          varchar(50),
   expressCharge        varchar(50),
   deliveryDate         varchar(50),
   deliveryTime         varchar(50),
   invoiceType          varchar(50),
   invoiceTitle         varchar(50),
   invoiceContent       varchar(50),
   cancelTime           varchar(50),
   courierNumber        varchar(50),
   express              varchar(50),
   primary key (id)
);

-- 【商品清单表】:订单表ID、商品ID、商品图片、商品价格、购买数量、商品名称
drop table if exists listOfGoods;
create table listOfGoods
(
   id                   varchar(50) not null,
   orderId              varchar(50) not null,
   commodityId          varchar(50) not null,
   imgUrl               varchar(200) not null,
   price                varchar(50) not null,
   number               varchar(20) not null,
   name                 varchar(200),
   tableName			varchar(50),
   primary key (id)
);

-- 【商品推荐层级表】
DROP TABLE IF EXISTS `recommended`;
CREATE TABLE `recommended` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `parentId` varchar(100) NOT NULL COMMENT '父级Id',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `level` varchar(11) NOT NULL COMMENT '排列顺序',
  `status` varchar(11) NOT NULL COMMENT '状态',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `endTime` varchar(20) DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-- 【被推荐商品表】
DROP TABLE IF EXISTS `recommendedgoods`;
CREATE TABLE `recommendedgoods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `recommendedId` varchar(50) NOT NULL COMMENT '与推荐层级表关联id',
  `productId` varchar(50) NOT NULL COMMENT '与商品表关联id',
  `goodsName` varchar(100) NOT NULL,
  `tableName` varchar(100),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 【首页幻灯片推荐】
DROP TABLE IF EXISTS `commodity`;
CREATE TABLE `commodity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dityid` varchar(50),
  `name` varchar(500),
  `tableName` varchar(100),
  `ordet` varchar(50),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;


-- 【商品基本信息表】
drop table if exists commodityBasicInfo;
create table commodityBasicInfo
(
   id                   varchar(50) not null,
   name                 varchar(50) not null,
   promotionWord        varchar(50),
   shopId               varchar(50),
   userId               varchar(50),
   created              varchar(50),
   status               varchar(50),
   content              text,
   categoryId           varchar(50),
   price                varchar(50),
   stock                varchar(50),
   primary key (id)
);
-- 【所选类目属性与值表】
drop table if exists categoryBasicProperty;
create table categoryBasicProperty
(
   id                   varchar(50) not null,
   commodityBasicId     varchar(50),
   propertyId           varchar(50),
   valueId              varchar(50),
   primary key (id)
);
-- 【商品基本信息图片表】
drop table if exists commodityBasicImage;
create table commodityBasicImage
(
   id                   varchar(50) not null,
   commodityBasicId     varchar(50),
   imageUrl             varchar(500),
   smallUrl             varchar(500),
   type                 varchar(50),
   primary key (id)
);
-- 【商品销售属性表】
drop table if exists propertyInfo;
create table propertyInfo
(
   id                   varchar(50) not null,
   commodityBasicId     varchar(50),
   name                 varchar(50),
   mark                 varchar(50),
   primary key (id)
);
-- 【商品销售属性值表】
drop table if exists propertyValueInfo;
create table propertyValueInfo
(
   id                   varchar(50) not null,
   propertyId           varchar(50),
   mark                 varchar(50),
   value                varchar(50),
   commodityBasicId     varchar(50),
   primary key (id)
);
-- 【有属性的商品表】
drop table if exists commodityTable;
create table commodityTable
(
   id                   varchar(32) not null,
   commodityBasicId     varchar(50) not null,
   name                 varchar(50),
   promotionWord        varchar(50),
   price                varchar(50),
   stock                varchar(50),
   primary key (id)
);
-- 【商品图片表】
drop table if exists commodityImage;
create table commodityImage
(
   id                   varchar(50) not null,
   commodityId          varchar(50),
   imageUrl             varchar(500),
   smallUrl             varchar(500),
   type                 varchar(50),
   commodityBasicId     varchar(50) not null,
   primary key (id)
);
-- 【商品属性表】
drop table if exists commodityProperty;
create table commodityProperty
(
   id                   varchar(50) not null,
   commodityId          varchar(50) not null,
   propertyId           varchar(50),
   propertyValue        varchar(50),
   commodityBasicId     varchar(50) not null,
   primary key (id)
);

/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
