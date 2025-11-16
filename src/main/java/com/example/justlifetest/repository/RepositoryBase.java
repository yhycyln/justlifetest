package com.example.justlifetest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("rawtypes")
@Transactional
@NoRepositoryBean
public interface RepositoryBase<T> extends JpaRepository<T, Long> {
}
