package com.ing.fraud.authentication.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ing.fraud.authentication.service.AccountsService;
import com.ing.fraud.authentication.vo.AccountDetail;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AccountController {
	
	@Autowired
	@Qualifier("initialAccountDetail")
	Map<String, AccountDetail> initialAccountDetail;
	

	@Value("${jwt.secret:changeit}")
	private String secret;
	

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	@Autowired
	AccountsService accountsService;

	@RequestMapping("/create")
	public String createAccount(@RequestParam int accountNumber, 
							    @RequestParam String userName, 
							    @RequestParam String password) throws ClientProtocolException, IOException {
		StringBuilder fullClientMessage = new StringBuilder("account not created. ");
		boolean doesAccountExist = accountsService.doesAccountExist(accountNumber);
		
		if  ( doesAccountExist ) {
			
			if ( initialAccountDetail.containsKey(userName)) {
				return fullClientMessage.append("Choosen User Name already exists.").toString();
			}
			
			if ( !StringUtils.hasText(password) || password.length() < 6 ) {
				return fullClientMessage.append("Password Must have at least 6 characters.").toString();
			}
			
			AccountDetail newDetail = new AccountDetail();
			newDetail.setAccountLocked(false);
			newDetail.setAccountPassword(password);
			newDetail.setAccountUserName(userName);
			newDetail.setFailureAttempts(0);
			initialAccountDetail.put(userName, newDetail);
			return "account created";
		}
		return fullClientMessage.toString();
	}	
	
	@RequestMapping("/authenticate")
	public String authenticate(@RequestParam String userName, @RequestParam String password) {
		String authenticationFailureMessage = "Authentication Failure";
		Map<String, String> credentials = 
		initialAccountDetail
			.values()
			.stream()
			.collect(Collectors.toMap(AccountDetail::getAccountUserName, AccountDetail::getAccountPassword));
		
		String passwordRetreived = credentials.get(userName);
		
		if ( passwordRetreived != null ) {
			
			if ( passwordRetreived.equals(password) ) {


				Map<String, Object> claims = new HashMap<>();
				return Jwts.builder().setClaims(claims).setSubject(userName + " is authenticated ")
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
						.signWith(SignatureAlgorithm.HS512, secret).compact();
			}
			
			initialAccountDetail
				.values()
				.stream()
				.filter(anAccountDetail -> anAccountDetail.getAccountUserName().equals(userName))
				.forEach(choosenAccount -> {
					
						if (choosenAccount.getFailureAttempts() >= 3) {
							choosenAccount.setAccountLocked(true);
							choosenAccount.setFailureAttempts(0);
							choosenAccount.setAccountBlockTime(LocalDate.now());
							return;
						}
						choosenAccount.setFailureAttempts(choosenAccount.getFailureAttempts() + 1);
				});

		}
		
			
		return authenticationFailureMessage;
		
	}
}
