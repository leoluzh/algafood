package com.lambdasys.food.api.exceptionhandler;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@SuppressWarnings("serial")
public class Field implements Serializable {

	private String name;
	private String userMessage;
	
}
