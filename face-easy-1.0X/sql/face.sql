create database faceDB;

use faceDB;
-- 人脸表
create table face(
fid int primary key auto_increment COMMENT '主键',
face_base longtext COMMENT '图片数据 base_64编码',
create_time datetime COMMENT '插入时间',
vef_num int COMMENT '验证次数',
face_name varchar(100) COMMENT '人脸名称',
remark varchar(200) COMMENT '人脸备注',
face_status int COMMENT '人脸是否可用，(0==可用，1,不可用)',
update_extend1 varchar(300) COMMENT '扩展字段1',
update_extend2 varchar(300) COMMENT '扩展字段2',
update_extend3 varchar(300) COMMENT '扩展字段3'
);
-- 验证日志表
create table face_vef_log(
lid int primary key auto_increment COMMENT '主键',
vef_time datetime COMMENT '验证时间',
vef_code int COMMENT '返回code',
vef_msg varchar(200) COMMENT '返回的消息',
login_name varchar(100) COMMENT '验证人'
);

