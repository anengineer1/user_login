/**
 * 
 */
package com.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class SUserController {

	@Autowired
	LUserServiceImpl userServiceImpl;
	
	private AuthenticationManager authenticationManager;

	private ILUserDAO iSuserDAO;

	private PasswordEncoder passwordEncoder;
	
	private JwtGenerator jwtGenerator;

	@Autowired
	public SUserController(ILUserDAO iUsuarioDAO, PasswordEncoder bCryptPasswordEncoder, JwtGenerator jwtGenerator,
			AuthenticationManager authenticationManager) {
		this.iSuserDAO = iUsuarioDAO;
		this.passwordEncoder = bCryptPasswordEncoder;
		this.jwtGenerator = jwtGenerator;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody LUsers user) {
		UsernamePasswordAuthenticationToken test = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		
		Authentication authentication = authenticationManager.authenticate(test);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		AuthResponseDTO authResponseDTO = new AuthResponseDTO(token);
		return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<LUsers> saveUsuario(@RequestBody LUsers user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		iSuserDAO.save(user);
		// return user;
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	/** Get: List all users */
	@GetMapping("/susers")
	public List<LUsers> listUsers() {
		return userServiceImpl.listUsers();
	}

	/** Get: Read info about an user */
	@GetMapping("/susers/{id}")
	public LUsers getUserById(@PathVariable(name = "id") Long id) {
		return userServiceImpl.getUserById(id);
	}

	/** Update: an user */
	@PutMapping("/susers/{id}")
	public LUsers updateUser(@PathVariable(name = "id") Long id, @RequestBody LUsers user) {

		LUsers user_selected = new LUsers();

		user_selected = userServiceImpl.getUserById(id);
		user_selected.setUsername(user.getUsername());
		user_selected.setPassword(user.getPassword());

		return userServiceImpl.updateUser(user_selected);
	}

	/** Delete: an user */
	@DeleteMapping("/susers/{id}")
	public void deleteUser(@PathVariable(name = "id") Long id) {
		userServiceImpl.deleteUser(id);
	}

}