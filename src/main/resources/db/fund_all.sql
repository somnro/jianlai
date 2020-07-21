CREATE TABLE `fund_all` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`name`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`dwjz`  double(11,5) NOT NULL ,
`ljjz`  double(11,5) NULL DEFAULT NULL ,
`rzdf`  double(11,5) NULL DEFAULT NULL ,
`rq`  date NOT NULL ,
`create`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1
ROW_FORMAT=DYNAMIC
;

