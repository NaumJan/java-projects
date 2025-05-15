package com.example.repository;

import com.example.model.Color;
import com.example.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface petRepository extends JpaRepository<Pet, Long> {
    Page<Pet> findByColor(Color color, Pageable pageable);
}
