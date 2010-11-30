package com.pe.util;
import java.util.List;
import java.util.Map;


public class ChatEntity
{
	private List<String> list;
	private Map<String, Integer> map1;
	private Map<String, Integer> map2;
	
	public List<String> getList()
	{
		return list;
	}
	public void setList(List<String> list)
	{
		this.list = list;
	}
	public Map<String, Integer> getMap1()
	{
		return map1;
	}
	public void setMap1(Map<String, Integer> map1)
	{
		this.map1 = map1;
	}
	public Map<String, Integer> getMap2()
	{
		return map2;
	}
	public void setMap2(Map<String, Integer> map2)
	{
		this.map2 = map2;
	}
}
