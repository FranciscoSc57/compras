package com.francisco.compras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.francisco.compras.models.AutenticacionRequest;
import com.francisco.compras.models.AuthResponse;
import com.francisco.compras.models.RegistroRequest;
import com.francisco.compras.service.AutenticacionService;

@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {
	
	@Autowired
	private AutenticacionService authService;

	@PostMapping("/registrar")
	public ResponseEntity<AuthResponse> registrar(@RequestBody RegistroRequest request){
		return ResponseEntity.ok(authService.registrar(request));
	}
	
	@GetMapping("/autenticar")
	public ResponseEntity<AuthResponse> autenticar(@RequestBody AutenticacionRequest request){
		return ResponseEntity.ok(authService.autenticar(request));
	}
	
}
