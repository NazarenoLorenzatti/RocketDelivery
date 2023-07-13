CREATE DATABASE  IF NOT EXISTS `rocketdelivery` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `rocketdelivery`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: rocketdelivery
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contacto`
--

DROP TABLE IF EXISTS `contacto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contacto` (
  `id_contacto` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `telefono` varchar(45) NOT NULL,
  `direccion` varchar(45) NOT NULL,
  `foto` blob,
  `usuario_id_usuario` int NOT NULL,
  PRIMARY KEY (`id_contacto`),
  KEY `fk_contacto_usuario1_idx` (`usuario_id_usuario`),
  CONSTRAINT `fk_contacto_usuario1` FOREIGN KEY (`usuario_id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacto`
--

LOCK TABLES `contacto` WRITE;
/*!40000 ALTER TABLE `contacto` DISABLE KEYS */;
INSERT INTO `contacto` VALUES (8,'RocketDelivery','Administrador','Rocket.Delivery@rocketdelivery.net.ar','123654878','Casa Central 123',NULL,7);
/*!40000 ALTER TABLE `contacto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estados`
--

DROP TABLE IF EXISTS `estados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estados` (
  `id_estado` int NOT NULL AUTO_INCREMENT,
  `nombre_estado` varchar(45) NOT NULL,
  PRIMARY KEY (`id_estado`),
  UNIQUE KEY `nombre_estado_UNIQUE` (`nombre_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estados`
--

LOCK TABLES `estados` WRITE;
/*!40000 ALTER TABLE `estados` DISABLE KEYS */;
INSERT INTO `estados` VALUES (11,'CANCELADO'),(8,'EN PROGRESO'),(10,'ENTREGADO'),(9,'LISTO PARA ENTREGAR'),(12,'NO ENTREGADO'),(7,'NUEVO');
/*!40000 ALTER TABLE `estados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredientes_en_menu`
--

DROP TABLE IF EXISTS `ingredientes_en_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredientes_en_menu` (
  `id_ingrediente_menu` int NOT NULL AUTO_INCREMENT,
  `cantidad` double NOT NULL,
  `menus_id_menu` int DEFAULT NULL,
  `ingredientes_en_stock_id` int DEFAULT NULL,
  PRIMARY KEY (`id_ingrediente_menu`),
  KEY `fk_ingredientes_menus1_idx` (`menus_id_menu`),
  KEY `fk_ingredientes_ingredientes1_idx` (`ingredientes_en_stock_id`),
  CONSTRAINT `fk_ingredientes_ingredientes1` FOREIGN KEY (`ingredientes_en_stock_id`) REFERENCES `ingredientes_en_stock` (`id_ingredientes_en_stock`),
  CONSTRAINT `fk_ingredientes_menus1` FOREIGN KEY (`menus_id_menu`) REFERENCES `menus` (`id_menu`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredientes_en_menu`
--

