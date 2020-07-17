CREATE TABLE `fund_info` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基金代码' ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '基金名称' ,
`archives`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '基金档案' ,
PRIMARY KEY (`id`),
UNIQUE INDEX `code1` (`code`) USING BTREE
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=13659
ROW_FORMAT=DYNAMIC
;

