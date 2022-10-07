-- liquibase formatted sql

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for base_customs
-- ----------------------------
DROP TABLE IF EXISTS `base_customs`;
CREATE TABLE `base_customs_dd`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关区编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关区名称',
  `short_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '关区简称',
  `is_enabled` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用  0 禁用 1 启用',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `editor` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `edit_time` datetime(0) NOT NULL COMMENT '修改时间',
  `deleted` tinyint(1) NOT NULL COMMENT '是否删除(0:否,1:是)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '关区表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
