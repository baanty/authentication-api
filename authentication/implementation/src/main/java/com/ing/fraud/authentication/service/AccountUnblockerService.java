package com.ing.fraud.authentication.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ing.fraud.authentication.vo.AccountDetail;

@Service
public class AccountUnblockerService {

	
	@Autowired
	@Qualifier("initialAccountDetail")
	Map<String, AccountDetail> initialAccountDetail;
	
	
	@Scheduled( fixedDelay = ( 60 * 60 * 1000 ) )
	public void unblockAccountsAfterOneDay() {
		
		LocalDate lastBlockTime = LocalDate.now().minusDays(1);
		
		initialAccountDetail
			.values()
			.stream()
			.filter(anAccount ->  ( anAccount.isAccountLocked() && 
			           anAccount.getAccountBlockTime().isBefore(lastBlockTime)) )
			.forEach(anAccount -> {
				anAccount.setAccountBlockTime(null);
				anAccount.setAccountLocked(false);
				anAccount.setFailureAttempts(0);
			});
	}
}
