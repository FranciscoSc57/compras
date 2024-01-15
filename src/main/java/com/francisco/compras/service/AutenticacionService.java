package com.francisco.compras.service;

import com.francisco.compras.models.request.AutenticacionRequest;
import com.francisco.compras.models.request.RegistroRequest;
import com.francisco.compras.models.response.AuthResponse;

public interface AutenticacionService {

	AuthResponse registrar(RegistroRequest registroRequest);
	AuthResponse autenticar(AutenticacionRequest autenticacionRequest);
}
