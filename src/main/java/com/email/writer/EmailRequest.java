package com.email.writer;

import lombok.Data;

@Data    //to generate getters setters and constructors 
public class EmailRequest {
	private String emailContent;
	private String tone;
}