LOCK TABLES `ingredientes_en_menu` WRITE;
/*!40000 ALTER TABLE `ingredientes_en_menu` DISABLE KEYS */;
INSERT INTO `ingredientes_en_menu` VALUES (15,100,13,4);
/*!40000 ALTER TABLE `ingredientes_en_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredientes_en_stock`
--

DROP TABLE IF EXISTS `ingredientes_en_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredientes_en_stock` (
  `id_ingredientes_en_stock` int NOT NULL AUTO_INCREMENT,
  `nombre_ingrediente` varchar(45) NOT NULL,
  `descripcion_ingrediente` varchar(128) DEFAULT NULL,
  `cantidad_stock` varchar(45) NOT NULL,
  `imagen_ingrediente` varchar(160) DEFAULT NULL,
  PRIMARY KEY (`id_ingredientes_en_stock`),
  UNIQUE KEY `nombre_ingrediente_UNIQUE` (`nombre_ingrediente`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredientes_en_stock`
--

LOCK TABLES `ingredientes_en_stock` WRITE;
/*!40000 ALTER TABLE `ingredientes_en_stock` DISABLE KEYS */;
INSERT INTO `ingredientes_en_stock` VALUES (4,'INGREDIENTE DE PRUEBA','INGREDIENTE DE PUREBA','15000','https://lh3.googleusercontent.com/drive-viewer/AITFw-w395Zf-67rYWEAPEq6w-jYH2LzyZXR5YXuERDjmRocH_C62k8rjFtB23QClREw3C-WqoeDXehtnSM-OoK09Z0Ut1Kd=s1600');
/*!40000 ALTER TABLE `ingredientes_en_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menus`
--

DROP TABLE IF EXISTS `menus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menus` (
  `id_menu` int NOT NULL AUTO_INCREMENT,
  `nombre_menu` varchar(45) NOT NULL,
  `descripcion_menu` varchar(256) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `imagen_menu` varchar(160) DEFAULT NULL,
  `disponible` tinyint DEFAULT NULL,
  PRIMARY KEY (`id_menu`),
  UNIQUE KEY `nombre_menu_UNIQUE` (`nombre_menu`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menus`
--

LOCK TABLES `menus` WRITE;
/*!40000 ALTER TABLE `menus` DISABLE KEYS */;
INSERT INTO `menus` VALUES (13,'MENU DE PRUEBA','ES UN MENU DE PRUEBA',1500,'https://lh3.googleusercontent.com/drive-viewer/AITFw-yVLAwW91xjmIUbWB7jSDDATv1myou2jXZ0jptpaI8yMliQO3KnNW-FsfLchvhMbG33NlzuVCbMtxRWINRXct-i_5vB=s2560',1);
/*!40000 ALTER TABLE `menus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menus_has_pedidos`
--

DROP TABLE IF EXISTS `menus_has_pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menus_has_pedidos` (
  `menus_id_menu` int NOT NULL,
  `pedidos_id_pedido` int NOT NULL,
  PRIMARY KEY (`menus_id_menu`,`pedidos_id_pedido`),
  KEY `fk_menus_has_pedidos_pedidos1_idx` (`pedidos_id_pedido`),
  CONSTRAINT `fk_menus_has_pedidos_menus1` FOREIGN KEY (`menus_id_menu`) REFERENCES `menus` (`id_menu`),
  CONSTRAINT `fk_menus_has_pedidos_pedidos1` FOREIGN KEY (`pedidos_id_pedido`) REFERENCES `pedidos` (`id_pedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menus_has_pedidos`
--

LOCK TABLES `menus_has_pedidos` WRITE;
/*!40000 ALTER TABLE `menus_has_pedidos` DISABLE KEYS */;
/*!40000 ALTER TABLE `menus_has_pedidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedidos` (
  `id_pedido` int NOT NULL AUTO_INCREMENT,
  `contacto_id_contacto` int NOT NULL,
  `estados_id_estado` int NOT NULL,
  PRIMARY KEY (`id_pedido`),
  KEY `fk_pedidos_contacto1_idx` (`contacto_id_contacto`),
  KEY `fk_pedidos_estados1_idx` (`estados_id_estado`),
  CONSTRAINT `fk_pedidos_contacto1` FOREIGN KEY (`contacto_id_contacto`) REFERENCES `contacto` (`id_contacto`),
  CONSTRAINT `fk_pedidos_estados1` FOREIGN KEY (`estados_id_estado`) REFERENCES `estados` (`id_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidos`
--

LOCK TABLES `pedidos` WRITE;
/*!40000 ALTER TABLE `pedidos` DISABLE KEYS */;
INSERT INTO `pedidos` VALUES (10,8,7);
/*!40000 ALTER TABLE `pedidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rol`
--

DROP TABLE IF EXISTS `rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rol` (
  `id_rol` int NOT NULL AUTO_INCREMENT,
  `nombre_rol` varchar(45) NOT NULL,
  PRIMARY KEY (`id_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (9,'ROLE_ADMIN'),(10,'ROLE_USER');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(128) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (7,'admin','$2a$10$lFZPmgERqLkdiYrp65IZreI9WmJHxPuTs2WxXo6NZkgvfQpaY00Fu'),(8,'user','$2a$10$ahiPY69tA.1YDv1Tm6Sfpu.D8KmNqCW86pxWvoqir4qzGGm54QJIu'),(9,'dubra','$2a$10$GMLPiRkOTNoVeoZ.7yba9OG/a6wg0vwWGzI955LUUgnKsifw8..MG');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_has_rol`
--

DROP TABLE IF EXISTS `usuario_has_rol`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_has_rol` (
  `usuario_id_usuario` int NOT NULL,
  `rol_id_rol` int NOT NULL,
  PRIMARY KEY (`usuario_id_usuario`,`rol_id_rol`),
  KEY `fk_usuario_has_rol_rol1_idx` (`rol_id_rol`),
  KEY `fk_usuario_has_rol_usuario1_idx` (`usuario_id_usuario`),
  CONSTRAINT `fk_usuario_has_rol_rol1` FOREIGN KEY (`rol_id_rol`) REFERENCES `rol` (`id_rol`),
  CONSTRAINT `fk_usuario_has_rol_usuario1` FOREIGN KEY (`usuario_id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_has_rol`
--

LOCK TABLES `usuario_has_rol` WRITE;
/*!40000 ALTER TABLE `usuario_has_rol` DISABLE KEYS */;
INSERT INTO `usuario_has_rol` VALUES (7,9),(7,10),(8,10),(9,10);
/*!40000 ALTER TABLE `usuario_has_rol` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-10 14:02:37
