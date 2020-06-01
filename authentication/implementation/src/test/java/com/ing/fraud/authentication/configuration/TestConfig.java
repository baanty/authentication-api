package com.ing.fraud.authentication.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ing.fraud.authentication.vo.AccountDetail;

@Configuration
public class TestConfig {
	
	@Bean
	@Qualifier("initialAccountDetail")
	public Map<String, AccountDetail> loadInitialAccountDetail(){
		Map<String, AccountDetail> initalLoadedAccounts = new HashMap<String, AccountDetail>();
		return initalLoadedAccounts;
	}
}
