package com.pe.packed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import com.pe.UserException;
import com.pe.dll.petest.PEAnalyzerDll;
import com.pe.entity.packed.JudgedResult;
import com.pe.entity.packed.PackedResult;
import com.pe.parser.ListDirFiles;
import com.pe.util.FileManager;
import com.pe.util.Zip;

public class JudgePacked
{
	private PackedResult result;
	private String testFile;
	private List<JudgedResult> data;
	private List<String> fileList;
	
	public JudgePacked(PackedResult rst, String file)
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
		
		PEAnalyzerDll PE = PEAnalyzerDll.INSTANCE;
		JudgedResult rs;
		data = new ArrayList<JudgedResult>();
		for (String file : fileList)
		{
		    PE.LoadPEHeader(file);
		    
		    /**
		     * 改写的C++的函数，
		     * 用于对要判断的文件生成arff文件，
		     * 可实现动态指定arff的生成路径位置 
		     */
		    OutputPEFeature.output(PE, file);					
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
		
		list.add(FileManager.getInstance().getPEHome().getAbsolutePath() + File.separator + testFile);
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


	public static String classifyInstance(Instances m_Training, Classifier m_Classifier, String filename) throws Exception
	{
		String FileName = filename + ".arff";
		Instances test = DataSource.read(FileName);
		test.setClassIndex(test.numAttributes() - 1);
		if (!m_Training.equalHeaders(test)) throw new IllegalArgumentException("Train data and test data are not compatible!");

		double pred = m_Classifier.classifyInstance(test.instance(0));
		String ClassType = test.classAttribute().value((int) pred);

		return ClassType;
	}
}
