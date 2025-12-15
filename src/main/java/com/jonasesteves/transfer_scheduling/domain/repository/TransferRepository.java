package com.jonasesteves.transfer_scheduling.domain.repository;

import com.jonasesteves.transfer_scheduling.domain.entity.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID> {

    @Query("""
        SELECT t FROM Transfer t
        WHERE t.scheduledAt BETWEEN :begin AND :end
    """)
    Page<Transfer> findBySchedulingDate(@Param("begin") OffsetDateTime begin, @Param("end") OffsetDateTime end, Pageable pageable);
}
