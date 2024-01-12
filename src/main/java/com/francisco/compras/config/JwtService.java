package com.francisco.compras.config;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "33cad586bf7462be20e7fd11d9c5cdc3b50a7ba33712940e4d8671130d2b242d";
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String getUserName(String token) {
		return getClaim(token, Claims::getSubject);
	}
	
	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaims(String token) {
		return Jwts
				.parser()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
		}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUserName(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
	}
	
	private boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
				}
	
	private Date getExpiration(String token) {
		return getClaim(token, Claims::getExpiration);
	}
}
