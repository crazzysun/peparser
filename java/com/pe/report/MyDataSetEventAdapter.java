package com.pe.report;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.IUpdatableDataSetRow;
import org.eclipse.birt.report.engine.api.script.eventadapter.ScriptedDataSetEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IDataSetInstance;

public class MyDataSetEventAdapter extends ScriptedDataSetEventAdapter
{
	private ReportEventHandle handle;

	/** 处理context包含的外部参数，并存入类变量，以便open调用 */
	@Override
	public void beforeOpen(IDataSetInstance instance, IReportContext context)
	{
		handle = (ReportEventHandle) context.getParameterValue("handle");
	}

	@Override
	public void open(IDataSetInstance dataSet)
	{
		handle.open(dataSet);
	}

	@Override
	public boolean fetch(IDataSetInstance dataSet, IUpdatableDataSetRow row)
	{
		return handle.fetch(dataSet, row);
	}
}
