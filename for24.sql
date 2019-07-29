/*
 Navicat Premium Data Transfer

 Source Server         : SOLONG
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : for24

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 07/04/2019 17:03:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `num1` int(11) NOT NULL,
  `num2` int(11) NOT NULL,
  `num3` int(11) NOT NULL,
  `num4` int(11) NOT NULL,
  `answer` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (1, 8, 6, 9, 8, '8*9-8*6');
INSERT INTO `question` VALUES (2, 1, 8, 4, 3, '(1+8-3)*4');
INSERT INTO `question` VALUES (3, 5, 4, 3, 4, '(5+4-3)*4');
INSERT INTO `question` VALUES (4, 9, 4, 2, 9, '9+4+9+2');
INSERT INTO `question` VALUES (5, 1, 8, 8, 10, '(1-8+10)*8');
INSERT INTO `question` VALUES (6, 8, 10, 2, 1, '(8-1)*2+10');
INSERT INTO `question` VALUES (7, 3, 8, 7, 4, '8-(3-7)*4');
INSERT INTO `question` VALUES (8, 9, 4, 5, 3, '(9+4-5)*3');
INSERT INTO `question` VALUES (9, 2, 3, 7, 6, '(2*7-6)*3');
INSERT INTO `question` VALUES (10, 5, 3, 6, 8, '6/((5-3)/8)');
INSERT INTO `question` VALUES (11, 10, 7, 9, 3, '(10+7-9)*3');
INSERT INTO `question` VALUES (12, 6, 5, 8, 4, '(4-6-5)*8');
INSERT INTO `question` VALUES (13, 5, 1, 9, 7, '(5-9)*(1-7)');
INSERT INTO `question` VALUES (14, 9, 4, 4, 8, '9*4-8-4');
INSERT INTO `question` VALUES (15, 8, 8, 5, 3, '8+8+3+5');
INSERT INTO `question` VALUES (16, 4, 4, 8, 2, '(4+4)*2+8');
INSERT INTO `question` VALUES (17, 6, 10, 2, 7, '(6+2)*(10-7)');
INSERT INTO `question` VALUES (18, 8, 1, 1, 3, '(8+1-1)*3');
INSERT INTO `question` VALUES (19, 4, 10, 5, 2, '4+10+2*5');
INSERT INTO `question` VALUES (20, 8, 7, 9, 10, '(8*9)/(10-7)');
INSERT INTO `question` VALUES (21, 7, 6, 9, 1, '(7+1)*(9-6)');
INSERT INTO `question` VALUES (22, 10, 7, 5, 9, '(10-7)*5+9');
INSERT INTO `question` VALUES (23, 4, 1, 10, 3, '(10-4)*(1+3)');
INSERT INTO `question` VALUES (24, 5, 7, 1, 6, '5*6+1-7');
INSERT INTO `question` VALUES (25, 5, 3, 6, 8, '6/((5-3)/8)');
INSERT INTO `question` VALUES (26, 1, 4, 4, 4, '(1+4)*4+4');
INSERT INTO `question` VALUES (27, 3, 3, 2, 7, '(3-2+7)*3');
INSERT INTO `question` VALUES (28, 6, 2, 5, 7, '6*2+7+5');
INSERT INTO `question` VALUES (29, 7, 2, 6, 1, '(7-2-1)*6');
INSERT INTO `question` VALUES (30, 6, 2, 2, 5, '(6+2)*(5-2)');
INSERT INTO `question` VALUES (31, 8, 5, 2, 7, '(5*2-7)*8');
INSERT INTO `question` VALUES (32, 8, 5, 10, 6, '(8-5)*10-6');
INSERT INTO `question` VALUES (33, 2, 10, 5, 7, '2+10+7+5');
INSERT INTO `question` VALUES (34, 3, 2, 5, 6, '(5-3+2)*6');
INSERT INTO `question` VALUES (35, 6, 2, 8, 10, '6*2*(10-8)');
INSERT INTO `question` VALUES (36, 9, 9, 7, 3, '(9/9+7)*3');
INSERT INTO `question` VALUES (37, 7, 4, 1, 8, '(7-4)*8*1');
INSERT INTO `question` VALUES (38, 2, 5, 9, 8, '2+5+8+9');
INSERT INTO `question` VALUES (39, 1, 8, 3, 9, '1*8*9/3');
INSERT INTO `question` VALUES (40, 5, 5, 6, 7, '5*5-7+6');
INSERT INTO `question` VALUES (41, 5, 3, 7, 8, '5*7-3-8');
INSERT INTO `question` VALUES (42, 6, 2, 3, 6, '(6+2)*(6-3)');
INSERT INTO `question` VALUES (43, 5, 3, 9, 1, '5*3*1+9');
INSERT INTO `question` VALUES (44, 5, 7, 10, 8, '(5+7)*(10-8)');
INSERT INTO `question` VALUES (45, 6, 4, 2, 2, '(6+4+2)*2');
INSERT INTO `question` VALUES (46, 7, 6, 10, 1, '7+6+1+10');
INSERT INTO `question` VALUES (47, 1, 2, 5, 10, '2*10-(1-5)');
INSERT INTO `question` VALUES (48, 5, 6, 2, 1, '(5+6+1)*2');
INSERT INTO `question` VALUES (49, 8, 8, 6, 10, '(8*6)/(10-8)');
INSERT INTO `question` VALUES (50, 7, 1, 9, 10, '(7-10)*(1-9)');
INSERT INTO `question` VALUES (51, 8, 10, 7, 10, '10-(8-10)*7');
INSERT INTO `question` VALUES (52, 5, 7, 10, 8, '(5+7)*(10-8)');
INSERT INTO `question` VALUES (53, 3, 3, 7, 4, '(7-3/3)*4');
INSERT INTO `question` VALUES (54, 10, 4, 2, 5, '10+4+5*2');
INSERT INTO `question` VALUES (55, 3, 7, 3, 7, '(3/7+3)*7');
INSERT INTO `question` VALUES (56, 4, 10, 2, 5, '4+10+5*2');
INSERT INTO `question` VALUES (57, 3, 4, 9, 4, '4*9-3*4');
INSERT INTO `question` VALUES (58, 7, 10, 2, 7, '(10/7+2)*7');
INSERT INTO `question` VALUES (59, 2, 7, 8, 2, '2*7+2+8');
INSERT INTO `question` VALUES (60, 4, 5, 5, 8, '(4-5/5)*8');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `integral` int(11) NULL DEFAULT NULL,
  `total` int(11) NULL DEFAULT NULL,
  `win` int(11) NULL DEFAULT NULL,
  `lose` int(11) NULL DEFAULT NULL,
  `tie` int(11) NULL DEFAULT NULL,
  `high_grade` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('001', 'a', 0, 0, 0, 0, 0, 325);
INSERT INTO `user` VALUES ('a', 'a', 126, 125, 46, 35, 55, 287);
INSERT INTO `user` VALUES ('beautiful', 'a', 0, 0, 0, 0, 0, 366);
INSERT INTO `user` VALUES ('dai', '123456', 0, 0, 0, 0, 0, 654);
INSERT INTO `user` VALUES ('dailinzhe', '1203', 265, 50, 30, 6, 17, 723);
INSERT INTO `user` VALUES ('dlz', 'a', 2, 1, 1, 0, 0, 554);
INSERT INTO `user` VALUES ('fine', 'a', 0, 0, 0, 0, 0, 156);
INSERT INTO `user` VALUES ('hhhh', 'a', 0, 0, 0, 0, 0, 257);
INSERT INTO `user` VALUES ('impossibly', 'a', 0, 0, 0, 0, 0, 415);
INSERT INTO `user` VALUES ('null', 'null', 0, 0, 0, 0, 0, 0);
INSERT INTO `user` VALUES ('solong', 'a', 125, 61, 36, 7, 18, 380);
INSERT INTO `user` VALUES ('test', 'a', 181, 87, 36, 14, 53, 279);
INSERT INTO `user` VALUES ('wmd', 'lilion81', 6, 4, 2, 0, 2, 0);
INSERT INTO `user` VALUES ('哈哈', 'a', -4, 2, 0, 2, 0, 999);
INSERT INTO `user` VALUES ('戴琳哲', 'a', 62, 17, 12, 5, 0, 290);

SET FOREIGN_KEY_CHECKS = 1;
