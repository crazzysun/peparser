#!/bin/sh

if [ ! -z "$(set | /bin/grep msys)" ]; then 
	PATH=/usr/bin:/bin:$PATH
fi

abc="$1"

echo
echo Create database schema
(
	cd sql
	psql -c "\i create.sql" $abc
)
echo OK!

echo
echo Import basic data
(
	cd sql
	psql -c "\i basic_data.sql" $abc
)
echo OK!

#=====================================================

x=:

if [ ! -z "$(set | grep msys)" ]; then x=";"; fi

cp=web/WEB-INF/classes
cp=$cp${x}web/WEB-INF/lib/esql.jar
cp=$cp${x}web/WEB-INF/lib/postgresql-8.3-604.jdbc3.jar
cp=$cp${x}web/WEB-INF/lib/commons-logging-1.0.4.jar
cp=$cp${x}web/WEB-INF/lib/dom4j-1.6.1.jar
cp=$cp${x}web/WEB-INF/lib/log4j-1.2.15.jar

echo "cp: $cp"

echo
echo OK!
