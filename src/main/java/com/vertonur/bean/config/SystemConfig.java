package com.vertonur.bean.config;

public class SystemConfig {
	private static SystemConfig config;
	
	private GlobalConfig globalConfig;

	public GlobalConfig getGlobalConfig() {
		return globalConfig;
	}

	public void setGlobalConfig(GlobalConfig globalConfig) {
		this.globalConfig = globalConfig;
	}

	public static SystemConfig getConfig() {
		return config;
	}

	public static void setConfig(SystemConfig config) {
		SystemConfig.config = config;
	}
}
