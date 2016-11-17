-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.30-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2015-04-16 21:11:16
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;
-- Dumping data for table mall.menu: ~14 rows (approximately)
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` (`id`, `parentId`, `name`, `url`, `type`, `status`, `level`) VALUES
	(2, 0, '人员管理', '', 0, 0, 1),
	(3, 0, '菜单管理', '/menu/list', 0, 0, 2),
	(4, 2, '添加人员', '/user/edit', 0, 0, 1),
	(5, 2, '人员列表', '/user/list', 0, 0, 2),
	(9, 0, '分类管理', '/category/list', 0, 0, 3),
	(10, 0, '商铺管理', '', 0, 0, 4),
	(11, 10, '添加商铺', '/shop/edit', 0, 0, 1),
	(12, 10, '商铺列表', '/shop/list', 0, 0, 2),
	(13, 10, '商品列表', '/shopsCommodity/queryListCommodity', 0, 0, 3),
	(14, 0, '地区IP管理', '', 0, 0, 5),
	(15, 14, '国家列表', '/area/country', 0, 0, 1),
	(16, 14, '省列表', '/area/province', 0, 0, 2),
	(17, 14, '城市列表', '/area/city', 0, 0, 3),
	(18, 14, '地区列表', '/area/area', 0, 0, 4),
	(19, 14, '地区IP列表', '/area/ip', 0, 0, 5),
	(20, 0, '推荐管理', '', 0, 0, 6),
	(21, 20, '推荐商品管理', '/recommended/list', 0, 0, 1),
	(22, 20, '活动推荐', '/recommended/list', 0, 0, 2);
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
