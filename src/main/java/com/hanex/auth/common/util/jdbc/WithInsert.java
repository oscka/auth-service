package com.hanex.auth.common.util.jdbc;

import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.transaction.annotation.Transactional;

public interface WithInsert<T> {

	JdbcAggregateOperations getJdbcAggregateOperation();

	@Transactional
	default T insert(T t){
		return this.getJdbcAggregateOperation().insert(t);
	}
}
