package com.example.repository;

import com.example.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ownerRepository extends JpaRepository<Owner, Long> {
}
