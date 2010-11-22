-- 判断加壳的训练集
create table t_packed_trainSet
(
	id									bigserial				not null,
	name								varchar(80)				not null,							-- 训练集名称（默认为训练样本文件名）
	classifier							varchar(80)				not null,							-- 分类器
	result								bytea					not null,							-- 训练集结果
	test_options						int						not null,							-- 选项类型
	option_value						int						not null,							-- 选项值
	create_time							varchar(255)			not null,							-- 训练集创建时间
	
	unique(name),
	primary key (id)
);
