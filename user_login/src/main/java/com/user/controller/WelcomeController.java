/**
 * 
 */
package com.user.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Francisco
 *
 */

@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class WelcomeController {

	public WelcomeController() {

	}

	/**
	 * Prints a welcome message
	 */
	@GetMapping("/")
	public String login() {

		return "<h1>Login & Register server<h1>";
	}
}