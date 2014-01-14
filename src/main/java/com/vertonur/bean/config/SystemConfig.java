package com.vertonur.bean.config;

public class SystemConfig {
	private static SystemConfig config;
	
	private GlobalConfig globalConfig;
	private RuntimeConfig runtimeConfig;

	public GlobalConfig getGlobalConfig() {
		return globalConfig;
	}

	public void setGlobalConfig(GlobalConfig globalConfig) {
		this.globalConfig = globalConfig;
	}

	public RuntimeConfig getRuntimeConfig() {
		return runtimeConfig;
	}

	public void setRuntimeConfig(RuntimeConfig runtimeConfig) {
		this.runtimeConfig = runtimeConfig;
	}

	public static SystemConfig getConfig() {
		return config;
	}

	public static void setConfig(SystemConfig config) {
		SystemConfig.config = config;
	}

}
