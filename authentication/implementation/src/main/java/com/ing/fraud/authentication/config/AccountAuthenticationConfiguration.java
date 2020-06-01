package com.ing.fraud.authentication.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ing.fraud.authentication.vo.AccountDetail;

@Configuration
@EnableScheduling
public class AccountAuthenticationConfiguration {

	@Bean
	@Qualifier("initialAccountDetail")
	public Map<String, AccountDetail> loadInitialAccountDetail(){
		Map<String, AccountDetail> initalLoadedAccounts = new HashMap<String, AccountDetail>();
		return initalLoadedAccounts;
	}
	
}
