package com.pe.parser;

/**
 * 定义C++头文件WINNT.H中的一些常量
 *
 * @author FangZhiyang
 */
public class WINNT_H
{
	//
	// Section characteristics.
	//
	public static final long IMAGE_SCN_CNT_CODE                 = 0x00000020;  // Section contains code.
	public static final long IMAGE_SCN_CNT_INITIALIZED_DATA     = 0x00000040;  // Section contains initialized data.
	public static final long IMAGE_SCN_CNT_UNINITIALIZED_DATA   = 0x00000080;  // Section contains uninitialized data.
	public static final long IMAGE_SCN_LNK_INFO                 = 0x00000200;  // Section contains comments or some other type of information.
	public static final long IMAGE_SCN_LNK_REMOVE               = 0x00000800;  // Section contents will not become part of image.
	public static final long IMAGE_SCN_LNK_COMDAT               = 0x00001000;  // Section contents comdat.
	public static final long IMAGE_SCN_MEM_DISCARDABLE          = 0x02000000;  // Section can be discarded.
	public static final long IMAGE_SCN_MEM_SHARED               = 0x10000000;  // Section is shareable.
	public static final long IMAGE_SCN_MEM_EXECUTE              = 0x20000000;  // Section is executable.
	public static final long IMAGE_SCN_MEM_READ                 = 0x40000000;  // Section is readable.
	public static final long IMAGE_SCN_MEM_WRITE                = 0x80000000;  // Section is writeable.
}
