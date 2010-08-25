#!/bin/sh

if [ ! -z "$(set | /bin/grep msys)" ]; then 
	PATH=/usr/bin:/bin:$PATH
fi

#export PGCLIENTENCODING=GBK
#export LANG=zh_CN.GBK

abc="$1"

if [ -z "$abc" ]; then 
	abc=test;
else
	aaa=$(cat sql/companies.txt | grep -e "^$abc=")
	if [ -z "$aaa" ]; then
		echo argument error: $1
		exit 1
	fi 
fi

echo
echo Create database schema
(
	cd sql
	psql -c "\i create.sql" tcc
)
echo OK!

echo
echo Import basic data
(
	cd sql
	psql -c "\i basic_data.sql" tcc
)
echo OK!

echo
echo Import $abc data
(
	cd sql/$abc
	psql -c "\i data.sql" tcc
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
echo Update sequences
java -cp $cp data.UpdateSequences $abc
echo OK!

#echo
#echo Load privileges
#java -cp $cp data.LoadPrivileges $abc
#echo OK!

echo
echo Load menus
java -cp $cp data.LoadMenus $abc
echo OK!

echo
