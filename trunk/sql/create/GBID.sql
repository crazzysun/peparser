-- 模式集
create table t_rules_lib
(
	id								bigserial					not null,
	name							varchar(255)				not null,				-- 模式集名称
	all_lib_file_path				varchar(255)				not null,				-- 带衍生规则的模式集保存的路径
	ntv_lib_file_path				varchar(255)				not null,				-- 不带衍生规则的模式集保存的路径
	create_time						varchar(255)				not null,				-- 模式集创建时间
	
	primary key (id)
);