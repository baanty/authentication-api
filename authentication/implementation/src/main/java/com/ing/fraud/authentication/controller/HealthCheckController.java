package com.ing.fraud.authentication.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class HealthCheckController {

	@GetMapping(value = "/healthcheck")
	public ResponseEntity<String> responseGet(@RequestParam("format") String format) {

		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add("Content-Type", "application/json");
	    
	    
		if (!isParameterValid(format)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}

		if ("short".equals(format)) {
			//return "{ \"status\": \"OK\" }";
			return new ResponseEntity<String>( "{ \"status\": \"OK\" }", responseHeaders, HttpStatus.OK);
		} else {
			String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
			String dateString = simpleDateFormat.format(new Date());
			//return "{ \"currentTime\": \" " + dateString + "\", \"status\": \"OK\" }";
			
			return new ResponseEntity<String>("{\"currentTime\": \"" + dateString + "\", \"status\": \"OK\" }", responseHeaders, HttpStatus.OK);

		}

	}

	
	@RequestMapping(value = "/healthcheck", method = { RequestMethod.DELETE, RequestMethod.HEAD, RequestMethod.OPTIONS,
			RequestMethod.PATCH, RequestMethod.POST, RequestMethod.PUT, RequestMethod.TRACE })
	@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
	public void responseOthers() {
	}

	private boolean isParameterValid(String format) {

		if (format != null && ("short".equals(format) || "full".equals(format))) {
			return true;
		}
		return false;
	}
}
