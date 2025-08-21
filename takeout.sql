CREATE DATABASE IF NOT EXISTS `takeout_app`;
USE `takeout_app`;

DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `user_id` bigint NOT NULL,
                                `consignee` varchar(50) COLLATE utf8_bin DEFAULT NULL,
                                `sex` varchar(2) COLLATE utf8_bin DEFAULT NULL,
                                `phone` varchar(11) COLLATE utf8_bin NOT NULL,
                                `province_code` varchar(12) CHARACTER SET utf8mb4 DEFAULT NULL,
                                `province_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL,
                                `city_code` varchar(12) CHARACTER SET utf8mb4 DEFAULT NULL,
                                `city_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL,
                                `district_code` varchar(12) CHARACTER SET utf8mb4 DEFAULT NULL,
                                `district_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL,
                                `detail` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL,
                                `label` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
                                `is_default` tinyint(1) NOT NULL DEFAULT '0',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `type` int DEFAULT NULL,
                            `name` varchar(32) COLLATE utf8_bin NOT NULL,
                            `sort` int NOT NULL DEFAULT '0',
                            `status` int DEFAULT NULL,
                            `create_time` datetime DEFAULT NULL,
                            `update_time` datetime DEFAULT NULL,
                            `create_user` bigint DEFAULT NULL,
                            `update_user` bigint DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `idx_category_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

INSERT INTO `category` VALUES (11,1,'Beverages',10,1,'2022-06-09 22:09:18','2022-06-09 22:09:18',1,1);
INSERT INTO `category` VALUES (12,1,'Traditional Main Dishes',9,1,'2022-06-09 22:09:32','2022-06-09 22:18:53',1,1);
INSERT INTO `category` VALUES (13,2,'Popular Sets',12,1,'2022-06-09 22:11:38','2022-06-10 11:04:40',1,1);
INSERT INTO `category` VALUES (15,2,'Business Sets',13,1,'2022-06-09 22:14:10','2022-06-10 11:04:48',1,1);
INSERT INTO `category` VALUES (16,1,'Sichuan Grilled Fish',4,1,'2022-06-09 22:15:37','2022-08-31 14:27:25',1,1);
INSERT INTO `category` VALUES (17,1,'Sichuan Bullfrogs',5,1,'2022-06-09 22:16:14','2022-08-31 14:39:44',1,1);
INSERT INTO `category` VALUES (18,1,'Special Steamed Dishes',6,1,'2022-06-09 22:17:42','2022-06-09 22:17:42',1,1);
INSERT INTO `category` VALUES (19,1,'Fresh Vegetables',7,1,'2022-06-09 22:18:12','2022-06-09 22:18:28',1,1);
INSERT INTO `category` VALUES (20,1,'Boiled Fish',8,1,'2022-06-09 22:22:29','2022-06-09 22:23:45',1,1);
INSERT INTO `category` VALUES (21,1,'Soups',11,1,'2022-06-10 10:51:47','2022-06-10 10:51:47',1,1);

DROP TABLE IF EXISTS `dish`;
CREATE TABLE `dish` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `name` varchar(32) COLLATE utf8_bin NOT NULL,
                        `category_id` bigint NOT NULL,
                        `price` decimal(10,2) DEFAULT NULL,
                        `image` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                        `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                        `status` int DEFAULT '1',
                        `create_time` datetime DEFAULT NULL,
                        `update_time` datetime DEFAULT NULL,
                        `create_user` bigint DEFAULT NULL,
                        `update_user` bigint DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `idx_dish_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

