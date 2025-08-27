package com.example.demo.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
	
	private String suffix;

	public String getSuffix()
	{
		return suffix;
	}
	
	public void setSuffix(String sufix)
	{
		this.suffix = sufix;
	}
		
}
