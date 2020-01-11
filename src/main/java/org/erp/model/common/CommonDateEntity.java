package org.erp.model.common;

import lombok.Getter;
import org.erp.model.user.UserEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonDateEntity {

    @CreatedDate // Entitiy 생성시 자동으로 Date Setting
    private LocalDateTime createdDT;

    @LastModifiedDate // Entity 수정시 자동으로 Date Setting
    private LocalDateTime modifiedDT;
}