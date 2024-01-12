package com.francisco.compras.service;

import com.francisco.compras.models.AutenticacionRequest;
import com.francisco.compras.models.AuthResponse;
import com.francisco.compras.models.RegistroRequest;

public interface AutenticacionService {

	AuthResponse registrar(RegistroRequest registroRequest);
	AuthResponse autenticar(AutenticacionRequest autenticacionRequest);
}
