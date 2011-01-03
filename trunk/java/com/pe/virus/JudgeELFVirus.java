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
			throw new Exception("没有要分析的文件！");
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
	
	/** 加载单文件列表 */
	private List<String> judgeSingle()
	{
		List<String> list = new ArrayList<String>();
		String parentPath = FileManager.getInstance().getPEHome().getAbsolutePath();
		list.add(parentPath + File.separator + testFile);
		return list;
	}

	/** 加载多文件列表 */
	private List<String> judgeMulti() throws Exception
	{
		List<String> list = new ArrayList<String>();
		File folder = unzipFolder();
		ListDirFiles.initalize();
		/** 列出指定目录中所有文件 */
		for (File f : folder.listFiles())
		{
			list = ListDirFiles.listFile(f, true);
		}
		if (list.isEmpty())
		{
			throw new UserException("指定目录中没有符合要求的文件!");
		}
		return list;
	}
	// 删除文件夹
	// param folderPath 文件夹完整绝对路径

	public static void delFolder(String folderPath) {
	   try {
		    delAllFile(folderPath); // 删除完里面所有内容
		    String filePath = folderPath;
		    filePath = filePath.toString();
		    File myFilePath = new File(filePath);
		    myFilePath.delete(); // 删除空文件夹
	   } catch (Exception e) {
	        //e.printStackTrace();
	   }
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
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
				 delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				 delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				 flag = true;
			}
	   }
	   return flag;
	}
	private File unzipFolder() throws Exception
	{
		/** 得到解压目录名，去掉“.zip” */
		String folderName = testFile.substring(0, testFile.length() - 4);
		String parentPath = FileManager.getInstance().getPEHome().getAbsolutePath();
		
		delFolder(parentPath + File.separator + folderName);
		
		/** 在当前目录解压文件  */
		Zip.unzip(parentPath + File.separator + testFile, parentPath + File.separator + folderName);
		
		File folder = new File(FileManager.getInstance().getPEHome(), folderName);
		return folder;
	}

	/** 只取文件名，解决ie低版本的安全问题 */
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
