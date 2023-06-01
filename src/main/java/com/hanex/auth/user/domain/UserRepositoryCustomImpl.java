package com.hanex.auth.user.domain;

import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	private final JdbcAggregateOperations jdbcAggregateOperations;

	public UserRepositoryCustomImpl(JdbcAggregateOperations jdbcAggregateOperations) {
		this.jdbcAggregateOperations = jdbcAggregateOperations;
	}


	@Override
	@Transactional
	public void deleteById(UUID id) {
		User user = this.jdbcAggregateOperations.findById(id, User.class);
		if (user == null) {
			throw new TransientDataAccessResourceException("User does not exist.id: " + id);
		}
		this.delete(user);
	}

	@Override
	@Transactional
	public void delete(User entity) {
		entity.delete();
		this.jdbcAggregateOperations.update(entity);
	}

}
