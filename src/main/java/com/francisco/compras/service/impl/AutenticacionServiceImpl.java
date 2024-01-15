package com.francisco.compras.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.francisco.compras.config.JwtService;
import com.francisco.compras.entity.User;
import com.francisco.compras.models.request.AutenticacionRequest;
import com.francisco.compras.models.request.RegistroRequest;
import com.francisco.compras.models.response.AuthResponse;
import com.francisco.compras.repository.UserRepository;
import com.francisco.compras.service.AutenticacionService;
import com.francisco.compras.utils.Role;

@Service
public class AutenticacionServiceImpl implements AutenticacionService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public AuthResponse registrar(RegistroRequest registroRequest) {
		User user = User.builder()
				.firstName(registroRequest.getFisrtName())
				.lastName(registroRequest.getLastName())
				.email(registroRequest.getEmail())
				.password(passwordEncoder.encode(registroRequest.getPassword()))
				.role(Role.USER)
				.build();
		userRepository.save(user);
		String jwtToken = jwtService.generateToken(user);
		return AuthResponse.builder().token(jwtToken).build();
	}

	@Override
	public AuthResponse autenticar(AutenticacionRequest autenticacionRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						autenticacionRequest.getEmail(), 
						autenticacionRequest.getPassword()));
		
		User user = userRepository.findUserByEmail(autenticacionRequest.getEmail()).orElseThrow(null);
		String jwtToken = jwtService.generateToken(user);
		return AuthResponse.builder().token(jwtToken).build();
	}

	
}
