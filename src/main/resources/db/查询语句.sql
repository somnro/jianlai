SELECT count(1) from fund;

-- 在MySQL中有个配置参数group_concat_max_len，它会限制使用group_concat返回的最大字符串长度，默认是1024
show variables like 'group_concat_max_len';
--修改，数据库重启会失效！！！
SET GLOBAL group_concat_max_len = 1024 * 100;
SET SESSION group_concat_max_len = 1024 * 100;

--行转列
SELECT group_concat(id) from fund;