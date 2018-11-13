/*
SQLyog Ultimate v11.52 (64 bit)
MySQL - 5.5.28 : Database - gdbc
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`gdbc` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `gdbc`;

/*Table structure for table `items` */

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `id` int(8) NOT NULL,
  `name` varchar(36) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `pic` varchar(100) DEFAULT NULL,
  `createtime` date DEFAULT NULL,
  `detail` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `items` */

insert  into `items`(`id`,`name`,`price`,`pic`,`createtime`,`detail`) values (1,'男装',100,NULL,'2016-09-11','男装试试撒实打实的');

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(128) NOT NULL COMMENT '资源名称',
  `type` varchar(32) NOT NULL COMMENT '资源类型：menu,button,',
  `url` varchar(128) DEFAULT NULL COMMENT '访问url地址',
  `percode` varchar(128) DEFAULT NULL COMMENT '权限代码字符串',
  `parentid` bigint(20) DEFAULT NULL COMMENT '父结点id',
  `parentids` varchar(128) DEFAULT NULL COMMENT '父结点id列表串',
  `sortstring` varchar(128) DEFAULT NULL COMMENT '排序号',
  `available` char(1) DEFAULT NULL COMMENT '是否可用,1：可用，0不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`name`,`type`,`url`,`percode`,`parentid`,`parentids`,`sortstring`,`available`) values (1,'权限','','',NULL,0,'0/','0','1'),(11,'商品管理','menu','/items/queryItems.action',NULL,1,'0/1/','1.','1'),(12,'商品新增','permission','/item/add.action','item:create',11,'0/1/11/','','1'),(13,'商品修改','permission','/item/editItem.action','item:update',11,'0/1/11/','','1'),(14,'商品删除','permission','','item:delete',11,'0/1/11/','','1'),(15,'商品查询','permission','/items/queryItems.action','item:query',11,'0/1/15/',NULL,'1'),(21,'用户管理','menu','/user/query.action','user:query',1,'0/1/','2.','1'),(22,'用户新增','permission','','user:create',21,'0/1/21/','','1'),(23,'用户修改','permission','','user:update',21,'0/1/21/','','1'),(24,'用户删除','permission','','user:delete',21,'0/1/21/','','1'),(25,'定时器管理','menu','/scheduler/list.action',NULL,1,'0/1/','1','1');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` varchar(36) NOT NULL,
  `name` varchar(128) NOT NULL,
  `available` char(1) DEFAULT NULL COMMENT '是否可用,1：可用，0不可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`available`) values ('ebc8a441-c6f9-11e4-b137-0adc305c3f28','商品管理员','1'),('ebc9d647-c6f9-11e4-b137-0adc305c3f28','用户管理员','1');

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` varchar(36) NOT NULL,
  `sys_role_id` varchar(32) NOT NULL COMMENT '角色id',
  `sys_permission_id` varchar(32) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`id`,`sys_role_id`,`sys_permission_id`) values ('ebc8a441-c6f9-11e4-b137-0adc305c3f21','ebc8a441-c6f9-11e4-b137-0adc305c','12'),('ebc8a441-c6f9-11e4-b137-0adc305c3f22','ebc8a441-c6f9-11e4-b137-0adc305c','11'),('ebc8a441-c6f9-11e4-b137-0adc305c3f24','ebc9d647-c6f9-11e4-b137-0adc305c','21'),('ebc8a441-c6f9-11e4-b137-0adc305c3f25','ebc8a441-c6f9-11e4-b137-0adc305c','15'),('ebc9d647-c6f9-11e4-b137-0adc305c3f23','ebc9d647-c6f9-11e4-b137-0adc305c','22'),('ebc9d647-c6f9-11e4-b137-0adc305c3f26','ebc8a441-c6f9-11e4-b137-0adc305c','13'),('ebc9d647-c6f9-11e4-b137-0adc305c3f27','ebc8a441-c6f9-11e4-b137-0adc305c','25');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `usercode` varchar(32) NOT NULL COMMENT '账号',
  `username` varchar(64) NOT NULL COMMENT '姓名',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `salt` varchar(64) DEFAULT NULL COMMENT '盐',
  `locked` char(1) DEFAULT NULL COMMENT '账号是否锁定，1：锁定，0未锁定',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`usercode`,`username`,`password`,`salt`,`locked`) values ('lisi','lisi','李四','bf07fd8bbc73b6f70b8319f2ebb87483','uiwueylm','0'),('zhangsan','zhangsan','张三','cb571f7bd7a6f73ab004a70322b963d5','eteokues','0');

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` varchar(36) NOT NULL,
  `sys_user_id` varchar(32) NOT NULL,
  `sys_role_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`sys_user_id`,`sys_role_id`) values ('ebc8a441-c6f9-11e4-b137-0adc305c3f28','zhangsan','ebc8a441-c6f9-11e4-b137-0adc305c'),('ebc9d647-c6f9-11e4-b137-0adc305c3f28','lisi','ebc9d647-c6f9-11e4-b137-0adc305c');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_NAME` char(30) NOT NULL,
  `USER_PASSWORD` char(10) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  KEY `IDX_NAME` (`USER_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`USER_ID`,`USER_NAME`,`USER_PASSWORD`) values (1,'luoguohui','123456'),(2,'zhangsan','123456');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
