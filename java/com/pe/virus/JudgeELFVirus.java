package com.pe.virus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import com.pe.elfParser.elfFactory;
import com.pe.elfParser.Ielf;
import com.pe.UserException;
import com.pe.entity.virus.JudgedResult;
import com.pe.entity.virus.VirusResult;
import com.pe.elfParser.ListDirFiles;
import com.pe.util.FileManager;
import com.pe.util.Zip;

public class JudgeELFVirus
{
	private VirusResult result;
	private String testFile;
	private List<JudgedResult> data;
	private List<String> fileList;
	private Ielf elfFile;
	
	public JudgeELFVirus(VirusResult rst, String file)
	{
		this.result = rst;
		this.testFile = file;
		this.data = new ArrayList<JudgedResult>();
		validateFileName();
	}

	public List<JudgedResult> judge() throws Exception
	{
		fileList = new ArrayList<String>();
		if (testFile.endsWith(".zip")) 
			fileList = judgeMulti();
		else
			fileList = judgeSingle();
		
		if (fileList.isEmpty())
			throw new Exception("û��Ҫ�������ļ���");
		elfFactory factory = new elfFactory();
		JudgedResult rs;
		for (String file : fileList)
		{
			elfFile = factory.create(file);
			if (elfFile.startParse())
			{
				elfFile.createarff();
				rs = new JudgedResult();
			    rs.setResult(classifyInstance(result.getInstance(), result.getClassifier(), file));
			    File tmpfile = new File(file+".arff");
			   
                if(tmpfile.exists())
                {
                	tmpfile.delete();
                }
				int k = file.lastIndexOf("\\");
				if (k > 0)
			        rs.setName(file.substring(k + 1));
				else
					rs.setName(file);
			    data.add(rs);
			}
			else
			{
				rs = new JudgedResult();
				int k = file.lastIndexOf("\\");
				if (k > 0)
			        rs.setName(file.substring(k + 1));
				else
					rs.setName(file);
			    rs.setResult("notELF");
			    data.add(rs);
			}
		}
		return data;
	}
	
	/** ���ص��ļ��б� */
	private List<String> judgeSingle()
	{
		List<String> list = new ArrayList<String>();
		String parentPath = FileManager.getInstance().getPEHome().getAbsolutePath();
		list.add(parentPath + File.separator + testFile);
		return list;
	}

	/** ���ض��ļ��б� */
	private List<String> judgeMulti() throws Exception
	{
		List<String> list = new ArrayList<String>();
		File folder = unzipFolder();
		ListDirFiles.initalize();
		/** �г�ָ��Ŀ¼�������ļ� */
		for (File f : folder.listFiles())
		{
			list = ListDirFiles.listFile(f, true);
		}
		if (list.isEmpty())
		{
			throw new UserException("ָ��Ŀ¼��û�з���Ҫ����ļ�!");
		}
		return list;
	}
	// ɾ���ļ���
	// param folderPath �ļ�����������·��

	public static void delFolder(String folderPath) {
	   try {
		    delAllFile(folderPath); // ɾ����������������
		    String filePath = folderPath;
		    filePath = filePath.toString();
		    File myFilePath = new File(filePath);
		    myFilePath.delete(); // ɾ�����ļ���
	   } catch (Exception e) {
	        //e.printStackTrace();
	   }
	}

	// ɾ��ָ���ļ����������ļ�
	// param path �ļ�����������·��
	public static boolean delAllFile(String path) {
	   boolean flag = false;
	   File file = new File(path);
	   if (!file.exists()) {
		   return flag;
	   }
	   if (!file.isDirectory()) {
		   return flag;
	   }
	   String[] tempList = file.list();
	   File temp = null;
	   for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
			   temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				 delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				 delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
				 flag = true;
			}
	   }
	   return flag;
	}
	private File unzipFolder() throws Exception
	{
		/** �õ���ѹĿ¼����ȥ����.zip�� */
		String folderName = testFile.substring(0, testFile.length() - 4);
		String parentPath = FileManager.getInstance().getPEHome().getAbsolutePath();
		
		delFolder(parentPath + File.separator + folderName);
		
		/** �ڵ�ǰĿ¼��ѹ�ļ�  */
		Zip.unzip(parentPath + File.separator + testFile, parentPath + File.separator + folderName);
		
		File folder = new File(FileManager.getInstance().getPEHome(), folderName);
		return folder;
	}

	/** ֻȡ�ļ��������ie�Ͱ汾�İ�ȫ���� */
	private void validateFileName()
	{
		testFile = testFile.replace('\\', '/');
		int k = testFile.lastIndexOf("/");
		if (k > 0) testFile  = testFile.substring(k + 1);
	}
	

	public static String classifyInstance(Instances m_Training, Classifier m_Classifier, String filename) throws Exception
	{
		String FileName = filename + ".arff";
		Instances test = DataSource.read(FileName);
		test.setClassIndex(test.numAttributes() - 1);
		if (!m_Training.equalHeaders(test)) throw new IllegalArgumentException("Train data and test data are not compatible!");

		double pred = m_Classifier.classifyInstance(test.instance(0));
		String ClassType = test.classAttribute().value((int) pred);
		test = null;
		return ClassType;
	}
}
