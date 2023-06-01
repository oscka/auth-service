package com.hanex.auth.user.domain;

import java.util.UUID;

public interface UserRepositoryCustom {

    void deleteById(UUID id);

    void delete(User entity);
}
