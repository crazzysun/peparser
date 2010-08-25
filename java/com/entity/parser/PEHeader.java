package com.entity.parser;

import java.util.Map;

import com.entity.Bean;

public class PEHeader implements Bean
{
	private static final long serialVersionUID = 4716602509900068674L;
	
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
