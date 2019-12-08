package org.erp.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// TODO DateFormat 어떻게 convert 할까
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CommonDateEntity {

    @CreatedDate // Entitiy 생성시 자동으로 Date Setting
    @JsonFormat(pattern="yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @LastModifiedDate // Entity 수정시 자동으로 Date Setting
    @JsonFormat(pattern="yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedAt;
}