/**
 * 
 */
package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.user.dao.ILUserDAO;
import com.user.service.LUserServiceImpl;
import com.user.dto.AuthResponseDTO;
import com.user.dto.LUsers;
import com.user.security.JwtGenerator;

/**
 * @author Francisco
 *
 */

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class LUserController {

	@Autowired
	LUserServiceImpl userServiceImpl;

	private AuthenticationManager authenticationManager;

	private ILUserDAO iSuserDAO;

	private PasswordEncoder passwordEncoder;

	private JwtGenerator jwtGenerator;

	public LUserController(ILUserDAO iUsuarioDAO, PasswordEncoder bCryptPasswordEncoder, JwtGenerator jwtGenerator,
			AuthenticationManager authenticationManager) {
		this.iSuserDAO = iUsuarioDAO;
		this.passwordEncoder = bCryptPasswordEncoder;
		this.jwtGenerator = jwtGenerator;
		this.authenticationManager = authenticationManager;
	}

	/**
	 * This is responsible for the login, it creates an object with the token and
	 * returns it
	 */
	@PostMapping("/login")
	public String login(@RequestBody LUsers user) throws JsonProcessingException {
		UsernamePasswordAuthenticationToken test = new UsernamePasswordAuthenticationToken(user.getUsername(),
				user.getPassword());

		Authentication authentication = authenticationManager.authenticate(test);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		AuthResponseDTO authResponseDTO = new AuthResponseDTO(token);
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(authResponseDTO);
	}

	/** This creates a new user for later to log in into */
	@PostMapping("/register")
	public LUsers saveUsuario(@RequestBody LUsers user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		iSuserDAO.save(user);
		// return user;
		return user;
	}

	/** List all users */
	@GetMapping("/users")
	public List<LUsers> listUsers() {
		return userServiceImpl.listUsers();
	}

	/** Read info about a user by specifying the id */
	@GetMapping("/users/{id}")
	public LUsers getUserById(@PathVariable(name = "id") Long id) {
		return userServiceImpl.getUserById(id);
	}

	/** Modify a user, you need specify the data by a json body */
	@PutMapping("/users/{id}")
	public LUsers updateUser(@PathVariable(name = "id") Long id, @RequestBody LUsers user) {

		LUsers user_selected = new LUsers();

		user_selected = userServiceImpl.getUserById(id);
		user_selected.setUsername(user.getUsername());
		user_selected.setPassword(user.getPassword());
		user_selected.setRole(user.getRole());

		return userServiceImpl.updateUser(user_selected);
	}

	/** Delete a user */
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable(name = "id") Long id) {
		userServiceImpl.deleteUser(id);
	}

}
