package com.ing.fraud.authentication.vo;

import java.time.LocalDate;

/**
 * 
 *
 */
public class AccountDetail {

	
	private String accountUserName;
	
	private String accountPassword;
	
	private int failureAttempts;
	
	private LocalDate accountBlockTime;
	
	private boolean accountLocked;
	public String getAccountUserName() {
		return accountUserName;
	}

	public void setAccountUserName(String accountUserName) {
		this.accountUserName = accountUserName;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public int getFailureAttempts() {
		return failureAttempts;
	}

	public void setFailureAttempts(int failureAttempts) {
		this.failureAttempts = failureAttempts;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public LocalDate  getAccountBlockTime() {
		return accountBlockTime;
	}

	public void setAccountBlockTime(LocalDate accountBlockTime) {
		this.accountBlockTime = accountBlockTime;
	}
	
	
}
