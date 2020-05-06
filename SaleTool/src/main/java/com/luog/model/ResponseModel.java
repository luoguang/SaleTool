package com.luog.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseModel {
	private String code;
	
	private String message;
	
	private Object data;
}
