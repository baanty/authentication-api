package com.ing.fraud.authentication.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
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
	
	public boolean doesAccountExist(int accountNumber) throws ClientProtocolException, IOException {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			
			HttpGet request = new HttpGet(accountServiceUrl + String.valueOf(accountNumber));
			   CloseableHttpResponse response = httpClient.execute(request);
			   HttpEntity entity = response.getEntity();
			   
			   if (entity != null) {
			      return EntityUtils.toString(entity).startsWith("{\"iban\"");
			   }
		} catch (ParseException | IOException exception) {
			return false;
		}
		return false;
	}
}
