package com.pe.dao;

import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import zuojie.esql.Esql;
import zuojie.esql.EsqlPgsqlImpl;

import com.pe.util.JavaPackageExplorer;

public class DaoManager
{
	public final static int READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED;
	public final static int SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE;

	private static Log log = LogFactory.getLog(DaoManager.class);

	private static Map<String, DaoManager> managers = new HashMap<String, DaoManager>();
	
	public static DaoManager getInstance(String name)
	{
		return managers.get(name);
	}

	public static DaoManager getInstance()
	{
		return managers.get("default");
	}
	
	public static void initialize(DataSource source) throws Exception
	{
		// ����Ĭ������Դ
		DaoManager manager = new DaoManager();
		manager.setDaoPackageName("com.pe.dao");
		manager.setDatabase(Class.forName("com.pe.dao.PostgreSQL"));
		manager.setDataSource(source);
		manager.startup();
		managers.put("default", manager);
	}
	
	//====================================================================
	
	private String name;
	private String daoPackageName;
	private Class<?> database;			// Dao����Ӧ��ʵ�ֵ���, ��������������ڲ�ͬ���ݿ��Dao
	private DataSource dataSource;

	private Esql esql;

	public DaoManager()
	{
	}
	
	private Map<Class<?>, Object> daos = new HashMap<Class<?>, Object>();

	/** ͨ�����ӳ�����Դ��ʼ�� */
	public void startup() throws Exception
	{
		managers.put(name, this);

		log.info("����DAO������");
		
		/** ����Դ��ʼ��������һ�ַ�ʽ 
		OracleConnectionPoolDataSource ds = new OracleConnectionPoolDataSource();
		ds.setDriverType("thin");
		ds.setNetworkProtocol("tcp");
		ds.setServerName("localhost");
		ds.setPortNumber(1521);
		ds.setDatabaseName("tcc");
		ds.setUser("tccdba");
		ds.setPassword("tcc123");
		*/
		
		// ����һ��ESQL����
		//esql = Esql.create("Oracle", dataSource);
		
		// �������ݿ����ͺ�����Դ����esql����
		esql = new EsqlPgsqlImpl();
		esql.setDataSource(dataSource);

		// �������ݴ�ȡ����
		JavaPackageExplorer explorer = new JavaPackageExplorer(daoPackageName);
		List<String> list = explorer.explore();
		for (String s : list)
		{
			register(s);
		}
	}

	/** ע��һ��DAO���� */
	private void register(String name)
	{
		try
		{
			Class<?> type = Class.forName(name);

			if (type.equals(BaseDao.class)) return;
			if (!BaseDao.class.isAssignableFrom(type)) return;
			if (!database.isAssignableFrom(type)) return;
			//�ų�������
			if (Modifier.isAbstract(type.getModifiers())) return ;

			if (log.isDebugEnabled()) log.debug("ע��DAO��: " + name);

			BaseDao dao = (BaseDao) type.newInstance();
			dao.setEsql(esql);

			Class<?>[] cs = type.getInterfaces();
			for (Class<?> c : cs)
			{
				daos.put(c, dao);
			}
		}
		catch (Exception e)
		{
			log.error("ע��DAO��ʧ��: " + name, e);
		}
	}

	public <T> T getDao(Class<T> type)
	{
		Object dao = daos.get(type);
		if (dao == null) throw new RuntimeException("ָ����DAO������: " + type.getCanonicalName());
		return type.cast(dao);
	}

	public Esql getEsql()
	{
		return esql;
	}
	
	/** ��ʼ���� */
	public void begin(int isolation) throws Exception
	{
		try
		{
			esql.begin(isolation);
		}
		catch (Exception e)
		{
			throw new Exception("��ʼ�������", e);
		}
	}

	public void begin() throws Exception
	{
		begin(DaoManager.READ_COMMITTED);
	}

	/** �ύ��ǰ���� */
	public void commit() throws Exception
	{
		try
		{
			esql.commit();
		}
		catch (Exception e)
		{
			throw new Exception("�ύ�������", e);
		}
	}

	/** ������ǰ�������û���ύ���ͻع� */
	public void end()
	{
		try
		{
			esql.end();
		}
		catch (Exception e)
		{
		}
	}

	public void setTransactionIsolation(int isolation) throws Exception
	{
		try
		{
			esql.setIsolation(isolation);
		}
		catch (SQLException e)
		{
			throw new Exception("�����������ȴ���", e);
		}
	}

	public void setDaoPackageName(String daoPackageName)
	{
		this.daoPackageName = daoPackageName;
	}

	public void setDatabase(Class<?> database)
	{
		this.database = database;
	}

	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
