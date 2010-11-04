package com.pe.entity.parser;

import java.util.Map;

import com.pe.entity.Bean;

/**
 * 段头部信息
 * @author FangZhiyang
 *
 */
public class SectionHeader implements Bean
{
	private static final long serialVersionUID = 1L;

	private String name;
	private Map<String, String> value;

	public Map<String, String> getValue() {
		return value;
	}

	public void setValue(Map<String, String> value) {
		this.value = value;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
