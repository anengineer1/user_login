/**
 * 
 */
package com.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.dto.LUsers;

/**
 * @author Elena, Alejandro, Francisco
 *
 */
public interface ILUserDAO extends JpaRepository<LUsers, Long> {
	LUsers findByUsername(Object username);
}
