//package com.dao;
//
//import java.lang.reflect.Modifier;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import zuojie.esql.Esql;
//import zuojie.esql.EsqlPgsqlImpl;
//
//import com.util.JavaPackageExplorer;
//
//public class DaoManager
//{
//	public final static int READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED;
//	public final static int SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE;
//
//	private static Log log = LogFactory.getLog(DaoManager.class);
//
//	private static Map<String, DaoManager> managers = new HashMap<String, DaoManager>();
//	
//	public static DaoManager getInstance(String name)
//	{
//		return managers.get(name);
//	}
//
//	public static DaoManager getInstance()
//	{
//		return managers.get("default");
//	}
//	
//	public static void initialize(DataSource source) throws Exception
//	{
//		// 本地默认数据源
//		DaoManager manager = new DaoManager();
//		manager.setDaoPackageName("com.dao");
//		manager.setDatabase(Class.forName("com.dao.PostgreSQL"));
//		manager.setDataSource(source);
//		manager.startup();
//		managers.put("default", manager);
//	}
//	
//	//====================================================================
//	
//	private String name;
//	private String daoPackageName;
//	private Class<?> database;			// Dao对象应该实现的类, 用于区别调入用于不同数据库的Dao
//	private DataSource dataSource;
//
//	private Esql esql;
//
//	public DaoManager()
//	{
//	}
//	
//	private Map<Class<?>, Object> daos = new HashMap<Class<?>, Object>();
//
//	/** 通过连接池数据源初始化 */
//	public void startup() throws Exception
//	{
//		managers.put(name, this);
//
//		log.info("启动DAO管理器");
//		
//		/** 数据源初始化的另外一种方式 
//		OracleConnectionPoolDataSource ds = new OracleConnectionPoolDataSource();
//		ds.setDriverType("thin");
//		ds.setNetworkProtocol("tcp");
//		ds.setServerName("localhost");
//		ds.setPortNumber(1521);
//		ds.setDatabaseName("tcc");
//		ds.setUser("tccdba");
//		ds.setPassword("tcc123");
//		*/
//		
//		// 声明一个ESQL对象
//		//esql = Esql.create("Oracle", dataSource);
//		
//		// 根据数据库类型和数据源创建esql对象
//		esql = new EsqlPgsqlImpl();
//		esql.setDataSource(dataSource);
//
//		// 设置数据存取对象
//		JavaPackageExplorer explorer = new JavaPackageExplorer(daoPackageName);
//		List<String> list = explorer.explore();
//		for (String s : list)
//		{
//			register(s);
//		}
//	}
//
//	/** 注册一个DAO对象 */
//	private void register(String name)
//	{
//		try
//		{
//			Class<?> type = Class.forName(name);
//
//			if (type.equals(BaseDao.class)) return;
//			if (!BaseDao.class.isAssignableFrom(type)) return;
//			if (!database.isAssignableFrom(type)) return;
//			//排除抽象类
//			if (Modifier.isAbstract(type.getModifiers())) return ;
//
//			if (log.isDebugEnabled()) log.debug("注册DAO类: " + name);
//
//			BaseDao dao = (BaseDao) type.newInstance();
//			dao.setEsql(esql);
//
//			Class<?>[] cs = type.getInterfaces();
//			for (Class<?> c : cs)
//			{
//				daos.put(c, dao);
//			}
//		}
//		catch (Exception e)
//		{
//			log.error("注册DAO类失败: " + name, e);
//		}
//	}
//
//	public <T> T getDao(Class<T> type)
//	{
//		Object dao = daos.get(type);
//		if (dao == null) throw new RuntimeException("指定的DAO不存在: " + type.getCanonicalName());
//		return type.cast(dao);
//	}
//
//	public Esql getEsql()
//	{
//		return esql;
//	}
//	
//	/** 开始事务 */
//	public void begin(int isolation) throws Exception
//	{
//		try
//		{
//			esql.begin(isolation);
//		}
//		catch (Exception e)
//		{
//			throw new Exception("开始事务错误", e);
//		}
//	}
//
//	public void begin() throws Exception
//	{
//		begin(DaoManager.READ_COMMITTED);
//	}
//
//	/** 提交当前事务 */
//	public void commit() throws Exception
//	{
//		try
//		{
//			esql.commit();
//		}
//		catch (Exception e)
//		{
//			throw new Exception("提交事务错误", e);
//		}
//	}
//
//	/** 结束当前事务。如果没有提交，就回滚 */
//	public void end()
//	{
//		try
//		{
//			esql.end();
//		}
//		catch (Exception e)
//		{
//		}
//	}
//
//	public void setTransactionIsolation(int isolation) throws Exception
//	{
//		try
//		{
//			esql.setIsolation(isolation);
//		}
//		catch (SQLException e)
//		{
//			throw new Exception("设置事务隔离度错误", e);
//		}
//	}
//
//	public void setDaoPackageName(String daoPackageName)
//	{
//		this.daoPackageName = daoPackageName;
//	}
//
//	public void setDatabase(Class<?> database)
//	{
//		this.database = database;
//	}
//
//	public void setDataSource(DataSource dataSource)
//	{
//		this.dataSource = dataSource;
//	}
//
//	public String getName()
//	{
//		return name;
//	}
//
//	public void setName(String name)
//	{
//		this.name = name;
//	}
//}
