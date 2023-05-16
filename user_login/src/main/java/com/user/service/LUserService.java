/**
 * 
 */
package com.user.service;

import java.util.List;

import com.user.dto.LUsers;

/**
 * @author Francisco
 *
 */
public interface LUserService {

	public List<LUsers> listUsers(); /**Get: List all users */
	
	public LUsers saveUser(LUsers user); /**Create: Save an user */

	public LUsers getUserById(Long id); /**Get: Read info about an user */

	public LUsers updateUser(LUsers user);/**Update: an user */

	public void deleteUser(Long id);/** Delete: an user*/
}

