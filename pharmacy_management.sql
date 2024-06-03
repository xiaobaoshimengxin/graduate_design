-- MySQL dump 10.13  Distrib 8.0.35, for Win64 (x86_64)
--
-- Host: localhost    Database: pharmacy_management
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `phone` char(11) DEFAULT NULL COMMENT '电话',
  `name` varchar(10) DEFAULT NULL COMMENT '名字',
  `score` bigint DEFAULT NULL COMMENT '积分',
  `create_time` date DEFAULT NULL,
  `age` int DEFAULT NULL COMMENT '年龄',
  `wallet` float DEFAULT NULL COMMENT '钱包',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  CONSTRAINT `wallet_limit` CHECK ((`wallet` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='顾客积分表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'13612228888','顾客1',1319,'2024-01-01',NULL,39.12),(2,'13423336789','顾客2',820,'2024-01-01',NULL,93.57),(3,'13433334444','小宝',497,'2024-01-26',NULL,29.02),(4,'19199998888','vvip',1498,'2024-01-26',NULL,199.74),(5,'18879551368','王老五',625,'2024-04-09',NULL,57.71),(6,'13779556248','李红',1014,'2024-05-07',NULL,55),(7,'13971662181','黄棋飞',509,'2024-05-07',NULL,82),(8,'13426121953','宋思源',50,'2024-05-07',NULL,45),(9,'13701917338','汪源',564,'2024-05-07',NULL,200),(10,'13368746267','刘称奇',634,'2024-05-07',NULL,100),(11,'13819985304','陈丽娜',15,'2024-05-07',NULL,50),(12,'13605481597','武湖',631,'2024-05-07',NULL,0),(13,'13003029958','杨欣怡',546,'2024-05-07',NULL,100),(14,'13888760970','罗水华',387,'2024-05-07',NULL,0),(15,'13801874516','吴昌珉',63,'2024-05-07',NULL,0),(16,'19101535898','蓝谨慎',12,'2024-05-10',NULL,0),(17,'19101535223','廖才',0,'2024-05-10',NULL,0),(18,'13108562465','王清',0,'2024-05-10',NULL,0),(19,'13456825473','李华',280,'2024-05-10',NULL,0),(20,'15647536548','刘旺经',1166,'2024-05-10',NULL,0),(21,'18770503658','赵梅',300,'2024-05-10',NULL,0),(22,'15623264562','廖强',120,'2024-05-10',NULL,100);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(10) DEFAULT NULL COMMENT '姓名',
  `birth` date DEFAULT NULL COMMENT '出生日期',
  `account` varchar(11) DEFAULT NULL COMMENT '账号/电话',
  `password` varchar(50) DEFAULT '123456' COMMENT '密码',
  `address` varchar(50) DEFAULT NULL COMMENT '家庭住址',
  `statue` tinyint DEFAULT '1' COMMENT '状态，0表示禁用',
  `power` tinyint DEFAULT '1' COMMENT '权限',
  `create_time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'管理员',NULL,'admin','admin',NULL,1,3,NULL),(2,'张三','2020-01-01','13312344321','123456','xx省xx市xx',1,1,'2023-01-03'),(3,'小四','2024-01-01','12121211212','123456','江西',1,2,'2024-01-26'),(4,'王五','2025-02-27','12345678911','123456','江西省宜春市',1,1,'2024-01-31'),(5,'李四','2020-02-06','18879551368','123456','江西宜春',1,2,'2024-04-23'),(6,'老六','1998-08-12','13779556248','123456','江西赣州',0,1,'2024-04-25'),(7,'jdwaio','2024-05-19','3425465','123456','江西奉新',1,2,'2024-05-20');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine`
--

DROP TABLE IF EXISTS `medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicine` (
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '药品编号,主键',
  `drug_name` varchar(20) DEFAULT NULL COMMENT '药品名称',
  `bar_code` varchar(20) DEFAULT NULL COMMENT '药品条码',
  `supplier` int DEFAULT NULL COMMENT '供应商id',
  `price` float DEFAULT NULL COMMENT '价格',
  `specs` varchar(10) DEFAULT NULL COMMENT '规格',
  `category` varchar(10) DEFAULT NULL COMMENT '类型',
  `pur_price` float DEFAULT NULL COMMENT '药品进价',
  PRIMARY KEY (`id`),
  KEY `bar_code` (`bar_code`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药品信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine`
--

LOCK TABLES `medicine` WRITE;
/*!40000 ALTER TABLE `medicine` DISABLE KEYS */;
INSERT INTO `medicine` VALUES (1,'布洛芬缓释颗粒','7281983678098',1,6.66,'24片/盒','片剂',5),(2,'莲花清瘟胶囊','3481956658799',1,8.88,'12粒/盒','胶囊',5),(3,'乳酸菌素片','8792657652183',1,4.44,'12片/盒','片剂',5),(4,'阿奇酶素','8907899878908',1,7.77,'6片/盒','片剂',5),(5,'这是测试数据','1111111111117',1,11,'12片/盒','片剂',5),(6,'这是测试数据','1111111111117',1,11,'12片/盒','片剂',5),(7,'这是测试数据','1111111111111',1,11,'12片/盒','片剂',5),(8,'这是测试数据','1111111111112',1,11,'12片/盒','片剂',5),(9,'这是测试数据','1111111111113',1,11,'12片/盒','片剂',5),(10,'这是测试数据','1111111111114',1,11,'12片/盒','片剂',5),(11,'这是测试数据','1111111111115',1,11,'12片/盒','片剂',5),(12,'这是测试数据','1111111111116',1,11,'12片/盒','片剂',5),(13,'这是测试数据','1111111111117',1,11,'12片/盒','片剂',5),(14,'汇源肾宝片','1234567891234',2,555,'非处方药','嗡嗡嗡',5),(15,'汇源肾宝片','58645645645655',1,45,'盒装','处方药',5),(16,'琥已红霉素颗粒','6909563100049',2,45,'12包/盒','中成药',5),(17,'阿莫西林胶囊','6936292121489',4,24,'32粒/盒','化学药品',5),(18,'西地碘含片','1943821710014',6,20,'50片/盒','非处方药',5),(19,'地塞米松片','8690482700189',5,12,'0.75mg','片剂',5),(20,'盐酸乙胺丁醇片','6902844001505',5,15,'0.25g','片剂',5),(21,'汤臣倍健钙片','6940863615086',13,45,'850mg','片剂',5),(22,'罗红霉素颗粒','6932329200042',23,23,'50mg','胶囊',5),(23,'青霉素V钾胶囊','6941548900565',16,13,'0.236g','胶囊',5),(24,'盐酸氨溴索片','6932329200196',19,6,'30mg','片剂',5),(25,'氯雷他定片','6926921400603',13,10,'10mg','片剂',5),(26,'西瓜霜','6975359520483',14,5,'3.5g','制剂',5),(27,'红霉素软膏','5682326284249',18,10,'10g','制剂',5),(28,'小儿氨酚黄那敏颗粒','8653468452362',6,10,'3g','袋装',5),(29,'夫西地酸','3546826459628',8,20,'20g','制剂',5),(30,'铁皮石斛','5682645895364',18,40,'2g','片剂',5),(31,'罗红霉素颗粒','3548625697423',23,10,'0.2g','胶囊',5),(32,'健胃消食片','4652315896473',5,10,'2g','片剂',5);
/*!40000 ALTER TABLE `medicine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pay_record`
--

DROP TABLE IF EXISTS `pay_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pay_record` (
  `pay_id` int NOT NULL AUTO_INCREMENT COMMENT 'id，代理主键',
  `customer_id` int DEFAULT NULL COMMENT '顾客id',
  `pay_amount` float DEFAULT NULL COMMENT '充值金额',
  `pay_date` date DEFAULT NULL COMMENT '充值时间',
  `employee_id` int DEFAULT NULL COMMENT '操作员工id',
  `notes` varchar(20) DEFAULT NULL COMMENT '备注（支付方式）',
  `customer_name` varchar(10) DEFAULT NULL COMMENT '顾客名称',
  PRIMARY KEY (`pay_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='充值记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pay_record`
--

LOCK TABLES `pay_record` WRITE;
/*!40000 ALTER TABLE `pay_record` DISABLE KEYS */;
INSERT INTO `pay_record` VALUES (1,1,50,NULL,NULL,NULL,'顾客1'),(2,5,60,NULL,NULL,NULL,'王老五'),(3,8,45,'2024-05-10',1,NULL,'宋思源'),(4,4,300,'2024-05-11',1,NULL,'vvip'),(5,10,100,'2024-05-11',1,NULL,'刘称奇'),(6,9,200,'2024-05-11',1,NULL,'汪源'),(7,1,20,'2024-05-12',1,NULL,'顾客1'),(8,7,100,'2024-05-13',1,NULL,'黄棋飞'),(9,1,10,'2024-05-16',1,NULL,'顾客1'),(10,5,100,'2024-05-17',1,NULL,'王老五'),(11,11,50,'2024-05-18',1,'1','陈丽娜'),(12,13,100,'2024-05-21',4,'1','杨欣怡'),(13,2,100,'2024-05-22',1,'1','顾客2');
/*!40000 ALTER TABLE `pay_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchase` (
  `buy_date` date DEFAULT NULL COMMENT '采购时间',
  `drug_name` varchar(20) DEFAULT NULL COMMENT '采购药品名称',
  `drug_count` int DEFAULT NULL COMMENT '采购数量',
  `supplier_name` varchar(20) DEFAULT NULL COMMENT '供应商',
  `pur_id` char(11) DEFAULT NULL COMMENT '订单号，p+日期+2位随机数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
INSERT INTO `purchase` VALUES ('2024-01-17','莲花清瘟胶囊',100,'xxxx制药','p2024011733'),('2024-01-17','布洛芬缓释颗粒',333,'xxxx制药','p2024011733'),('2024-01-18','乳酸菌素片',111,'xxxx制药','p2024011865'),('2024-01-18','阿奇酶素',111,'xxxx制药','p2024011865'),('2024-01-31','汇源肾宝片',100,'江中集团','p2024013188'),('2024-04-09','汇源肾宝片',100,'xxxx制药','p2024040902'),('2024-04-25','琥已红霉素颗粒',100,'江中集团','p2024042586'),('2024-04-25','阿莫西林胶囊',50,'石药集团','p2024042553'),('2024-04-25','西地碘含片',50,'江苏恒瑞医药有限公司','p2024042516'),('2024-05-07','地塞米松片',50,'国药集团容生制药有限公司','p2024050744'),('2024-05-07','盐酸乙胺丁醇片',60,'国药集团容生制药有限公司','p2024050744'),('2024-05-07','汤臣倍健钙片',20,'兴和制药（中国）有限公司','p2024050710'),('2024-05-07','罗红霉素颗粒',40,'绿竹生物制药（珠海市）有限公司','p2024050791'),('2024-05-07','青霉素V钾胶囊',40,'健康药业（中国）有限公司','p2024050745'),('2024-05-07','盐酸氨溴索片',20,'协和麒麟（中国）制药有限公司','p2024050782'),('2024-05-07','氯雷他定片',30,'兴和制药（中国）有限公司','p2024050779'),('2024-05-07','西瓜霜',20,'勃林格殷格翰生物药业（中国）有限公司','p2024050782'),('2024-05-07','红霉素软膏',20,'中国中药霍山石斛科技有限公司','p2024050741'),('2024-05-08','小儿氨酚黄那敏颗粒',20,'江苏恒瑞医药有限公司','p2024050868'),('2024-05-20','夫西地酸',30,'齐鲁制药集团有限公司','p2024052028'),('2024-05-20','铁皮石斛',30,'中国中药霍山石斛科技有限公司','p2024052026'),('2024-05-20','罗红霉素颗粒',50,'绿竹生物制药（珠海市）有限公司','p2024052098'),('2024-05-20','健胃消食片',50,'国药集团容生制药有限公司','p2024052032');
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales`
--

DROP TABLE IF EXISTS `sales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales` (
  `sale_date` datetime DEFAULT NULL COMMENT '售卖时间',
  `employee_id` int DEFAULT NULL COMMENT '售卖员工id',
  `drug_id` int DEFAULT NULL COMMENT '售卖药品id',
  `drug_name` varchar(20) DEFAULT NULL COMMENT '药品',
  `price` float DEFAULT NULL COMMENT '单价',
  `drug_count` int DEFAULT NULL COMMENT '售卖数量',
  `customer_id` int DEFAULT NULL COMMENT '顾客id',
  `sale_id` char(11) DEFAULT NULL COMMENT '订单编号：s+日期+两位随机数',
  `notes` varchar(255),
  `pay_type` int DEFAULT NULL COMMENT '支付方式(1：医保余额；2：微信支付；3：现金支付)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='出售信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales`
--

LOCK TABLES `sales` WRITE;
/*!40000 ALTER TABLE `sales` DISABLE KEYS */;
INSERT INTO `sales` VALUES ('2024-01-17 19:44:01',1,1,'布洛芬缓释颗粒',6.66,1,1,'s2024011734',NULL,1),('2024-01-27 21:33:49',1,1,'布洛芬缓释颗粒',6.66,1,1,'s2024012796',NULL,1),('2024-01-27 21:39:02',1,1,'布洛芬缓释颗粒',6.66,3,1,'s2024012743',NULL,1),('2024-01-27 21:39:02',1,4,'阿奇酶素',7.77,1,1,'s2024012743',NULL,1),('2024-01-29 19:33:57',1,1,'布洛芬缓释颗粒',6.66,1,NULL,'s2024012943',NULL,1),('2024-01-29 19:34:03',1,1,'布洛芬缓释颗粒',6.66,1,1,'s2024012952',NULL,1),('2024-01-29 20:17:23',1,3,'乳酸菌素片',4.44,2,NULL,'s2024012943',NULL,1),('2024-01-29 21:08:31',1,4,'阿奇酶素',7.77,10,3,'s2024012977',NULL,1),('2024-01-29 21:08:53',1,1,'布洛芬缓释颗粒',6.66,5,4,'s2024012993',NULL,1),('2024-01-31 19:40:59',1,4,'阿奇酶素',7.77,12,1,'s2024013106',NULL,1),('2024-04-09 17:44:28',1,3,'乳酸菌素片',4.44,22,1,'s2024040939',NULL,1),('2024-04-09 17:46:25',1,2,'莲花清瘟胶囊',8.88,1,NULL,'s2024040941',NULL,1),('2024-04-09 17:47:28',1,5,'这是测试数据',11,1,NULL,'s2024040935',NULL,1),('2024-04-09 17:49:49',1,3,'乳酸菌素片',4.44,1,5,'s2024040964',NULL,1),('2024-04-12 12:58:15',1,4,'阿奇酶素',7.77,1,2,'s2024041205',NULL,1),('2024-04-16 09:29:27',2,2,'莲花清瘟胶囊',8.88,1,2,'s2024041613',NULL,1),('2024-04-23 22:09:07',1,2,'莲花清瘟胶囊',8.88,2,2,'s2024042379',NULL,1),('2024-04-23 22:19:14',1,3,'乳酸菌素片',4.44,1,NULL,'s2024042371',NULL,1),('2024-04-23 22:34:03',1,4,'阿奇酶素',7.77,1,NULL,'s2024042313',NULL,1),('2024-04-23 22:35:29',1,8,'这是测试数据',11,1,NULL,'s2024042323',NULL,1),('2024-04-24 20:35:04',1,1,'布洛芬缓释颗粒',6.66,1,4,'s2024042461',NULL,1),('2024-04-24 20:35:04',1,8,'这是测试数据',11,2,4,'s2024042461',NULL,1),('2024-04-24 20:35:04',1,15,'汇源肾宝片',45,3,4,'s2024042461',NULL,1),('2024-04-25 19:35:29',1,5,'这是测试数据',11,1,4,'s2024042583','使用353抵扣了3元',1),('2024-04-25 19:35:29',1,1,'布洛芬缓释颗粒',6.66,1,4,'s2024042583','使用353抵扣了3元',1),('2024-04-25 19:52:48',1,15,'汇源肾宝片',45,1,3,'s2024042572','使用2250抵扣了22.5元',1),('2024-04-25 19:54:01',1,15,'汇源肾宝片',45,1,3,'s2024042550','使用450抵扣了4.5元',1),('2024-04-25 20:30:03',1,3,'乳酸菌素片',4.44,1,1,'s2024042549','使用130抵扣了1.3元',1),('2024-04-25 20:30:03',1,16,'琥已红霉素颗粒',45,1,1,'s2024042549','使用130抵扣了1.3元',1),('2024-04-25 20:34:55',1,3,'乳酸菌素片',4.44,1,5,'s2024042563','使用1044抵扣了10.44元',1),('2024-04-25 20:34:55',1,4,'阿奇酶素',7.77,1,5,'s2024042563','使用1044抵扣了10.44元',1),('2024-05-03 19:24:23',1,1,'布洛芬缓释颗粒',6.66,3,3,'s2024050334','使用1800抵扣了18.0元',1),('2024-05-03 19:24:23',1,3,'乳酸菌素片',4.44,3,3,'s2024050334','使用1800抵扣了18.0元',1),('2024-05-07 14:09:48',1,3,'乳酸菌素片',4.44,1,5,'s2024050791','使用122抵扣了1.22元',1),('2024-05-07 14:09:48',1,5,'这是测试数据',11,1,5,'s2024050791','使用122抵扣了1.22元',1),('2024-05-07 14:09:48',1,1,'布洛芬缓释颗粒',6.66,1,5,'s2024050791','使用122抵扣了1.22元',1),('2024-05-07 14:09:48',1,2,'莲花清瘟胶囊',8.88,1,5,'s2024050791','使用122抵扣了1.22元',1),('2024-05-07 14:11:28',1,2,'莲花清瘟胶囊',8.88,1,1,'s2024050768','',1),('2024-05-07 14:11:28',1,5,'这是测试数据',11,2,1,'s2024050768','',1),('2024-05-07 14:11:28',1,1,'布洛芬缓释颗粒',6.66,1,1,'s2024050768','',1),('2024-05-07 14:12:20',1,2,'莲花清瘟胶囊',8.88,1,5,'s2024050731','',1),('2024-05-07 14:12:20',1,1,'布洛芬缓释颗粒',6.66,1,5,'s2024050731','',1),('2024-05-07 14:12:20',1,4,'阿奇酶素',7.77,1,5,'s2024050731','',1),('2024-05-07 14:24:00',1,3,'乳酸菌素片',4.44,3,14,'s2024050761','',1),('2024-05-07 19:20:27',1,1,'布洛芬缓释颗粒',6.66,1,2,'s2024050752','',1),('2024-05-07 23:31:14',1,3,'乳酸菌素片',4.44,2,3,'s2024050773','使用333抵扣了3.33元',1),('2024-05-07 23:31:14',1,5,'这是测试数据',11,2,3,'s2024050773','使用333抵扣了3.33元',1),('2024-05-07 23:31:14',1,1,'布洛芬缓释颗粒',6.66,1,3,'s2024050773','使用333抵扣了3.33元',1),('2024-05-08 12:19:40',1,4,'阿奇酶素',7.77,1,14,'s2024050811','使用133抵扣了1.33元',1),('2024-05-08 12:19:40',1,25,'氯雷他定片',10,1,14,'s2024050811','使用133抵扣了1.33元',1),('2024-05-08 12:19:40',1,27,'红霉素软膏',10,1,14,'s2024050811','使用133抵扣了1.33元',1),('2024-05-08 12:19:40',1,10,'这是测试数据',11,1,14,'s2024050811','使用133抵扣了1.33元',1),('2024-05-08 15:49:20',1,2,'莲花清瘟胶囊',8.88,1,6,'s2024050815','使用125抵扣了1.25元',1),('2024-05-08 15:49:20',1,4,'阿奇酶素',7.77,1,6,'s2024050815','使用125抵扣了1.25元',1),('2024-05-08 15:52:35',1,2,'莲花清瘟胶囊',8.88,1,4,'s2024050813','使用888抵扣了8.88元',1),('2024-05-08 15:55:09',1,1,'布洛芬缓释颗粒',6.66,2,NULL,'s2024050868','',1),('2024-05-09 13:29:01',1,2,'莲花清瘟胶囊',8.88,1,2,'s2024050990','使用689抵扣了6.89元',1),('2024-05-11 10:41:39',1,2,'莲花清瘟胶囊',8.88,1,4,'s2024051100','',1),('2024-05-11 11:29:20',1,3,'乳酸菌素片',4.44,1,3,'s2024051141','',1),('2024-05-11 11:38:58',1,3,'乳酸菌素片',4.44,1,7,'s2024051188','',1),('2024-05-11 11:38:58',1,1,'布洛芬缓释颗粒',6.66,1,7,'s2024051188','',1),('2024-05-11 11:40:06',1,1,'布洛芬缓释颗粒',6.66,1,7,'s2024051139','',1),('2024-05-11 11:41:26',1,1,'布洛芬缓释颗粒',6.66,1,1,'s2024051101','',1),('2024-05-11 11:41:26',1,27,'红霉素软膏',10,1,1,'s2024051101','',1),('2024-05-11 11:51:03',1,3,'乳酸菌素片',4.44,1,NULL,'s2024051148','',1),('2024-05-11 11:51:28',1,25,'氯雷他定片',10,1,NULL,'s2024051144','',1),('2024-05-11 13:18:08',1,3,'乳酸菌素片',4.44,1,4,'s2024051130','使用500抵扣了5.0元',1),('2024-05-11 13:18:08',1,27,'红霉素软膏',10,1,4,'s2024051130','使用500抵扣了5.0元',1),('2024-05-11 13:25:04',1,3,'乳酸菌素片',4.44,1,4,'s2024051136','使用500抵扣了5.0元',1),('2024-05-11 13:25:04',1,27,'红霉素软膏',10,1,4,'s2024051136','使用500抵扣了5.0元',1),('2024-05-11 13:25:35',1,3,'乳酸菌素片',4.44,1,4,'s2024051147','使用500抵扣了5.0元',1),('2024-05-11 13:25:35',1,27,'红霉素软膏',10,1,4,'s2024051147','使用500抵扣了5.0元',1),('2024-05-11 13:26:39',1,2,'莲花清瘟胶囊',8.88,1,1,'s2024051133','',1),('2024-05-11 13:26:39',1,5,'这是测试数据',11,1,1,'s2024051133','',1),('2024-05-11 13:53:45',1,28,'小儿氨酚黄那敏颗粒',10,1,4,'s2024051177','医保余额支付21.0元',1),('2024-05-11 13:53:45',1,5,'这是测试数据',11,1,4,'s2024051177','医保余额支付21.0元',1),('2024-05-11 14:00:47',1,5,'这是测试数据',11,1,3,'s2024051119','医保余额支付23.0元',1),('2024-05-11 14:00:47',1,19,'地塞米松片',12,1,3,'s2024051119','医保余额支付23.0元',1),('2024-05-11 14:01:11',1,1,'布洛芬缓释颗粒',6.66,1,3,'s2024051118','医保余额支付6.66元',1),('2024-05-11 14:06:42',1,4,'阿奇酶素',7.77,1,3,'s2024051114','医保余额支付7.77元',1),('2024-05-11 14:11:19',1,3,'乳酸菌素片',4.44,2,3,'s2024051162','医保余额支付24.42元',1),('2024-05-11 14:11:19',1,4,'阿奇酶素',7.77,2,3,'s2024051162','医保余额支付24.42元',1),('2024-05-11 14:16:29',1,1,'布洛芬缓释颗粒',6.66,5,4,'s2024051132','使用80抵扣了0.8元医保余额支付32.5元',1),('2024-05-11 14:19:08',1,4,'阿奇酶素',7.77,3,4,'s2024051106','使用333积分抵扣了3.33元	微信支付23.31元',1),('2024-05-11 14:20:16',1,1,'布洛芬缓释颗粒',6.66,3,4,'s2024051123','使用233积分抵扣了2.33元	现金支付19.98元',1),('2024-05-11 14:21:27',1,2,'莲花清瘟胶囊',8.88,1,4,'s2024051148','医保余额支付8.88元',1),('2024-05-11 16:56:42',1,4,'阿奇酶素',7.77,1,3,'s2024051150','微信支付7.77元',1),('2024-05-11 17:08:43',1,3,'乳酸菌素片',4.44,1,5,'s2024051145','使用500积分抵扣了5.0元	医保余额支付10.44元',1),('2024-05-11 17:08:43',1,5,'这是测试数据',11,1,5,'s2024051145','使用500积分抵扣了5.0元	医保余额支付10.44元',1),('2024-05-11 23:32:05',1,3,'乳酸菌素片',4.44,1,3,'s2024051177','微信支付4.44元',1),('2024-05-13 12:41:01',1,26,'西瓜霜',5,1,4,'s2024051377','医保余额支付5.0元',1),('2024-05-13 21:46:25',1,19,'地塞米松片',12,1,1,'s2024051341','医保余额支付57.0元',1),('2024-05-13 21:46:25',1,21,'汤臣倍健钙片',45,1,1,'s2024051341','医保余额支付57.0元',1),('2024-05-14 09:29:04',1,3,'乳酸菌素片',4.44,1,3,'s2024051443','医保余额支付4.44元',1),('2024-05-15 13:43:06',1,3,'乳酸菌素片',4.44,1,NULL,'s2024051577','微信支付15.44元',1),('2024-05-15 13:43:06',1,5,'这是测试数据',11,1,NULL,'s2024051577','微信支付15.44元',1),('2024-05-16 15:23:45',1,2,'莲花清瘟胶囊',8.88,1,5,'s2024051615','医保余额支付34.88元',1),('2024-05-16 15:23:45',1,23,'青霉素V钾胶囊',13,2,5,'s2024051615','医保余额支付34.88元',1),('2024-05-16 21:06:43',1,1,'布洛芬缓释颗粒',6.66,1,5,'s2024051689','医保余额支付28.66元',1),('2024-05-16 21:06:43',1,6,'这是测试数据',11,2,5,'s2024051689','医保余额支付28.66元',1),('2024-05-17 09:46:05',1,1,'布洛芬缓释颗粒',6.66,1,5,'s2024051778','医保余额支付6.66元',1),('2024-05-17 09:52:23',1,2,'莲花清瘟胶囊',8.88,1,NULL,'s2024051748','微信支付8.88元',1),('2024-05-17 09:53:34',1,4,'阿奇酶素',7.77,1,5,'s2024051766','医保余额支付40.77元',1),('2024-05-17 09:53:34',1,19,'地塞米松片',12,1,5,'s2024051766','医保余额支付40.77元',1),('2024-05-17 09:53:34',1,24,'盐酸氨溴索片',6,1,5,'s2024051766','医保余额支付40.77元',1),('2024-05-17 09:53:34',1,20,'盐酸乙胺丁醇片',15,1,5,'s2024051766','医保余额支付40.77元',1),('2024-05-18 16:11:55',1,3,'乳酸菌素片',4.44,1,2,'s2024051833','医保余额支付4.44元',1),('2024-05-18 16:33:52',1,2,'莲花清瘟胶囊',8.88,2,4,'s2024051803','使用138积分抵扣了1.38元	微信支付17.76元',1),('2024-05-18 20:35:14',1,2,'莲花清瘟胶囊',8.88,1,3,'s2024051812','医保余额支付8.88元',1),('2024-05-18 20:54:30',1,2,'莲花清瘟胶囊',8.88,1,4,'s2024051854','医保余额支付53.88元',1),('2024-05-18 20:54:30',1,16,'琥已红霉素颗粒',45,1,4,'s2024051854','医保余额支付53.88元',1),('2024-05-18 20:55:54',1,2,'莲花清瘟胶囊',8.88,1,1,'s2024051857','医保余额支付8.88元',1),('2024-05-20 11:23:22',7,5,'这是测试数据',11,1,4,'s2024052027','微信支付56.0元',2),('2024-05-20 11:23:22',7,21,'汤臣倍健钙片',45,1,4,'s2024052027','微信支付56.0元',2),('2024-05-21 10:13:56',1,32,'健胃消食片',10,2,6,'s2024052123','微信支付20.0元',2),('2024-05-21 11:08:34',1,30,'铁皮石斛',40,1,4,'s2024052112','微信支付40.0元',2),('2024-05-21 11:10:00',1,29,'夫西地酸',20,1,21,'s2024052150','微信支付20.0元',2),('2024-05-21 11:10:40',1,28,'小儿氨酚黄那敏颗粒',10,1,21,'s2024052126','微信支付10.0元',2),('2024-05-21 11:12:39',1,19,'地塞米松片',12,1,22,'s2024052154','微信支付12.0元',2),('2024-05-21 11:16:09',1,27,'红霉素软膏',10,1,19,'s2024052112','微信支付10.0元',2),('2024-05-21 11:17:05',1,1,'布洛芬缓释颗粒',6.66,1,10,'s2024052176','微信支付6.66元',2),('2024-05-21 11:23:58',1,1,'布洛芬缓释颗粒',6.66,1,5,'s2024052178','微信支付6.66元',2),('2024-05-21 11:25:20',1,2,'莲花清瘟胶囊',8.88,1,6,'s2024052100','微信支付8.88元',2),('2024-05-21 11:26:09',1,1,'布洛芬缓释颗粒',6.66,1,2,'s2024052176','微信支付6.66元',2),('2024-05-21 13:07:06',1,1,'布洛芬缓释颗粒',6.66,1,5,'s2024052123','医保余额支付6.66元',1),('2024-05-21 13:09:18',1,1,'布洛芬缓释颗粒',6.66,1,7,'s2024052157','微信支付6.66元',2),('2024-05-21 13:10:10',1,1,'布洛芬缓释颗粒',6.66,1,6,'s2024052178','微信支付6.66元',2),('2024-05-21 13:10:46',1,1,'布洛芬缓释颗粒',6.66,1,20,'s2024052123','现金支付6.66元',3),('2024-05-21 19:57:24',4,3,'乳酸菌素片',4.44,1,6,'s2024052198','微信支付49.44元',2),('2024-05-21 19:57:24',4,16,'琥已红霉素颗粒',45,1,6,'s2024052198','微信支付49.44元',2),('2024-05-21 19:59:23',4,26,'西瓜霜',5,3,5,'s2024052135','微信支付15.0元',2),('2024-05-21 19:59:57',4,26,'西瓜霜',5,3,5,'s2024052123','现金支付15.0元',3),('2024-05-21 20:00:35',4,32,'健胃消食片',10,2,5,'s2024052190','使用1000积分抵扣了10.0元	微信支付20.0元',2),('2024-05-21 20:01:41',4,24,'盐酸氨溴索片',6,3,19,'s2024052177','微信支付18.0元',2),('2024-05-21 20:03:54',1,17,'阿莫西林胶囊',24,1,NULL,'s2024052120','微信支付24.0元',2),('2024-05-22 09:43:30',1,29,'夫西地酸',20,2,20,'s2024052205','微信支付60.0元',2),('2024-05-22 09:43:30',1,18,'西地碘含片',20,1,20,'s2024052205','微信支付60.0元',2),('2024-05-22 09:46:03',1,18,'西地碘含片',20,1,7,'s2024052277','医保余额支付20.0元',1),('2024-05-22 14:48:36',1,12,'这是测试数据',11,1,2,'s2024052223','使用397积分抵扣了3.97元	医保余额支付7.03元',1),('2024-05-22 14:59:43',1,4,'阿奇酶素',7.77,2,1,'s2024052262','使用1554积分抵扣了15.54元	医保余额支付-3.8146972E-8元',1),('2024-05-22 15:01:56',1,19,'地塞米松片',12,1,2,'s2024052221','微信支付12.0元',2),('2024-05-22 15:02:20',1,25,'氯雷他定片',10,2,2,'s2024052224','医保余额支付20.0元',1);
/*!40000 ALTER TABLE `sales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sales_datecount`
--

DROP TABLE IF EXISTS `sales_datecount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sales_datecount` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '代理主键',
  `date` date DEFAULT NULL COMMENT '日期',
  `amount` float DEFAULT NULL COMMENT '金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药品日销售额';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sales_datecount`
--

LOCK TABLES `sales_datecount` WRITE;
/*!40000 ALTER TABLE `sales_datecount` DISABLE KEYS */;
/*!40000 ALTER TABLE `sales_datecount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '采购商id',
  `name` varchar(20) DEFAULT NULL COMMENT '采购商名称',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `address` varchar(50) DEFAULT NULL COMMENT '地址',
  `contact_number` varchar(16) DEFAULT NULL COMMENT '供应商联系电话',
  `statue` int NOT NULL DEFAULT '1' COMMENT '状态1正常，0停用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='供应商信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'xxxx制药','2024-01-17','xxx省x市xxx','89985 78767',1),(2,'江中集团','2024-01-17','江西省南昌市高新区','66666 88888',1),(3,'华润集团','2024-04-12','广东省深圳市xxxx','20846898',1),(4,'石药集团','2024-04-25','石家庄','0311-6716377',1),(5,'国药集团容生制药有限公司','2024-04-25','北京','0391-7273656',1),(6,'江苏恒瑞医药有限公司','2024-04-25','南京','0732-4562486',1),(7,'上海复星医药股份有限公司','2024-04-25','上海','165235798',1),(8,'齐鲁制药集团有限公司','2024-04-25','济南','0853-456462',1),(9,'华润三九医药股份有限公司','2024-04-25','深圳市龙华区观湖街道','4008000999',1),(10,'阿斯利康药业（中国）有限公司','2024-05-07','泰州市药城大道88号','13212915767',1),(11,'弘美制药（中国）有限公司','2024-05-07','吉林省梅河口市弘美工业园','15650921176',1),(12,'绿十字（中国）生物制品有限公司','2024-05-07','安徽省淮南市经济技术开发区国庆东路26号','4006103799005',1),(13,'兴和制药（中国）有限公司','2024-05-07','	 江苏省常州市新北区紫苏路1号','411067696663',1),(14,'勃林格殷格翰生物药业（中国）有限公司','2024-05-07','中国（上海）自由贸易试验区哈雷路1090号','10000094237075',1),(15,'曼秀雷敦（中国）药业有限公司','2024-05-07','广东省中山市三乡镇第二工业区','400-700-7708',1),(16,'健康药业（中国）有限公司','2024-05-07','珠海市红旗镇虹晖一路8号423室','4006174989103',1),(17,'诺和诺德(中国)制药有限公司','2024-05-07','	 天津经济技术开发区南海路','59615858',1),(18,'中国中药霍山石斛科技有限公司','2024-05-07','安徽霍山高桥湾现代产业园','0564-5037356',1),(19,'协和麒麟（中国）制药有限公司','2024-05-07','中国（上海）自由贸易试验区龙东大道970号','021-50800909',1),(20,'参天制药（中国）有限公司','2024-05-07','苏州工业园区汀兰巷169号','0512-62957500',1),(21,'河北金芳制药有限公司','2024-05-07','邯郸市邯山区雪驰路大桥东侧路南','0310-8018445',1),(22,'四川摩尔生物制药有限公司','2024-05-07','成都市蒲江县鹤山镇工业南路51号','028-88557116',1),(23,'绿竹生物制药（珠海市）有限公司','2024-05-07','珠海市金湾区三灶镇机场西路628号珠海国际健康港8栋','0756-7681909',1);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `drug_id` int NOT NULL COMMENT '药品id',
  `num` int DEFAULT NULL COMMENT '库存数量',
  `receive_date` date DEFAULT NULL COMMENT '到货日期',
  `date_of_manufacture` date DEFAULT NULL COMMENT '生产日期',
  `effective_date` date DEFAULT NULL COMMENT '到期日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='仓库库存表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (1,57,'2024-01-01','2023-12-30','2024-12-30'),(2,312,'2024-01-01','2023-12-30','2024-12-30'),(3,56,'2024-01-01','2023-12-30','2024-12-30'),(4,72,'2024-01-01','2023-12-30','2024-12-30'),(5,98,'2024-01-01','2023-12-30','2024-12-30'),(6,112,'2024-01-01','2023-12-30','2024-12-30'),(7,111,'2024-01-01','2023-12-30','2024-12-30'),(8,108,'2024-01-01','2023-12-30','2024-12-30'),(9,111,'2024-01-01','2023-12-30','2024-12-30'),(10,110,'2024-01-01','2023-12-30','2024-12-30'),(11,111,'2024-01-01','2023-12-30','2024-12-30'),(12,110,'2024-01-01','2023-12-30','2024-12-30'),(13,111,'2024-01-01','2023-12-30','2024-12-30'),(14,66,'2024-01-31','2024-01-17','2024-07-01'),(15,86,'2024-04-09','2024-04-09','2029-06-05'),(16,94,'2024-04-25','2024-04-25','2027-04-23'),(17,47,'2024-04-25','2024-04-02','2027-04-02'),(18,48,'2024-04-25','2024-04-24','2027-04-25'),(19,45,'2024-05-07','2024-05-01','2028-02-02'),(20,59,'2024-05-07','2024-05-01','2029-05-01'),(21,18,'2024-05-07','2022-03-01','2025-05-01'),(22,40,'2024-05-07','2024-05-07','2027-05-01'),(23,38,'2024-05-07','2024-05-07','2026-05-01'),(24,16,'2024-05-07','2024-05-02','2026-05-02'),(25,26,'2024-05-07','2024-05-01','2026-05-01'),(26,13,'2024-05-07','2022-05-01','2025-05-01'),(27,14,'2024-05-07','2024-05-07','2026-05-07'),(28,18,'2024-05-08','2024-05-08','2026-06-10'),(29,27,'2024-05-20','2024-05-01','2028-05-01'),(30,29,'2024-05-20','2024-05-02','2028-05-02'),(31,50,'2024-05-20','2024-05-01','2026-05-01'),(32,46,'2024-05-20','2021-05-01','2024-07-01');
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-02 15:27:51