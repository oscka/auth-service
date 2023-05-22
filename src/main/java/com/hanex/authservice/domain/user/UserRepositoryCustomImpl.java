package com.hanex.authservice.domain.user;

import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	private final JdbcAggregateOperations jdbcAggregateOperations;

	public UserRepositoryCustomImpl(JdbcAggregateOperations jdbcAggregateOperations) {
		this.jdbcAggregateOperations = jdbcAggregateOperations;
	}

	@Transactional
	public void deleteById(UUID id) {
		User account = this.jdbcAggregateOperations.findById(id, User.class);
		if (account == null) {
			throw new TransientDataAccessResourceException("account does not exist.id: " + id);
		}

		this.delete(account);
	}

	@Transactional
	public void delete(User entity) {
		entity.delete();
		this.jdbcAggregateOperations.update(entity);
	}

}
