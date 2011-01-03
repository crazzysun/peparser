begin;

\encoding UTF-8

create schema tmp authorization peuser;
grant all on schema tmp to public;
set search_path = tmp;

set session authorization peuser;

create function get_generated_id(text) returns int8 as $BODY$
	select currval($1 || '_id_seq');
$BODY$ language sql;

\i create/packed.sql
\i create/elf.sql
\i create/pe.sql
\i create/gdl.sql
\i create/indicator.sql
\i create/GBID.sql


reset session authorization;

drop schema public cascade;
alter schema tmp rename to public;
set search_path = public;

commit;
