package com.operation;

/**
 * 操作接口
 * 
 * 所有的具体操作都必须实现该接口
 * 
 * 具体实现也可以从 AbstractOperation 抽象类继承
 */
public interface Operation
{
	/** 执行操作 */
	public void execute() throws Exception;
}
