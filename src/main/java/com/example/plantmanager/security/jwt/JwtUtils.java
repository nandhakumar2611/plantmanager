package com.example.plantmanager.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
* This JwtUtils class
* 
* <ul>
* <li>Generate a JWT from username, date, expiration, secret</li>
* <li>Get username from JWT</li>
* <li>Validate a JWT</li>
* </ul>
* 
* @author Nandha Kumar
* @version 1.0
* @since 14-03-2023
* 
*/

@Component
public class JwtUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
	
	private int jwtExpirationMs = 1200000;
	private String jwtSecret = "Nandha";
	
	public String generateJwtToken(Authentication authentication) {
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject((userDetails.getUsername()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS256, jwtSecret)
				.compact();
	}
	
	public boolean validateJwtToken(String authToken) {
		
		try {
			
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		}
		catch(SignatureException ex){
			LOGGER.error("INVALID JWT SIGNATURE: ", ex.getMessage());
		}
		catch (MalformedJwtException ex) {
			LOGGER.error("INVALID JWT TOKEN: ", ex.getMessage());
		}
		catch (ExpiredJwtException ex) {
			LOGGER.error("JWT TOKEN IS EXPIRED : ", ex.getMessage());
		}
		catch (UnsupportedJwtException ex) {
			LOGGER.error("JWT TOKEN IS UNSUPPORTED : ", ex.getMessage());
		}
		catch (IllegalArgumentException ex) {
			LOGGER.error("JWT CLAIMS STRING IS EMPTY : ", ex.getMessage());
		}
		return false;
	}

	public String getUserNameFromJwtToKen(String token)
	{
		return Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
