package com.kkkssh.community.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AutoCloseable.class)
@Getter
public class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)  //수정시에는 관여X
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(insertable = false)  //create시에는 관여X
    private LocalDateTime updatedTime;
}
