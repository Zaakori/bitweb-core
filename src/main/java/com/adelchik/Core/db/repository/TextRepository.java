package com.adelchik.Core.db.repository;

import com.adelchik.Core.db.entities.TextEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface TextRepository extends JpaRepository<TextEntity, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE text SET status = ?1 WHERE id = ?2", nativeQuery = true)
    void updateStatus(String status, String entityId);

    @Transactional
    @Query(value = "SELECT * FROM text WHERE id = ?1", nativeQuery = true)
    TextEntity findByStringId(String id);
}
