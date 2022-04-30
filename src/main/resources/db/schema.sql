DROP TABLE IF EXISTS T_FILE;
DROP TABLE IF EXISTS t_file;


CREATE TABLE t_file
(
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  create_name VARCHAR(30) NULL DEFAULT NULL COMMENT '创建人姓名',
  from_ip VARCHAR(30) NULL DEFAULT NULL COMMENT '创建人ip',
  file_name VARCHAR(30) NULL DEFAULT NULL COMMENT '文件名',
  file_path VARCHAR(100) NULL DEFAULT NULL COMMENT '文件路径',
  create_time VARCHAR(30) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (id)
);