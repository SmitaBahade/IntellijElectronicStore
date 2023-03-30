package com.Icwd.electronic.store.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @Column(name = "create_Date",updatable = false)
    @CreationTimestamp
    private LocalDate create;
    @Column(name = "update_Date", insertable = false)
    @UpdateTimestamp
    private LocalDate update;
}
