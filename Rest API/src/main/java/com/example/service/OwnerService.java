package com.example.service;

import com.example.DTO.ownerDto;
import com.example.model.Owner;
import com.example.repository.ownerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {
    private final ownerRepository ownerRepository;

    public OwnerService(ownerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public ownerDto getOwnerById(Long id) {
        Owner owner = ownerRepository.findById(id).orElseThrow(() -> new RuntimeException("Owner not found"));
        return mapToDTO(owner);
    }

    public List<ownerDto> getAllOwners() {
        return ownerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private ownerDto mapToDTO(Owner owner) {
        ownerDto dto = new ownerDto();
        dto.setId(owner.getId());
        dto.setName(owner.getName());
        dto.setBirthDate(owner.getBirthDate());
        return dto;
    }

    public ownerDto createOwner(ownerDto ownerDto) {
        Owner owner = new Owner();
        owner.setName(ownerDto.getName());
        owner.setBirthDate(ownerDto.getBirthDate());
        Owner savedOwner = ownerRepository.save(owner);
        return mapToDTO(savedOwner);
    }

    public ownerDto updateOwner(Long id, ownerDto ownerDto) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        owner.setName(ownerDto.getName());
        owner.setBirthDate(ownerDto.getBirthDate());
        Owner updatedOwner = ownerRepository.save(owner);
        return mapToDTO(updatedOwner);
    }

    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }
}
