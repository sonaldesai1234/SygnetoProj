package com.sygneto.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomResponse {

	@Autowired
	SygnetoResponse response;

	public Object failure(int errorCode, String message) {
		response=new SygnetoResponse();
		response.setStatusCode(errorCode);
		response.setMessage(message);
		response.setStatus("failure");
		response.setData(null);

		if (errorCode == 400) {
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

		} else if (errorCode == 404) {
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return null;
	}

	public Object success(String message, Object data) {
		response=new SygnetoResponse();
		response.setStatusCode(200);
		response.setMessage(message);
		response.setStatus("success");
		response.setData(data);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
