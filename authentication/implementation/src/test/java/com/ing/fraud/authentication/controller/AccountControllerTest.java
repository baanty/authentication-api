package com.ing.fraud.authentication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ing.fraud.authentication.vo.AccountDetail;


@RunWith(JUnit4.class)
public class AccountControllerTest {


	
	@Mock
	private Map<String, AccountDetail> initialAccountDetail;
	
	@InjectMocks
	private AccountController controller;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test() throws ClientProtocolException, IOException {
		String response = controller.createAccount(100, "kurt_cobain", "nirvana");
		assertEquals("account created", response);
	}

}
