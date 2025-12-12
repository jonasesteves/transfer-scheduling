package com.jonasesteves.transfer_scheduling.repository;

import com.jonasesteves.transfer_scheduling.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID> {
}
