package com.example.justlifetest.model.base;

import com.example.justlifetest.constants.BusinessConstants;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@Setter
@MappedSuperclass
@Where(
        clause = "deleted = false"
)
public abstract class BaseEntity {
    public static final String SOFT_DELETE_CLAUSE = "deleted = false";

    @Id
    private Long id;

    @CreationTimestamp
    private LocalDateTime createTime;

    @Nonnull
    private Long createdBy = 0L;

    @UpdateTimestamp
    private LocalDateTime lastUpdateTime;

    private Long lastUpdatedBy;

    @Nonnull
    private Boolean deleted = false;

    public void setDeleted(Boolean status) {
        deleted = status;
    }

    @Override
    public String toString() {
        return "Id:" + getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(createTime, that.createTime) && Objects.equals(createdBy, that.createdBy) && Objects.equals(lastUpdateTime, that.lastUpdateTime) && Objects.equals(lastUpdatedBy, that.lastUpdatedBy) && Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createTime, createdBy, lastUpdateTime, lastUpdatedBy, deleted);
    }

    @PrePersist
    public void prePersist() {
        this.createdBy = BusinessConstants.SYSTEM_USER;
        this.deleted = false;
        this.lastUpdateTime = null;
        this.lastUpdatedBy = null;
    }

}
