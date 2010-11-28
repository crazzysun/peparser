package com.pe.report;

import org.eclipse.birt.report.engine.api.script.IUpdatableDataSetRow;
import org.eclipse.birt.report.engine.api.script.instance.IDataSetInstance;

public interface ReportEventHandle
{
	public void open(IDataSetInstance dataSet);

	public boolean fetch(IDataSetInstance dataSet, IUpdatableDataSetRow row);
}
