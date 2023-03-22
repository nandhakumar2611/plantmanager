package com.example.plantmanager.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plantmanager.dto.LoginRequest;
import com.example.plantmanager.dto.SignupRequest;
import com.example.plantmanager.model.Role;
import com.example.plantmanager.model.User;
import com.example.plantmanager.security.jwt.JwtUtils;
import com.example.plantmanager.service.RoleService;
import com.example.plantmanager.service.UserService;
import com.example.plantmanager.service.impl.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateuser(@RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String generateJwtToken = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();
	    List<String> roles = userDetailsImpl.getAuthorities().stream()
	            .map(item -> item.getAuthority())
	            .collect(Collectors.toList());
		return ResponseEntity
				.ok(new JwtResponse(generateJwtToken,userDetailsImpl.getUserId(),userDetailsImpl.getUsername(),userDetailsImpl.getEmail(),roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registeruser(@RequestBody SignupRequest signupRequest) {
		
		if(userService.existsByUsername(signupRequest.getUserName())) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : USERNAME IS ALREADY TAKEN !..."));
		}
		if(userService.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("ERROR : EMAIL IS ALREADY IN USE !..."));
		}
		User user = new User(signupRequest.getUserName(), encoder.encode(signupRequest.getPassword()), signupRequest.getEmail(), signupRequest.getContactNo());
		
		Set<String> userRoles = signupRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if(userRoles == null) {
			Role role = roleService.findByRole("USER")
					.orElseThrow(() -> new RuntimeException("ERROR : ROLE IS NOT FOUND."));
			roles.add(role);
		}
		else {
			userRoles.forEach(role -> {
				switch(role) {
				case "admin":
					Role adminRole =  roleService.findByRole("ADMIN")
						.orElseThrow(() -> new RuntimeException("ERROR : ROLE IS NOT FOUND."));
					roles.add(adminRole);
					break;
				case "manager":
					Role modRole =  roleService.findByRole("MANAGER")
						.orElseThrow(() -> new RuntimeException("ERROR : ROLE IS NOT FOUND."));
					roles.add(modRole);
					break;
				default:
					Role userRole =  roleService.findByRole("USER")
						.orElseThrow(() -> new RuntimeException("ERROR : ROLE IS NOT FOUND."));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userService.saveuser(user);
		
		return ResponseEntity.ok(new MessageResponse("USER REGISTERED SUCCESSFULLY!..."));
	}

}
