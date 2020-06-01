package com.ing.fraud.authentication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ing.fraud.authentication.service.AccountsService;
import com.ing.fraud.authentication.vo.AccountDetail;


@RunWith(JUnit4.class)
public class AccountControllerTest {

	@Mock
	private AccountsService service;
	
	@Mock
	private Map<String, AccountDetail> initialAccountDetail;
	
	@InjectMocks
	private AccountController controller;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void noAccountCreatedBecauseOfFalseAccountCheck() throws ClientProtocolException, IOException {
		
		String response = controller.createAccount(100, "kurt_cobain", "nirvana");
		assertEquals("account not created.", response);
	}
	
	@Test
	public void accountCreated() throws ClientProtocolException, IOException {
		
		when(initialAccountDetail.get(any())).thenReturn(new AccountDetail());
		when(service.doesAccountExist(any())).thenReturn(true);
		
		
		String response = controller.createAccount(100, "kurt_cobain", "nirvana");
		assertEquals("account created", response);
	}
	
	@Test
	public void noAccountCreatedBecauseOfShortpassword() throws ClientProtocolException, IOException {
		when(initialAccountDetail.get(any())).thenReturn(new AccountDetail());
		when(service.doesAccountExist(any())).thenReturn(true);
		
		String response = controller.createAccount(100, "kurt_cobain", "n");
		assertEquals("account not created.Password Must have at least 6 characters.", response);
	}
	
	@Test
	public void noAccountCreatedBecauseOfUserNameExists() throws ClientProtocolException, IOException {
		when(initialAccountDetail.containsKey("kurt_cobain")).thenReturn(true);
		when(service.doesAccountExist(any())).thenReturn(true);
		
		String response = controller.createAccount(100, "kurt_cobain", "n");
		assertEquals("account not created.Choosen User Name already exists.", response);
	}
	
	@Test
	public void authenticateFailure() throws ClientProtocolException, IOException {
		when(initialAccountDetail.containsKey("kurt_cobain")).thenReturn(true);
		when(service.doesAccountExist(any())).thenReturn(true);
		String authenticationMessage = controller.authenticate("kurt_cobain", "nirvana");
		assertEquals("Authentication Failure", authenticationMessage);
	}
	
	@Test
	public void authenticateSuccess() throws ClientProtocolException, IOException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		when(initialAccountDetail.containsKey("kurt_cobain")).thenReturn(true);
		when(service.doesAccountExist(any())).thenReturn(true);
		Map<String, AccountDetail> initialAccountDetail = new HashMap<String, AccountDetail>();
		AccountDetail detail = new AccountDetail();
		detail.setAccountUserName("kurt_cobain");
		detail.setAccountPassword("nirvana");
		initialAccountDetail.put("kurt_cobain", detail);
		Field field = Arrays.stream(AccountController.class.getDeclaredFields())
							.filter(f -> f.getName().equals("initialAccountDetail"))
							.findFirst()
							.get();
		field.setAccessible(true);
		field.set(controller, initialAccountDetail);
		
		
		Field secretField = Arrays.stream(AccountController.class.getDeclaredFields())
				.filter(f -> f.getName().equals("secret"))
				.findFirst()
				.get();
		secretField.setAccessible(true);
		secretField.set(controller, "changeit");
		String authenticationMessage = controller.authenticate("kurt_cobain", "nirvana");
		String expectedJwt = "eyJhbGciOiJIUzUxMiJ9";
		assertEquals(expectedJwt, authenticationMessage.split("\\.")[0]);
	}
}
