package com.pe.operation.GBID;

import java.io.File;

import com.pe.dao.DaoManager;
import com.pe.dao.GBID.GBIDDao;
import com.pe.dll.petest.GBIDDll;
import com.pe.entity.GBID.RulesLib;
import com.pe.operation.Operation;
import com.pe.operation.文件.AbstractFileOperation;
import com.pe.util.FileManager;
import com.pe.util.Util;
import com.pe.util.Zip;

public class 创建模式集 extends AbstractFileOperation implements Operation
{
	private String dataset;
	private String trainSetName;
	private String bounceFilePath[] = new String[3];

	public void execute() throws Exception
	{
		/** 创建训练集 */
		validateTrainFile();
		
		GBIDDll GBID = GBIDDll.INSTANCE;
		String allLibFilePath = (new File(FileManager.getInstance().getGBIDResultHome(), "AllRulesLib.lib")).getAbsolutePath();
		String ntvLibFilePath = (new File(FileManager.getInstance().getGBIDResultHome(), "NtvRulesLib.lib")).getAbsolutePath();
		GBID.GetAllRulesLib(bounceFilePath, allLibFilePath);
		GBID.GetNtvRulesLib(bounceFilePath, ntvLibFilePath);

		/** 保存模式集的相关属性 */
		RulesLib lib = new RulesLib();
		lib.setName(trainSetName);
		lib.setAllLibFilePath(allLibFilePath);
		lib.setNtvLibFilePath(ntvLibFilePath);
		lib.setCreateTime(Util.showNowTime());

		/** 结果加入数据库 */
		GBIDDao dao = DaoManager.getInstance().getDao(GBIDDao.class);
		// 数据库中没有同名的就添加, 否则先删除再添加
		boolean exist = dao.isExist(trainSetName);
		if (exist) dao.deleteRulesLibByName(trainSetName);
		dao.AddRuleslib(lib);
	}

	private void validateTrainFile() throws Exception
	{
		if (dataset == null || dataset.equals(""))
		{
			bounceFilePath[0] = "D:/work/peparser/data/GBIDFiles/int/norm/synth/bounce.int";
			bounceFilePath[1] = "D:/work/peparser/data/GBIDFiles/int/norm/synth/bounce-1.int";
			bounceFilePath[2] = "D:/work/peparser/data/GBIDFiles/int/norm/synth/bounce-2.int";
		}
		else
		{
			/** 只取文件名，解决ie低版本的安全问题 */
			dataset = dataset.replace('\\', '/');
			int k = dataset.lastIndexOf("/");
			if (k > 0) dataset = dataset.substring(k + 1);

			unzipFolder(dataset);
		}
	}
	
	private void unzipFolder(String zipPath) throws Exception
	{
		/** 得到解压目录名，去掉“.zip” */
		String folderName = zipPath.substring(0, zipPath.length() - 4);
		String parentPath = FileManager.getInstance().getPEHome().getAbsolutePath();
		
		/** 在当前目录解压文件  */
		Zip.unzip(parentPath + File.separator + zipPath, parentPath + File.separator + folderName);
		
		File folder = new File(FileManager.getInstance().getPEHome(), folderName);
		
		int i = 0;
		for (File f : folder.listFiles())
			bounceFilePath[i++] = f.getAbsolutePath(); 
	}

	public String getDataset()
	{
		return dataset;
	}

	public void setDataset(String dataset)
	{
		this.dataset = dataset;
	}

	public String getTrainSetName()
	{
		return trainSetName;
	}

	public void setTrainSetName(String trainSetName)
	{
		this.trainSetName = trainSetName;
	}
}
