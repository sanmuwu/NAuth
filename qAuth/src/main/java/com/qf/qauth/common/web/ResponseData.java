package com.qf.qauth.common.web;

import java.util.LinkedHashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseData {
	
	public static final String ERRORS_KEY = "errors";
	
	private final String message;
	private final int code;
	private final LinkedHashMap<String, Object> data = new LinkedHashMap<String, Object>();
	
	public ResponseData putDataValue(String key, Object value) {
		data.put(key, value);
		return this;
	}
	
	private ResponseData(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static ResponseData ok() {
		return new ResponseData(200, "Ok");
	}
	
	public static ResponseData notFound() {
		return new ResponseData(404, "Not Found");
	}
	
	public static ResponseData badRequest() {
		return new ResponseData(400, "Bad Request");
	}
	
	public static ResponseData forbidden() {
		return new ResponseData(403, "Forbidden");
	}
	
	public static ResponseData unauthorized() {
		return new ResponseData(401, "unauthorized");
	}
	
	public static ResponseData serverInternalError() {
		return new ResponseData(500, "Server Internal Error");
	}
	
	public static ResponseData customerError() {
		return new ResponseData(1001, "Customer Error");
	}
}
