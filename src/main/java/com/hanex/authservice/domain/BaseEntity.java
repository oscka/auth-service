package com.hanex.authservice.domain;

import com.hanex.authservice.domain.user.User;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class BaseEntity extends BaseTimeEntity {

    @CreatedBy
    private AggregateReference<User, @NotNull UUID> createdBy;

    @LastModifiedBy
    private AggregateReference<User, @NotNull UUID> updatedBy;
}
