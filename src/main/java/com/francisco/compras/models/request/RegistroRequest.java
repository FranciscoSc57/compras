package com.francisco.compras.models.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroRequest {

	private String fisrtName;
	private String lastName;
	private String email;
	private String password;
}
