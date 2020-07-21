CREATE TABLE `test`.`fund_all`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基金代码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基金名称',
  `dwjz` double(11, 5) NOT NULL COMMENT '单位净值',
  `ljjz` double(11, 5) NULL DEFAULT NULL COMMENT '累计净值',
  `rzdf` double(11, 5) NULL DEFAULT NULL COMMENT '日增长率',
  `rq` date NOT NULL COMMENT '日期',
  `create` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;