INSERT INTO `dish` VALUES (46,'Wong Lo Kat',11,6.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/41bfcacf-7ad4-4927-8b26-df366553a94c.png','',1,'2022-06-09 22:40:47','2022-06-09 22:40:47',1,1);
INSERT INTO `dish` VALUES (47,'Arctic Ocean',11,4.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4451d4be-89a2-4939-9c69-3a87151cb979.png','Childhood flavor',1,'2022-06-10 09:18:49','2022-06-10 09:18:49',1,1);
INSERT INTO `dish` VALUES (48,'Snow Beer',11,4.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/bf8cbfc1-04d2-40e8-9826-061ee41ab87c.png','',1,'2022-06-10 09:22:54','2022-06-10 09:22:54',1,1);
INSERT INTO `dish` VALUES (49,'Rice',12,2.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/76752350-2121-44d2-b477-10791c23a8ec.png','Premium Wuchang rice',1,'2022-06-10 09:30:17','2022-06-10 09:30:17',1,1);
INSERT INTO `dish` VALUES (50,'Steamed Buns',12,1.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/475cc599-8661-4899-8f9e-121dd8ef7d02.png','Quality flour',1,'2022-06-10 09:34:28','2022-06-10 09:34:28',1,1);
INSERT INTO `dish` VALUES (51,'Sauerkraut Fish',20,56.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4a9cefba-6a74-467e-9fde-6e687ea725d7.png','Ingredients: broth, grass carp, sauerkraut',1,'2022-06-10 09:40:51','2022-06-10 09:40:51',1,1);
INSERT INTO `dish` VALUES (52,'Classic Sauerkraut Catfish',20,66.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/5260ff39-986c-4a97-8850-2ec8c7583efc.png','Ingredients: sauerkraut, river fish, catfish',1,'2022-06-10 09:46:02','2022-06-10 09:46:02',1,1);
INSERT INTO `dish` VALUES (53,'Sichuan Boiled Grass Carp',20,38.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a6953d5a-4c18-4b30-9319-4926ee77261f.png','Ingredients: grass carp, broth',1,'2022-06-10 09:48:37','2022-06-10 09:48:37',1,1);
INSERT INTO `dish` VALUES (54,'Stir-fried Baby Bok Choy',19,18.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/3613d38e-5614-41c2-90ed-ff175bf50716.png','Ingredients: baby bok choy',1,'2022-06-10 09:51:46','2022-06-10 09:51:46',1,1);
INSERT INTO `dish` VALUES (55,'Garlic Baby Cabbage',19,18.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/4879ed66-3860-4b28-ba14-306ac025fdec.png','Ingredients: garlic, baby cabbage',1,'2022-06-10 09:53:37','2022-06-10 09:53:37',1,1);
INSERT INTO `dish` VALUES (56,'Stir-fried Broccoli',19,18.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/e9ec4ba4-4b22-4fc8-9be0-4946e6aeb937.png','Ingredients: broccoli',1,'2022-06-10 09:55:44','2022-06-10 09:55:44',1,1);
INSERT INTO `dish` VALUES (57,'Stir-fried Cabbage',19,18.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/22f59feb-0d44-430e-a6cd-6a49f27453ca.png','Ingredients: cabbage',1,'2022-06-10 09:58:35','2022-06-10 09:58:35',1,1);
INSERT INTO `dish` VALUES (58,'Steamed Sea Bass',18,98.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c18b5c67-3b71-466c-a75a-e63c6449f21c.png','Ingredients: sea bass',1,'2022-06-10 10:12:28','2022-06-10 10:12:28',1,1);
INSERT INTO `dish` VALUES (59,'Dongpo Pork Elbow',18,138.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a80a4b8c-c93e-4f43-ac8a-856b0d5cc451.png','Ingredients: pork elbow',1,'2022-06-10 10:24:03','2022-06-10 10:24:03',1,1);
INSERT INTO `dish` VALUES (60,'Braised Pork with Preserved Vegetables',18,58.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/6080b118-e30a-4577-aab4-45042e3f88be.png','Ingredients: pork, preserved vegetables',1,'2022-06-10 10:26:03','2022-06-10 10:26:03',1,1);
INSERT INTO `dish` VALUES (61,'Fish Head with Chopped Peppers', 18,66.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/13da832f-ef2c-484d-8370-5934a1045a06.png','Ingredients: silver carp, chopped peppers',1,'2022-06-10 10:28:54','2022-06-10 10:28:54',1,1);
INSERT INTO `dish` VALUES (62,'Golden Soup Sauerkraut Bullfrog',17,88.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7694a5d8-7938-4e9d-8b9e-2075983a2e38.png','Ingredients: fresh bullfrog, sauerkraut',1,'2022-06-10 10:33:05','2022-06-10 10:33:05',1,1);
INSERT INTO `dish` VALUES (63,'Spicy Pot Bullfrog',17,88.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/f5ac8455-4793-450c-97ba-173795c34626.png','Ingredients: fresh bullfrog, lotus root, bamboo shoots',1,'2022-06-10 10:35:40','2022-06-10 10:35:40',1,1);
INSERT INTO `dish` VALUES (64,'Spicy Bullfrog',17,88.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/7a55b845-1f2b-41fa-9486-76d187ee9ee1.png','Ingredients: fresh bullfrog, sponge gourd, soybean sprouts',1,'2022-06-10 10:37:52','2022-06-10 10:37:52',1,1);
INSERT INTO `dish` VALUES (65,'Grass Carp 2kg',16,68.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/b544d3ba-a1ae-4d20-a860-81cb5dec9e03.png','Ingredients: grass carp, soybean sprouts, lotus root',1,'2022-06-10 10:41:08','2022-06-10 10:41:08',1,1);
INSERT INTO `dish` VALUES (66,'River Fish 2kg',16,119.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/a101a1e9-8f8b-47b2-afa4-1abd47ea0a87.png','Ingredients: river fish, soybean sprouts, lotus root',1,'2022-06-10 10:42:42','2022-06-10 10:42:42',1,1);
INSERT INTO `dish` VALUES (67,'Catfish 2kg',16,72.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/8cfcc576-4b66-4a09-ac68-ad5b273c2590.png','Ingredients: catfish, soybean sprouts, lotus root',1,'2022-06-10 10:43:56','2022-06-10 10:43:56',1,1);
INSERT INTO `dish` VALUES (68,'Egg Soup',21,4.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/c09a0ee8-9d19-428d-81b9-746221824113.png','Ingredients: eggs, seaweed',1,'2022-06-10 10:54:25','2022-06-10 10:54:25',1,1);
INSERT INTO `dish` VALUES (69,'Mushroom Tofu Soup',21,6.00,'https://sky-itcast.oss-cn-beijing.aliyuncs.com/16d0a3d6-2253-4cfc-9b49-bf7bd9eb2ad2.png','Ingredients: tofu, mushrooms',1,'2022-06-10 10:55:02','2022-06-10 10:55:02',1,1);

DROP TABLE IF EXISTS `dish_flavor`;
CREATE TABLE `dish_flavor` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `dish_id` bigint NOT NULL,
                               `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
                               `value` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

INSERT INTO `dish_flavor` VALUES (40,10,'Sweetness','[\"No Sugar\",\"Less Sugar\",\"Half Sugar\",\"More Sugar\",\"Full Sugar\"]');
INSERT INTO `dish_flavor` VALUES (41,7,'Dietary Restrictions','[\"No Onion\",\"No Garlic\",\"No Cilantro\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (42,7,'Temperature','[\"Hot\",\"Room Temperature\",\"No Ice\",\"Less Ice\",\"More Ice\"]');
INSERT INTO `dish_flavor` VALUES (45,6,'Dietary Restrictions','[\"No Onion\",\"No Garlic\",\"No Cilantro\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (46,6,'Spiciness','[\"Not Spicy\",\"Mildly Spicy\",\"Medium Spicy\",\"Very Spicy\"]');
INSERT INTO `dish_flavor` VALUES (47,5,'Spiciness','[\"Not Spicy\",\"Mildly Spicy\",\"Medium Spicy\",\"Very Spicy\"]');
INSERT INTO `dish_flavor` VALUES (48,5,'Sweetness','[\"No Sugar\",\"Less Sugar\",\"Half Sugar\",\"More Sugar\",\"Full Sugar\"]');
INSERT INTO `dish_flavor` VALUES (49,2,'Sweetness','[\"No Sugar\",\"Less Sugar\",\"Half Sugar\",\"More Sugar\",\"Full Sugar\"]');
INSERT INTO `dish_flavor` VALUES (50,4,'Sweetness','[\"No Sugar\",\"Less Sugar\",\"Half Sugar\",\"More Sugar\",\"Full Sugar\"]');
INSERT INTO `dish_flavor` VALUES (51,3,'Sweetness','[\"No Sugar\",\"Less Sugar\",\"Half Sugar\",\"More Sugar\",\"Full Sugar\"]');
INSERT INTO `dish_flavor` VALUES (52,3,'Dietary Restrictions','[\"No Onion\",\"No Garlic\",\"No Cilantro\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (86,52,'Dietary Restrictions','[\"No Onion\",\"No Garlic\",\"No Cilantro\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (87,52,'Spiciness','[\"Not Spicy\",\"Mildly Spicy\",\"Medium Spicy\",\"Very Spicy\"]');
INSERT INTO `dish_flavor` VALUES (88,51,'Dietary Restrictions','[\"No Onion\",\"No Garlic\",\"No Cilantro\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (89,51,'Spiciness','[\"Not Spicy\",\"Mildly Spicy\",\"Medium Spicy\",\"Very Spicy\"]');
INSERT INTO `dish_flavor` VALUES (92,53,'Dietary Restrictions','[\"No Onion\",\"No Garlic\",\"No Cilantro\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (93,53,'Spiciness','[\"Not Spicy\",\"Mildly Spicy\",\"Medium Spicy\",\"Very Spicy\"]');
INSERT INTO `dish_flavor` VALUES (94,54,'Dietary Restrictions','[\"No Onion\",\"No Garlic\",\"No Cilantro\"]');
INSERT INTO `dish_flavor` VALUES (95,56,'Dietary Restrictions','[\"No Onion\",\"No Garlic\",\"No Cilantro\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (96,57,'Dietary Restrictions','[\"No Onion\",\"No Garlic\",\"No Cilantro\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (97,60,'Dietary Restrictions','[\"No Onion\",\"No Garlic\",\"No Cilantro\",\"No Spicy\"]');
INSERT INTO `dish_flavor` VALUES (101,66,'Spiciness','[\"Not Spicy\",\"Mildly Spicy\",\"Medium Spicy\",\"Very Spicy\"]');
INSERT INTO `dish_flavor` VALUES (102,67,'Spiciness','[\"Not Spicy\",\"Mildly Spicy\",\"Medium Spicy\",\"Very Spicy\"]');
INSERT INTO `dish_flavor` VALUES (103,65,'Spiciness','[\"Not Spicy\",\"Mildly Spicy\",\"Medium Spicy\",\"Very Spicy\"]');

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `name` varchar(32) COLLATE utf8_bin NOT NULL,
                            `username` varchar(32) COLLATE utf8_bin NOT NULL,
                            `password` varchar(64) COLLATE utf8_bin NOT NULL,
                            `phone` varchar(11) COLLATE utf8_bin NOT NULL,
                            `sex` varchar(2) COLLATE utf8_bin NOT NULL,
                            `id_number` varchar(18) COLLATE utf8_bin NOT NULL,
                            `status` int NOT NULL DEFAULT '1',
                            `create_time` datetime DEFAULT NULL,
                            `update_time` datetime DEFAULT NULL,
                            `create_user` bigint DEFAULT NULL,
                            `update_user` bigint DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

INSERT INTO `employee` VALUES (1,'Administrator','admin','123456','13812312312','1','110101199001010047',1,'2022-02-15 15:51:20','2022-02-17 09:16:20',10,1);

DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
                                `image` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                                `order_id` bigint NOT NULL,
                                `dish_id` bigint DEFAULT NULL,
                                `setmeal_id` bigint DEFAULT NULL,
                                `dish_flavor` varchar(50) COLLATE utf8_bin DEFAULT NULL,
                                `number` int NOT NULL DEFAULT '1',
                                `amount` decimal(10,2) NOT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `number` varchar(50) COLLATE utf8_bin DEFAULT NULL,
                          `status` int NOT NULL DEFAULT '1',
                          `user_id` bigint NOT NULL,
                          `address_book_id` bigint NOT NULL,
                          `order_time` datetime NOT NULL,
                          `checkout_time` datetime DEFAULT NULL,
                          `pay_method` int NOT NULL DEFAULT '1',
                          `pay_status` tinyint NOT NULL DEFAULT '0',
                          `amount` decimal(10,2) NOT NULL,
                          `remark` varchar(100) COLLATE utf8_bin DEFAULT NULL,
                          `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL,
                          `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                          `user_name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
                          `consignee` varchar(32) COLLATE utf8_bin DEFAULT NULL,
                          `cancel_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                          `rejection_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                          `cancel_time` datetime DEFAULT NULL,
                          `estimated_delivery_time` datetime DEFAULT NULL,
                          `delivery_status` tinyint(1) NOT NULL DEFAULT '1',
                          `delivery_time` datetime DEFAULT NULL,
                          `pack_amount` int DEFAULT NULL,
                          `tableware_number` int DEFAULT NULL,
                          `tableware_status` tinyint(1) NOT NULL DEFAULT '1',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `setmeal`;
CREATE TABLE `setmeal` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `category_id` bigint NOT NULL,
                           `name` varchar(32) COLLATE utf8_bin NOT NULL,
                           `price` decimal(10,2) NOT NULL,
                           `status` int DEFAULT '1',
                           `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                           `image` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                           `create_time` datetime DEFAULT NULL,
                           `update_time` datetime DEFAULT NULL,
                           `create_user` bigint DEFAULT NULL,
                           `update_user` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `idx_setmeal_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `setmeal_dish`;
CREATE TABLE `setmeal_dish` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `setmeal_id` bigint DEFAULT NULL,
                                `dish_id` bigint DEFAULT NULL,
                                `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
                                `price` decimal(10,2) DEFAULT NULL,
                                `copies` int DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `shopping_cart`;
CREATE TABLE `shopping_cart` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
                                 `image` varchar(255) COLLATE utf8_bin DEFAULT NULL,
                                 `user_id` bigint NOT NULL,
                                 `dish_id` bigint DEFAULT NULL,
                                 `setmeal_id` bigint DEFAULT NULL,
                                 `dish_flavor` varchar(50) COLLATE utf8_bin DEFAULT NULL,
                                 `number` int NOT NULL DEFAULT '1',
                                 `amount` decimal(10,2) NOT NULL,
                                 `create_time` datetime DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `openid` varchar(45) COLLATE utf8_bin DEFAULT NULL,
                        `name` varchar(32) COLLATE utf8_bin DEFAULT NULL,
                        `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL,
                        `sex` varchar(2) COLLATE utf8_bin DEFAULT NULL,
                        `id_number` varchar(18) COLLATE utf8_bin DEFAULT NULL,
                        `avatar` varchar(500) COLLATE utf8_bin DEFAULT NULL,
                        `create_time` datetime DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;