package com.example.algamoney.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("api-properties")
public class ApiProperty {

	private final Seguranca seguranca = new Seguranca();
	
	public Seguranca getSeguranca() {
		return seguranca;
	}

	public static class Seguranca {
		
		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}
		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}
		
	}
	
}
