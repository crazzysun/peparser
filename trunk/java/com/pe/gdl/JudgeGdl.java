package com.pe.gdl;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import com.pe.UserException;
import com.pe.entity.gdl.JudgedResult;
import com.pe.entity.gdl.GdlResult;
import com.pe.parser.ListDirFiles;
import com.pe.util.FileManager;
import com.pe.util.Zip;

public class JudgeGdl
{
	private GdlResult result;
	private String testFile;
	private List<JudgedResult> data;
	private List<String> fileList;
	
	public JudgeGdl(GdlResult rst, String file)
	{
		this.result = rst;
		this.testFile = file;
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
		
		//PEAnalyzerDll PE = PEAnalyzerDll.INSTANCE;
		OperateGdl op=new OperateGdl();
		JudgedResult rs;
		data = new ArrayList<JudgedResult>();
		for (String file : fileList)
		{
			
			//System.out.println("准备分析文件。。。。。"+file);
			file=op.createArffRec (file);
			
			//System.out.println(file+"!!!!!!");
		 
		    rs = new JudgedResult();
		    rs.setName(file);
		    rs.setResult(classifyInstance(result.getInstance(), result.getClassifier(), file));
		    data.add(rs);
		}
		return data;
	}
	
	/** 加载单文件列表 */
	private List<String> judgeSingle()
	{
		List<String> list = new ArrayList<String>();
		File file = new File(FileManager.getInstance().getPEHome(), testFile);
		list.add(file.getAbsolutePath());
		return list;
	}

	/** 加载多文件列表 */
	private List<String> judgeMulti() throws Exception
	{
		List<String> list = new ArrayList<String>();
		File folder = unzipFolder();
		
		/** 列出指定目录中所有文件 */
		for (File f : folder.listFiles())
		{
			
			//System.out.println("file in zip is  "+f.getName());
			list = ListDirFiles.listFile(f, null, true);
		}
		if (list.isEmpty())
		{
			throw new UserException("指定目录中没有符合要求的文件!");
		}
		return list;
	}
	
	private File unzipFolder() throws Exception
	{
		/** 得到解压目录名，去掉“.zip” */
		String folderName = testFile.substring(0, testFile.length() - 4);
		String parentPath = FileManager.getInstance().getPEHome().getAbsolutePath();
		
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


	public static String classifyInstance(Instances m_Training, Classifier m_Classifier, String filename) throws Exception  //这里的filename是训练集还是PE文件
	{
		String FileName =filename + ".arff";
		//System.out.println("....."+FileName);
		//System.out.println("...@@@.."+m_Training.numAttributes());
		Instances test = DataSource.read(FileName);
		//System.out.println("...@@@.."+test.numAttributes());
		test.setClassIndex(test.numAttributes() - 1);
		if (!m_Training.equalHeaders(test)) throw new IllegalArgumentException("Train data and test data are not compatible!");

		double pred = m_Classifier.classifyInstance(test.instance(0));
		String ClassType = test.classAttribute().value((int) pred);

		return ClassType;
	}
}
