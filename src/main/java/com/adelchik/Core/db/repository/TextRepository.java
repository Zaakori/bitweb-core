package com.adelchik.Core.db.repository;

import com.adelchik.Core.db.entities.TextEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TextRepository extends JpaRepository<TextEntity, Long> {
}
