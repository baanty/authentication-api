package com.ing.fraud.authentication.service;

import static org.junit.Assert.assertFalse;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


@RunWith(JUnit4.class)
public class AccountsServiceTest {
	
	
	@InjectMocks
	AccountsService service;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testDoesAccountExistTrueReturn() throws Exception {
		Field accountServiceUrl = Arrays.stream(AccountsService.class.getDeclaredFields())
				.filter(f -> f.getName().equals("accountServiceUrl"))
				.findFirst()
				.get();
		accountServiceUrl.setAccessible(true);
		accountServiceUrl.set(service, "http://localhsot:8080/mockendPoint");
		boolean doesAccountExist = service.doesAccountExist(1234);
		assertFalse(doesAccountExist);
	}

}
