-- 指标
create table t_indicator
(
	id									bigserial				not null,
	name								varchar(80)				not null,							-- 指标名称
	comment								varchar(255)			not null,							-- 中英文解释
	
	primary key (id)
);