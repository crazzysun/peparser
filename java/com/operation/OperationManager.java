package com.operation;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.UserException;
import com.util.JavaPackageExplorer;
import com.util.bean.BeanCreator;

/**
 * 操作管理器 根据指定的帐号、操作名，查询数据库得到具体的操作Java类，并执行动作
 * 
 * 本架构目的是为了实现灵活的权限控制。
 * 
 * 在本架构中，给每个操作进行了命名，每个操作可以有若干个具体的实现，以对应不同权限下的具体操作过程。
 * 例如，“列出人员名单”是一个操作。对管理员来说，该操作是列出所有人员的名单，但对子部门管理员，
 * 则是列出本部门的人员名单。两个具体操作分别用两个Java类实现，但都实现了Operation接口(也可继承AbstractOperation)。
 * 
 * 在客户端，可以通过JavaScript直接调用:
 * 
 * // 首先创建一个操作对象, 指定要执行哪个操作
 * var operation = new Operation("系统.查看");
 * 
 * // 给操作对象设置值  请对照相应的Operation类
 * operation.id = 2;
 * operation.offset = 4;
 * operation.limit = 10;
 * 
 * // 执行, 用回调函数处理返回结果
 * var callback = function (result)
 * {
 *     ......
 * }
 * operation.execute(callback);
 * 
 */
public class OperationManager
{
	private static final Log log = LogFactory.getLog(OperationManager.class);

	private static String operationPackageName;
	
	private static OperationManager instance;

	public static OperationManager getInstance()
	{
		return instance;
	}
	
	public static void initialize() throws Exception
	{
		String clazz = "com.operation.OperationManager";
		
		instance = (OperationManager) Class.forName(clazz).newInstance();
		instance.startup();
	}
	
	//====================================================
	
	/** 所有可用操作实现类 */
	public Map<String, Class<? extends Operation>> operations = new ConcurrentHashMap<String, Class<? extends Operation>>();
	
	/** 组织成树的所有操作 */
	protected OperationTree tree = new OperationTree();

	/** 初始化管理器 */
	public void startup() throws Exception
	{
		log.info("启动操作管理器");
		
//		DaoManager dm = DaoManager.getInstance();
//		try
//		{
//			dm.begin();

			operationPackageName = "com.operation";
			
			loadOperationClasses();
			
//			dm.commit();
//		}
//		finally
//		{
//			dm.end();
//		}
	}

	/** 执行操作 */
	public Object execute(String name, Map<String, ? extends Object> parameters) throws Exception
	{
		try
		{
			// 装入具体实现类, 进行执行
			Class<? extends Operation> type = getOperationClass(name);
			Operation operation = BeanCreator.create(type, parameters);

			operation.execute();

			return operation;
		}
		catch (UserException e)
		{
			String s = "执行操作 " + name + " 错误: " + e.getMessage();
			log.info(s);
			throw e;
		}
		catch (Exception e)
		{
			String s = "执行操作 " + name + " 错误: " + e.getMessage();
			log.error(s);
			throw new Exception(s, e);
		}
	}

	/** 载入所有的操作实现类 */
	private void loadOperationClasses() throws Exception
	{
		JavaPackageExplorer explorer = new JavaPackageExplorer(operationPackageName);
		List<String> list = explorer.explore();
		for (String s : list)
		{
			register(s);
		}
	}
	
	/** 注册一个操作实现类 */
	@SuppressWarnings("unchecked")
	private void register(String className)
	{
		try
		{
			Class<?> type = Class.forName(className);

			int m = type.getModifiers();
			if (Modifier.isAbstract(m) || Modifier.isInterface(m)) return;
			if (!Operation.class.isAssignableFrom(type)) return;

			// 根据操作名和实现名进行注册
			String operationName = type.getCanonicalName();
			operationName = operationName.substring(operationPackageName.length() + 1);
			
			operations.put(operationName, (Class<? extends Operation>) type);
			tree.add(operationName);

			if (log.isDebugEnabled()) log.debug("载入操作: " + operationName);
		}
		catch (Exception e)
		{
			log.warn("载入操作失败: " + className, e);
		}
	}

	/** 获得指定帐户指定操作的实现类 */
	public Class<? extends Operation> getOperationClass(String name) throws Exception
	{
		Class<? extends Operation> clazz = operations.get(name);
		if (clazz == null) throw new UserException("操作不存在: " + name);
		
		return clazz;
	}
	
	/** 获取所有操作的名称集合 */
	public Collection<String> getAllOperationNames()
	{
		return operations.keySet();
	}
}
