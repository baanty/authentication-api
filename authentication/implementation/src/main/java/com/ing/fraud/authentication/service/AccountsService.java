package com.ing.fraud.authentication.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class AccountsService {

	@Value("${account.service.url}")
	String accountServiceUrl;
	
	public boolean doesAccountExist(Integer accountNumber) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			
			HttpGet request = new HttpGet(accountServiceUrl + String.valueOf(accountNumber));
			   CloseableHttpResponse response = httpClient.execute(request);
			   HttpEntity entity = response.getEntity();
			   
			   if (entity != null) {
			      return EntityUtils.toString(entity).startsWith("{\"iban\"");
			   }
		} catch (Exception exception) {
			return false;
		}
		return false;
	}
}
