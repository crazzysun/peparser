package com.pe.operation;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pe.UserException;
import com.pe.util.bean.BeanCreator;
import com.pe.util.JavaPackageExplorer;
import com.pe.util.SystemConfigure;

/**
 * ���������� ����ָ�����ʺš�����������ѯ���ݿ�õ�����Ĳ���Java�࣬��ִ�ж���
 * 
 * ���ܹ�Ŀ����Ϊ��ʵ������Ȩ�޿��ơ�
 * 
 * �ڱ��ܹ��У���ÿ������������������ÿ���������������ɸ������ʵ�֣��Զ�Ӧ��ͬȨ���µľ���������̡�
 * ���磬���г���Ա��������һ���������Թ���Ա��˵���ò������г�������Ա�������������Ӳ��Ź���Ա��
 * �����г������ŵ���Ա������������������ֱ�������Java��ʵ�֣�����ʵ����Operation�ӿ�(Ҳ�ɼ̳�AbstractOperation)��
 * 
 * �ڿͻ��ˣ�����ͨ��JavaScriptֱ�ӵ���:
 * 
 * // ���ȴ���һ����������, ָ��Ҫִ���ĸ�����
 * var operation = new Operation("ϵͳ.�鿴");
 * 
 * // ��������������ֵ  �������Ӧ��Operation��
 * operation.id = 2;
 * operation.offset = 4;
 * operation.limit = 10;
 * 
 * // ִ��, �ûص����������ؽ��
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
		String clazz = SystemConfigure.get("OperationManager");
		
		instance = (OperationManager) Class.forName(clazz).newInstance();
		instance.startup();
	}
	
	//====================================================
	
	/** ���п��ò���ʵ���� */
	public Map<String, Class<? extends Operation>> operations = new ConcurrentHashMap<String, Class<? extends Operation>>();
	
	/** ��֯���������в��� */
	protected OperationTree tree = new OperationTree();

	/** ��ʼ�������� */
	public void startup() throws Exception
	{
		log.info("��������������");
		
//		DaoManager dm = DaoManager.getInstance();
//		try
//		{
//			dm.begin();

			operationPackageName = SystemConfigure.get("OperationPackageName");
			
			loadOperationClasses();
			
//			dm.commit();
//		}
//		finally
//		{
//			dm.end();
//		}
	}

	/** ִ�в��� */
	public Object execute(String name, Map<String, ? extends Object> parameters) throws Exception
	{
		try
		{
			// װ�����ʵ����, ����ִ��
			Class<? extends Operation> type = getOperationClass(name);
			Operation operation = BeanCreator.create(type, parameters);

			operation.execute();

			return operation;
		}
		catch (UserException e)
		{
			String s = "ִ�в��� " + name + " ����: " + e.getMessage();
			log.info(s);
			throw e;
		}
		catch (Exception e)
		{
			String s = "ִ�в��� " + name + " ����: " + e.getMessage();
			log.error(s);
			throw new Exception(s, e);
		}
	}

	/** �������еĲ���ʵ���� */
	private void loadOperationClasses() throws Exception
	{
		JavaPackageExplorer explorer = new JavaPackageExplorer(operationPackageName);
		List<String> list = explorer.explore();
		for (String s : list)
		{
			register(s);
		}
	}
	
	/** ע��һ������ʵ���� */
	@SuppressWarnings("unchecked")
	private void register(String className)
	{
		try
		{
			Class<?> type = Class.forName(className);

			int m = type.getModifiers();
			if (Modifier.isAbstract(m) || Modifier.isInterface(m)) return;
			if (!Operation.class.isAssignableFrom(type)) return;

			// ���ݲ�������ʵ��������ע��
			String operationName = type.getCanonicalName();
			operationName = operationName.substring(operationPackageName.length() + 1);
			
			operations.put(operationName, (Class<? extends Operation>) type);
			tree.add(operationName);

			if (log.isDebugEnabled()) log.debug("�������: " + operationName);
		}
		catch (Exception e)
		{
			log.warn("�������ʧ��: " + className, e);
		}
	}

	/** ���ָ���ʻ�ָ��������ʵ���� */
	public Class<? extends Operation> getOperationClass(String name) throws Exception
	{
		Class<? extends Operation> clazz = operations.get(name);
		if (clazz == null) throw new UserException("����������: " + name);
		
		return clazz;
	}
	
	/** ��ȡ���в��������Ƽ��� */
	public Collection<String> getAllOperationNames()
	{
		return operations.keySet();
	}
}
