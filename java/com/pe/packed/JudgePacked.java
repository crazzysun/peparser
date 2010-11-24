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
			throw new Exception("û��Ҫ�������ļ���");
		
		PEAnalyzerDll PE = PEAnalyzerDll.INSTANCE;
		JudgedResult rs;
		data = new ArrayList<JudgedResult>();
		for (String file : fileList)
		{
		    PE.LoadPEHeader(file);
		    
		    /**
		     * ��д��C++�ĺ�����
		     * ���ڶ�Ҫ�жϵ��ļ�����arff�ļ���
		     * ��ʵ�ֶ�ָ̬��arff������·��λ�� 
		     */
		    OutputPEFeature.output(PE, file);					
		    rs = new JudgedResult();
		    rs.setName(file);
		    rs.setResult(classifyInstance(result.getInstance(), result.getClassifier(), file));
		    data.add(rs);
		}
		return data;
	}
	
	/** ���ص��ļ��б� */
	private List<String> judgeSingle()
	{
		List<String> list = new ArrayList<String>();
		
		list.add(FileManager.getInstance().getPEHome().getAbsolutePath() + File.separator + testFile);
		return list;
	}

	/** ���ض��ļ��б� */
	private List<String> judgeMulti() throws Exception
	{
		List<String> list = new ArrayList<String>();
		File folder = unzipFolder();
		
		/** �г�ָ��Ŀ¼�������ļ� */
		for (File f : folder.listFiles())
		{
			list = ListDirFiles.listFile(f, null, true);
		}
		if (list.isEmpty())
		{
			throw new UserException("ָ��Ŀ¼��û�з���Ҫ����ļ�!");
		}
		return list;
	}
	
	private File unzipFolder() throws Exception
	{
		/** �õ���ѹĿ¼����ȥ����.zip�� */
		String folderName = testFile.substring(0, testFile.length() - 4);
		String parentPath = FileManager.getInstance().getPEHome().getAbsolutePath();
		
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

		return ClassType;
	}
}
