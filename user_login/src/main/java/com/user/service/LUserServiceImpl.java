/**
 * 
 */
package com.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.dao.ILUserDAO;
import com.user.dto.LUsers;

/**
 * @author Francisco
 *
 */

@Service
public class LUserServiceImpl implements LUserService {
	@Autowired
	ILUserDAO iUserDAO;

	 /**Get: List all users */
	@Override
	public List<LUsers> listUsers() {

		return iUserDAO.findAll();
	}

	 /**Create: Save an user */
	@Override
	public LUsers saveUser(LUsers user) {

		return iUserDAO.save(user);
	}

	/**Get: Read info about an user */
	@Override
	public LUsers getUserById(Long id) {
		
		return iUserDAO.findById(id).get();
	}

	/**Update: an user */
	@Override
	public LUsers updateUser(LUsers user) {

		return iUserDAO.save(user);
	}

	/** Delete: an user*/
	@Override
	public void deleteUser(Long id) {
		iUserDAO.deleteById(id);
	}
}
